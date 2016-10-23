package pmoreira.kikayon.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.List;

import pmoreira.kikayon.BuildConfig;
import pmoreira.kikayon.R;
import pmoreira.kikayon.utils.Constants;
import pmoreira.kikayon.utils.FirebaseUtils;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getName();
    private static final int RC_SIGN_IN = 9001;
    private static final String VALID_DOMAIN = "@ciandt.com";

    private GoogleApiClient googleApiClient;

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;

    private boolean logged;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setUp();

        OptionalPendingResult<GoogleSignInResult> optionalResult = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if (optionalResult.isDone()) {
            handleSignInResult(optionalResult.get());
        }

        SignInButton googleSignInButton = (SignInButton) findViewById(R.id.google_sign_in_button);
        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                signIn();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(final GoogleSignInResult result) {

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() == null) {
            Toast.makeText(this, "No internet connection available!", Toast.LENGTH_SHORT).show();
        } else if (result.isSuccess()) {
            if (isValid(result)) {
                firebaseAuthWithGoogle(result.getSignInAccount());
            } else {
                Toast.makeText(this, "Please provide a valid ciandt mail", Toast.LENGTH_LONG).show();
                signOut();
            }
        } else {
            Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValid(final GoogleSignInResult result) {

        if (result.getSignInAccount() == null) {
            return false;
        }

        String domain = extractDomain(result.getSignInAccount().getEmail());

        return VALID_DOMAIN.equals(domain);
//        return true;
    }

    //TODO EXTRACT
    public static String extractDomain(final String email) {
        return email.substring(email.indexOf("@"));
    }

    public static String extractLogin(final String email) {
        return email.substring(0, email.indexOf("@"));
    }

    public static String encodeUrl(final String url) {
        String encodedUrl;
        try {
            encodedUrl = URLEncoder.encode(String.valueOf(url), "utf-8");
        } catch (UnsupportedEncodingException e) {
            encodedUrl = "";
        }
        return encodedUrl;
    }

    public static final String decodeUrl(final String url) {
        String decodedUrl = "";
        if (url != null) {
            try {
                decodedUrl = URLDecoder.decode(url, "utf-8");
            } catch (UnsupportedEncodingException e) {
            }
        }
        return decodedUrl;
    }

    private void setUp() {
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(BuildConfig.FIREBASE_TOKEN_ID)
                .requestEmail()
                .requestProfile()
                .build();

        googleApiClient = new GoogleApiClient
                .Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull final ConnectionResult connectionResult) {
                        //TODO
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();

        googleApiClient.connect();

        auth = FirebaseAuth.getInstance();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(FirebaseAuth firebaseAuth) {
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                if (currentUser != null && !logged) {
                    logged = true;

                    FirebaseUtils.getInstance().getReference(Constants.FIREBASE_LOCATION_IMAGES)
                            .child(extractLogin(currentUser.getEmail()))
                            .setValue(encodeUrl(String.valueOf(currentUser.getPhotoUrl())));

                     FirebaseUtils.getInstance().getReference(Constants.FIREBASE_LOCATION_LOGINS)
                            .addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    List<String> logins = (List<String>) dataSnapshot.getValue();

                                    SharedPreferences.Editor editor = getSharedPreferences(Constants.SHARED_PREFERENCES_DEFAULT, Context.MODE_PRIVATE).edit();
                                    editor.putStringSet(Constants.SHARED_PREFERENCES_KEY_LOGINS, new HashSet<String>(logins));
                                    editor.commit();
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        };

    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signOut() {
        auth.signOut();
        Auth.GoogleSignInApi
                .signOut(googleApiClient)
                .setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(final Status status) {
                        //TODO
                    }
                });
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}

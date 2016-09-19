package pmoreira.kikayon.view.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;

import pmoreira.kikayon.R;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getName();
    private static final int RC_SIGN_IN = 9001;
    private static final String VALID_DOMAIN = "@ciandt.com";

    public static final String EXTRA_PROFILE = "EXTRA_PROFILE";

    private GoogleApiClient googleApiClient;

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
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
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
        } else if (result != null) {
            if (isValid(result)) {
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra(LoginActivity.EXTRA_PROFILE, result.getSignInAccount());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Please provide a valid ciandt mail", Toast.LENGTH_LONG).show();
                Auth.GoogleSignInApi.signOut(googleApiClient);
            }
        } else {
            Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValid(final GoogleSignInResult result) {

        if (result.getSignInAccount() == null) {
            return false;
        }

        String email = result.getSignInAccount().getEmail();
        String domain = email.substring(email.indexOf("@"));

        return VALID_DOMAIN.equals(domain);
    }

    private void setUp() {
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .build();

        googleApiClient = new GoogleApiClient
                .Builder(this)
                .enableAutoManage(this, new onConnectionFailedListener())
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();

        googleApiClient.connect();
    }
    
    private class onConnectionFailedListener implements GoogleApiClient.OnConnectionFailedListener {
        @Override
        public void onConnectionFailed(final ConnectionResult connectionResult) {
            // TODO: 17/09/16  
        }
    }
}

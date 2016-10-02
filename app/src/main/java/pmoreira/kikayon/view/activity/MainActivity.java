package pmoreira.kikayon.view.activity;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import pmoreira.kikayon.R;
import pmoreira.kikayon.utils.FragmentUtils;
import pmoreira.kikayon.view.fragment.DrawerFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        // getSupportActionBar().setDisplayShowHomeEnabled(true);

        setUpDrawer(toolbar);
        FragmentUtils.selectFragment(getSupportFragmentManager(), FragmentUtils.MAIN);
    }

    private void setUpDrawer(final Toolbar toolbar) {
        DrawerFragment drawer = (DrawerFragment) getSupportFragmentManager().findFragmentById(R.id.drawer_fragment);
        drawer.setUp((DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

//        GoogleSignInAccount result = getIntent().getParcelableExtra(LoginActivity.EXTRA_PROFILE);
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            TextView name = (TextView) findViewById(R.id.profile_name);
            name.setText(currentUser.getDisplayName());

            TextView email = (TextView) findViewById(R.id.profile_email);
            email.setText(currentUser.getEmail());

            ImageView profilePicture = (ImageView) findViewById(R.id.profile_picture);
            Picasso.with(this)
                    .load(currentUser.getPhotoUrl())
                    .transform(new CropCircleTransformation())
                    .into(profilePicture);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        if (item.getItemId() == R.id.action_settings) {
            Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
            return true;
        } else if (item.getItemId() == android.R.id.home) {
            Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

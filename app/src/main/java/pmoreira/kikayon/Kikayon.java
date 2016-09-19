package pmoreira.kikayon;

import android.app.Application;

import com.firebase.client.Firebase;

public class Kikayon extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }

}

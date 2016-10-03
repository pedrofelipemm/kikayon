package pmoreira.kikayon.utils;

import com.google.firebase.database.FirebaseDatabase;

public class FirebaseUtils {

    private static FirebaseDatabase database;

    private FirebaseUtils() {
    }

    public static FirebaseDatabase getInstance() {
        if (database == null) {
            database = FirebaseDatabase.getInstance();
            database.setPersistenceEnabled(true);
        }
        return database;
    }

}

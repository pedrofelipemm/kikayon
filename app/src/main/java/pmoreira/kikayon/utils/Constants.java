package pmoreira.kikayon.utils;

import pmoreira.kikayon.BuildConfig;

public class Constants {

    public static final String FIREBASE_BASE_URL = BuildConfig.UNIQUE_FIREBASE_ROOT_URL;

    public static final String FIREBASE_LOCATION_RECORDS = "records";
    public static final String FIREBASE_LOCATION_LOGINS = "logins";
    public static final String FIREBASE_LOCATION_LOGS = "logs";
    public static final String FIREBASE_LOCATION_IMAGES = "images";

    public static final String FIREBASE_LOCATION_SETTINGS = "settings";
    public static final String FIREBASE_LOCATION_SETTINGS_VALIDATE_EMAIL = "settings_validate_email";

    public static final String SHARED_PREFERENCES_DEFAULT = "defaultSharedPreferences";
    public static final String SHARED_PREFERENCES_KEY_LOGINS = "logins";
}

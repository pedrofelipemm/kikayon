package pmoreira.kikayon.utils;

import java.io.File;

import pmoreira.kikayon.BuildConfig;

public class Constants {

    public static final String FIREBASE_RECORDS_LOCATION = "records";

    public static final String FIREBASE_BASE_URL = BuildConfig.UNIQUE_FIREBASE_ROOT_URL;
    public static final String FIREBASE_RECORDS_URL = FIREBASE_BASE_URL + File.separator + FIREBASE_RECORDS_LOCATION;

}

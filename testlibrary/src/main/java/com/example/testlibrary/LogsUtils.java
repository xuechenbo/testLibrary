package com.example.testlibrary;

import android.util.Log;

public class LogsUtils {
    private final static String LOG_TAG_STRING = "LOGE:::";
    private final static String LOG_TAG_STRING1 = "LOGD:::";

    public static void e(String msg) {
        try {
            Log.e(LOG_TAG_STRING, " : " + msg);
        } catch (Throwable t) {

        }
    }

    public static void d(String msg) {
        try {
            Log.d(LOG_TAG_STRING1, " : " + msg);
        } catch (Throwable t) {

        }
    }
}

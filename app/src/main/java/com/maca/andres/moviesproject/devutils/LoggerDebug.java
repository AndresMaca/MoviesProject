package com.maca.andres.moviesproject.devutils;

import android.util.Log;

public final class LoggerDebug {
    private final static String TAG = LoggerDebug.class.getSimpleName();
    private final static String MODE_DEBUG = "DEBUG";


    public static void print(String className, String message) {
        Log.d(TAG + "_" + MODE_DEBUG + "_" + " from: " + className, message);
    }

    public static void printMessageTrace(String message, String className, String mode){
        Log.d(TAG + "_" + mode + "_" + " from: " + className, message);

    }

}
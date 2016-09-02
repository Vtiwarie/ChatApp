package com.apppartner.androidprogrammertest.classes;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Vishaan on 9/1/2016.
 */
public class Util {
    public static void printTrace(String tag, Exception e) {
        Log.e(tag, e.getMessage());
        e.printStackTrace();
    }

    public static void log(String tag, String message) {
        Log.d(tag, message);
    }

    public static void toast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}

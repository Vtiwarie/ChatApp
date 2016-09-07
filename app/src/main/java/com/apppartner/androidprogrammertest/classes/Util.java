package com.apppartner.androidprogrammertest.classes;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

    public static void setUpToolbar(AppCompatActivity activity, int toolbarId, String title) {
        Toolbar toolbar = (Toolbar) activity.findViewById(toolbarId);
        activity.setSupportActionBar(toolbar);

        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle(title);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}

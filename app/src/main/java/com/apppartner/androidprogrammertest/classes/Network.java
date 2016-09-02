package com.apppartner.androidprogrammertest.classes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Vishaan on 9/1/2016.
 */
public class Network {

    private static final String LOG_TAG = Network.class.getSimpleName();

    public static Drawable getImageFromNetwork(Context context, String urlString) {
        HttpURLConnection urlConnection;
        InputStream is = null;
        Bitmap bitmap = null;

        try {
            URL url = new URL(urlString);
            Util.log(LOG_TAG, url.toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            is = urlConnection.getInputStream();
            Util.log(LOG_TAG, is.toString());

            bitmap = BitmapFactory.decodeStream(is);
            RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), bitmap);
            roundedBitmapDrawable.setCircular(true);
            return roundedBitmapDrawable;

        } catch (IOException exception) {
            Util.printTrace(LOG_TAG, exception);
        } finally {
            try {
                is.close();
            } catch (IOException ioexception) {
                Util.printTrace(LOG_TAG, ioexception);
            }
        }
        return null;
    }
}

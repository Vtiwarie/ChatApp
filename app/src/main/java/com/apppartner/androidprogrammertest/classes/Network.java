package com.apppartner.androidprogrammertest.classes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Base64;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
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

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            is = urlConnection.getInputStream();

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

    public static AsyncClient.Response logInToNetwork(AsyncClient.Request request) {
        HttpURLConnection urlConnection;
        InputStream is = null;
        InputStreamReader inputStreamReader;
        BufferedReader bufferedReader;
        String line;
        StringBuilder stringBuilder;
        int responseCode;

        try {
            URL url = new URL(request.url);
            stringBuilder = new StringBuilder();

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(request.method);
            final String username = "SuperBoise";
            final String password = "qwerty";
            urlConnection.setInstanceFollowRedirects(true);
            OutputStream os = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(String.format("username=%s&password=%s", username, password));
            writer.flush();
            writer.close();
            os.close();
            urlConnection.connect();

            responseCode = urlConnection.getResponseCode();

            is = urlConnection.getInputStream();
            inputStreamReader = new InputStreamReader(is);
            bufferedReader = new BufferedReader(inputStreamReader);

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append("\n");
            }

            return new AsyncClient.Response(stringBuilder.toString(), responseCode);
        } catch (IOException exception) {
            Util.printTrace(LOG_TAG, exception);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException ioexception) {
                Util.printTrace(LOG_TAG, ioexception);
            }
        }
        return null;
    }
}

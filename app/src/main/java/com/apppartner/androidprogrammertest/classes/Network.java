package com.apppartner.androidprogrammertest.classes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import com.apppartner.androidprogrammertest.classes.Login.LoginRequest;
import com.apppartner.androidprogrammertest.classes.Login.LoginResponse;

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

    public static LoginResponse logInToNetwork(LoginRequest loginRequest) {
        HttpURLConnection urlConnection;
        InputStream is = null;
        InputStreamReader inputStreamReader;
        BufferedReader bufferedReader;
        String line;
        StringBuilder stringBuilder;

        try {
            long connectionEnd = System.currentTimeMillis();
            URL url = new URL(loginRequest.url);
            stringBuilder = new StringBuilder();

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(loginRequest.method);
            final String username = loginRequest.getLoginCredentials().username;
            final String password = loginRequest.getLoginCredentials().password;
            urlConnection.setInstanceFollowRedirects(true);
            OutputStream os = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(String.format("username=%s&password=%s", username, password));
            writer.flush();
            writer.close();
            os.close();
            urlConnection.connect();

            is = urlConnection.getInputStream();
            inputStreamReader = new InputStreamReader(is);
            bufferedReader = new BufferedReader(inputStreamReader);

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append("\n");
            }
            connectionEnd = System.currentTimeMillis() - connectionEnd;

            return new LoginResponse(LoginResponse.parseFromJSON(stringBuilder.toString()), String.valueOf(connectionEnd));
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

package com.apppartner.androidprogrammertest.classes;

import com.apppartner.androidprogrammertest.classes.AsyncClient.Request;
import com.apppartner.androidprogrammertest.classes.AsyncClient.Response;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Vishaan on 9/4/2016.
 */
public class Login {
    private static final String LOG_TAG = Login.class.getSimpleName();

    public static class LoginCredentials {

        public String username;
        public String password;

        public LoginCredentials(String theUsername, String thePassword) {
            username = theUsername;
            password = thePassword;
        }
    }

    public static class LoginRequest extends Request {

        private LoginCredentials loginCredentials;

        public LoginRequest(String theUrl, String theMethod, LoginCredentials theLoginCredentials) {
            super(theUrl, theMethod);
            loginCredentials = theLoginCredentials;
        }

        public LoginCredentials getLoginCredentials() {
            return loginCredentials;
        }
    }

    public static class LoginResponse extends Response{

        public String code;
        public String message;
        private boolean isValidLogin = false;

        public LoginResponse(JSONObject jsonObject, String theConnectionTime) {
            super(theConnectionTime);
            code = "";
            message = "";

            if (jsonObject != null) {
                try {
                    code = jsonObject.getString("code");
                    message = jsonObject.getString("message");
                    isValidLogin = (code.equals("Success"));
                } catch (JSONException e) {
                    Util.printTrace(LOG_TAG, e);
                }
            }
        }

        public boolean isValidLogin() {
            return isValidLogin;
        }

        public static JSONObject parseFromJSON(String jsonString) {

            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                return jsonObject;
            } catch (Exception e) {
                Util.printTrace(LOG_TAG, e);
            }

            return null;
        }
    }
}

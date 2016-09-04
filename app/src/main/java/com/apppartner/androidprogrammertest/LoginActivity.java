package com.apppartner.androidprogrammertest;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;

import com.apppartner.androidprogrammertest.classes.AsyncClient;
import com.apppartner.androidprogrammertest.classes.AsyncClient.*;
import com.apppartner.androidprogrammertest.classes.Network;
import com.apppartner.androidprogrammertest.classes.Util;
import com.apppartner.androidprogrammertest.classes.Login.*;


public class LoginActivity extends ActionBarActivity {

    private static final String LOG_TAG = LoginActivity.class.getSimpleName();
    private static final String TITLE = "Login";
    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private AsyncClient mAsynClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Util.setUpToolbar(this, R.id.toolbar, TITLE);
        mUsernameEditText = (EditText) findViewById(R.id.username);
        mPasswordEditText = (EditText) findViewById(R.id.password);
        initializeAsyncClient();
    }

    private void initializeAsyncClient() {
        mAsynClient = new AsyncClient() {
            @Override
            public Response handleRequest(Request request) {
                return Network.logInToNetwork((LoginRequest)request);
            }

            @Override
            //handle the response of the network request
            public void handleResponse(Response response) {
                Util.log(LOG_TAG, "connection time: " + response.connectionTime + " milliseconds");
                showLoginDialog((LoginResponse)response);
            }
        };
    }

    private void showLoginDialog(final LoginResponse loginResponse) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final String dialogBody =
                "Code: " + loginResponse.code + "\n" +
                "Message: " + loginResponse.message + "\n" +
                "Connection time: " + loginResponse.connectionTime + " milliseconds";

        AlertDialog dialog = builder.setMessage(dialogBody)
                .setTitle("Login")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(loginResponse.isValidLogin()) {
                            startActivity(MainActivity.getMainActivityIntent(LoginActivity.this));
                        }
                    }
                })
                .create();
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        startActivity(MainActivity.getMainActivityIntent(this));
    }

    public void handleClick(View view) {
        switch (view.getId()) {
            case R.id.loginButton:
                try {
                    processUserInput();
                } catch (Exception e) {
                    Util.toast(this, e.getMessage());
                }
                break;
        }
    }

    private void processUserInput() throws  Exception {
        final String username = mUsernameEditText.getText().toString();
        final String password = mPasswordEditText.getText().toString();

        if(username.equals("") || password.equals("")) {
            throw new Exception("Field(s) cannot be empty");
        }

        makeRequest(new LoginCredentials(username, password));
    }

    private void makeRequest(LoginCredentials loginCredentials) {
        final String appPartnerUrl = getResources().getString(R.string.app_partner_request_url);
        mAsynClient.makeRequest(new LoginRequest(appPartnerUrl, Request.POST, loginCredentials));
    }

}

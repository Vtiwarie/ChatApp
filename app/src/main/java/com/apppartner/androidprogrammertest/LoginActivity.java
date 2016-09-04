package com.apppartner.androidprogrammertest;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;

import com.apppartner.androidprogrammertest.classes.AsyncClient;
import com.apppartner.androidprogrammertest.classes.Network;
import com.apppartner.androidprogrammertest.classes.Util;

public class LoginActivity extends ActionBarActivity {

    private static final String LOG_TAG = LoginActivity.class.getSimpleName();
    private EditText mUsername;
    private EditText mPassword;
    private AsyncClient mAsynClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Util.setUpToolbar(this, R.id.toolbar, "Login");
        mUsername = (EditText) findViewById(R.id.username);
        mPassword = (EditText) findViewById(R.id.password);
        initializeAsyncClient();
    }

    private void initializeAsyncClient() {
        mAsynClient = new AsyncClient() {
            @Override
            public Response handleRequest(Request request) {
                return Network.logInToNetwork(request);
            }

            @Override
            //handle the response of the network request
            public void handleResponse(AsyncClient.Response response) {
                Util.log(LOG_TAG, response.responseBody);
                Util.log(LOG_TAG, "connection time: " + response.connecitonTime + " milliseconds");
            }
        };
    }

    private void makeRequest() {
        final String appPartnerUrl = getResources().getString(R.string.app_partner_request_url);
        mAsynClient.makeRequest(new AsyncClient.Request(appPartnerUrl, AsyncClient.Request.POST));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void handleClick(View view) {
        switch (view.getId()) {
            case R.id.loginButton:
                makeRequest();
        }
    }
}

package com.apppartner.androidprogrammertest;

import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.apppartner.androidprogrammertest.classes.AsyncClient;
import com.apppartner.androidprogrammertest.classes.AsyncClient.Request;
import com.apppartner.androidprogrammertest.classes.Login.LoginCredentials;
import com.apppartner.androidprogrammertest.classes.Login.LoginRequest;
import com.apppartner.androidprogrammertest.classes.Login.LoginResponse;
import com.apppartner.androidprogrammertest.classes.Network;
import com.apppartner.androidprogrammertest.classes.Util;


public class LoginActivity extends AppCompatActivity {

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
        mUsernameEditText.setTypeface(Typeface.createFromAsset(getAssets(), "Jelloween - Machinato.ttf"));

        mPasswordEditText = (EditText) findViewById(R.id.password);
        mPasswordEditText.setTypeface(Typeface.createFromAsset(getAssets(), "Jelloween - Machinato.ttf"));

        initializeAsyncClient();
    }

    /**
     * Initialize the async client
     */
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

    /**
     * Show login dialog
     *
     * @param loginResponse object
     */
    private void showLoginDialog(final LoginResponse loginResponse) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final String dialogBody =
                "Code: " + loginResponse.code + "\n\n" +
                "Message: " + loginResponse.message + "\n\n" +
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

    /**
     * Handle click events for this activity
     *
     * @param view
     */
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

    /**
     * Process user input
     *
     * @throws Exception
     */
    private void processUserInput() throws  Exception {
        if( ! Network.checkNetwork(this)) {
            Util.toast(this, Network.STATUS_NO_NETWORK);
            return;
        }

        final String username = mUsernameEditText.getText().toString();
        final String password = mPasswordEditText.getText().toString();

        if(username.equals("") || password.equals("")) {
            throw new Exception("Field(s) cannot be empty");
        }

        makeLoginRequest(new LoginCredentials(username, password));
    }

    /**
     * Make request to async client to login
     *
     * @param loginCredentials object
     */
    private void makeLoginRequest(LoginCredentials loginCredentials) {
        final String appPartnerUrl = getResources().getString(R.string.app_partner_request_url);
        mAsynClient.makeRequest(new LoginRequest(appPartnerUrl, Request.POST, loginCredentials));
    }

}

package com.apppartner.androidprogrammertest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    private TextView mHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHeader = (TextView) findViewById(R.id.textView);
        //set font
        mHeader.setTypeface(Typeface.createFromAsset(getAssets(), "Jelloween - Machinato Bold.ttf"));
    }

    public static Intent getMainActivityIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    public void onLoginButtonClicked(View v)
    {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void onChatButtonClicked(View v)
    {
        Intent intent = new Intent(this, ChatActivity.class);
        startActivity(intent);
    }

    public void onAnimationTestButtonClicked(View v)
    {
        Intent intent = new Intent(this, AnimationActivity.class);
        startActivity(intent);
    }
}

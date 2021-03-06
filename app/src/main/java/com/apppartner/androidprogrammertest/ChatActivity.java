package com.apppartner.androidprogrammertest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.apppartner.androidprogrammertest.adapters.ChatsArrayAdapter;
import com.apppartner.androidprogrammertest.classes.Network;
import com.apppartner.androidprogrammertest.classes.Util;
import com.apppartner.androidprogrammertest.models.ChatData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class ChatActivity extends AppCompatActivity {
    private static final String LOG_TAG = "ActionBarActivity";
    private static final String TITLE = "Chat";
    private ArrayList<ChatData> chatDataArrayList;
    private ChatsArrayAdapter chatsArrayAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        listView = (ListView) findViewById(R.id.listView);
        Util.setUpToolbar(this, R.id.toolbar, TITLE);
        chatDataArrayList = new ArrayList<ChatData>();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if( ! Network.checkNetwork(this)) {
            Util.toast(this, Network.STATUS_NO_NETWORK);
        }
        update();
    }

    /**
     * update function, if ever data set changes. Data won't change in
     * this particular app, however, it is good practice to add
     * an update function in case the model data does change.
     */
    private void update() {
        try {
            String chatFileData = loadChatFile();
            JSONObject jsonData = new JSONObject(chatFileData);
            JSONArray jsonArray = jsonData.getJSONArray("data");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ChatData chatData = new ChatData(jsonObject);
                chatDataArrayList.add(chatData);
            }
        } catch (Exception e) {
            Log.w(LOG_TAG, e);
        }

        //update the adapter or create one if null
        if(chatsArrayAdapter == null) {
            chatsArrayAdapter = new ChatsArrayAdapter(this, chatDataArrayList);
            listView.setAdapter(chatsArrayAdapter);
        } else {
            chatsArrayAdapter.notifyDataSetChanged();
        }
    }

    /**
     * Load chat JSON file
     *
     * @return String
     * @throws IOException
     */
    private String loadChatFile() throws IOException {
        InputStream inputStream = getResources().openRawResource(R.raw.chat_data);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String receiveString;
        StringBuilder stringBuilder = new StringBuilder();

        while ((receiveString = bufferedReader.readLine()) != null) {
            stringBuilder.append(receiveString);
            stringBuilder.append("\n");
        }

        bufferedReader.close();
        inputStreamReader.close();
        inputStream.close();

        return stringBuilder.toString();
    }

}

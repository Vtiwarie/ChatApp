package com.apppartner.androidprogrammertest.adapters;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.apppartner.androidprogrammertest.R;
import com.apppartner.androidprogrammertest.classes.ThreadedImageView;
import com.apppartner.androidprogrammertest.models.ChatData;

import java.util.List;

/**
 * Created on 12/23/14.
 *
 * @author Thomas Colligan
 */
public class ChatsArrayAdapter extends ArrayAdapter<ChatData> {
    private Activity mActivity;
    private ThreadedImageView mThreadedImageView;

    public ChatsArrayAdapter(Activity activity, List<ChatData> objects) {
        super(activity, 0, objects);
        mActivity = activity;
        mThreadedImageView = new ThreadedImageView(activity);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.cell_chat, parent, false);

        ChatCell chatCell = new ChatCell();

        chatCell.usernameTextView = (TextView) convertView.findViewById(R.id.usernameTextView);
        chatCell.usernameTextView.setTypeface(Typeface.createFromAsset(mActivity.getAssets(), "Jelloween - Machinato.ttf"));
        chatCell.messageTextView = (TextView) convertView.findViewById(R.id.messageTextView);
        chatCell.usernameTextView.setTypeface(Typeface.createFromAsset(mActivity.getAssets(), "Jelloween - Machinato Light.ttf"));
        chatCell.avatarImageView = (ImageView) convertView.findViewById(R.id.img_avatar);

        ChatData chatData = getItem(position);

        chatCell.usernameTextView.setText(chatData.username);
        chatCell.messageTextView.setText(chatData.message);
        mThreadedImageView.loadImage(chatCell.avatarImageView, chatData.avatarURL);

        return convertView;
    }

    private static class ChatCell {
        ImageView avatarImageView;
        TextView usernameTextView;
        TextView messageTextView;
    }
}

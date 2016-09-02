package com.apppartner.androidprogrammertest.classes;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.widget.ImageView;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Vishaan on 9/1/2016.
 */
public class ThreadedImageView {

    private static final String LOG_TAG = ThreadedImageView.class.getSimpleName();
    private static final short THREAD_POOL_COUNT = 10;

    private Executor mThreadPool = Executors.newFixedThreadPool(THREAD_POOL_COUNT);
    private Activity mActivity;

    public ThreadedImageView(Activity activity) {
        mActivity = activity;
    }

    /**
     * Load the avatar image from the given url into the imageview
     *
     * @param imageView
     * @param url
     */
    public void loadImage(final ImageView imageView, final String url) {
        try {
            mThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    final Drawable bitmap = Network.getImageFromNetwork(mActivity, url);
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageDrawable(bitmap);
                        }
                    });
                }
            });
        } catch (Exception e) {
            Util.printTrace(LOG_TAG, e);
        }
    }

}

package com.apppartner.androidprogrammertest;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apppartner.androidprogrammertest.classes.Util;


public class AnimationActivity extends AppCompatActivity {

    private static final String TITLE = "Animation";
    private TextView mAnimationPrompt;
    private TextView mAnimationBonus;
    private ImageView mAppPartnerIcon;
    private boolean mShouldFadeIn = false;
    private ViewGroup rootLayout;
    private RelativeLayout.LayoutParams mParamsIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        Util.setUpToolbar(this, R.id.toolbar, TITLE);

        mAnimationPrompt = (TextView) findViewById(R.id.animation_prompt);
        mAnimationPrompt.setTypeface(Typeface.createFromAsset(getAssets(), "Jelloween - Machinato ExtraLight.ttf"));

        mAnimationBonus = (TextView) findViewById(R.id.animation_bonus);
        mAnimationBonus.setTypeface(Typeface.createFromAsset(getAssets(), "Jelloween - Machinato SemiBold Italic.ttf"));

        rootLayout = (ViewGroup) findViewById(R.id.container);
        mAppPartnerIcon = (ImageView) rootLayout.findViewById(R.id.app_partner_icon);
        mParamsIcon = (RelativeLayout.LayoutParams) mAppPartnerIcon.getLayoutParams();
        mAppPartnerIcon.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {
                final int X = (int) event.getRawX();
                final int Y = (int) event.getRawY();

                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        mParamsIcon.addRule(RelativeLayout.CENTER_HORIZONTAL, 0);
                        ViewGroup iconParent = ((ViewGroup) view.getParent());

                        //add the imageview to the root layout to appear on top of other layouts
                        if (iconParent.findViewById(R.id.app_partner_icon) != null) {
                            iconParent.removeView(view);
                            mParamsIcon.leftMargin = X - view.getWidth() / 2;
                            mParamsIcon.topMargin = Y - view.getHeight() / 2;
                            rootLayout.addView(view, mParamsIcon);
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mParamsIcon.rightMargin = -view.getWidth();
                        mParamsIcon.bottomMargin = -view.getHeight();
                        mParamsIcon.leftMargin = X - view.getWidth() / 2;
                        mParamsIcon.topMargin = Y - view.getHeight() / 2;
                        view.setLayoutParams(mParamsIcon);
                        break;
                }
                rootLayout.invalidate();
                return true;
            }
        });
    }

    /**
     * handle click events for this activity
     *
     * @param view
     */
    public void handleClick(View view) {
        switch (view.getId()) {
            case R.id.fade_button:
                animate();
                break;
        }
    }

    /**
     * Handle animations for app partner icon
     */
    private void animate() {
        Animator animator;
        if (mShouldFadeIn) {
            animator = AnimatorInflater.loadAnimator(this, R.animator.fade_in);
            mShouldFadeIn = false;
        } else {
            animator = AnimatorInflater.loadAnimator(this, R.animator.fade_out);
            mShouldFadeIn = true;
        }
        animator.setTarget(mAppPartnerIcon);
        animator.start();
    }
}

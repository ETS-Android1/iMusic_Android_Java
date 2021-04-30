package com.thanguit.imusic.animations;

import android.content.Context;
import android.media.Image;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.thanguit.imusic.R;
import com.thanguit.imusic.activities.MainActivity;

public class ScaleAnimation {
    private Animation scaleUpAnimation, scaleDownAnimation;
    private Context context;
    private Button button;
    private ImageView imageView;

    public ScaleAnimation() {
    }

    public ScaleAnimation(Context context, Button button) {
        this.context = context;
        this.button = button;
    }

    public ScaleAnimation(Context context, ImageView imageView) {
        this.context = context;
        this.imageView = imageView;
    }

    public void Event_Button() {
        this.scaleUpAnimation = AnimationUtils.loadAnimation(this.context, R.anim.scale_up);
        this.scaleDownAnimation = AnimationUtils.loadAnimation(this.context, R.anim.scale_down);

        this.button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    button.startAnimation(scaleDownAnimation);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    button.startAnimation(scaleUpAnimation);
                }
                return false;
            }
        });
    }

    public void Event_ImageView() {
        this.scaleUpAnimation = AnimationUtils.loadAnimation(this.context, R.anim.scale_up);
        this.scaleDownAnimation = AnimationUtils.loadAnimation(this.context, R.anim.scale_down);

        this.imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    imageView.startAnimation(scaleDownAnimation);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    imageView.startAnimation(scaleUpAnimation);
                }
                return false;
            }
        });
    }
}

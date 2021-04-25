package com.thanguit.imusic.animations;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.thanguit.imusic.R;
import com.thanguit.imusic.activities.MainActivity;

public class ScaleAnimation {
    private Button button;
    private Context context;
    private Animation scaleUpAnimation, scaleDownAnimation;

    public ScaleAnimation() {
    }

    public ScaleAnimation(Context context, Button button) {
        this.context = context;
        this.button = button;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Animation getScaleUpAnimation() {
        return scaleUpAnimation;
    }

    public void setScaleUpAnimation(Animation scaleUpAnimation) {
        this.scaleUpAnimation = scaleUpAnimation;
    }

    public Animation getScaleDownAnimation() {
        return scaleDownAnimation;
    }

    public void setScaleDownAnimation(Animation scaleDownAnimation) {
        this.scaleDownAnimation = scaleDownAnimation;
    }

    public void Event() {
        this.scaleUpAnimation = AnimationUtils.loadAnimation(this.context, R.anim.scale_up);
        this.scaleDownAnimation = AnimationUtils.loadAnimation(this.context, R.anim.scale_down);

        this.button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    button.startAnimation(scaleUpAnimation);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    button.startAnimation(scaleDownAnimation);
                }
                return false;
            }
        });
    }
}

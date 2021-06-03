package com.thanguit.imusic.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.thanguit.imusic.R;
import com.thanguit.imusic.animations.ScaleAnimation;

public class PersonalPageActivity extends AppCompatActivity {

    private ImageView ivBack;

    private ScaleAnimation scaleAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_page);

        Mapping();
        Event();
    }

    private void Mapping() {
        this.ivBack = findViewById(R.id.ivBack);
    }

    private void Event() {
        this.scaleAnimation = new ScaleAnimation(PersonalPageActivity.this, this.ivBack);
        this.scaleAnimation.Event_ImageView();
        this.ivBack.setOnClickListener(v -> finish());
    }
}
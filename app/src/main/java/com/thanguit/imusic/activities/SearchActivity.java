package com.thanguit.imusic.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.thanguit.imusic.R;
import com.thanguit.imusic.animations.ScaleAnimation;

public class SearchActivity extends AppCompatActivity {

    private EditText editText;
    private TextView textView;
    private ImageView ivBack;

    private ScaleAnimation scaleAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Mapping();
        Event();
    }

    private void Mapping() {
        this.editText = (EditText) findViewById(R.id.etSearchBox);
        this.editText.requestFocus(); // When Activity show, Searchbox will be focused

        this.textView = (TextView) findViewById(R.id.tvSearchHint);
        this.textView.setSelected(true); // Text will be moved

        this.ivBack = (ImageView) findViewById(R.id.ivBack);

    }

    private void Event() {
        this.scaleAnimation = new ScaleAnimation(this, this.ivBack);
        this.scaleAnimation.Event_ImageView();
        this.ivBack.setOnClickListener(v -> finish());

    }
}
package com.thanguit.imusic.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.thanguit.imusic.R;
import com.thanguit.imusic.animations.ScaleAnimation;

public class PersonalPlaylistActivity extends AppCompatActivity {
    private ImageView ivPersonalPlaylistBack;
    private TextView tvPersonalPlaylistTitle;
    private ImageView ivPersonalPlaylistMore;
    private Button btnPersonalPlayAll;

    private RecyclerView rlPersonalPlaylist;

    private ScaleAnimation scaleAnimation;

    private static final String TAG = "PersonalPlaylistActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_playlist);

        Mapping();
        Event();
    }

    private void Mapping() {
        this.ivPersonalPlaylistBack = findViewById(R.id.ivPersonalPlaylistBack);
        this.tvPersonalPlaylistTitle = findViewById(R.id.tvPersonalPlaylistTitle);
        this.ivPersonalPlaylistMore = findViewById(R.id.ivPersonalPlaylistMore);
        this.btnPersonalPlayAll = findViewById(R.id.btnPersonalPlayAll);

        this.rlPersonalPlaylist = findViewById(R.id.rlPersonalPlaylist);
    }

    private void Event() {
        this.scaleAnimation = new ScaleAnimation(this, this.ivPersonalPlaylistBack);
        this.scaleAnimation.Event_ImageView();
        this.ivPersonalPlaylistBack.setOnClickListener(v -> {
            finish();
        });

        this.scaleAnimation = new ScaleAnimation(this, this.ivPersonalPlaylistMore);
        this.scaleAnimation.Event_ImageView();

        this.scaleAnimation = new ScaleAnimation(this, this.btnPersonalPlayAll);
        this.scaleAnimation.Event_Button();
    }
}
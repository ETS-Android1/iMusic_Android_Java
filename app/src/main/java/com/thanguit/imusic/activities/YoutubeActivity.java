package com.thanguit.imusic.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.thanguit.imusic.R;
import com.thanguit.imusic.services.FullPlayerManagerService;

public class YoutubeActivity extends YouTubeBaseActivity {
    YouTubePlayerView youTubePlayerView;
    YouTubePlayer.OnInitializedListener onInitializedListener;
    //ImageView back;
    private Button back;
    private TextView tvSongName;
    private TextView tvArtist;
    private String MvCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

        this.back = findViewById(R.id.ivBack);
        this.back.setOnClickListener(v -> finish());

        this.youTubePlayerView = findViewById(R.id.youtubeView2);

        this.tvSongName = findViewById(R.id.tvSongName);
        this.tvSongName.setSelected(true); // Text will be moved
        this.tvSongName.setText(getIntent().getStringExtra("SongName").trim());

        this.tvArtist = findViewById(R.id.tvArtist);
        this.tvArtist.setSelected(true); // Text will be
        this.tvArtist.setText(getIntent().getStringExtra("Artist").trim());

        this.MvCode = getIntent().getStringExtra("MvCode");
        if (!TextUtils.isEmpty(this.MvCode)) {
            onInitializedListener = new YouTubePlayer.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                    youTubePlayer.loadVideo(MvCode); // Hiển thị Video của Youtube
                    try {
                        if (FullPlayerManagerService.mediaPlayer != null) {
                            FullPlayerManagerService.mediaPlayer.pause();
                        } else {
                            youTubePlayer.play();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
                }

                @Override
                public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                    Toast.makeText(YoutubeActivity.this, R.string.toast31, Toast.LENGTH_SHORT).show();
                }
            };
        }

        youTubePlayerView.initialize("AIzaSyCxmDstUZ507tv0OvMhJ67Dc3Jyi_1tTyU", onInitializedListener);
    }
}
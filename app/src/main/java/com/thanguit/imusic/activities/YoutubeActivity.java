package com.thanguit.imusic.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.thanguit.imusic.R;
import com.thanguit.imusic.services.FullPlayerManagerService;
import com.thanguit.imusic.services.SettingLanguage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class YoutubeActivity extends YouTubeBaseActivity {
    private SettingLanguage settingLanguage;

    YouTubePlayerView youTubePlayerView;
    YouTubePlayer.OnInitializedListener onInitializedListener;
    //ImageView back;
    Button back;
    TextView tvSongName;
    TextView tvArtist;
    private String MvCode;
    boolean error = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

        this.settingLanguage = SettingLanguage.getInstance(this);
        this.settingLanguage.Update_Language();

        MvCode = getIntent().getStringExtra("MvCode");

        youTubePlayerView = findViewById(R.id.youtubeView2);
        back = findViewById(R.id.ivBack);
        tvSongName = findViewById(R.id.tvSongName);
        tvArtist = findViewById(R.id.tvArtist);
        tvSongName.setText(getIntent().getStringExtra("SongName"));
        tvArtist.setText(getIntent().getStringExtra("Artist"));
        back.setOnClickListener(v -> finish());
        if (MvCode != null) {
            onInitializedListener = new YouTubePlayer.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                    //youTubePlayer.loadVideo(MvCode);
                    youTubePlayer.loadVideo(MvCode);
                    try {
                        if (FullPlayerManagerService.mediaPlayer != null)
                            FullPlayerManagerService.mediaPlayer.pause();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
                }

                @Override
                public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                    error = true;
                }
            };
        }

        if (error) {
            Toast.makeText(this, "Chức năng này cần bạn down Youtube về để sử dụng!", Toast.LENGTH_SHORT).show();
        }
        ///btn = findViewById(R.id.btnPlay);
        //btn.setOnClickListener(v->{
        youTubePlayerView.initialize("AIzaSyCUyAkQaYLAsYcimtKB3nyCo3ow-pyuElM", onInitializedListener);
        //});
    }

}
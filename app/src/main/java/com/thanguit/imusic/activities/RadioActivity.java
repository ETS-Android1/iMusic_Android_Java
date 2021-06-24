package com.thanguit.imusic.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.thanguit.imusic.R;
import com.thanguit.imusic.SharedPreferences.DataLocalManager;
import com.thanguit.imusic.models.Song;
import com.thanguit.imusic.services.FullPlayerManagerService;
import com.thanguit.imusic.services.MiniPlayerOnLockScreenService;
import com.thanguit.imusic.services.SettingLanguage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Random;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class RadioActivity extends AppCompatActivity {
    YouTubePlayerView youTubePlayerView;
    YouTubePlayer.OnInitializedListener onInitializedListener;
    Button btn;
    TextView roomName;
    private TextView tvListItemSongName;
    private TextView tvListItemSongSinger;
    private ImageView ivListItemSongLove;
    private ImageView ivRequest;
    private ImageView ivPlay;
    private ImageView ivListItemSong;
    int room;
    Gson gs = new Gson();
    private int timeCurrent = 0;
    String userid = "usercurrent" + DataLocalManager.getUserID();
    private MediaPlayer mediaPlayer;
    private static final String CHAR_LIST = "0123456789";

    private static final int RANDOM_STRING_LENGTH = 15;
    private Socket mSocket;
    private ImageView ivBack;

    {
        try {
            mSocket = IO.socket("192.168.1.13:3000");
            //mSocket = IO.socket("http://localhost:8000");
        } catch (URISyntaxException e) {
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio);

        room = getIntent().getIntExtra("room", 1); // Lấy thông tin phòng
        mSocket.connect(); // Kết nối
        mSocket.emit("joinroom", gs.toJson(new Room(room, userid))); // Gửi
        mSocket.on("currentsong", onNewMessage2); // Lắng nghe
        mSocket.on(userid, onNewMessage);
        Mapping();
        Event();
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            mediaPlayer.stop();
            mediaPlayer.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class Room {
        int room;
        String userid;

        public Room(int r, String id) {
            room = r;
            userid = id;
        }
    }

    private void Mapping() {
        this.ivListItemSong = findViewById(R.id.ivListItemSong);
        this.ivPlay = findViewById(R.id.ivPlayRadio);
        this.ivListItemSongLove = findViewById(R.id.ivLove);
        this.ivListItemSongLove.setVisibility(View.INVISIBLE);
        this.tvListItemSongName = findViewById(R.id.tvListItemSongName);
        this.tvListItemSongSinger = findViewById(R.id.tvListItemSongSinger);
        this.ivRequest = findViewById(R.id.ivRequest);
        this.ivRequest.setVisibility(View.INVISIBLE);
        this.ivBack = findViewById(R.id.ivBack);
    }

    private void Event() {
        ivPlay.setOnClickListener(v -> {
            onSongPlay();

        });
        ivBack.setOnClickListener(v -> {
            finish();
        });
    }

    private void onSongPlay() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            this.ivPlay.setImageResource(R.drawable.ic_play_1);
            //CreateNotification(MiniPlayerOnLockScreenService.ACTION_PAUSE);
        } else {
            mSocket.emit("joinroom", gs.toJson(new Room(room, userid)));
            this.ivPlay.setImageResource(R.drawable.ic_pause);
            //CreateNotification(MiniPlayerOnLockScreenService.ACTION_PLAY);
        }
    }

    public class PlayMP3 extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return strings[0];
        }

        @Override
        protected void onPostExecute(String song) {
            super.onPostExecute(song);
            try {
                try {
                    if (mediaPlayer.isPlaying() || mediaPlayer != null) {
                        mediaPlayer.stop();
                        mediaPlayer.release(); // Đồng bộ
                        mediaPlayer = null;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

                mediaPlayer.setOnCompletionListener(mp -> {
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                });
                //Log.d("CurrentSong",FullPlayerManagerService.currentSong.getName());
                mediaPlayer.setDataSource(song); // Cái này quan trọng nè Thắng
                mediaPlayer.prepare();
                mediaPlayer.start();
                mediaPlayer.seekTo(timeCurrent * 1000);
                Log.i("myactivity", song);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    //byte[] audio;
                    Song song = null;
                    try {
                        //audio = (byte[]) data.get("audio");
                        if (data.getString("userid").equalsIgnoreCase(userid) && data.getInt("room") == room) {
                            timeCurrent += 5;
                            //RunMp31(song.getLink(),time+3);
                            new PlayMP3().execute(data.getString("link"));
                            tvListItemSongName.setText(data.getString("name"));
                            tvListItemSongSinger.setText(data.getString("singer"));
                            timeCurrent = data.getInt("timecurrent");
                            String link = data.getString("image");
                            Picasso.get()
                                    .load(link)
                                    .placeholder(R.drawable.ic_logo)
                                    .error(R.drawable.ic_logo)
                                    .into(ivListItemSong);
                        }
                       /* if(aaa == 1){
                            mSocket.emit("server-gui-request",mediaPlayerRoom1.getCurrentPosition());
                        }*/
/*                         if(aaa == 2){
                            mSocket.emit("server-gui-request",mediaPlayerRoom2.getCurrentPosition());
                        }*/
                        //playMp3FromByte(audio);
                    } catch (JSONException e) {
                        Log.d("Error", e.getMessage());
                        return;
                    }
                }
            });
        }
    };
    private Emitter.Listener onNewMessage2 = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    //byte[] audio;
                    Song song = null;
                    try {
                        //audio = (byte[]) data.get("audio");
                        if (data.getInt("room") == room) {
                            timeCurrent += 5;
                            //RunMp31(song.getLink(),time+3);
                            new PlayMP3().execute(data.getString("link"));
                            tvListItemSongName.setText(data.getString("name"));
                            tvListItemSongSinger.setText(data.getString("singer"));
                            timeCurrent = data.getInt("timecurrent");
                            String link = data.getString("image");
                            Picasso.get()
                                    .load(link)
                                    .placeholder(R.drawable.ic_logo)
                                    .error(R.drawable.ic_logo)
                                    .into(ivListItemSong);
                        }
                       /* if(aaa == 1){
                            mSocket.emit("server-gui-request",mediaPlayerRoom1.getCurrentPosition());
                        }*/
/*                         if(aaa == 2){
                            mSocket.emit("server-gui-request",mediaPlayerRoom2.getCurrentPosition());
                        }*/
                        //playMp3FromByte(audio);
                    } catch (JSONException e) {
                        Log.d("Error", e.getMessage());
                        return;
                    }
                }
            });
        }
    };
}
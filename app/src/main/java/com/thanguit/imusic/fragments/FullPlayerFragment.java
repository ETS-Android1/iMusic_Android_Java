package com.thanguit.imusic.fragments;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.thanguit.imusic.R;
import com.thanguit.imusic.activities.FullPlayerActivity;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class FullPlayerFragment extends Fragment {

    private MediaPlayer mediaPlayer = new MediaPlayer();

    private ImageView ivCover;
    private ImageView ivFavorite;
    private ImageView ivDownload;
    private ImageView ivShare;
    private ImageView ivShuffle;
    private ImageView ivPrev;
    private ImageView ivPlayPause;
    private ImageView ivNext;
    private ImageView ivRepeat;
    private TextView tvTimeStart;
    private TextView tvTimeEnd;
    private SeekBar sbSong;

    private static final String TAG = "FullPlayerFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_full_player, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Mapping(view);
        Event();
    }

    private void Mapping(View view) {
        this.ivCover = (ImageView) view.findViewById(R.id.ivCover);
        this.ivDownload = (ImageView) view.findViewById(R.id.ivDownload);
        this.ivFavorite = (ImageView) view.findViewById(R.id.ivFavorite);
        this.ivShare = (ImageView) view.findViewById(R.id.ivShare);
        this.ivShuffle = (ImageView) view.findViewById(R.id.ivShuffle);
        this.ivPrev = (ImageView) view.findViewById(R.id.ivPrev);
        this.ivPlayPause = (ImageView) view.findViewById(R.id.ivPlayPause);
        this.ivNext = (ImageView) view.findViewById(R.id.ivNext);
        this.ivRepeat = (ImageView) view.findViewById(R.id.ivRepeat);
        this.tvTimeStart = (TextView) view.findViewById(R.id.tvTimeStart);
        this.tvTimeEnd = (TextView) view.findViewById(R.id.tvTimeEnd);
        this.sbSong = (SeekBar) view.findViewById(R.id.sbSong);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        if (FullPlayerActivity.dataSongArrayList.size() > 0) {
//            FullPlayerActivity.tvSongName.setText(FullPlayerActivity.dataSongArrayList.get(0).getName());
//            FullPlayerActivity.tvArtist.setText(FullPlayerActivity.dataSongArrayList.get(0).getSinger());
            new PlayMP3().execute(FullPlayerActivity.dataSongArrayList.get(0).getLink());
            this.ivPlayPause.setImageResource(R.drawable.ic_pause);
        }
    }

    private void Event() {
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            if (FullPlayerActivity.dataSongArrayList.size() > 0) {
                Picasso.get()
                        .load(FullPlayerActivity.dataSongArrayList.get(0).getImg())
                        .placeholder(R.drawable.ic_logo)
                        .error(R.drawable.ic_logo)
                        .into(this.ivCover);
            } else {
                handler.postDelayed((Runnable) this, 1000);
            }
        }, 1000);

        this.ivPlayPause.setOnClickListener(v -> {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                this.ivPlayPause.setImageResource(R.drawable.ic_play);
            } else {
                mediaPlayer.start();
                this.ivPlayPause.setImageResource(R.drawable.ic_pause);
            }
        });
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
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setOnCompletionListener(mp -> {
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                });

                mediaPlayer.setDataSource(song);
                mediaPlayer.prepare();
            } catch (IOException e) {
                Toast.makeText(getActivity(), "Fail", Toast.LENGTH_SHORT).show();
                Log.d(TAG, e.getMessage());
                e.printStackTrace();
            }
            mediaPlayer.start();
            TimeSong();
        }
    }

    private void TimeSong() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        this.tvTimeEnd.setText(simpleDateFormat.format(mediaPlayer.getDuration()));
        this.sbSong.setMax(mediaPlayer.getDuration());
    }
}

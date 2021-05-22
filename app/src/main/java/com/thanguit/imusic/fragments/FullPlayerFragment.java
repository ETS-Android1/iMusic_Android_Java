package com.thanguit.imusic.fragments;

import android.content.Context;
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
import com.thanguit.imusic.animations.ScaleAnimation;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Random;

public class FullPlayerFragment extends Fragment {
    private MediaPlayer mediaPlayer;

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

    private ScaleAnimation scaleAnimation;

    private int position = 0;
    private boolean repeat = false;
    private boolean checkRandom = false;
    private boolean next = false;

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
            FullPlayerActivity.tvSongName.setText(FullPlayerActivity.dataSongArrayList.get(0).getName());
            FullPlayerActivity.tvArtist.setText(FullPlayerActivity.dataSongArrayList.get(0).getSinger());
            new PlayMP3().execute(FullPlayerActivity.dataSongArrayList.get(0).getLink());
            this.ivPlayPause.setImageResource(R.drawable.ic_pause);
        }
    }

    private void Event() {
        this.scaleAnimation = new ScaleAnimation(getActivity(), this.ivDownload);
        this.scaleAnimation.Event_ImageView();
        this.scaleAnimation = new ScaleAnimation(getActivity(), this.ivFavorite);
        this.scaleAnimation.Event_ImageView();
        this.scaleAnimation = new ScaleAnimation(getActivity(), this.ivShare);
        this.scaleAnimation.Event_ImageView();
        this.scaleAnimation = new ScaleAnimation(getActivity(), this.ivShuffle);
        this.scaleAnimation.Event_ImageView();
        this.scaleAnimation = new ScaleAnimation(getActivity(), this.ivPrev);
        this.scaleAnimation.Event_ImageView();
        this.scaleAnimation = new ScaleAnimation(getActivity(), this.ivPlayPause);
        this.scaleAnimation.Event_ImageView();
        this.scaleAnimation = new ScaleAnimation(getActivity(), this.ivNext);
        this.scaleAnimation.Event_ImageView();
        this.scaleAnimation = new ScaleAnimation(getActivity(), this.ivRepeat);
        this.scaleAnimation.Event_ImageView();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (FullPlayerActivity.dataSongArrayList.size() > 0) {
                    Picasso.get()
                            .load(FullPlayerActivity.dataSongArrayList.get(0).getImg())
                            .placeholder(R.drawable.ic_logo)
                            .error(R.drawable.ic_logo)
                            .into(ivCover);
                } else {
                    handler.postDelayed(this, 1000);
                }
            }
        }, 1000);

        this.ivPlayPause.setOnClickListener(v -> {
            if (mediaPlayer.isPlaying()) {
                this.mediaPlayer.pause();
                this.ivPlayPause.setImageResource(R.drawable.ic_play);
            } else {
                this.mediaPlayer.start();
                this.ivPlayPause.setImageResource(R.drawable.ic_pause);
            }
        });


        this.ivRepeat.setOnClickListener(v -> {
            if (repeat == false) {
                if (checkRandom == true) {
                    checkRandom = false;
                    this.ivRepeat.setImageResource(R.drawable.ic_loop_check);
                    this.ivShuffle.setImageResource(R.drawable.ic_shuffle);
                } else {
                    this.ivRepeat.setImageResource(R.drawable.ic_loop_check);
                    repeat = true;
                }
            } else {
                this.ivRepeat.setImageResource(R.drawable.ic_loop);
                repeat = false;
            }
        });


        this.ivShuffle.setOnClickListener(v -> {
            if (checkRandom == false) {
                if (repeat == true) {
                    repeat = false;
                    this.ivShuffle.setImageResource(R.drawable.ic_shuffle_check);
                    this.ivRepeat.setImageResource(R.drawable.ic_loop);
                } else {
                    this.ivShuffle.setImageResource(R.drawable.ic_shuffle_check);
                    checkRandom = true;
                }
            } else {
                this.ivShuffle.setImageResource(R.drawable.ic_shuffle);
                checkRandom = false;
            }
        });


        this.sbSong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });


        this.ivNext.setOnClickListener(v -> {
            if (FullPlayerActivity.dataSongArrayList.size() > 0) {
                if (this.mediaPlayer.isPlaying() || this.mediaPlayer != null) {
                    this.mediaPlayer.stop();
                    this.mediaPlayer.release(); // Đồng bộ
                    this.mediaPlayer = null;
                }
                if (this.position < FullPlayerActivity.dataSongArrayList.size()) {
                    ivPlayPause.setImageResource(R.drawable.ic_pause);
                    this.position++;

                    if (repeat == true) {
                        if (this.position == 0) {
                            this.position = FullPlayerActivity.dataSongArrayList.size();
                        }
                        this.position -= 1;
                    }

                    if (checkRandom == true) {
                        Random random = new Random();
                        int index = random.nextInt(FullPlayerActivity.dataSongArrayList.size());
                        if (index == this.position) {
                            this.position = index - 1;
                        }
                        this.position = index;
                    }
                    if (this.position > FullPlayerActivity.dataSongArrayList.size() - 1) {
                        this.position = 0;
                    }
                }
                new PlayMP3().execute(FullPlayerActivity.dataSongArrayList.get(this.position).getLink());

                Picasso.get()
                        .load(FullPlayerActivity.dataSongArrayList.get(this.position).getImg())
                        .placeholder(R.drawable.ic_logo)
                        .error(R.drawable.ic_logo)
                        .into(this.ivCover);

                FullPlayerActivity.tvSongName.setText(FullPlayerActivity.dataSongArrayList.get(this.position).getName());
                FullPlayerActivity.tvArtist.setText(FullPlayerActivity.dataSongArrayList.get(this.position).getSinger());
                LyricsPlayerFragment.position = this.position;
                UpdateTimeSong();
            }
            this.ivNext.setClickable(false);
            this.ivPrev.setClickable(false);

            new Handler().postDelayed(() -> {
                this.ivNext.setClickable(true);
                this.ivPrev.setClickable(true);
            }, 2000);
        });


        this.ivPrev.setOnClickListener(v -> {
            if (FullPlayerActivity.dataSongArrayList.size() > 0) {
                if (this.mediaPlayer.isPlaying() || this.mediaPlayer != null) {
                    this.mediaPlayer.stop();
                    this.mediaPlayer.release(); // Đồng bộ
                    this.mediaPlayer = null;
                }
                if (this.position < FullPlayerActivity.dataSongArrayList.size()) {
                    ivPlayPause.setImageResource(R.drawable.ic_pause);
                    this.position--;

                    if (this.position < 0) {
                        this.position = FullPlayerActivity.dataSongArrayList.size() - 1;
                    }

                    if (repeat == true) {
                        this.position += 1;
                    }

                    if (checkRandom == true) {
                        Random random = new Random();
                        int index = random.nextInt(FullPlayerActivity.dataSongArrayList.size());
                        if (index == this.position) {
                            this.position = index - 1;
                        }
                        this.position = index;
                    }
                }
                new PlayMP3().execute(FullPlayerActivity.dataSongArrayList.get(this.position).getLink());

                Picasso.get()
                        .load(FullPlayerActivity.dataSongArrayList.get(this.position).getImg())
                        .placeholder(R.drawable.ic_logo)
                        .error(R.drawable.ic_logo)
                        .into(this.ivCover);

                FullPlayerActivity.tvSongName.setText(FullPlayerActivity.dataSongArrayList.get(this.position).getName());
                FullPlayerActivity.tvArtist.setText(FullPlayerActivity.dataSongArrayList.get(this.position).getSinger());
                LyricsPlayerFragment.position = this.position;
                UpdateTimeSong();
            }
            this.ivNext.setClickable(false);
            this.ivPrev.setClickable(false);

            new Handler().postDelayed(() -> {
                this.ivNext.setClickable(true);
                this.ivPrev.setClickable(true);
            }, 2000);
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
                mediaPlayer = new MediaPlayer();
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
            UpdateTimeSong();
        }
    }

    private void TimeSong() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");

        this.tvTimeEnd.setText(simpleDateFormat.format(mediaPlayer.getDuration()));
        this.sbSong.setMax(mediaPlayer.getDuration());
    }

    private void UpdateTimeSong() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");

                    tvTimeStart.setText(simpleDateFormat.format(mediaPlayer.getCurrentPosition()));
                    sbSong.setProgress(mediaPlayer.getCurrentPosition());
                    handler.postDelayed(this, 1000);

                    mediaPlayer.setOnCompletionListener(mp -> {
                        next = true;
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        }, 1000);


        Handler handler_1 = new Handler();
        handler_1.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (next == true) {
                    if (position < FullPlayerActivity.dataSongArrayList.size()) {
                        ivPlayPause.setImageResource(R.drawable.ic_pause);
                        position++;

                        if (repeat == true) {
                            if (position == 0) {
                                position = FullPlayerActivity.dataSongArrayList.size();
                            }
                            position -= 1;
                        }

                        if (checkRandom == true) {
                            Random random = new Random();
                            int index = random.nextInt(FullPlayerActivity.dataSongArrayList.size());
                            if (index == position) {
                                position = index - 1;
                            }
                            position = index;
                        }
                        if (position > FullPlayerActivity.dataSongArrayList.size() - 1) {
                            position = 0;
                        }
                    }
                    new PlayMP3().execute(FullPlayerActivity.dataSongArrayList.get(position).getLink());

                    Picasso.get()
                            .load(FullPlayerActivity.dataSongArrayList.get(position).getImg())
                            .placeholder(R.drawable.ic_logo)
                            .error(R.drawable.ic_logo)
                            .into(ivCover);

                    FullPlayerActivity.tvSongName.setText(FullPlayerActivity.dataSongArrayList.get(position).getName());
                    FullPlayerActivity.tvArtist.setText(FullPlayerActivity.dataSongArrayList.get(position).getSinger());
                    LyricsPlayerFragment.position = position;

                    ivNext.setClickable(false);
                    ivPrev.setClickable(false);

                    new Handler().postDelayed(() -> {
                        ivNext.setClickable(true);
                        ivPrev.setClickable(true);
                    }, 2000);

                    next = false;
                    handler_1.removeCallbacks(this); // Lưu ý khúc này
                } else {
                    handler_1.postDelayed(this, 1000);
                }
            }
        }, 1000);
    }
}

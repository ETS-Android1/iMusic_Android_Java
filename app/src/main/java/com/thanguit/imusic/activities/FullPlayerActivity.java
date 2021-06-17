package com.thanguit.imusic.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rd.PageIndicatorView;
import com.thanguit.imusic.R;
import com.thanguit.imusic.adapters.FullPlayerAdapter;
import com.thanguit.imusic.animations.LoadingDialog;
import com.thanguit.imusic.animations.ScaleAnimation;
import com.thanguit.imusic.fragments.DetailPlayerFragment;
import com.thanguit.imusic.fragments.FullPlayerFragment;
import com.thanguit.imusic.fragments.LyricsPlayerFragment;
import com.thanguit.imusic.models.Song;

import java.util.ArrayList;

public class FullPlayerActivity extends AppCompatActivity implements FullPlayerFragment.ISendPositionListener {
    private ViewPager viewpager;
    public static FullPlayerAdapter fullPlayerAdapter;
    private PageIndicatorView pageIndicatorView;

    private ScaleAnimation scaleAnimation;

    private FullPlayerFragment fullPlayerFragment;
    private DetailPlayerFragment detailPlayerFragment;
    private LyricsPlayerFragment lyricsPlayerFragment;

    private Song song;
    private ArrayList<Song> songArrayList = new ArrayList<>();

    public static ArrayList<Song> dataSongArrayList = new ArrayList<>(); // Mảng này để truyền dữ liệu cho các fragment

    private ImageView ivBack;
    public static TextView tvSongName;
    public static TextView tvArtist;

    private static final String TAG = "FullPlayerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_player);

        Mapping();
        Get_Data_Intent();
        Event();
    }

    private void Mapping() {
        this.viewpager = findViewById(R.id.vpFullPlayer);
        fullPlayerAdapter = new FullPlayerAdapter(getSupportFragmentManager(), 1);
        this.pageIndicatorView = findViewById(R.id.pageIndicatorView);
        this.ivBack = findViewById(R.id.ivBack);

        tvSongName = findViewById(R.id.tvSongName);
        tvSongName.setSelected(true); // Text will be moved
        tvArtist = findViewById(R.id.tvArtist);
        tvArtist.setSelected(true); // Text will be moved

        this.fullPlayerFragment = new FullPlayerFragment();
        this.detailPlayerFragment = new DetailPlayerFragment();
        this.lyricsPlayerFragment = new LyricsPlayerFragment();

        fullPlayerAdapter.Add_Fragment(this.detailPlayerFragment); // 0
        fullPlayerAdapter.Add_Fragment(this.fullPlayerFragment); // 1
        fullPlayerAdapter.Add_Fragment(this.lyricsPlayerFragment); // 2

        this.viewpager.setAdapter(fullPlayerAdapter);
        this.viewpager.setCurrentItem(1); // Set default Fragment
    }

    private void Event() {
        this.viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                pageIndicatorView.setSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        this.scaleAnimation = new ScaleAnimation(this, this.ivBack);
        this.scaleAnimation.Event_ImageView();
        this.ivBack.setOnClickListener(v -> finish());
    }

    private void Get_Data_Intent() {
        Intent intent = getIntent();
        dataSongArrayList.clear(); // Xóa hết dữ liệu bài hát khi nhận đc một dữ liệu bài hát mới

        if (intent != null) {
            if (intent.hasExtra("SONG")) { // Khi chọn một bài hát
                this.song = (Song) intent.getParcelableExtra("SONG");
                if (this.song != null) {
                    dataSongArrayList.add(this.song);

                    Log.d(TAG, "Bài hát người dùng chọn: " + this.song.getName());
                }
            } else if (intent.hasExtra("SONGSLIDER")) {
                this.songArrayList = intent.getParcelableArrayListExtra("SONGSLIDER");
                if (this.songArrayList != null) {
                    dataSongArrayList = this.songArrayList;

                    Log.d(TAG, "Bài hát từ Slider: " + this.songArrayList.get(0).getName());
                }
            } else if (intent.hasExtra("SONGCHART")) { // Khi chọn một bài hát từ bảng xếp hạng bài hát
                this.song = (Song) intent.getParcelableExtra("SONGCHART");
                if (this.song != null) {
                    dataSongArrayList.add(this.song);

                    Log.d(TAG, "Bài hát từ bảng xếp hạng: " + this.song.getName());
                }
            } else if (intent.hasExtra("ALLSONGS")) { // Khi chọn nghe tất cả bài hát từ SongActivity
                this.songArrayList = intent.getParcelableArrayListExtra("ALLSONGS");
                if (this.songArrayList != null) {
                    dataSongArrayList = this.songArrayList;

                    for (int i = 0; i < this.songArrayList.size(); i++) {
                        Log.d(TAG, "Bài hát từ SongActivity " + (i + 1) + ": " + this.songArrayList.get(i).getName());
                    }
                }
            } else if (intent.hasExtra("ALLFAVORITESONGS")) { // Khi chọn nghe tất cả bài hát từ PersonalPlaylistActivity
                this.songArrayList = intent.getParcelableArrayListExtra("ALLFAVORITESONGS");
                if (this.songArrayList != null) {
                    dataSongArrayList = this.songArrayList;

                    for (int i = 0; i < this.songArrayList.size(); i++) {
                        Log.d(TAG, "Bài hát yêu thích " + (i + 1) + ": " + this.songArrayList.get(i).getName());
                    }
                }
            }
        } else {
            Toast.makeText(FullPlayerActivity.this, R.string.toast11, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void Send_Position(int position) {
        lyricsPlayerFragment.Get_Position(position);
    }
}
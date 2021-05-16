package com.thanguit.imusic.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rd.PageIndicatorView;
import com.thanguit.imusic.R;
import com.thanguit.imusic.adapters.FullPlayerAdapter;
import com.thanguit.imusic.animations.ScaleAnimation;
import com.thanguit.imusic.fragments.DetailPlayerFragment;
import com.thanguit.imusic.fragments.FullPlayerFragment;
import com.thanguit.imusic.fragments.LyricsPlayerFragment;
import com.thanguit.imusic.models.Song;

import java.util.ArrayList;

public class FullPlayerActivity extends AppCompatActivity {

    private ViewPager viewpager;
    public static FullPlayerAdapter fullPlayerAdapter;
    private PageIndicatorView pageIndicatorView;

    private ScaleAnimation scaleAnimation;

    private FullPlayerFragment fullPlayerFragment;
    private DetailPlayerFragment detailPlayerFragment;
    private LyricsPlayerFragment lyricsPlayerFragment;

    private Song song;
    private ArrayList<Song> songArrayList = new ArrayList<>();

    public static ArrayList<Song> dataSongArrayList = new ArrayList<>(); // mảng này để hiển thị danh sách bài hát cho DetailPlayerFragment

    private ImageView ivBack;
    public static TextView tvSongName, tvArtist;

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
        this.viewpager = (ViewPager) findViewById(R.id.vpFullPlayer);
        fullPlayerAdapter = new FullPlayerAdapter(getSupportFragmentManager(), 1);
        this.pageIndicatorView = (PageIndicatorView) findViewById(R.id.pageIndicatorView);
        this.ivBack = (ImageView) findViewById(R.id.ivBack);
        tvSongName = (TextView) findViewById(R.id.tvSongName);
        tvArtist = (TextView) findViewById(R.id.tvArtist);

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
        dataSongArrayList.clear();

        if (intent != null) {
            if (intent.hasExtra("SONG")) {
                this.song = (Song) intent.getParcelableExtra("SONG");
                if (this.song != null) {
                    dataSongArrayList.add(this.song);

                    Log.d(TAG, this.song.getName());
                }
            } else if (intent.hasExtra("SONGLOVE")) {
                this.song = (Song) intent.getParcelableExtra("SONGLOVE");
                if (this.song != null) {
                    dataSongArrayList.add(this.song);

                    Log.d(TAG, this.song.getName());
                }
            } else if (intent.hasExtra("SONGALL")) {
                this.songArrayList = intent.getParcelableArrayListExtra("SONGALL");
                if (this.songArrayList != null) {
                    dataSongArrayList = this.songArrayList;

                    for (int i = 0; i < this.songArrayList.size(); i++) {
                        Log.d(TAG, this.songArrayList.get(i).getName());
                    }
                }
            }
        } else {
            Toast.makeText(FullPlayerActivity.this, "Không có dữ liệu!", Toast.LENGTH_SHORT).show();
        }
    }
}
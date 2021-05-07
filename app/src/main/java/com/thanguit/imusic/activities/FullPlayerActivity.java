package com.thanguit.imusic.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.rd.PageIndicatorView;
import com.thanguit.imusic.R;
import com.thanguit.imusic.adapters.FullPlayerAdapter;
import com.thanguit.imusic.animations.ScaleAnimation;
import com.thanguit.imusic.fragments.DetailPlayerFragment;
import com.thanguit.imusic.fragments.FullPlayerFragment;
import com.thanguit.imusic.fragments.LyricsPlayerFragment;

public class FullPlayerActivity extends AppCompatActivity {

    private ViewPager viewpager;
    private FullPlayerAdapter fullPlayerAdapter;
    private PageIndicatorView pageIndicatorView;

    private ScaleAnimation scaleAnimation;

    private FullPlayerFragment fullPlayerFragment;
    private DetailPlayerFragment detailPlayerFragment;
    private LyricsPlayerFragment lyricsPlayerFragment;

    private ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_player);

        Mapping();
        Event();
    }

    private void Mapping() {
        this.viewpager = (ViewPager) findViewById(R.id.vpFullPlayer);
        this.fullPlayerAdapter = new FullPlayerAdapter(getSupportFragmentManager(), 1);
        this.pageIndicatorView = (PageIndicatorView) findViewById(R.id.pageIndicatorView);
        this.ivBack = (ImageView) findViewById(R.id.ivBack);

        this.fullPlayerFragment = new FullPlayerFragment();
        this.detailPlayerFragment = new DetailPlayerFragment();
        this.lyricsPlayerFragment = new LyricsPlayerFragment();

        this.fullPlayerAdapter.Add_Fragment(this.detailPlayerFragment); // 0
        this.fullPlayerAdapter.Add_Fragment(this.fullPlayerFragment); // 1
        this.fullPlayerAdapter.Add_Fragment(this.lyricsPlayerFragment); // 2

        this.viewpager.setAdapter(this.fullPlayerAdapter);
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
        this.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
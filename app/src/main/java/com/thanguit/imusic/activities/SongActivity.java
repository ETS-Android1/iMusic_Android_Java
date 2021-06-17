package com.thanguit.imusic.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.thanguit.imusic.API.APIService;
import com.thanguit.imusic.API.DataService;
import com.thanguit.imusic.R;
import com.thanguit.imusic.adapters.SongAdapter;
import com.thanguit.imusic.models.Playlist;
import com.thanguit.imusic.models.Song;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SongActivity extends AppCompatActivity {
    private CoordinatorLayout coordinatorlayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Toolbar toolbar;
    private FloatingActionButton floatingActionButton;

    private RecyclerView rvListSong;
    private ShimmerFrameLayout sflItemSong;

    private ArrayList<Song> songArrayList;
    private Playlist playlist;

    private static final String TAG = "SongActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);

        Mapping();
        Get_Data_Intent();
        Event();
    }

    private void Mapping() {
        this.coordinatorlayout = findViewById(R.id.cdlListSong);
        this.collapsingToolbarLayout = findViewById(R.id.ctlImage);
        this.toolbar = findViewById(R.id.tbListSong);

        this.floatingActionButton = findViewById(R.id.fabPlay);
        this.floatingActionButton.setEnabled(false); // Set false để cho nó không hoạt động trước đã, sau khi load xong hết các bài hát thì gọi hàm Play_All_Song();

        this.rvListSong = findViewById(R.id.rvListSong);
        this.sflItemSong = findViewById(R.id.sflItemSong);

//        this.playlist = new Playlist();

        setSupportActionBar(this.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        this.toolbar.setNavigationIcon(R.drawable.ic_angle_left);

        this.collapsingToolbarLayout.setExpandedTitleTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorMain2)));
        this.collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.colorMain2));
    }

    private void Event() {
        this.toolbar.setNavigationOnClickListener(v -> {
            finish();
        });
    }

    private void Get_Data_Intent() {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("PLAYLIST")) {
                this.playlist = (Playlist) intent.getParcelableExtra("PLAYLIST");
                if (this.playlist != null) {
                    Log.d(TAG, this.playlist.getName());

                    this.collapsingToolbarLayout.setTitle(this.playlist.getName());
                    Display_Song_Playlist(this.playlist.getId());
                }
            }
        }
    }

    private void Display_Song_Playlist(int id) {
        DataService dataService = APIService.getService();
        Call<List<Song>> callBack = dataService.getSongPlaylist(id);
        callBack.enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                songArrayList = (ArrayList<Song>) response.body();

                if (songArrayList != null && songArrayList.size() > 0) {
                    rvListSong.setHasFixedSize(true);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(SongActivity.this);
                    layoutManager.setOrientation(RecyclerView.VERTICAL); // Chiều dọc
                    rvListSong.setLayoutManager(layoutManager);
                    rvListSong.setAdapter(new SongAdapter(SongActivity.this, songArrayList, "SONG"));

                    sflItemSong.setVisibility(View.GONE); // Load biến mất
                    rvListSong.setVisibility(View.VISIBLE); // Hiện thông tin

                    Play_All_Song();

                    Log.d(TAG, songArrayList.get(0).getName());
                }
            }

            @Override
            public void onFailure(Call<List<Song>> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
    }

    private void Play_All_Song() { // Hàm này sẽ đảm bảo khi các bài hát load xong về giao diện thì button này mới hoạt động
        this.floatingActionButton.setEnabled(true);
        this.floatingActionButton.setOnClickListener(v -> {
            Intent intent = new Intent(SongActivity.this, FullPlayerActivity.class);
            intent.putExtra("ALLSONGS", songArrayList);
            startActivity(intent);
        });
    }
}


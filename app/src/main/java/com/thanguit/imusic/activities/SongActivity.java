package com.thanguit.imusic.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.thanguit.imusic.API.APIService;
import com.thanguit.imusic.API.DataService;
import com.thanguit.imusic.R;
import com.thanguit.imusic.adapters.ChartAdapter;
import com.thanguit.imusic.adapters.SongAdapter;
import com.thanguit.imusic.models.Playlist;
import com.thanguit.imusic.models.Song;

import java.io.IOException;
import java.net.URL;
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

    private RecyclerView recyclerview;

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
        this.coordinatorlayout = (CoordinatorLayout) findViewById(R.id.cdlListSong);
        this.collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.ctlImage);
        this.toolbar = (Toolbar) findViewById(R.id.tbListSong);
        this.floatingActionButton = (FloatingActionButton) findViewById(R.id.fabPlay);

        this.recyclerview = (RecyclerView) findViewById(R.id.rvListSong);

        this.playlist = new Playlist();

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
                this.playlist = (Playlist) intent.getSerializableExtra("PLAYLIST");
                if (this.playlist != null) {
                    Log.d(TAG, this.playlist.getName());

                    Set_View_Background(this.playlist.getName(), this.playlist.getImg());
                    Display_Song_Playlist(this.playlist.getId());
                }
            }
        }
    }

    private void Set_View_Background(String name, String image) {
        this.collapsingToolbarLayout.setTitle(name);
//        try {
//            URL url = new URL(image);
//            Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//            BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
//            this.collapsingToolbarLayout.setBackground(bitmapDrawable);
//
//            Log.d(TAG, bitmapDrawable.toString());
//        } catch (Exception e) {
//            Log.d(TAG, e.getMessage());
//        }
    }

    private void Display_Song_Playlist(String id) {
        DataService dataService = APIService.getService();
        Call<List<Song>> callBack = dataService.getSongWithPlaylist(id);
        callBack.enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                songArrayList = (ArrayList<Song>) response.body();

                recyclerview.setHasFixedSize(true);
                LinearLayoutManager layoutManager = new LinearLayoutManager(SongActivity.this);
                layoutManager.setOrientation(RecyclerView.VERTICAL); // Chiều dọc
                recyclerview.setLayoutManager(layoutManager);
                recyclerview.setAdapter(new SongAdapter(songArrayList));

                Log.d(TAG, songArrayList.get(0).getName());
            }

            @Override
            public void onFailure(Call<List<Song>> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
    }
}


package com.thanguit.imusic.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.thanguit.imusic.API.APIService;
import com.thanguit.imusic.API.DataService;
import com.thanguit.imusic.R;
import com.thanguit.imusic.adapters.GenreAdapter;
import com.thanguit.imusic.animations.LoadingDialog;
import com.thanguit.imusic.animations.ScaleAnimation;
import com.thanguit.imusic.models.Genre;
import com.thanguit.imusic.models.Theme;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GenreActivity extends AppCompat {
    private static final String TAG = "GenreActivity";

    private ScaleAnimation scaleAnimation;
    private LoadingDialog loadingDialog;

    private ImageView ivBack;
    private TextView tvTitle;

    private GridView gvGridView;
    private RecyclerView rvRecyclerView;

    private Theme theme;

    private List<Genre> genreList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre);

        Mapping();
        Get_Data_Intent();
        Event();
    }

    private void Mapping() {
        this.loadingDialog = new LoadingDialog(this);
        this.loadingDialog.Start_Loading();

        this.ivBack = findViewById(R.id.ivBack);
        this.tvTitle = findViewById(R.id.tvTitle);

        this.gvGridView = findViewById(R.id.gvGridView);
        this.rvRecyclerView = findViewById(R.id.rvRecyclerView);
    }

    private void Event() {
        this.scaleAnimation = new ScaleAnimation(this, this.ivBack);
        this.scaleAnimation.Event_ImageView();
        this.ivBack.setOnClickListener(v -> finish());
    }

    private void Get_Data_Intent() {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("THEME")) {
                this.theme = (Theme) intent.getParcelableExtra("THEME");
                if (this.theme != null) {
                    Log.d(TAG, this.theme.getName());
                    this.tvTitle.setText(this.theme.getName());

                    Display_Genre(this.theme.getIdTheme());
                }
            }
        }
    }

    private void Display_Genre(int id) {
        DataService dataService = APIService.getService();
        Call<List<Genre>> callBack = dataService.getGenre(id);
        callBack.enqueue(new Callback<List<Genre>>() {
            @Override
            public void onResponse(Call<List<Genre>> call, Response<List<Genre>> response) {
                genreList = new ArrayList<>();
                genreList = response.body();

                if (genreList != null) {
                    gvGridView.setAdapter(new GenreAdapter(GenreActivity.this, genreList));
                    loadingDialog.Cancel_Loading();
                }
            }

            @Override
            public void onFailure(Call<List<Genre>> call, Throwable t) {
                loadingDialog.Cancel_Loading();
                Log.d(TAG, "Display_Genre(Error)" + t.getMessage());
            }
        });
    }
}
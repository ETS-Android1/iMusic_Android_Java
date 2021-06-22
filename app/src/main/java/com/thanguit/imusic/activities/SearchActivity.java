package com.thanguit.imusic.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.thanguit.imusic.API.APIService;
import com.thanguit.imusic.API.DataService;
import com.thanguit.imusic.R;
import com.thanguit.imusic.adapters.SongAdapter;
import com.thanguit.imusic.animations.ScaleAnimation;
import com.thanguit.imusic.models.Song;
import com.thanguit.imusic.services.SettingLanguage;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
    private static final String TAG = "SearchActivity";

    private SettingLanguage settingLanguage;

    private EditText etSearchBox;
    private ImageView ivBack;
    private TextView tvSearchHint;

    private RecyclerView rvSearchResult;

    private ScaleAnimation scaleAnimation;

    private ArrayList<Song> songArrayList = new ArrayList<>();
    private SongAdapter songAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Mapping();
        Event();
    }

    private void Mapping() {
        this.settingLanguage = SettingLanguage.getInstance(this);
        this.settingLanguage.Update_Language();

        this.etSearchBox = findViewById(R.id.etSearchBox);
        this.etSearchBox.requestFocus(); // When Activity show, Searchbox will be focused

        this.ivBack = findViewById(R.id.ivBack);

        this.tvSearchHint = findViewById(R.id.tvSearchHint);
        this.tvSearchHint.setSelected(true); // Text will be moved

        this.rvSearchResult = findViewById(R.id.rvSearchResult);
    }

    private void Event() {
        this.scaleAnimation = new ScaleAnimation(this, this.ivBack);
        this.scaleAnimation.Event_ImageView();
        this.ivBack.setOnClickListener(v -> finish());

        this.etSearchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String keyWord = s.toString().toLowerCase().trim(); // Chuyển kí tự về dạng chữ viết thường để tìm kiếm cho nhanh

                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        if (!keyWord.isEmpty()) {
                            Handle_Search(keyWord);
                        }
                    }
                };

                Handler handler = new Handler();
                handler.postDelayed(runnable, 1000);
            }
        });
    }

    private void Handle_Search(String keyword) {
        DataService dataService = APIService.getService();
        Call<List<Song>> callBack = dataService.getSongSearch(keyword);
        callBack.enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                songArrayList = new ArrayList<>();
                songArrayList = (ArrayList<Song>) response.body();

                if (songArrayList != null && songArrayList.size() > 0) {
                    tvSearchHint.setVisibility(View.GONE);

                    rvSearchResult.setHasFixedSize(true);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(SearchActivity.this);
                    layoutManager.setOrientation(RecyclerView.VERTICAL); // Chiều dọc
                    rvSearchResult.setLayoutManager(layoutManager);

                    rvSearchResult.setAdapter(new SongAdapter(SearchActivity.this, songArrayList, "SONGSEARCH"));
                } else {
                    rvSearchResult.setAdapter(new SongAdapter(SearchActivity.this, songArrayList, "SONGSEARCH"));
                }
            }

            @Override
            public void onFailure(Call<List<Song>> call, Throwable t) {
                Log.d(TAG, "Handle_Search(Error)" + t.getMessage());
            }
        });
    }
}
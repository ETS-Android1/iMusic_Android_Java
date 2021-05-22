package com.thanguit.imusic.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thanguit.imusic.R;
import com.thanguit.imusic.activities.FullPlayerActivity;
import com.thanguit.imusic.adapters.ChartAdapter;
import com.thanguit.imusic.adapters.ListSongAdapter;
import com.thanguit.imusic.adapters.LyricAdapter;

import java.util.Objects;

public class LyricsPlayerFragment extends Fragment {
    private TextView tvLyric;

    public static int position = 0;

    private static final String TAG = "LyricsPlayerFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lyrics_player, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Mapping(view);
    }

    private void Mapping(View view) {
        this.tvLyric = (TextView) view.findViewById(R.id.tvLyric);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        if (FullPlayerActivity.dataSongArrayList.size() > 0) {
            String lyric = FullPlayerActivity.dataSongArrayList.get(position).getLyric().replace("\\n", Objects.requireNonNull(System.getProperty("line.separator")));
            this.tvLyric.setText(lyric);

            Log.d(TAG, lyric);
        } else {
            Log.d(TAG, "Lỗi! Không có dữ liệu");
        }
    }
}
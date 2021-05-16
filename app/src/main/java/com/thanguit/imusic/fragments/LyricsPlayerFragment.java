package com.thanguit.imusic.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thanguit.imusic.R;
import com.thanguit.imusic.activities.FullPlayerActivity;
import com.thanguit.imusic.adapters.ChartAdapter;
import com.thanguit.imusic.adapters.LyricAdapter;

public class LyricsPlayerFragment extends Fragment {

    private RecyclerView rvLyric;

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
        this.rvLyric = (RecyclerView) view.findViewById(R.id.rvLyric);

        if (FullPlayerActivity.dataSongArrayList.size() > 0) {
            this.rvLyric.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(RecyclerView.VERTICAL); // Chiều dọc
            this.rvLyric.setLayoutManager(layoutManager);

            this.rvLyric.setAdapter(new LyricAdapter(FullPlayerActivity.dataSongArrayList));

            Log.d(TAG, FullPlayerActivity.dataSongArrayList.get(0).getLyric());
        } else {
            Log.d(TAG, "No data");
        }
    }
}
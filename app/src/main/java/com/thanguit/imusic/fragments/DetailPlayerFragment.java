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

import com.thanguit.imusic.R;
import com.thanguit.imusic.activities.FullPlayerActivity;
import com.thanguit.imusic.adapters.ListSongAdapter;
import com.thanguit.imusic.adapters.ThemeHomeAdapter;

public class DetailPlayerFragment extends Fragment {
    private RecyclerView rvDataListSong;

    private static final String TAG = "DetailPlayerFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_player, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Mapping(view);
        Event();
    }

    private void Mapping(View view) {
        this.rvDataListSong = view.findViewById(R.id.rvDataListSong);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        if (FullPlayerActivity.dataSongArrayList.size() > 0) {
            this.rvDataListSong.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(RecyclerView.VERTICAL); // Chiều dọc
            this.rvDataListSong.setLayoutManager(layoutManager);

            this.rvDataListSong.setAdapter(new ListSongAdapter(FullPlayerActivity.dataSongArrayList));

            Log.d(TAG, FullPlayerActivity.dataSongArrayList.get(0).getName());
        } else {
            Log.d(TAG, "Lỗi! Không có dữ liệu");
        }
    }

    private void Event() {
    }
}
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

import com.thanguit.imusic.API.APIService;
import com.thanguit.imusic.API.DataService;
import com.thanguit.imusic.R;
import com.thanguit.imusic.adapters.ChartAdapter;
import com.thanguit.imusic.animations.ScaleAnimation;
import com.thanguit.imusic.models.Song;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChartFragment extends Fragment {

    private ArrayList<Song> songArrayList;

    private RecyclerView rvChart;

    private ScaleAnimation scaleAnimation;

    private static final String TAG = "ChartFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Mapping(view);
        Event();
    }

    private void Mapping(View view) {
        this.rvChart = (RecyclerView) view.findViewById(R.id.rvChart);
    }

    private void Event() {

        Handle_SongChart();
    }

    private void Handle_SongChart() {
        DataService dataService = APIService.getService(); // Khởi tạo Phương thức để đẩy lên
        Call<List<Song>> callBack = dataService.getSongChart();
        callBack.enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                songArrayList = (ArrayList<Song>) response.body();

                rvChart.setHasFixedSize(true);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                layoutManager.setOrientation(RecyclerView.VERTICAL); // Chiều dọc
                rvChart.setLayoutManager(layoutManager);
                rvChart.setAdapter(new ChartAdapter(songArrayList));

                Log.d(TAG, songArrayList.get(0).getName());
            }

            @Override
            public void onFailure(Call<List<Song>> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
    }
}
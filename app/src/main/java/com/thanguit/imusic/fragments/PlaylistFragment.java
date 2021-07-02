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
import android.widget.ImageView;
import android.widget.TextView;

import com.thanguit.imusic.API.APIService;
import com.thanguit.imusic.API.DataService;
import com.thanguit.imusic.R;
import com.thanguit.imusic.adapters.PlaylistHomeAdapter;
import com.thanguit.imusic.animations.ScaleAnimation;
import com.thanguit.imusic.models.Playlist;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaylistFragment extends Fragment {
    private static final String TAG = "PlaylistFragment";

    private ArrayList<Playlist> playlistArrayList;

    private RecyclerView rvPlaylist;

    private ImageView ivPlaylistMore;
    private TextView tvPlaylist;

    private ScaleAnimation scaleAnimation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_playlist, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Mapping(view);
        Handle_Playlist();
    }

    private void Mapping(View view) {
        this.rvPlaylist = view.findViewById(R.id.rvPlaylist);

        this.ivPlaylistMore = view.findViewById(R.id.ivPlaylistMore);
        this.scaleAnimation = new ScaleAnimation(getActivity(), this.ivPlaylistMore);
        this.scaleAnimation.Event_ImageView();

        this.tvPlaylist = view.findViewById(R.id.tvPlaylist);
        this.tvPlaylist.setSelected(true); // Text will be moved
    }

    private void Handle_Playlist() {
        DataService dataService = APIService.getService(); // Khởi tạo Phương thức để đẩy lên
        Call<List<Playlist>> callBack = dataService.getPlaylistCurrentDay(); // Gửi phương thức getPlaylistCurrentDay() -> trả về dữ liệu cho biến callBack
        callBack.enqueue(new Callback<List<Playlist>>() {
            @Override
            public void onResponse(Call<List<Playlist>> call, Response<List<Playlist>> response) {
                playlistArrayList = new ArrayList<>();
                playlistArrayList = (ArrayList<Playlist>) response.body(); // Lấy dữ liệu về đưa vào Arraylist

                if (playlistArrayList != null) {
                    rvPlaylist.setHasFixedSize(true);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    layoutManager.setOrientation(RecyclerView.VERTICAL); // Chiều dọc xuống
                    rvPlaylist.setLayoutManager(layoutManager);
                    rvPlaylist.setAdapter(new PlaylistHomeAdapter(getContext(), playlistArrayList));

                    Log.d(TAG, playlistArrayList.get(0).getImg());
                }
            }

            @Override
            public void onFailure(Call<List<Playlist>> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
    }
}
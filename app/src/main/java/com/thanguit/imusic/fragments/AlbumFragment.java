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
import com.thanguit.imusic.adapters.AlbumHomeAdapter;
import com.thanguit.imusic.adapters.ThemeHomeAdapter;
import com.thanguit.imusic.animations.ScaleAnimation;
import com.thanguit.imusic.models.Album;
import com.thanguit.imusic.models.Playlist;
import com.thanguit.imusic.models.Theme;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbumFragment extends Fragment {

    private ArrayList<Album> albumArrayList;

    private RecyclerView rvAlbum;

    private ImageView ivAlbumMore;
    private TextView tvAlbum;

    private ScaleAnimation scaleAnimation;

    private static final String TAG = "AlbumFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_album, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Mapping(view);
        Event();
    }

    private void Mapping(View view) {
        this.rvAlbum = (RecyclerView) view.findViewById(R.id.rvAlbum);

        this.ivAlbumMore = (ImageView) view.findViewById(R.id.ivAlbumMore);
        this.scaleAnimation = new ScaleAnimation(getActivity(), this.ivAlbumMore);
        this.scaleAnimation.Event_ImageView();

        this.tvAlbum = (TextView) view.findViewById(R.id.tvAlbum);
        this.tvAlbum.setSelected(true); // Text will be moved

    }

    private void Event() {

        Handle_Album();
    }

    private void Handle_Album() {
        DataService dataService = APIService.getService(); // Khởi tạo Phương thức để đẩy lên
        Call<List<Album>> callBack = dataService.getAlbumCurrentDay();
        callBack.enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                albumArrayList = (ArrayList<Album>) response.body();

                rvAlbum.setHasFixedSize(true);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                layoutManager.setOrientation(RecyclerView.HORIZONTAL); // Chiều ngang
                rvAlbum.setLayoutManager(layoutManager);
                rvAlbum.setAdapter(new AlbumHomeAdapter(albumArrayList));

                Log.d(TAG, albumArrayList.get(0).getImg());
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
    }
}
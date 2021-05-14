package com.thanguit.imusic.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.thanguit.imusic.R;
import com.thanguit.imusic.animations.ScaleAnimation;
import com.thanguit.imusic.models.Album;
import com.thanguit.imusic.models.Playlist;

import java.util.ArrayList;

public class AlbumFragment extends Fragment {

    private ArrayList<Album> albumArrayList;

    private RecyclerView rvAlbum;

    private ImageView ivAlbum;
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
    }
}
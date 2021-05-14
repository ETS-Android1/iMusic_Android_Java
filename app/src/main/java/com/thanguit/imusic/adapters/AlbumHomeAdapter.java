package com.thanguit.imusic.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.thanguit.imusic.R;
import com.thanguit.imusic.models.Album;

import java.util.ArrayList;

public class AlbumHomeAdapter extends RecyclerView.Adapter<AlbumHomeAdapter.ViewHolder> {

    private ArrayList<Album> albumArrayList;

    public AlbumHomeAdapter(ArrayList<Album> albumArrayList) {
        this.albumArrayList = albumArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_album_home, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumHomeAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return this.albumArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivAlbum;
        private CardView cvAlbum;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


        }
    }
}

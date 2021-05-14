package com.thanguit.imusic.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.thanguit.imusic.R;
import com.thanguit.imusic.models.Playlist;

import java.util.ArrayList;

public class PlaylistHomeAdapter extends RecyclerView.Adapter<PlaylistHomeAdapter.ViewHolder> {

    private ArrayList<Playlist> playlistArrayList;

    public PlaylistHomeAdapter(ArrayList<Playlist> playlistArrayList) {
        this.playlistArrayList = playlistArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_playlist_home, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistHomeAdapter.ViewHolder holder, int position) {
        Picasso.get()
                .load(this.playlistArrayList.get(position).getImg())
                .placeholder(R.drawable.ic_logo)
                .error(R.drawable.ic_logo)
                .into(holder.ivPlaylist);
    }

    @Override
    public int getItemCount() {
        return this.playlistArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivPlaylist;
        private CardView cvPlaylist;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.ivPlaylist = (ImageView) itemView.findViewById(R.id.ivPlaylistImage);
            this.cvPlaylist = (CardView) itemView.findViewById(R.id.cvPlaylist);
        }
    }
}

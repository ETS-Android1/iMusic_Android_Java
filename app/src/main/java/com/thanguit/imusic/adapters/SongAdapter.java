package com.thanguit.imusic.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.thanguit.imusic.R;
import com.thanguit.imusic.models.Song;

import java.util.ArrayList;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {

    private ArrayList<Song> songArrayList;

    public SongAdapter(ArrayList<Song> songArrayList) {
        this.songArrayList = songArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongAdapter.ViewHolder holder, int position) {
        Picasso.get()
                .load(this.songArrayList.get(position).getImg())
                .placeholder(R.drawable.ic_logo)
                .error(R.drawable.ic_logo)
                .into(holder.ivItemSong);
        holder.tvItemSongName.setText(this.songArrayList.get(position).getName());
        holder.tvItemSongSinger.setText(this.songArrayList.get(position).getSinger());
    }

    @Override
    public int getItemCount() {
        return this.songArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivItemSong, ivItemSongLove, ivItemSongMore;
        private TextView tvItemSongName, tvItemSongSinger;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.ivItemSong = (ImageView) itemView.findViewById(R.id.ivItemSong);
            this.ivItemSongLove = (ImageView) itemView.findViewById(R.id.ivItemSongLove);
            this.ivItemSongMore = (ImageView) itemView.findViewById(R.id.ivItemSongMore);

            this.tvItemSongName = (TextView) itemView.findViewById(R.id.tvItemSongName);
            this.tvItemSongName.setSelected(true); // Text will be moved

            this.tvItemSongSinger = (TextView) itemView.findViewById(R.id.tvItemSongSinger);
            this.tvItemSongSinger.setSelected(true); // Text will be moved
        }
    }
}

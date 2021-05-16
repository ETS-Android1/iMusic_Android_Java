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

public class ListSongAdapter extends RecyclerView.Adapter<ListSongAdapter.ViewHolder> {

    private ArrayList<Song> songArrayList;

    public ListSongAdapter(ArrayList<Song> songArrayList) {
        this.songArrayList = songArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_song, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListSongAdapter.ViewHolder holder, int position) {
        Picasso.get()
                .load(this.songArrayList.get(position).getImg())
                .placeholder(R.drawable.ic_logo)
                .error(R.drawable.ic_logo)
                .into(holder.ivListItemSong);
        holder.tvListItemSongName.setText(this.songArrayList.get(position).getName());
        holder.tvListItemSongSinger.setText(this.songArrayList.get(position).getSinger());
    }

    @Override
    public int getItemCount() {
        return this.songArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivListItemSong, ivListItemSongLove, ivListItemSongMore;
        private TextView tvListItemSongName, tvListItemSongSinger;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.ivListItemSong = (ImageView) itemView.findViewById(R.id.ivListItemSong);
            this.ivListItemSongLove = (ImageView) itemView.findViewById(R.id.ivListItemSongLove);
            this.ivListItemSongMore = (ImageView) itemView.findViewById(R.id.ivListItemSongMore);

            this.tvListItemSongName = (TextView) itemView.findViewById(R.id.tvListItemSongName);
            this.tvListItemSongName.setSelected(true); // Text will be moved

            this.tvListItemSongSinger = (TextView) itemView.findViewById(R.id.tvListItemSongSinger);
            this.tvListItemSongSinger.setSelected(true); // Text will be moved
        }
    }
}

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

public class ChartAdapter extends RecyclerView.Adapter<ChartAdapter.ViewHolder> {

    private ArrayList<Song> songArrayList;

    public ChartAdapter(ArrayList<Song> songArrayList) {
        this.songArrayList = songArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_top_song, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChartAdapter.ViewHolder holder, int position) {
        Picasso.get()
                .load(this.songArrayList.get(position).getImg())
                .placeholder(R.drawable.ic_logo)
                .error(R.drawable.ic_logo)
                .into(holder.ivSong);

        Handle_Position(holder.tvChartNumber, position);
        holder.tvSongName.setText(this.songArrayList.get(position).getName());
        holder.tvSongSinger.setText(this.songArrayList.get(position).getSinger());
        holder.tvLikeNumber.setText(this.songArrayList.get(position).getLike());
    }

    @Override
    public int getItemCount() {
        return this.songArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvChartNumber, tvSongName, tvSongSinger, tvLikeNumber;
        private ImageView ivSong;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.tvChartNumber = (TextView) itemView.findViewById(R.id.tvChartNumber);
            this.tvChartNumber.setSelected(true); // Text will be moved

            this.tvSongName = (TextView) itemView.findViewById(R.id.tvChartSongName);
            this.tvSongName.setSelected(true); // Text will be moved

            this.tvSongSinger = (TextView) itemView.findViewById(R.id.tvSongSinger);
            this.tvSongSinger.setSelected(true); // Text will be moved

            this.tvLikeNumber = (TextView) itemView.findViewById(R.id.tvLikeNumber);
            this.tvLikeNumber.setSelected(true); // Text will be moved

            this.ivSong = (ImageView) itemView.findViewById(R.id.ivSong);
        }
    }

    private void Handle_Position(TextView tvPosition, int position) {
        tvPosition.setText(String.valueOf(position + 1));
    }
}

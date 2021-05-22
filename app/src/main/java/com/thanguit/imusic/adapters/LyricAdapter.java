package com.thanguit.imusic.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thanguit.imusic.R;
import com.thanguit.imusic.models.Song;

import java.util.ArrayList;
import java.util.Objects;

public class LyricAdapter extends RecyclerView.Adapter<LyricAdapter.ViewHolder> {

    private ArrayList<Song> songArrayList;
    private static final String TAG = "LyricAdapter";

    public LyricAdapter(ArrayList<Song> songArrayList) {
        this.songArrayList = songArrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lyric, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LyricAdapter.ViewHolder holder, int position) {
        holder.tvLyrics.setText(this.songArrayList.get(position).getLyric().replace("\\n", Objects.requireNonNull(System.getProperty("line.separator"))));
    }

    @Override
    public int getItemCount() {
        return this.songArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvLyrics;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.tvLyrics = (TextView) itemView.findViewById(R.id.tvLyrics);
        }
    }
}

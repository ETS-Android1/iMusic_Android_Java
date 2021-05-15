package com.thanguit.imusic.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
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
        Picasso.get()
                .load(this.albumArrayList.get(position).getImg())
                .placeholder(R.drawable.ic_logo)
                .error(R.drawable.ic_logo)
                .into(holder.ivAlbum);
        holder.tvAlbumName.setText(this.albumArrayList.get(position).getName());
        holder.tvAlbumSinger.setText(this.albumArrayList.get(position).getSinger());
    }

    @Override
    public int getItemCount() {
        return this.albumArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CardView cvAlbum;
        private ImageView ivAlbum;
        private TextView tvAlbumName, tvAlbumSinger;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.cvAlbum = (CardView) itemView.findViewById(R.id.cvAlbum);
            this.ivAlbum = (ImageView) itemView.findViewById(R.id.ivAlbum);

            this.tvAlbumName = (TextView) itemView.findViewById(R.id.tvAlbumName);
            this.tvAlbumName.setSelected(true); // Text will be moved

            this.tvAlbumSinger = (TextView) itemView.findViewById(R.id.tvAlbumSinger);
            this.tvAlbumSinger.setSelected(true); // Text will be moved
        }
    }
}

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
import com.thanguit.imusic.animations.ScaleAnimation;
import com.thanguit.imusic.models.Theme;

import java.util.ArrayList;

public class ThemeHomeAdapter extends RecyclerView.Adapter<ThemeHomeAdapter.ViewHolder> {

    private ArrayList<Theme> themeArrayList;

    public ThemeHomeAdapter(ArrayList<Theme> themeArrayList) {
        this.themeArrayList = themeArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_theme_home, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThemeHomeAdapter.ViewHolder holder, int position) {
        Picasso.get()
                .load(this.themeArrayList.get(position).getImg())
                .placeholder(R.drawable.ic_logo)
                .error(R.drawable.ic_logo)
                .into(holder.ivTheme);
    }

    @Override
    public int getItemCount() {
        return this.themeArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivTheme;
        private CardView cvTheme;

        private ScaleAnimation scaleAnimation;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.ivTheme = (ImageView) itemView.findViewById(R.id.ivTheme);
        }
    }
}

package com.thanguit.imusic.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.thanguit.imusic.R;
import com.thanguit.imusic.activities.FullPlayerActivity;
import com.thanguit.imusic.models.Song;

import java.util.ArrayList;

public class ChartAdapter extends RecyclerView.Adapter<ChartAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Song> songArrayList;

    public ChartAdapter(Context context, ArrayList<Song> songArrayList) {
        this.context = context;
        this.songArrayList = songArrayList;
        notifyDataSetChanged();
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
                .into(holder.ivSongChartImage);

        Handle_Position(holder.tvChartNumber, position);
        holder.tvChartSongName.setText(this.songArrayList.get(position).getName());
        holder.tvChartSongSinger.setText(this.songArrayList.get(position).getSinger());
        holder.tvChartLikeNumber.setText(this.songArrayList.get(position).getLike());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), FullPlayerActivity.class);
            intent.putExtra("SONGCHART", songArrayList.get(position));
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return this.songArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvChartNumber;
        private final TextView tvChartSongName;
        private final TextView tvChartSongSinger;
        private final TextView tvChartLikeNumber;
        private final ImageView ivSongChartImage;
        private final ImageView ivChartSongMore;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.tvChartNumber = itemView.findViewById(R.id.tvChartNumber);
            this.tvChartNumber.setSelected(true); // Text will be moved

            this.tvChartSongName = itemView.findViewById(R.id.tvChartSongName);
            this.tvChartSongName.setSelected(true); // Text will be moved

            this.tvChartSongSinger = itemView.findViewById(R.id.tvChartSongSinger);
            this.tvChartSongSinger.setSelected(true); // Text will be moved

            this.tvChartLikeNumber = itemView.findViewById(R.id.tvChartLikeNumber);
            this.tvChartLikeNumber.setSelected(true); // Text will be moved

            this.ivSongChartImage = itemView.findViewById(R.id.ivSongChartImage);

            this.ivChartSongMore = itemView.findViewById(R.id.ivChartSongMore);
            this.ivChartSongMore.setVisibility(View.GONE);
        }
    }

    private void Handle_Position(TextView tvPosition, int position) {
        tvPosition.setText(String.valueOf(position + 1));

        if (position == 0) {
            tvPosition.setTextColor(context.getResources().getColor(R.color.colorMain4));
            tvPosition.setTypeface(Typeface.DEFAULT_BOLD);
        } else if (position == 1) {
            tvPosition.setTextColor(context.getResources().getColor(R.color.colorMain7));
            tvPosition.setTypeface(Typeface.DEFAULT_BOLD);
        } else if (position == 2) {
            tvPosition.setTextColor(context.getResources().getColor(R.color.colorMain8));
            tvPosition.setTypeface(Typeface.DEFAULT_BOLD);
        } else { // Ko biết tại sao, khí nào quay lại tìm hiểu sau
            tvPosition.setTextColor(context.getResources().getColor(R.color.colorLight7));
            tvPosition.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        }
    }
}

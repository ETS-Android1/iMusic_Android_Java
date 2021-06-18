package com.thanguit.imusic.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.thanguit.imusic.R;
import com.thanguit.imusic.activities.SongActivity;
import com.thanguit.imusic.models.Genre;

import java.util.ArrayList;
import java.util.List;

public class GenreAdapter extends BaseAdapter {
    private static final String TAG = "GenreAdapter";

    private AlertDialog alertDialog;

    private Context context;
    private List<Genre> genreList;

    public GenreAdapter(Context context, List<Genre> genreList) {
        this.context = context;
        this.genreList = genreList;
    }

    @Override
    public int getCount() {
        return genreList.size();
    }

    @Override
    public Object getItem(int position) {
        return genreList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) { // Ánh xạ lần đầu tiên
            holder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_genre, null);

            holder.ivGenreImage = convertView.findViewById(R.id.ivGenreImage);
            holder.tvGenreName = convertView.findViewById(R.id.tvGenreName);
            holder.tvGenreName.setSelected(true);

            convertView.setTag(holder); // Xét trạng thái đã ánh xạ
        } else {
            holder = (ViewHolder) convertView.getTag(); // Lấy trạng thái đã ánh xạ lần đầu để lần sau không phải ánh xạ nữa
        }

        Picasso.get()
                .load(this.genreList.get(position).getImg())
                .placeholder(R.drawable.ic_logo)
                .error(R.drawable.ic_logo)
                .into(holder.ivGenreImage);

        holder.tvGenreName.setText(this.genreList.get(position).getName());

        holder.ivGenreImage.setOnClickListener(v -> {
            Intent intent = new Intent(context, SongActivity.class);
            intent.putExtra("GENRE", genreList.get(position));
            context.startActivity(intent);
        });

        return convertView;
    }

    private class ViewHolder {
        ImageView ivGenreImage;
        TextView tvGenreName;
    }
}

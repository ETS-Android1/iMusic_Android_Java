package com.thanguit.imusic.adapters;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.thanguit.imusic.API.APIService;
import com.thanguit.imusic.API.DataService;
import com.thanguit.imusic.R;
import com.thanguit.imusic.SharedPreferences.DataLocalManager;
import com.thanguit.imusic.activities.FullPlayerActivity;
import com.thanguit.imusic.activities.PersonalPlaylistActivity;
import com.thanguit.imusic.animations.ScaleAnimation;
import com.thanguit.imusic.models.Song;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {
    private static final String TAG = "SongAdapter";

    private ArrayList<Song> favoriteSongArrayList;
    private ArrayList<Song> songArrayList;

    private ScaleAnimation scaleAnimation;

    public SongAdapter(ArrayList<Song> songArrayList) {
        this.songArrayList = songArrayList;
        notifyDataSetChanged();
    }

    public void Update_Data() {
        notifyDataSetChanged();
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

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), FullPlayerActivity.class);
            intent.putExtra("SONG", songArrayList.get(position));
            v.getContext().startActivity(intent);
        });


        Handle_Favourite_Icon_Color(holder.ivItemSongLove, position);


        holder.ivItemSongLove.setOnClickListener(v -> {
//            holder.ivItemSongLove.setImageResource(R.drawable.ic_favorite);
        });
    }

    private void Handle_Favourite_Icon_Color(ImageView imageView, int position) {
        DataService dataService = APIService.getService(); // Khởi tạo Phương thức để đẩy lên
        Call<List<Song>> callBack = dataService.getFavoriteSongUser(DataLocalManager.getUserID());
        callBack.enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                favoriteSongArrayList = (ArrayList<Song>) response.body();

                if (favoriteSongArrayList != null && favoriteSongArrayList.size() > 0) { // Trường hợp người dùng đã có bài hát yêu thích
                    for (int i = 0; i < favoriteSongArrayList.size(); i++) {
                        if (songArrayList.get(position).getId().equals(favoriteSongArrayList.get(i).getId())) {
                            imageView.setImageResource(R.drawable.ic_favorite);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Song>> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.songArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivItemSong;
        private ImageView ivItemSongLove;
        private ImageView ivItemSongMore;
        private TextView tvItemSongName;
        private TextView tvItemSongSinger;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.ivItemSong = itemView.findViewById(R.id.ivItemSong);

            this.ivItemSongLove = itemView.findViewById(R.id.ivItemSongLove);
            scaleAnimation = new ScaleAnimation(itemView.getContext(), this.ivItemSongLove);
            scaleAnimation.Event_ImageView();

            this.ivItemSongMore = itemView.findViewById(R.id.ivItemSongMore);

            this.tvItemSongName = itemView.findViewById(R.id.tvItemSongName);
            this.tvItemSongName.setSelected(true); // Text will be moved

            this.tvItemSongSinger = itemView.findViewById(R.id.tvItemSongSinger);
            this.tvItemSongSinger.setSelected(true); // Text will be moved
        }
    }
}

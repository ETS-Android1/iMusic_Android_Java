package com.thanguit.imusic.adapters;

import android.content.Context;
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
import com.thanguit.imusic.API.UserPlaylistManager;
import com.thanguit.imusic.R;
import com.thanguit.imusic.SharedPreferences.DataLocalManager;
import com.thanguit.imusic.activities.FullPlayerActivity;
import com.thanguit.imusic.activities.PersonalPlaylistActivity;
import com.thanguit.imusic.animations.ScaleAnimation;
import com.thanguit.imusic.models.Song;
import com.thanguit.imusic.models.Status;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {
    private static final String TAG = "SongAdapter";

    private ArrayList<Song> favoriteSongArrayList = new ArrayList<>();

    private List<Integer> idFavoriteSong;
    //    private List<Integer> listSongID = new ArrayList<>();
//    private ArrayList<Song> getIDFavoriteSongArrayList = new ArrayList<>();

    private ArrayList<Status> statusArrayList = new ArrayList<>();

    private Context context;
    private ArrayList<Song> songArrayList = new ArrayList<>();

    public SongAdapter(Context context, ArrayList<Song> songArrayList) {
        this.context = context;
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

        Handle_Favourite_Icon_Color(holder.ivItemSongLove, position); // Load những bài hát yếu thích của người dùng

        holder.ivItemSongLove.setOnClickListener(v -> {
            if (Is_Exist_FavoriteSong(songArrayList.get(position).getId())) {
                Handle_Add_Delete_Favorite_Song("delete", holder.ivItemSongLove, position);
                notifyDataSetChanged();
            } else {
                Handle_Add_Delete_Favorite_Song("insert", holder.ivItemSongLove, position);
            }
        });
    }

    private void Handle_Add_Delete_Favorite_Song(String action, ImageView imageView, int position) {
        DataService dataService = APIService.getService(); // Khởi tạo Phương thức để đẩy lên
        Call<List<Status>> callBack = dataService.addDeleteFavoriteSong(action, DataLocalManager.getUserID(), songArrayList.get(position).getId());
        callBack.enqueue(new Callback<List<Status>>() {
            @Override
            public void onResponse(Call<List<Status>> call, Response<List<Status>> response) {
                statusArrayList = (ArrayList<Status>) response.body();

                if (statusArrayList != null) {
                    if (action.equals("insert")) {
                        if (statusArrayList.get(0).getStatus() == 1) {
                            imageView.setImageResource(R.drawable.ic_favorite);
                            Toast.makeText(context, "Đã thêm \"" + songArrayList.get(position).getName() + "\" vào bài hát yêu thích", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Thêm \"" + songArrayList.get(position).getName() + "\" không thành công", Toast.LENGTH_SHORT).show();
                        }
                    } else if (action.equals("delete")) {
                        if (statusArrayList.get(0).getStatus() == 1) {
                            imageView.setImageResource(R.drawable.ic_not_favorite);
                            Toast.makeText(context, "Đã xóa \"" + songArrayList.get(position).getName() + "\" ra khỏi bài hát yêu thích", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Xóa \"" + songArrayList.get(position).getName() + "\" không thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Status>> call, Throwable t) {
                Log.d(TAG, "Handle_Add_Delete_Favorite_Song(Error)" + t.getMessage());
            }
        });
    }

    private void Handle_Favourite_Icon_Color(ImageView imageView, int position) {
        DataService dataService = APIService.getService(); // Khởi tạo Phương thức để đẩy lên
        Call<List<Song>> callBack = dataService.getFavoriteSongUser(DataLocalManager.getUserID());
        callBack.enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                idFavoriteSong = new ArrayList<>(); // Nhớ khởi tạo nha Thắng ơi, chú hay quên lắm (Thắng tự nhắc bản thân)
                favoriteSongArrayList = (ArrayList<Song>) response.body();

                if (favoriteSongArrayList != null && favoriteSongArrayList.size() > 0) {
                    for (int i = 0; i < favoriteSongArrayList.size(); i++) {
                        idFavoriteSong.add(favoriteSongArrayList.get(i).getId()); // Thêm id bài hát yêu thích của người dùng vảo list
                        Log.d(TAG, "ID: " + favoriteSongArrayList.get(i).getId());

                        if (songArrayList.get(position).getId() == favoriteSongArrayList.get(i).getId()) {
                            imageView.setImageResource(R.drawable.ic_favorite);

                            Log.d(TAG, "Bài hát yêu thích: " + favoriteSongArrayList.get(i).getName());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Song>> call, Throwable t) {
                Log.d(TAG, "Handle_Favourite_Icon_Color(Error): " + t.getMessage());
            }
        });
    }

    public boolean Is_Exist_FavoriteSong(int id) {
        List<Integer> idFavoriteSong = this.idFavoriteSong;
        return idFavoriteSong.contains(id);
    }

//    public List<Integer> GetID_FavoriteSong() {
//        DataService dataService = APIService.getService(); // Khởi tạo Phương thức để đẩy lên
//        Call<List<Song>> callBack = dataService.getFavoriteSongUser(DataLocalManager.getUserID());
//        callBack.enqueue(new Callback<List<Song>>() {
//            @Override
//            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
//                idFavoriteSong = new ArrayList<>(); // Nhớ khởi tạo nha Thắng ơi, chú hay quên lắm (Thắng tự nhắc bản thân)
//
//                getIDFavoriteSongArrayList = (ArrayList<Song>) response.body();
//                if (getIDFavoriteSongArrayList != null) {
//                    for (int i = 0; i < getIDFavoriteSongArrayList.size(); i++) {
//                        idFavoriteSong.add(getIDFavoriteSongArrayList.get(i).getId());
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Song>> call, Throwable t) {
//                Log.d(TAG, "GetID_FavoriteSong(Error): " + t.getMessage());
//            }
//        });
//
//        return idFavoriteSong;
//    }


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

        private ScaleAnimation scaleAnimation;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.ivItemSong = itemView.findViewById(R.id.ivItemSong);

            this.ivItemSongLove = itemView.findViewById(R.id.ivItemSongLove);
            this.scaleAnimation = new ScaleAnimation(itemView.getContext(), this.ivItemSongLove);
            this.scaleAnimation.Event_ImageView();

            this.ivItemSongMore = itemView.findViewById(R.id.ivItemSongMore);
            this.scaleAnimation = new ScaleAnimation(itemView.getContext(), this.ivItemSongMore);
            this.scaleAnimation.Event_ImageView();

            this.tvItemSongName = itemView.findViewById(R.id.tvItemSongName);
            this.tvItemSongName.setSelected(true); // Text will be moved

            this.tvItemSongSinger = itemView.findViewById(R.id.tvItemSongSinger);
            this.tvItemSongSinger.setSelected(true); // Text will be moved
        }
    }
}

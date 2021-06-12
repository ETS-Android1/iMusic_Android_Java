package com.thanguit.imusic.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.thanguit.imusic.R;
import com.thanguit.imusic.animations.ScaleAnimation;
import com.thanguit.imusic.models.Theme;
import com.thanguit.imusic.models.UserPlaylist;

import java.util.ArrayList;

public class UserPlaylistAdapter extends RecyclerView.Adapter<UserPlaylistAdapter.ViewHolder> {
    private Context context;
    private ArrayList<UserPlaylist> userPlaylistArrayList;

    public UserPlaylistAdapter(Context context, ArrayList<UserPlaylist> userPlaylistArrayList) {
        this.context = context;
        this.userPlaylistArrayList = userPlaylistArrayList;
    }

    public void Update_Data() {
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_playlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserPlaylistAdapter.ViewHolder holder, int position) {
        holder.tvPlaylistName.setText(userPlaylistArrayList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return this.userPlaylistArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cvPlaylistCover;
        private TextView tvPlaylistName;
        private TextView tvNumberPlaylist;
        private ImageView ivPlaylistMore;

        private ScaleAnimation scaleAnimation;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.cvPlaylistCover = itemView.findViewById(R.id.cvPlaylistCover);

            this.tvPlaylistName = itemView.findViewById(R.id.tvPlaylistName);
            this.tvPlaylistName.setSelected(true);

            this.tvNumberPlaylist = itemView.findViewById(R.id.tvNumberPlaylist);

            this.ivPlaylistMore = itemView.findViewById(R.id.ivPlaylistMore);
            this.scaleAnimation = new ScaleAnimation(itemView.getContext(), this.ivPlaylistMore);
            this.scaleAnimation.Event_ImageView();
        }
    }
}

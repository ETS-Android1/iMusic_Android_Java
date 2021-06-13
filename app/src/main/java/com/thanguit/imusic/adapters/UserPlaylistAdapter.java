package com.thanguit.imusic.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.thanguit.imusic.API.APIService;
import com.thanguit.imusic.API.DataService;
import com.thanguit.imusic.R;
import com.thanguit.imusic.SharedPreferences.DataLocalManager;
import com.thanguit.imusic.activities.PersonalPlaylistActivity;
import com.thanguit.imusic.animations.ScaleAnimation;
import com.thanguit.imusic.models.Status;
import com.thanguit.imusic.models.Theme;
import com.thanguit.imusic.models.UserPlaylist;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserPlaylistAdapter extends RecyclerView.Adapter<UserPlaylistAdapter.ViewHolder> {
    private static final String TAG = "UserPlaylistAdapter";

    private Dialog dialog;

    private Context context;
    private ArrayList<UserPlaylist> userPlaylistArrayList;
    private ScaleAnimation scaleAnimation;

    private ArrayList<Status> statusArrayList;

    private final String ACTION_UPDATE_PLAYLIST = "update";
    private final String ACTION_DELETE_PLAYLIST = "delete";
    private final String ACTION_DELETEALL_PLAYLIST = "deleteall";

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

        holder.ivPlaylistMore.setOnClickListener(v -> Open_Info_Playlist_Dialog(Gravity.BOTTOM, position));

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, PersonalPlaylistActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("IDPLAYLIST", userPlaylistArrayList.get(position).getYouID());
            bundle.putString("TITLEPLAYLIST", holder.tvPlaylistName.getText().toString());

            intent.putExtras(bundle);
            context.startActivity(intent);
        });
    }

    private void Open_Info_Playlist_Dialog(int gravity, int position) {
        this.dialog = new Dialog(this.context);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_userplaylist_more);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // Set màu mờ mờ cho background dialog, che đi activity chính, nhưng vẫn có thể thấy được một phần activity

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        dialog.setCancelable(true); // Bấm ra chỗ khác sẽ thoát dialog

        TextView tvInfoPlaylistName = dialog.findViewById(R.id.tvInfoPlaylistName);
        tvInfoPlaylistName.setSelected(true); // Text will be moved
        tvInfoPlaylistName.setText(String.valueOf(userPlaylistArrayList.get(position).getName()));

//        RelativeLayout rlAddPlaylist = dialog.findViewById(R.id.rlAddPlaylist);
        RelativeLayout rlEditPlaylist = dialog.findViewById(R.id.rlEditPlaylist);
        RelativeLayout rlDeletePlaylist = dialog.findViewById(R.id.rlDeletePlaylist);
        RelativeLayout rlDeleteAllPlaylist = dialog.findViewById(R.id.rlDeleteAllPlaylist);
        RelativeLayout rlCloseInfoPlaylist = dialog.findViewById(R.id.rlCloseInfoPlaylist);

//        this.scaleAnimation = new ScaleAnimation(context, rlAddPlaylist);
//        this.scaleAnimation.Event_RelativeLayout();

        this.scaleAnimation = new ScaleAnimation(context, rlEditPlaylist);
        this.scaleAnimation.Event_RelativeLayout();
        rlEditPlaylist.setOnClickListener(v -> {

        });

        this.scaleAnimation = new ScaleAnimation(context, rlDeletePlaylist);
        this.scaleAnimation.Event_RelativeLayout();
        rlDeletePlaylist.setOnClickListener(v -> {
            Handle_Add_Update_Delete_DeleteAll_UserPlaylist(ACTION_DELETE_PLAYLIST, userPlaylistArrayList.get(position).getYouID(), DataLocalManager.getUserID(), userPlaylistArrayList.get(position).getName(), position);
        });

        this.scaleAnimation = new ScaleAnimation(context, rlDeleteAllPlaylist);
        this.scaleAnimation.Event_RelativeLayout();
        rlDeleteAllPlaylist.setOnClickListener(v -> {

        });

        this.scaleAnimation = new ScaleAnimation(context, rlCloseInfoPlaylist);
        this.scaleAnimation.Event_RelativeLayout();
        rlCloseInfoPlaylist.setOnClickListener(v -> {
            dialog.dismiss();
        });

//        btnDialogCreate.setOnClickListener(v -> {
//            String playlistName = etDialogContentPlaylist.getText().toString().trim();
//            if (playlistName.isEmpty()) {
//                Toast.makeText(v.getContext(), R.string.toast12, Toast.LENGTH_SHORT).show();
//            } else {
////                Handle_Add_Update_Delete_DeleteAll_UserPlaylist("insert", 0, DataLocalManager.getUserID(), playlistName);
//
//                DataService dataService = APIService.getService();
//                Call<List<Status>> callBack = dataService.addUpdateDeleteUserPlaylist("insert", 0, DataLocalManager.getUserID(), playlistName);
//                callBack.enqueue(new Callback<List<Status>>() {
//                    @Override
//                    public void onResponse(Call<List<Status>> call, Response<List<Status>> response) {
//                        statusArrayList = new ArrayList<>();
//                        statusArrayList = (ArrayList<Status>) response.body();
//
//                        if (statusArrayList != null) {
//                            if (statusArrayList.get(0).getStatus() == 1) {
//                                userPlaylistAdapter.Update_Data();
//                                Toast.makeText(v.getContext(), R.string.toast13, Toast.LENGTH_SHORT).show();
//                                dialog.dismiss();
//                            } else if (statusArrayList.get(0).getStatus() == 2) {
//                                Toast.makeText(v.getContext(), R.string.toast14, Toast.LENGTH_SHORT).show();
//                                dialog.dismiss();
//                            } else if (statusArrayList.get(0).getStatus() == 3) {
//                                Toast.makeText(v.getContext(), R.string.toast15, Toast.LENGTH_SHORT).show();
//                            } else {
//                                Toast.makeText(v.getContext(), R.string.toast11, Toast.LENGTH_SHORT).show();
//                                dialog.dismiss();
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<List<Status>> call, Throwable t) {
//                        Log.d(TAG, "Handle_Add_Update_Delete_DeleteAll_UserPlaylist(Error): " + t.getMessage());
//                    }
//                });
//            }
//        });

        dialog.show(); // câu lệnh này sẽ hiển thị Dialog lên
    }

    private void Handle_Add_Update_Delete_DeleteAll_UserPlaylist(String action, int playlistID, String userID, String playlistName, int position) {
        DataService dataService = APIService.getService();
        Call<List<Status>> callBack = dataService.addUpdateDeleteUserPlaylist(action, playlistID, userID, playlistName);
        callBack.enqueue(new Callback<List<Status>>() {
            @Override
            public void onResponse(Call<List<Status>> call, Response<List<Status>> response) {
                statusArrayList = new ArrayList<>();
                statusArrayList = (ArrayList<Status>) response.body();

                if (statusArrayList != null) {
                    if (statusArrayList.get(0).getStatus() == 1) {
                        userPlaylistArrayList.remove(position);
                        notifyDataSetChanged();
                        dialog.dismiss();
                        Toast.makeText(context, R.string.toast16, Toast.LENGTH_SHORT).show();
                    } else if (statusArrayList.get(0).getStatus() == 2) {
                        dialog.dismiss();
                        Toast.makeText(context, R.string.toast17, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, R.string.toast11, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Status>> call, Throwable t) {
                dialog.dismiss();
                Log.d(TAG, "Handle_Add_Update_Delete_DeleteAll_UserPlaylist(Error): " + t.getMessage());
            }
        });
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
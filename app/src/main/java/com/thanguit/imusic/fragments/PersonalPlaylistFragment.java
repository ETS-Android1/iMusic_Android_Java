package com.thanguit.imusic.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.thanguit.imusic.API.APIService;
import com.thanguit.imusic.API.DataService;
import com.thanguit.imusic.R;
import com.thanguit.imusic.SharedPreferences.DataLocalManager;
import com.thanguit.imusic.activities.FullPlayerActivity;
import com.thanguit.imusic.activities.MainActivity;
import com.thanguit.imusic.activities.PersonalPageActivity;
import com.thanguit.imusic.activities.PersonalPlaylistActivity;
import com.thanguit.imusic.adapters.SongAdapter;
import com.thanguit.imusic.animations.LoadingDialog;
import com.thanguit.imusic.animations.ScaleAnimation;
import com.thanguit.imusic.models.Song;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonalPlaylistFragment extends Fragment {
    private LinearLayout llFrameLoveSong;

    private TextView tvNumberPlaylist;
    private TextView tvNumberSongLove;
    private ImageView ivAddPlaylist;
    private TextView tvTitleLoveSong;

    private RecyclerView rvYourPlaylist;

    private ScaleAnimation scaleAnimation;
    private LoadingDialog loadingDialog;

    private ArrayList<Song> songArrayList;

    private static final String TAG = "PPFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_personal_playlist, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Mapping(view);
        Handle_Number_Favorite_Song();
        Event();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.loadingDialog = new LoadingDialog(getActivity());
        this.loadingDialog.Start_Loading();
        Handle_Number_Favorite_Song();
    }

    @Override
    public void onPause() {
        super.onPause();
        Handle_Number_Favorite_Song();
    }

    @Override
    public void onStop() {
        super.onStop();
        Handle_Number_Favorite_Song();
    }


    private void Mapping(View view) {
        this.llFrameLoveSong = view.findViewById(R.id.llFrameLoveSong);
        this.tvNumberPlaylist = view.findViewById(R.id.tvNumberPlaylist);
        this.tvNumberSongLove = view.findViewById(R.id.tvNumberSongLove);
        this.ivAddPlaylist = view.findViewById(R.id.ivAddPlaylist);
        this.tvTitleLoveSong = view.findViewById(R.id.tvTitleLoveSong);

        this.rvYourPlaylist = view.findViewById(R.id.rvYourPlaylist);
    }

    private void Event() {
        this.scaleAnimation = new ScaleAnimation(getContext(), this.ivAddPlaylist);
        this.scaleAnimation.Event_ImageView();
        this.ivAddPlaylist.setOnClickListener(v -> {
            Open_Dialog(Gravity.CENTER);
        });

        this.llFrameLoveSong.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), PersonalPlaylistActivity.class);
            intent.putExtra("FAVORITESONG", this.tvTitleLoveSong.getText());
            startActivity(intent);
        });
    }

    private void Open_Dialog(int gravity) {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_add_playlist);

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

        EditText etDialogContentPlaylist = dialog.findViewById(R.id.etDialogContentPlaylist);
        Button btnDialogCancel = dialog.findViewById(R.id.btnDialogCancel);
        Button btnDialogCreate = dialog.findViewById(R.id.btnDialogCreate);

        dialog.setOnShowListener(dialogInterface -> {
            etDialogContentPlaylist.requestFocus(); // When Activity show, Searchbox will be focused
        });

        this.scaleAnimation = new ScaleAnimation(getContext(), btnDialogCancel);
        this.scaleAnimation.Event_Button();
        btnDialogCancel.setOnClickListener(v -> {
            dialog.dismiss();
        });

        this.scaleAnimation = new ScaleAnimation(getContext(), btnDialogCreate);
        this.scaleAnimation.Event_Button();
        btnDialogCreate.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.show(); // câu lệnh này sẽ hiển thị Dialog lên
    }

    private void Handle_Number_Favorite_Song() {
        DataService dataService = APIService.getService(); // Khởi tạo Phương thức để đẩy lên
        Call<List<Song>> callBack = dataService.getFavoriteSongUser(DataLocalManager.getUserID());
        callBack.enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                songArrayList = (ArrayList<Song>) response.body();
                if (songArrayList != null) {
                    tvNumberSongLove.setText(String.valueOf(songArrayList.size()));
                    loadingDialog.Cancel_Loading();

                    Log.d(TAG, "Number Favorite Song: " + songArrayList.size());
                } else {
                    tvNumberSongLove.setText("0");
                }
            }

            @Override
            public void onFailure(Call<List<Song>> call, Throwable t) {
                Log.d(TAG, "Handle_Number_Favorite_Song(Error): " + t.getMessage());
            }
        });
    }
}
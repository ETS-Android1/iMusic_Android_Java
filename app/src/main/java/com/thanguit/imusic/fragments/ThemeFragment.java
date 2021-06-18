package com.thanguit.imusic.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.thanguit.imusic.API.APIService;
import com.thanguit.imusic.API.DataService;
import com.thanguit.imusic.R;
import com.thanguit.imusic.adapters.ThemeHomeAdapter;
import com.thanguit.imusic.animations.ScaleAnimation;
import com.thanguit.imusic.models.Theme;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThemeFragment extends Fragment {
    private static final String TAG = "ThemeFragment";

    private List<Theme> themeArrayList;

    private RecyclerView rvTheme;

    private ImageView ivThemeMore;
    private TextView tvTheme1;

    private ScaleAnimation scaleAnimation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_theme, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Mapping(view);
        Handle_Theme();
    }

    private void Mapping(View view) {
        this.rvTheme = view.findViewById(R.id.rvTheme);

        this.ivThemeMore = view.findViewById(R.id.ivThemeMore);
        this.scaleAnimation = new ScaleAnimation(getActivity(), this.ivThemeMore);
        this.scaleAnimation.Event_ImageView();

        this.tvTheme1 = view.findViewById(R.id.tvTheme1);
        this.tvTheme1.setSelected(true); // Text will be moved

    }

    private void Handle_Theme() {
        DataService dataService = APIService.getService(); // Khởi tạo Phương thức để đẩy lên
        Call<List<Theme>> callBack = dataService.getThemeCurrentDay(); // Gửi phương thức getThemeCurrentDay() -> trả về dữ liệu cho biến callBack
        callBack.enqueue(new Callback<List<Theme>>() {
            @Override
            public void onResponse(Call<List<Theme>> call, Response<List<Theme>> response) {
                themeArrayList = new ArrayList<>();
                themeArrayList = response.body();

                if (themeArrayList != null) {
                    rvTheme.setHasFixedSize(true);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    layoutManager.setOrientation(RecyclerView.HORIZONTAL); // Chiều ngang
                    rvTheme.setLayoutManager(layoutManager);

                    rvTheme.setAdapter(new ThemeHomeAdapter(getContext(), themeArrayList));

                    Log.d(TAG, themeArrayList.get(0).getImg());
                }
            }

            @Override
            public void onFailure(Call<List<Theme>> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
    }
}
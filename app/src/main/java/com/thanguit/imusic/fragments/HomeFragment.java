package com.thanguit.imusic.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.thanguit.imusic.API.APIService;
import com.thanguit.imusic.API.DataService;
import com.thanguit.imusic.R;
import com.thanguit.imusic.activities.FullPlayerActivity;
import com.thanguit.imusic.adapters.SliderAdapter;
import com.thanguit.imusic.models.Slider;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private FragmentTransaction fragmentTransaction;
    private FrameLayout flPlaylistFragment;
    private FrameLayout flFragmentTheme;

    private SliderView sliderView;
    private SliderAdapter sliderAdapter;
    private ArrayList<Slider> sliderArrayList;

    private static final String TAG = "HomeFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Mapping(view);
        Handle_Slider();
    }

    private void Mapping(View view) {

        this.flPlaylistFragment = view.findViewById(R.id.flFragmentPlaylist);
        this.flFragmentTheme = view.findViewById(R.id.flFragmentTheme);

        this.fragmentTransaction = getChildFragmentManager().beginTransaction();
        this.fragmentTransaction.add(R.id.flFragmentAlbum, new AlbumFragment()); // Thêm AlbumTheme vào HomeFragment
        this.fragmentTransaction.add(R.id.flFragmentPlaylist, new PlaylistFragment()); // Thêm FragmentPlaylist vào HomeFragment
        this.fragmentTransaction.add(R.id.flFragmentTheme, new ThemeFragment()); // Thêm FragmentTheme vào HomeFragment
        this.fragmentTransaction.commit(); // Thực hiện gắn Fragment

        this.sliderView = view.findViewById(R.id.isvSlider);
    }

    private void Handle_Slider() {
        DataService dataService = APIService.getService(); // Khởi tạo Phương thức để đẩy lên
        Call<List<Slider>> callBack = dataService.getSlider(); // Gửi phương thức getSlider() -> trả về dữ liệu cho biến callBack
        callBack.enqueue(new Callback<List<Slider>>() {
            @Override
            public void onResponse(Call<List<Slider>> call, Response<List<Slider>> response) {
                sliderArrayList = (ArrayList<Slider>) response.body(); // Lấy dữ liệu về đưa vào Arraylist

                sliderAdapter = new SliderAdapter(sliderArrayList);
                sliderView.setSliderTransformAnimation(SliderAnimations.ZOOMOUTTRANSFORMATION);
                sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
                sliderView.setSliderAdapter(sliderAdapter);

                Log.d(TAG, sliderArrayList.get(0).getImage());
            }

            @Override
            public void onFailure(Call<List<Slider>> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
    }
}

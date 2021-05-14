package com.thanguit.imusic.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

    private SliderView sliderView;
    private List<Integer> imageSliders = new ArrayList<Integer>();

    private Button button;

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
        Event();
    }

    private void Mapping(View view) {
        this.sliderView = (SliderView) view.findViewById(R.id.isvSlider);
        this.button = (Button) view.findViewById(R.id.btnTest);

        Handle_Slider();

    }

    private void Event() {
        this.button.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), FullPlayerActivity.class);
            startActivity(intent);
        });
    }

    private void Handle_Slider() {
        DataService dataService = APIService.getService(); // Khởi tạo Phương thức để đẩy lên
        Call<List<Slider>> callBack = dataService.getSlider(); // Gửi phương thức getSlider() -> trả về dữ liệu cho biến callBack
        callBack.enqueue(new Callback<List<Slider>>() {
            @Override
            public void onResponse(Call<List<Slider>> call, Response<List<Slider>> response) {
                ArrayList<Slider> sliderArrayList = (ArrayList<Slider>) response.body(); // Lấy dữ liệu về đưa vào Arraylist

                SliderAdapter sliderAdapter = new SliderAdapter(sliderArrayList);
                sliderView.setSliderAdapter(sliderAdapter);

                sliderView.setSliderTransformAnimation(SliderAnimations.ZOOMOUTTRANSFORMATION);
                sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
                Log.d(TAG, sliderArrayList.get(0).getImage());
            }

            @Override
            public void onFailure(Call<List<Slider>> call, Throwable t) {
                Log.d(TAG, t.getMessage());
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}

package com.thanguit.imusic.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.thanguit.imusic.R;
import com.thanguit.imusic.adapters.SliderAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private SliderView sliderView;
    private List<Integer> imageSliders = new ArrayList<Integer>();

    public HomeFragment() {
        // Required empty public constructor
    }

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
        this.imageSliders.add(R.drawable.slide_1);
        this.imageSliders.add(R.drawable.slide_2);
        this.imageSliders.add(R.drawable.slide_3);
        this.imageSliders.add(R.drawable.slide_4);
        this.imageSliders.add(R.drawable.slide_5);
        this.imageSliders.add(R.drawable.slide_6);
        this.imageSliders.add(R.drawable.slide_7);

        SliderAdapter sliderAdapter = new SliderAdapter(this.imageSliders);
        this.sliderView.setSliderAdapter(sliderAdapter);
        this.sliderView.setSliderTransformAnimation(SliderAnimations.ZOOMOUTTRANSFORMATION);
        this.sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
    }

    private void Event() {

    }


}

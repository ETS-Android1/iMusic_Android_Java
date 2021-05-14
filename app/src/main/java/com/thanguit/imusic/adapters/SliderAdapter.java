package com.thanguit.imusic.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;
import com.thanguit.imusic.R;
import com.thanguit.imusic.models.Slider;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.MyViewHolder> {

    private ArrayList<Slider> imageSliders;

    public SliderAdapter(ArrayList<Slider> imageSliders) {
        this.imageSliders = imageSliders;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_slider, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int position) {
        Picasso.get()
                .load(this.imageSliders.get(position).getImage())
                .placeholder(R.drawable.ic_logo)
                .error(R.drawable.ic_logo)
                .into(viewHolder.imageView);
    }

    @Override
    public int getCount() {
        return this.imageSliders.size();
    }

    public class MyViewHolder extends SliderViewAdapter.ViewHolder {
        private ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);

            this.imageView = (ImageView) itemView.findViewById(R.id.ivImage);
        }
    }
}

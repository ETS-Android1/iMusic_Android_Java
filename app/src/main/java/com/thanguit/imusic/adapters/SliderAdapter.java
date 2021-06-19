package com.thanguit.imusic.adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;
import com.thanguit.imusic.API.APIService;
import com.thanguit.imusic.API.DataService;
import com.thanguit.imusic.R;
import com.thanguit.imusic.activities.FullPlayerActivity;
import com.thanguit.imusic.models.Slider;
import com.thanguit.imusic.models.Song;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.MyViewHolder> {
    private static final String TAG = "SliderAdapter";

    private AlertDialog alertDialog;

    private Context context;
    private ArrayList<Slider> imageSliders;

    private ArrayList<Song> songArrayList;

    public SliderAdapter(Context context, ArrayList<Slider> imageSliders) {
        this.context = context;
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
                .into(viewHolder.ivSliderImage);

        viewHolder.itemView.setOnClickListener(v -> {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(v.getContext());
            View view = LayoutInflater.from(v.getContext()).inflate(R.layout.layout_loading_dialog, null);
            alertBuilder.setView(view);
            alertBuilder.setCancelable(true);
            this.alertDialog = alertBuilder.create();
            this.alertDialog.show();

            Handle_Song_Slider(imageSliders.get(position).getSongID());
        });
    }

    private void Handle_Song_Slider(int songID) {
        DataService dataService = APIService.getService(); // Khởi tạo Phương thức để đẩy lên
        Call<List<Song>> callBack = dataService.getSongFromSlider(songID); // Gửi phương thức getSlider() -> trả về dữ liệu cho biến callBack
        callBack.enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                songArrayList = new ArrayList<>();
                songArrayList = (ArrayList<Song>) response.body();

                if (songArrayList != null && songArrayList.size() > 0) {
                    Intent intent = new Intent(context, FullPlayerActivity.class);
                    intent.putExtra("SONGSLIDER", songArrayList);
                    context.startActivity(intent);

                    alertDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<Song>> call, Throwable t) {
                alertDialog.dismiss();
                Log.d(TAG, "Handle_Song_Slider(Error): " + t.getMessage());
            }
        });
    }

    @Override
    public int getCount() {
        return this.imageSliders.size();
    }

    public class MyViewHolder extends SliderViewAdapter.ViewHolder {
        private ImageView ivSliderImage;

        public MyViewHolder(View itemView) {
            super(itemView);

            this.ivSliderImage = (ImageView) itemView.findViewById(R.id.ivSliderImage);
        }
    }
}

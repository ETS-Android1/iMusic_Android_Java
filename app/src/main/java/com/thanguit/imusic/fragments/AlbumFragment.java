package com.thanguit.imusic.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.thanguit.imusic.API.APIService;
import com.thanguit.imusic.API.DataService;
import com.thanguit.imusic.R;
import com.thanguit.imusic.adapters.AlbumHomeAdapter;
import com.thanguit.imusic.animations.ScaleAnimation;
import com.thanguit.imusic.models.Album;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbumFragment extends Fragment {
    private static final String TAG = "AlbumFragment";

    private List<Album> albumArrayList;

//    private RecyclerView rvAlbum;

    private ViewPager2 vpg2Album;

    private ImageView ivAlbumMore;
    private TextView tvAlbum;

    private ScaleAnimation scaleAnimation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_album, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Mapping(view);
        Handle_Album();
    }

    private void Mapping(View view) {
//        this.rvAlbum = (RecyclerView) view.findViewById(R.id.rvAlbum);
        this.vpg2Album = (ViewPager2) view.findViewById(R.id.vpg2Album);

        this.ivAlbumMore = (ImageView) view.findViewById(R.id.ivAlbumMore);
        this.scaleAnimation = new ScaleAnimation(getActivity(), this.ivAlbumMore);
        this.scaleAnimation.Event_ImageView();

        this.tvAlbum = (TextView) view.findViewById(R.id.tvAlbum);
        this.tvAlbum.setSelected(true); // Text will be moved
    }

    private void Handle_Album() {
        DataService dataService = APIService.getService(); // Khởi tạo Phương thức để đẩy lên
        Call<List<Album>> callBack = dataService.getAlbumCurrentDay();
        callBack.enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                albumArrayList = new ArrayList<>();
                albumArrayList = (ArrayList<Album>) response.body();

                if (albumArrayList != null) {
//                    rvAlbum.setHasFixedSize(true);
//                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
//                    layoutManager.setOrientation(RecyclerView.HORIZONTAL); // Chiều ngang
//                    rvAlbum.setLayoutManager(layoutManager);
//                    rvAlbum.setAdapter(new AlbumHomeAdapter(getContext(), albumArrayList));

                    vpg2Album.setClipToPadding(false); // Set clip padding
                    vpg2Album.setClipChildren(false); // Set clip children
                    vpg2Album.setOffscreenPageLimit(3); // Set page limit
                    vpg2Album.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER); // Không bao giờ cho phép người dùng cuộn quá chế độ xem này.

                    vpg2Album.setAdapter(new AlbumHomeAdapter(getContext(), albumArrayList, vpg2Album));

                    // Xét các hiệu ứng chuyển động cho vpg2Album
                    CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
                    compositePageTransformer.addTransformer(new MarginPageTransformer(30));
                    compositePageTransformer.addTransformer((page, position) -> {
                        float r = 1 - Math.abs(position);
                        page.setScaleY(0.8f + r * 0.2f);
                    });

                    vpg2Album.setPageTransformer(compositePageTransformer);

                    Log.d(TAG, albumArrayList.get(0).getImg());
                }
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
    }
}
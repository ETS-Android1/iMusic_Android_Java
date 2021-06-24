package com.thanguit.imusic.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.thanguit.imusic.R;
import com.thanguit.imusic.activities.RadioActivity;

public class RadioFragment extends Fragment {
    private TextView tvRoom1;
    private TextView tvRoom2;
    private ImageView room1;
    private ImageView room2;
    private ImageView room3;
    private ImageView room4;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_radio, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //this.tvRadio = view.findViewById(R.id.tvRadio);
        //this.tvRadio.setSelected(true);
        // btn = view.findViewById(R.id.btnJoin);

        this.tvRoom1 = view.findViewById(R.id.tvRoom1);
        this.tvRoom1.setSelected(true); // Text will be moved

        this.tvRoom2 = view.findViewById(R.id.tvRoom2);
        this.tvRoom2.setSelected(true); // Text will be moved

        this.room1 = view.findViewById(R.id.ivRoom1);
        this.room2 = view.findViewById(R.id.ivRoom2);
//        room3 = view.findViewById(R.id.ivRoom3);
//        room4 = view.findViewById(R.id.ivRoom4);
/*        btn.setOnClickListener(v->{
            Intent intent = new Intent(getActivity(), RadioActivity.class);
            startActivity(intent);
        });*/

//        room1.setOnClickListener(v -> {
//            Intent intent = new Intent(getActivity(), RadioActivity.class);
//            intent.putExtra("room", 1);
//            startActivity(intent);
//        });
//        room2.setOnClickListener(v -> {
//            Intent intent = new Intent(getActivity(), RadioActivity.class);
//            intent.putExtra("room", 2);
//            startActivity(intent);
//        });


//        room3.setOnClickListener(v -> {
//            Intent intent = new Intent(getActivity(), RadioActivity.class);
//            intent.putExtra("room", 3);
//            startActivity(intent);
//        });
//        room4.setOnClickListener(v -> {
//            Intent intent = new Intent(getActivity(), RadioActivity.class);
//            intent.putExtra("room", 4);
//            startActivity(intent);
//        });
    }
}
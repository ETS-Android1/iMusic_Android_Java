package com.thanguit.imusic.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
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
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.thanguit.imusic.API.APIService;
import com.thanguit.imusic.API.DataService;
import com.thanguit.imusic.R;
import com.thanguit.imusic.SharedPreferences.DataLocalManager;
import com.thanguit.imusic.activities.FullActivity;
import com.thanguit.imusic.adapters.UserPlaylistAdapter;
import com.thanguit.imusic.animations.ScaleAnimation;
import com.thanguit.imusic.models.UserPlaylist;
import com.thanguit.imusic.services.SettingLanguage;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingFragment extends Fragment {
    private static final String TAG = "SettingFragment";

    private SettingLanguage settingLanguage;

    private LottieAnimationView btnSwitchTheme;
    private TextView tvVietNamese;
    private TextView tvEnglish;
    private Button btnRating;

    private ScaleAnimation scaleAnimation;

    private final String VIETNAMESE = "vi";
    private final String ENGLISH = "en";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DataLocalManager.init(getContext());

        Mapping(view);
        Event();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void Mapping(View view) {
        this.settingLanguage = new SettingLanguage(getContext());

        this.btnSwitchTheme = view.findViewById(R.id.btnSwitchTheme);
        this.tvVietNamese = view.findViewById(R.id.tvVietNamese);
        this.tvEnglish = view.findViewById(R.id.tvEnglish);
        this.btnRating = view.findViewById(R.id.btnRating);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            this.tvVietNamese.setVisibility(View.GONE);
//            Toast.makeText(getContext(), "Can't change another language, because the device is out of date!", Toast.LENGTH_LONG).show();
        }

        if (DataLocalManager.getTheme()) {
            this.btnSwitchTheme.setMinAndMaxProgress(0.5f, 1.0f); // Tối
        } else {
            this.btnSwitchTheme.setMinAndMaxProgress(0.1f, 0.5f); // Sáng
        }

        if (DataLocalManager.getLanguage().equals(VIETNAMESE)) {
            this.tvVietNamese.setTextColor(getResources().getColor(R.color.colorMain3));
            this.tvEnglish.setTextColor(getResources().getColor(R.color.colorLight7));
        } else {
            this.tvEnglish.setTextColor(getResources().getColor(R.color.colorMain3));
            this.tvVietNamese.setTextColor(getResources().getColor(R.color.colorLight7));
        }
    }

    private void Event() {
        this.btnSwitchTheme.setOnClickListener(v -> {
            if (DataLocalManager.getTheme()) {
                DataLocalManager.setTheme(false);
                btnSwitchTheme.setMinAndMaxProgress(0.65f, 1.0f); // Sáng
                btnSwitchTheme.playAnimation();

                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            } else {
                DataLocalManager.setTheme(true);
                btnSwitchTheme.setMinAndMaxProgress(0.1f, 0.5f); // Tối
                btnSwitchTheme.playAnimation();

                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
        });

        this.tvVietNamese.setOnClickListener(v -> {
            this.tvVietNamese.setTextColor(getResources().getColor(R.color.colorMain3));
            this.tvEnglish.setTextColor(getResources().getColor(R.color.colorLight7));

            settingLanguage.Update_Language(VIETNAMESE);

            Intent intent = new Intent(getContext(), FullActivity.class);
            startActivity(intent);

            Log.d(TAG, "VietNam: " + DataLocalManager.getLanguage());
        });

        this.tvEnglish.setOnClickListener(v -> {
            this.tvEnglish.setTextColor(getResources().getColor(R.color.colorMain3));
            this.tvVietNamese.setTextColor(getResources().getColor(R.color.colorLight7));

            settingLanguage.Update_Language(ENGLISH);

            Intent intent = new Intent(getContext(), FullActivity.class);
            startActivity(intent);

            Log.d(TAG, "English: " + DataLocalManager.getLanguage());
        });

        this.scaleAnimation = new ScaleAnimation(getContext(), this.btnRating);
        this.scaleAnimation.Event_Button();
        this.btnRating.setOnClickListener(v -> {
            Open_Feedback_Dialog();
        });
    }

    private void Open_Feedback_Dialog() {
        final Dialog dialog = new Dialog(getContext());

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_feedback_app);

        Window window = (Window) dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // Set màu mờ mờ cho background dialog, che đi activity chính, nhưng vẫn có thể thấy được một phần

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        windowAttributes.windowAnimations = R.style.DialogAnimation;
        window.setAttributes(windowAttributes);

        dialog.setCancelable(true); // Bấm ra chỗ khác sẽ thoát dialog

        TextView tvFeedbackTitle = dialog.findViewById(R.id.tvFeedbackTitle);
        tvFeedbackTitle.setSelected(true);
        RatingBar rbRating = dialog.findViewById(R.id.rbRating);
        EditText etFeedbackContent = dialog.findViewById(R.id.etFeedbackContent);
        Button btnFeedbackCancel = dialog.findViewById(R.id.btnFeedbackCancel);
        Button btnSendFeedback = dialog.findViewById(R.id.btnSendFeedback);

        rbRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Toast.makeText(getActivity(), "Rating: " + rating, Toast.LENGTH_SHORT).show();
            }
        });

        this.scaleAnimation = new ScaleAnimation(getContext(), btnFeedbackCancel);
        this.scaleAnimation.Event_Button();
        btnFeedbackCancel.setOnClickListener(v -> dialog.dismiss());

        this.scaleAnimation = new ScaleAnimation(getContext(), btnSendFeedback);
        this.scaleAnimation.Event_Button();
        btnSendFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        DataService dataService = APIService.getService(); // Khởi tạo Phương thức để đẩy lên


        dialog.show();
    }
}
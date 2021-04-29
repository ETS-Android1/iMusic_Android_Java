package com.thanguit.imusic.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.kaushikthedeveloper.doublebackpress.DoubleBackPress;
import com.kaushikthedeveloper.doublebackpress.helper.DoubleBackPressAction;
import com.kaushikthedeveloper.doublebackpress.helper.FirstBackPressAction;
import com.squareup.picasso.Picasso;
import com.thanguit.imusic.R;
import com.thanguit.imusic.fragments.ChartFragment;
import com.thanguit.imusic.fragments.HomeFragment;
import com.thanguit.imusic.fragments.PersonalFragment;
import com.thanguit.imusic.fragments.RadioFragment;
import com.thanguit.imusic.fragments.SettingFragment;

import java.util.Calendar;

public class FullActivity extends AppCompatActivity {
//    private FirebaseAuth firebaseAuth;
//    private GoogleSignInClient googleSignInClient;
//
//    private User user;
//
//    private ImageView Avatar;
//    private TextView ID;
//    private TextView Name;
//    private TextView Email;
//    private Button Signout;

    private MeowBottomNavigation meowBottomNavigation;
    private Fragment fragment;

    private FirstBackPressAction firstBackPressAction;
    private DoubleBackPressAction doubleBackPressAction;
    private DoubleBackPress doubleBackPress;
    private final int TIME_DURATION = 2000;

    private final int ID_PERSONAL = 1;
    private final int ID_CHART = 2;
    private final int ID_HOME = 3;
    private final int ID_RADIO = 4;
    private final int ID_SETTING = 5;

    private final String LOG_TAG = "FULL ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full);

        Mapping();
        Event();
    }


    private void Mapping() {
        this.meowBottomNavigation = (MeowBottomNavigation) findViewById(R.id.bottomNavigation);

        this.meowBottomNavigation.add(new MeowBottomNavigation.Model(this.ID_PERSONAL, R.drawable.ic_music_note));
        this.meowBottomNavigation.add(new MeowBottomNavigation.Model(this.ID_CHART, R.drawable.ic_chart));
        this.meowBottomNavigation.add(new MeowBottomNavigation.Model(this.ID_HOME, R.drawable.ic_home));
        this.meowBottomNavigation.add(new MeowBottomNavigation.Model(this.ID_RADIO, R.drawable.ic_radio));
        this.meowBottomNavigation.add(new MeowBottomNavigation.Model(this.ID_SETTING, R.drawable.ic_setting));
//        this.Avatar = findViewById(R.id.imvAvatar);
//        this.ID = findViewById(R.id.tvID);
//        this.Name = findViewById(R.id.tvName);
//        this.Email = findViewById(R.id.tvEmail);
//        this.Signout = findViewById(R.id.btnSignout);
//
//        this.firebaseAuth = FirebaseAuth.getInstance();
//        FirebaseUser firebaseUser = this.firebaseAuth.getCurrentUser();
//
//        if (firebaseUser != null) {
//            for (UserInfo userInfo : firebaseUser.getProviderData()) {
//                switch (userInfo.getProviderId()) {
//                    case "facebook.com": {
//                        try {
//                            String photoUrl = firebaseUser.getPhotoUrl() + "?height=1000&access_token=" + AccessToken.getCurrentAccessToken().getToken();
//                            this.user = new User(firebaseUser.getUid(), photoUrl, String.valueOf(firebaseUser.getDisplayName()), String.valueOf(firebaseUser.getEmail()));
//
//                            Picasso.get().load(user.getAvatar()).into(this.Avatar);
//                            this.ID.setText(user.getId());
//                            this.Name.setText(user.getName());
//                            this.Email.setText(user.getEmail());
//
//                            Log.d("USER INFO", user.toString());
//                        } catch (Exception e) {
//                            Toast.makeText(FullActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                            Log.d("FAIL", e.getMessage());
//                        }
//                        break;
//                    }
//
//                    case "google.com": {
//                        try {
//                            this.user = new User(firebaseUser.getUid(), String.valueOf(firebaseUser.getPhotoUrl()), String.valueOf(firebaseUser.getDisplayName()), String.valueOf(firebaseUser.getEmail()));
//
//                            Picasso.get().load(user.getAvatar()).into(this.Avatar);
//                            this.ID.setText(user.getId());
//                            this.Name.setText(user.getName());
//                            this.Email.setText(user.getEmail());
//
//                            Log.d("USER INFO", user.toString());
//                        } catch (Exception e) {
//                            Toast.makeText(FullActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                            Log.d("FAIL", e.getMessage());
//                        }
//                        break;
//                    }
//                }
//            }
//        }
    }

    private void Event() {
        // Event for Bottom Navigation
        this.meowBottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                Log.d(LOG_TAG, "Fragment: " + item.getId());
            }
        });

        this.meowBottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                fragment = null;
                switch (item.getId()) {
                    case 1: {
                        fragment = new PersonalFragment();
                        break;
                    }
                    case 2: {
                        fragment = new ChartFragment();
                        break;
                    }
                    case 3: {
                        fragment = new HomeFragment();
                        break;
                    }
                    case 4: {
                        fragment = new RadioFragment();
                        break;
                    }
                    case 5: {
                        fragment = new SettingFragment();
                        break;
                    }
                }
                loadFragment(fragment);
            }
        });

        this.meowBottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {

            }
        });

        this.meowBottomNavigation.show(this.ID_HOME, true); // Default tab when open


        // Event for Press Back Twice To Exit App
        this.doubleBackPressAction = new DoubleBackPressAction() {
            @Override
            public void actionCall() {
                finish();
                moveTaskToBack(true);
                System.exit(0);
            }
        };

        this.firstBackPressAction = new FirstBackPressAction() {
            @Override
            public void actionCall() {
                Toast.makeText(FullActivity.this, R.string.toast7, Toast.LENGTH_SHORT).show();
            }
        };

        this.doubleBackPress = new DoubleBackPress()
                .withDoublePressDuration(this.TIME_DURATION)
                .withFirstBackPressAction(this.firstBackPressAction)
                .withDoubleBackPressAction(this.doubleBackPressAction);
//        Signout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
//
//                if (firebaseUser != null) {
//                    for (UserInfo userInfo : firebaseUser.getProviderData()) {
//                        switch (userInfo.getProviderId()) {
//                            case "facebook.com": {
//                                firebaseAuth.signOut();
//                                LoginManager.getInstance().logOut();
//                                break;
//                            }
//                            case "google.com": {
//                                firebaseAuth.signOut();
//                                googleSignInClient.signOut();
//                                break;
//                            }
//                        }
//                    }
//                }
//                finish();
//            }
//        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        this.doubleBackPress.onBackPressed();
        Log.d(LOG_TAG, "Back Twice To Exit!");
    }
}
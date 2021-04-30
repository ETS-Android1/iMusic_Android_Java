package com.thanguit.imusic.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

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
import com.thanguit.imusic.animations.ScaleAnimation;
import com.thanguit.imusic.fragments.ChartFragment;
import com.thanguit.imusic.fragments.HomeFragment;
import com.thanguit.imusic.fragments.PersonalFragment;
import com.thanguit.imusic.fragments.RadioFragment;
import com.thanguit.imusic.fragments.SettingFragment;
import com.thanguit.imusic.models.User;

import de.hdodenhof.circleimageview.CircleImageView;

public class FullActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private GoogleSignInClient googleSignInClient;

    private User user;
    private ScaleAnimation scaleAnimation;

    private ImageView ivBell;

    private MeowBottomNavigation meowBottomNavigation;
    private Fragment fragment;

    private FirstBackPressAction firstBackPressAction;
    private DoubleBackPressAction doubleBackPressAction;
    private DoubleBackPress doubleBackPress;
    private final int TIME_DURATION = 2000;

    private CircleImageView circleImageView;

    private final int ID_PERSONAL = 1;
    private final int ID_CHART = 2;
    private final int ID_HOME = 3;
    private final int ID_RADIO = 4;
    private final int ID_SETTING = 5;

    private final String LOG_TAG = "FULLACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full);

        Mapping();
        Event();
    }


    private void Mapping() {
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.meowBottomNavigation = (MeowBottomNavigation) findViewById(R.id.bottomNavigation);

        this.ivBell = (ImageView) findViewById(R.id.ivBell);

        this.meowBottomNavigation.add(new MeowBottomNavigation.Model(this.ID_PERSONAL, R.drawable.ic_music_note));
        this.meowBottomNavigation.add(new MeowBottomNavigation.Model(this.ID_CHART, R.drawable.ic_chart));
        this.meowBottomNavigation.add(new MeowBottomNavigation.Model(this.ID_HOME, R.drawable.ic_home));
        this.meowBottomNavigation.add(new MeowBottomNavigation.Model(this.ID_RADIO, R.drawable.ic_radio));
        this.meowBottomNavigation.add(new MeowBottomNavigation.Model(this.ID_SETTING, R.drawable.ic_setting));

        this.circleImageView = (CircleImageView) findViewById(R.id.civAvatar);
    }

    private void Event() {
        this.scaleAnimation = new ScaleAnimation(FullActivity.this, this.ivBell);
        this.scaleAnimation.Event_ImageView();

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
                // Just code
            }
        });

        this.meowBottomNavigation.show(this.ID_HOME, true); // Default tab when open


        // Event for load Image of User
        FirebaseUser firebaseUser = this.firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            for (UserInfo userInfo : firebaseUser.getProviderData()) {
                switch (userInfo.getProviderId()) {
                    case "facebook.com": {
                        try {
                            String photoUrl = firebaseUser.getPhotoUrl() + "?height=1000&access_token=" + AccessToken.getCurrentAccessToken().getToken();
                            this.user = new User(firebaseUser.getUid(), photoUrl, String.valueOf(firebaseUser.getDisplayName()), String.valueOf(firebaseUser.getEmail()));

                            Picasso.get().load(user.getAvatar()).into(this.circleImageView);

                            Log.d("USER INFO", user.toString());
                        } catch (Exception e) {
                            Log.d("FAIL", e.getMessage());
                            Toast.makeText(FullActivity.this, R.string.toast8, Toast.LENGTH_SHORT).show();
                            firebaseAuth.signOut();
                            LoginManager.getInstance().logOut();
                            finish();
                        }
                        break;
                    }

                    case "google.com": {
                        try {
                            this.user = new User(firebaseUser.getUid(), String.valueOf(firebaseUser.getPhotoUrl()), String.valueOf(firebaseUser.getDisplayName()), String.valueOf(firebaseUser.getEmail()));

                            Picasso.get().load(user.getAvatar()).into(this.circleImageView);

                            Log.d("USER INFO", user.toString());
                        } catch (Exception e) {
                            Toast.makeText(FullActivity.this, R.string.toast8, Toast.LENGTH_SHORT).show();
                            Log.d("FAIL", e.getMessage());
//                            firebaseAuth.signOut();
//                            googleSignInClient.signOut();
//                            finish();
                        }
                        break;
                    }
                }
            }
        }


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
package com.thanguit.imusic.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;
import com.thanguit.imusic.R;
import com.thanguit.imusic.fragments.ChartFragment;
import com.thanguit.imusic.fragments.HomeFragment;
import com.thanguit.imusic.fragments.RadioFragment;
import com.thanguit.imusic.fragments.SettingFragment;
import com.thanguit.imusic.fragments.SingerFragment;

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

    private final int ID_HOME = 1;
    private final int ID_SINGER = 2;
    private final int ID_CHART = 3;
    private final int ID_RADIO = 4;
    private final int ID_SETTING = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full);

        Mapping();
        Event();
    }

    private void Mapping() {
        this.meowBottomNavigation = (MeowBottomNavigation) findViewById(R.id.bottomNavigation);

        this.meowBottomNavigation.add(new MeowBottomNavigation.Model(this.ID_HOME, R.drawable.ic_home));
        this.meowBottomNavigation.add(new MeowBottomNavigation.Model(this.ID_SINGER, R.drawable.ic_singer));
        this.meowBottomNavigation.add(new MeowBottomNavigation.Model(this.ID_CHART, R.drawable.ic_chart));
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
        this.meowBottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                Toast.makeText(FullActivity.this, "Fragment: " + item.getId(), Toast.LENGTH_SHORT).show();
            }
        });

        this.meowBottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                fragment = null;
                switch (item.getId()) {
                    case 1: {
                        fragment = new HomeFragment();
                        break;
                    }
                    case 2: {
                        fragment = new SingerFragment();
                        break;
                    }
                    case 3: {
                        fragment = new ChartFragment();
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
        this.meowBottomNavigation.show(this.ID_CHART, true);
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
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .commit();
    }
}
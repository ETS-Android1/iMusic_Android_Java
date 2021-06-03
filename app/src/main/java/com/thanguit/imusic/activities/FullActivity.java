package com.thanguit.imusic.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.login.LoginManager;
import com.kaushikthedeveloper.doublebackpress.DoubleBackPress;
import com.kaushikthedeveloper.doublebackpress.helper.DoubleBackPressAction;
import com.kaushikthedeveloper.doublebackpress.helper.FirstBackPressAction;
import com.squareup.picasso.Picasso;
import com.thanguit.imusic.R;
import com.thanguit.imusic.animations.ScaleAnimation;
import com.thanguit.imusic.fragments.ChartFragment;
import com.thanguit.imusic.fragments.HomeFragment;
import com.thanguit.imusic.fragments.PersonalPlaylistFragment;
import com.thanguit.imusic.fragments.RadioFragment;
import com.thanguit.imusic.fragments.SettingFragment;
import com.thanguit.imusic.models.User;
import com.zing.zalo.zalosdk.oauth.ValidateOAuthCodeCallback;
import com.zing.zalo.zalosdk.oauth.ZaloOpenAPICallback;
import com.zing.zalo.zalosdk.oauth.ZaloSDK;

import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class FullActivity extends AppCompatActivity {
    private User user;
    private ScaleAnimation scaleAnimation;

    private ImageView ivBell;

    private MeowBottomNavigation meowBottomNavigation;
    private Fragment fragment;

    private FirstBackPressAction firstBackPressAction;
    private DoubleBackPressAction doubleBackPressAction;
    private DoubleBackPress doubleBackPress;
    private static final int TIME_DURATION = 2000;

    private CircleImageView circleImageView;
    private EditText editText;

    private static final int ID_PERSONAL = 1;
    private static final int ID_CHART = 2;
    private static final int ID_HOME = 3;
    private static final int ID_RADIO = 4;
    private static final int ID_SETTING = 5;

    private final String LOG_TAG = "FULLACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full);

        Mapping();
        Event();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (AccessToken.getCurrentAccessToken() != null) {
            LoginManager.getInstance().logOut();
            finish();
        }

        ZaloSDK.Instance.isAuthenticate(new ValidateOAuthCodeCallback() {
            @Override
            public void onValidateComplete(boolean validated, int i, long l, String s) {
                if (!validated) {
                    ZaloSDK.Instance.unauthenticate();
                    finish();
                }
            }
        });
    }

    private void Mapping() {
        this.meowBottomNavigation = findViewById(R.id.bottomNavigation);

        this.ivBell = findViewById(R.id.ivBell);

        this.meowBottomNavigation.add(new MeowBottomNavigation.Model(ID_PERSONAL, R.drawable.ic_music_note));
        this.meowBottomNavigation.add(new MeowBottomNavigation.Model(ID_CHART, R.drawable.ic_chart));
        this.meowBottomNavigation.add(new MeowBottomNavigation.Model(ID_HOME, R.drawable.ic_home));
        this.meowBottomNavigation.add(new MeowBottomNavigation.Model(ID_RADIO, R.drawable.ic_radio));
        this.meowBottomNavigation.add(new MeowBottomNavigation.Model(ID_SETTING, R.drawable.ic_setting));

        this.circleImageView = findViewById(R.id.civAvatar);
        this.editText = findViewById(R.id.etSearch);
    }

    private void Event() {
        this.scaleAnimation = new ScaleAnimation(FullActivity.this, this.ivBell);
        this.scaleAnimation.Event_ImageView();

        this.scaleAnimation = new ScaleAnimation(FullActivity.this, this.circleImageView);
        this.scaleAnimation.Event_CircleImageView();


        this.circleImageView.setOnClickListener(v -> {
            Intent intent = new Intent(FullActivity.this, PersonalPageActivity.class);
            startActivity(intent);
        });


        // Event for Bottom Navigation
        this.meowBottomNavigation.setOnClickMenuListener(item -> Log.d(LOG_TAG, "Fragment: " + item.getId()));

        this.meowBottomNavigation.setOnShowListener(item -> {
            fragment = null;
            switch (item.getId()) {
                case 1: {
                    fragment = new PersonalPlaylistFragment();
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
        });

        this.meowBottomNavigation.setOnReselectListener(item -> {// I think, I should reload page in here
        });

        this.meowBottomNavigation.show(ID_HOME, true); // Default tab when open


        // Event for load info of User with Facebook
        if (AccessToken.getCurrentAccessToken() != null) {
            GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), (object, response) -> {
                try {
                    String avatar = object.getJSONObject("picture").getJSONObject("data").getString("url");

                    Picasso.get()
                            .load(avatar)
                            .placeholder(R.drawable.ic_logo)
                            .error(R.drawable.ic_logo)
                            .into(this.circleImageView);

                    Log.d(LOG_TAG, String.valueOf(object));
                } catch (Exception e) {
                    e.printStackTrace();
//                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            Bundle bundle = new Bundle();
            bundle.putString("fields", "id, name, picture.width(1000).height(1000), first_name, last_name, gender");
            graphRequest.setParameters(bundle);
            graphRequest.executeAsync();
        }


        // Event for load info of User with Zalo
        if (!ZaloSDK.Instance.getOAuthCode().isEmpty()) {
            ZaloOpenAPICallback callBack = new ZaloOpenAPICallback() {
                @Override
                public void onResult(JSONObject jsonObject) {
                    String avatar = jsonObject.optJSONObject("picture").optJSONObject("data").optString("url");
                    Picasso.get()
                            .load(avatar)
                            .placeholder(R.drawable.ic_logo)
                            .error(R.drawable.ic_logo)
                            .into(circleImageView);

                    Log.d(LOG_TAG, String.valueOf(jsonObject));
                }
            };
            String[] getData = {"id", "name", "picture"};
            ZaloSDK.Instance.getProfile(this, callBack, getData);
        }


        // Event for Search
        this.editText.setOnClickListener(v -> {
            Intent intent = new Intent(FullActivity.this, SearchActivity.class);
            startActivity(intent);
        });


        // Event for Press Back Twice To Exit App
        this.doubleBackPressAction = () -> {
            finish();
            moveTaskToBack(true);
            System.exit(0);
        };
        this.firstBackPressAction = () -> Toast.makeText(FullActivity.this, R.string.toast7, Toast.LENGTH_SHORT).show();
        this.doubleBackPress = new DoubleBackPress()
                .withDoublePressDuration(TIME_DURATION)
                .withFirstBackPressAction(this.firstBackPressAction)
                .withDoubleBackPressAction(this.doubleBackPressAction);
    }


    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commitAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        this.doubleBackPress.onBackPressed();
        Log.d(LOG_TAG, "Back Twice To Exit!");
    }
}
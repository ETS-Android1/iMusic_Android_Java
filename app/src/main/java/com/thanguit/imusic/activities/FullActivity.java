package com.thanguit.imusic.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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
import com.thanguit.imusic.API.APIService;
import com.thanguit.imusic.API.DataService;
import com.thanguit.imusic.R;
import com.thanguit.imusic.SharedPreferences.DataLocalManager;
import com.thanguit.imusic.adapters.AlbumHomeAdapter;
import com.thanguit.imusic.animations.LoadingDialog;
import com.thanguit.imusic.animations.ScaleAnimation;
import com.thanguit.imusic.fragments.ChartFragment;
import com.thanguit.imusic.fragments.HomeFragment;
import com.thanguit.imusic.fragments.PersonalPlaylistFragment;
import com.thanguit.imusic.fragments.RadioFragment;
import com.thanguit.imusic.fragments.SettingFragment;
import com.thanguit.imusic.models.Album;
import com.thanguit.imusic.models.Song;
import com.thanguit.imusic.models.User;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FullActivity extends AppCompatActivity {
    private ScaleAnimation scaleAnimation;
    private LoadingDialog loadingDialog;

    private ImageView ivBell;

    private MeowBottomNavigation meowBottomNavigation;
    private Fragment fragment;

    private FirstBackPressAction firstBackPressAction;
    private DoubleBackPressAction doubleBackPressAction;
    private DoubleBackPress doubleBackPress;
    private static final int TIME_DURATION = 2000;

    private CircleImageView circleImageView;
    private EditText editText;

    private ArrayList<User> userArrayList;

    private static final int ID_PERSONAL = 1;
    private static final int ID_CHART = 2;
    private static final int ID_HOME = 3;
    private static final int ID_RADIO = 4;
    private static final int ID_SETTING = 5;

    private final String TAG = "FullActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full);

        DataLocalManager.init(this);
//        Toast.makeText(this, "User_ID: " + DataLocalManager.getUserID(), Toast.LENGTH_SHORT).show();

        Mapping();
        Event();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Check_Login();
//        ZaloSDK.Instance.isAuthenticate(new ValidateOAuthCodeCallback() {
//            @Override
//            public void onValidateComplete(boolean validated, int i, long l, String s) {
//                if (validated) {
//                    ZaloSDK.Instance.unauthenticate();
//                    Intent intent = new Intent(FullActivity.this, MainActivity.class);
//                    startActivity(intent);
//                }
//            }
//        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Check_Login();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Check_Login();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Check_Login();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void Check_Login() {
        if (AccessToken.getCurrentAccessToken() == null) {
            LoginManager.getInstance().logOut();
            DataLocalManager.deleteAllData();
            finish();
            Intent intent = new Intent(FullActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    private void Mapping() {
//        this.loadingDialog = new LoadingDialog(this);
//        this.loadingDialog.Start_Loading();

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
        this.meowBottomNavigation.setOnClickMenuListener(item -> Log.d(TAG, "Fragment: " + item.getId()));

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

        // Load info of User with Facebook
        if (AccessToken.getCurrentAccessToken().getToken() != null && !DataLocalManager.getUserID().isEmpty()) {
//            loadingDialog.Cancel_Loading();
            GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), (object, response) -> {
                try {
                    String id = object.getString("id");
                    String name = object.getString("name");
                    String email = !object.getString("email").isEmpty() ? object.getString("email") : "Null";
                    String avatarFacebook = !object.getJSONObject("picture").getJSONObject("data").getString("url").isEmpty() ? object.getJSONObject("picture").getJSONObject("data").getString("url") : "Null";
                    String isDark = "0";
                    String isEnglish = "0";

                    Picasso.get()
                            .load(avatarFacebook)
                            .placeholder(R.drawable.ic_logo)
                            .error(R.drawable.ic_logo)
                            .into(this.circleImageView);

                    Handle_User(id, name, email, avatarFacebook, isDark, isEnglish);

                    Log.d(TAG, "User information (FACEBOOK): " + object);
                } catch (Exception e) {
                    e.printStackTrace();
//                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            Bundle bundle = new Bundle();
            bundle.putString("fields", "id, name, email, picture.width(1000).height(1000)");
            graphRequest.setParameters(bundle);
            graphRequest.executeAsync();
        }


        // Event for load info of User with Zalo
//        String[] getData = {"id", "name", "picture"};
//        ZaloSDK.Instance.getProfile(this, new ZaloOpenAPICallback() {
//            @Override
//            public void onResult(JSONObject jsonObject) {
//                String avatarZalo = jsonObject.optJSONObject("picture").optJSONObject("data").optString("url");
//                Picasso.get()
//                        .load(avatarZalo)
//                        .placeholder(R.drawable.ic_logo)
//                        .error(R.drawable.ic_logo)
//                        .into(circleImageView);
//
//                LOGIN_TYPE = 2;
//
//                Log.d(LOG_TAG, "User information (ZALO): " + String.valueOf(jsonObject));
//            }
//        }, getData);


        // Event for Search
        this.editText.setOnClickListener(v -> {
            Intent intent_1 = new Intent(FullActivity.this, SearchActivity.class);
            startActivity(intent_1);
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

    private void Handle_User(String id, String name, String email, String img, String isDark, String isEnglish) {
        DataService dataService = APIService.getService(); // Khởi tạo Phương thức để đẩy lên
        Call<List<User>> callBack = dataService.addNewUser(id, name, email, img, isDark, isEnglish);
        callBack.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                userArrayList = new ArrayList<>();
                userArrayList = (ArrayList<User>) response.body();

                if (userArrayList != null && userArrayList.size() > 0) {
//                    DataLocalManager.setUserID(id); // Lưu ID người dùng vào SharedPreferences
//                    loadingDialog.Cancel_Loading();
                    Toast.makeText(FullActivity.this, R.string.toast1, Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "User_ID: " + userArrayList.get(0).getId());
                } else {
                    Toast.makeText(FullActivity.this, R.string.toast3, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.d(TAG, "Handle_User (Error): " + t.getMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        this.doubleBackPress.onBackPressed();
        Log.d(TAG, "Back Twice To Exit!");
    }
}
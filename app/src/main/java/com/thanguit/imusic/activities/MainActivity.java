package com.thanguit.imusic.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.kaushikthedeveloper.doublebackpress.DoubleBackPress;
import com.kaushikthedeveloper.doublebackpress.helper.DoubleBackPressAction;
import com.kaushikthedeveloper.doublebackpress.helper.FirstBackPressAction;
import com.squareup.picasso.Picasso;
import com.thanguit.imusic.API.APIService;
import com.thanguit.imusic.API.DataService;
import com.thanguit.imusic.SharedPreferences.DataLocalManager;
import com.thanguit.imusic.animations.ScaleAnimation;
import com.thanguit.imusic.R;
import com.thanguit.imusic.models.Album;
import com.thanguit.imusic.models.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private CallbackManager callbackManager;

    private ScaleAnimation scaleAnimation;

    private Animation topAnimation, bottomAnimation;

    private ImageView imvLogo;
    private Button btnLoginFB;
//    private Button btnLoginZL;

//    private OAuthCompleteListener oAuthCompleteListener;

    private FirstBackPressAction firstBackPressAction;
    private DoubleBackPressAction doubleBackPressAction;
    private DoubleBackPress doubleBackPress;
    private static final int TIME_DURATION = 2000;

    private ArrayList<User> userArrayList;

    private static final String TAG = "MainActivity";
    private static final String LOG_TAG_1 = "LOGIN WITH FACEBOOK";
//    private static final String LOG_TAG_2 = "LOGIN WITH ZALO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataLocalManager.init(this);

//        DataLocalManager.init(this);

//        try {
//            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                String hashKey = new String(Base64.encode(md.digest(), 0));
//                Log.i("Key", "printHashKey() Hash Key: " + hashKey);
//            }
//        } catch (NoSuchAlgorithmException e) {
//            Log.e("Key", "printHashKey()", e);
//        } catch (Exception e) {
//            Log.e("Key", "printHashKey()", e);
//        }

        Mapping();
        Event();
        Login_Facebook();
//        Login_Zalo();
    }

    private void Mapping() {
        this.callbackManager = CallbackManager.Factory.create();

        this.imvLogo = (ImageView) findViewById(R.id.imvLogo);
        this.btnLoginFB = (Button) findViewById(R.id.btnLoginFB);
//        this.btnLoginZL = (Button) findViewById(R.id.btnLoginZL);

        this.topAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.top_animation);
        this.bottomAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.bottom_animation);
    }

    private void Event() {
        this.imvLogo.setAnimation(this.topAnimation);
        this.btnLoginFB.setAnimation(this.bottomAnimation);
//        this.btnLoginZL.setAnimation(this.bottomAnimation);

        this.scaleAnimation = new ScaleAnimation(MainActivity.this, this.btnLoginFB);
        this.scaleAnimation.Event_Button();
//        this.scaleAnimation = new ScaleAnimation(MainActivity.this, this.btnLoginZL);
//        this.scaleAnimation.Event_Button();

        this.doubleBackPressAction = () -> {
            finish();
            moveTaskToBack(true);
            System.exit(0);
        };
        this.firstBackPressAction = () -> Toast.makeText(this, R.string.toast7, Toast.LENGTH_SHORT).show();
        this.doubleBackPress = new DoubleBackPress()
                .withDoublePressDuration(TIME_DURATION)
                .withFirstBackPressAction(this.firstBackPressAction)
                .withDoubleBackPressAction(this.doubleBackPressAction);
    }


    private void Login_Facebook() {
        this.btnLoginFB.setOnClickListener(v -> {
            LoginManager.getInstance().logInWithReadPermissions(MainActivity.this, Arrays.asList("public_profile", "email"));
            LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    Log.d(LOG_TAG_1, "facebook: onSuccess: " + loginResult);
                    if (loginResult.getAccessToken().getToken() != null) {
                        GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), (object, response) -> {
                            try {
                                String id = object.getString("id");
                                String name = object.getString("name");
                                String email = object.getString("email");
                                String img = object.getJSONObject("picture").getJSONObject("data").getString("url");
                                String isDark = "0";
                                String isEnglish = "0";

                                Handle_User(id, name, email, img, isDark, isEnglish);

                                Intent intent = new Intent(MainActivity.this, FullActivity.class);
                                startActivity(intent);

                                Toast.makeText(MainActivity.this, R.string.toast1, Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "User information (FACEBOOK): " + String.valueOf(object));
                            } catch (Exception e) {
                                Log.d(LOG_TAG_1, e.getMessage());
//                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        Bundle bundle = new Bundle();
                        bundle.putString("fields", "id, name, email, picture.width(1000).height(1000)");
                        graphRequest.setParameters(bundle);
                        graphRequest.executeAsync();
                    }
                }

                @Override
                public void onCancel() {
                    Log.d(LOG_TAG_1, "facebook: onCancel");
                    Toast.makeText(MainActivity.this, R.string.toast2, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(FacebookException error) {
                    Log.d(LOG_TAG_1, "facebook: onError ", error);
                    Toast.makeText(MainActivity.this, R.string.toast3, Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

//    private void Login_Zalo() {
//        this.btnLoginZL.setOnClickListener(v -> {
//            oAuthCompleteListener = new OAuthCompleteListener() {
//                @Override
//                public void onAuthenError(int errorCode, String message) {
//                    Toast.makeText(MainActivity.this, R.string.toast3, Toast.LENGTH_SHORT).show();
//                    Log.d(LOG_TAG_2, errorCode + " | " + message);
//                }
//
//                @Override
//                public void onGetOAuthComplete(OauthResponse response) {
//                    String code = response.getOauthCode();
//                    if (!code.isEmpty()) {
//                        if (!DataLocalManager.getLogin()) {
//                            DataLocalManager.setLogin(true);
//                        }
//
//                        Intent intent = new Intent(MainActivity.this, FullActivity.class);
//                        intent.putExtra("LOGIN_ZALO", "Zalo");
//                        startActivity(intent);
//
//                        Toast.makeText(MainActivity.this, R.string.toast1, Toast.LENGTH_SHORT).show();
//                        Log.d(LOG_TAG_2, "Code ZALO: " + code);
//                    }
//                }
//            };
//            ZaloSDK.Instance.authenticate(MainActivity.this, APP_OR_WEB, this.oAuthCompleteListener);
//        });
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);
//        ZaloSDK.Instance.onActivityResult(this, requestCode, resultCode, data);
    }

    private void Handle_User(String id, String name, String email, String img, String isDark, String isEnglish) {
        DataService dataService = APIService.getService(); // Khởi tạo Phương thức để đẩy lên
        Call<List<User>> callBack = dataService.addNewUser(id, name, email, img, isDark, isEnglish);
        callBack.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                userArrayList = (ArrayList<User>) response.body();
                if (userArrayList != null) {
                    DataLocalManager.setUserID(userArrayList.get(0).getId());

                    Log.d(TAG, "User_ID: " + userArrayList.get(0).getId());
                } else {
                    Toast.makeText(MainActivity.this, "Lỗi thêm User!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.d(TAG, "Handle_User (Error): " + t.getMessage());
            }
        });
    }

    public void onStart() {
        super.onStart();

        if (AccessToken.getCurrentAccessToken() != null && !DataLocalManager.getUserID().isEmpty()) {
            Intent intent = new Intent(MainActivity.this, FullActivity.class);
            startActivity(intent);
        }

//        if (!ZaloSDK.Instance.getOAuthCode().isEmpty()) {
//        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        this.doubleBackPress.onBackPressed();
        Log.d(TAG, "Back Twice To Exit!");
    }
}
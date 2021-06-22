package com.thanguit.imusic.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.AccessTokenTracker;
import com.facebook.FacebookSdk;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
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
import com.thanguit.imusic.API.APIService;
import com.thanguit.imusic.API.DataService;
import com.thanguit.imusic.SharedPreferences.DataLocalManager;
import com.thanguit.imusic.animations.ScaleAnimation;
import com.thanguit.imusic.R;
import com.thanguit.imusic.models.User;
import com.thanguit.imusic.services.SettingLanguage;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private SettingLanguage settingLanguage;

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

    private static final String TAG = "MainActivity";
    private static final String LOG_TAG_1 = "LOGIN WITH FACEBOOK";
//    private static final String LOG_TAG_2 = "LOGIN WITH ZALO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataLocalManager.init(this);

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
    protected void onDestroy() {
        super.onDestroy();
    }

    private void Mapping() {
        this.settingLanguage = SettingLanguage.getInstance(this);
        this.settingLanguage.Update_Language();

        this.callbackManager = CallbackManager.Factory.create();

        this.imvLogo = findViewById(R.id.imvLogo);
        this.btnLoginFB = findViewById(R.id.btnLoginFB);
//        this.btnLoginZL = findViewById(R.id.btnLoginZL);

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
                    Log.d(LOG_TAG_1, "facebook: onSuccess: " + loginResult.getAccessToken());
                    Get_User_Information();
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


    private void Get_User_Information() {
        GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), (object, response) -> {
            try {
                Log.d(LOG_TAG_1, "User information (FACEBOOK): " + object);

                String id = object.getString("id");
                DataLocalManager.setUserID(id); // Lưu ID người dùng vào SharedPreferences

                if (!DataLocalManager.getUserID().isEmpty()) {
//                    Toast.makeText(MainActivity.this, "ID:" + DataLocalManager.getUserID(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, FullActivity.class);
                    startActivity(intent);
                }
            } catch (Exception e) {
                Log.d(LOG_TAG_1, "Get_User_Information(Error):" + e.getMessage());
                Toast.makeText(MainActivity.this, R.string.toast10, Toast.LENGTH_SHORT).show();
            }
        });
        Bundle bundle = new Bundle();
        bundle.putString("fields", "id");
        graphRequest.setParameters(bundle);
        graphRequest.executeAsync(); // Thực thi không đồng bộ
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
//        ZaloSDK.Instance.onActivityResult(this, requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

//        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
//            @Override
//            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
//                if (currentAccessToken == null) {
//                    LoginManager.getInstance().logOut();
//
//                    Toast.makeText(MainActivity.this, "Log out!", Toast.LENGTH_SHORT).show();
//                    Log.d(LOG_TAG_1, "Log out!");
//                }
//            }
//        };
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        this.doubleBackPress.onBackPressed();
        Log.d(TAG, "Back Twice To Exit!");
    }
}
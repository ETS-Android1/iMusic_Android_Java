package com.thanguit.imusic.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
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
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.thanguit.imusic.animations.ScaleAnimation;
import com.thanguit.imusic.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private CallbackManager callbackManager;

    private ScaleAnimation scaleAnimation;

    private Animation topAnimation, bottomAnimation;

    private ImageView imvLogo;
    private Button btnLoginFB, btnLoginGG;

    private static final String LOG_TAG_1 = "LOGIN WITH FACEBOOK";
    private static final String LOG_TAG_2 = "LOGIN WITH GOOGLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    }

    private void Mapping() {
        this.callbackManager = CallbackManager.Factory.create();

        this.imvLogo = (ImageView) findViewById(R.id.imvLogo);
        this.btnLoginFB = (Button) findViewById(R.id.btnLoginFB);
        this.btnLoginGG = (Button) findViewById(R.id.btnLoginGG);

        this.topAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.top_animation);
        this.bottomAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.bottom_animation);
    }

    private void Event() {
        this.imvLogo.setAnimation(this.topAnimation);
        this.btnLoginFB.setAnimation(this.bottomAnimation);
        this.btnLoginGG.setAnimation(this.bottomAnimation);

        this.scaleAnimation = new ScaleAnimation(MainActivity.this, this.btnLoginFB);
        this.scaleAnimation.Event_Button();
        this.scaleAnimation = new ScaleAnimation(MainActivity.this, this.btnLoginGG);
        this.scaleAnimation.Event_Button();
    }


    private void Login_Facebook() {
        this.btnLoginFB.setOnClickListener(v -> {
            LoginManager.getInstance().logInWithReadPermissions(MainActivity.this, Arrays.asList("public_profile", "email"));
            LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    Log.d(LOG_TAG_1, "facebook: onSuccess: " + loginResult);
//                    handleFacebookAccessToken(loginResult.getAccessToken());
                    updateUI(loginResult.getAccessToken());
                    Toast.makeText(MainActivity.this, R.string.toast1, Toast.LENGTH_SHORT).show();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onStart() {
        super.onStart();

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null) {
            updateUI(accessToken);
        }
    }

    private void updateUI(AccessToken token) {
        if (token != null) {
            Intent intent = new Intent(MainActivity.this, FullActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(MainActivity.this, R.string.toast4, Toast.LENGTH_SHORT).show();
        }
    }
}
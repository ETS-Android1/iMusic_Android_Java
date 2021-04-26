package com.thanguit.imusic.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.thanguit.imusic.animations.ScaleAnimation;
import com.thanguit.imusic.R;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private CallbackManager callbackManager;
    private GoogleSignInClient googleSignInClient;

    private ScaleAnimation scaleAnimation;

    private Animation topAnimation, bottomAnimation;

    private ImageView imvLogo;
    private Button btnLoginFB, btnLoginGG;

    private final static int RC_SIGN_IN = 100;
    private final String TAG = "LOGIN WITH FACEBOOK";
    private final String TAG1 = "LOGIN WITH GOOGLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Mapping();
        Event();
        Login_Facebook();
        Login_Google();
    }

    private void Mapping() {
        this.firebaseAuth = FirebaseAuth.getInstance();
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
        this.scaleAnimation.Event();
        this.scaleAnimation = new ScaleAnimation(MainActivity.this, this.btnLoginGG);
        this.scaleAnimation.Event();
    }


    private void Login_Facebook() {
        this.btnLoginFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(MainActivity.this, Arrays.asList("public_profile", "email"));
                LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d(TAG, "facebook: onSuccess: " + loginResult);
                        handleFacebookAccessToken(loginResult.getAccessToken());
                        Toast.makeText(MainActivity.this, R.string.toast1, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel() {
                        Log.d(TAG, "facebook: onCancel");
                        Toast.makeText(MainActivity.this, R.string.toast2, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d(TAG, "facebook: onError ", error);
                        Toast.makeText(MainActivity.this, R.string.toast3, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        this.firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential: success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential: failure", task.getException());
                            Toast.makeText(MainActivity.this, R.string.toast5, Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    public void Login_Google() {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        this.googleSignInClient = GoogleSignIn.getClient(MainActivity.this, gso);

        this.btnLoginGG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        this.firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG1, "signInWithCredential: success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG1, "signInWithCredential: failure", task.getException());
                            updateUI(null);
                        }
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) { // Login Google
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG1, "firebaseAuthWithGoogle: " + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.w(TAG1, "Google sign in failed!", e);
            }
        } else { // Login Facebook
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = this.firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            updateUI(currentUser);
        }
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Intent intent = new Intent(MainActivity.this, FullActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(MainActivity.this, R.string.toast4, Toast.LENGTH_SHORT).show();
        }
    }
}
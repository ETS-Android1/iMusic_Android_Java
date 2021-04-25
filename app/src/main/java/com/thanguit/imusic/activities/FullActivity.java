package com.thanguit.imusic.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;
import com.thanguit.imusic.R;
import com.thanguit.imusic.models.User;

public class FullActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private User user;

    private ImageView Avatar;
    private TextView ID;
    private TextView Name;
    private TextView Email;
    private Button Signout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full);

        Mapping();
        Event();
    }

    private void Mapping() {
        this.Avatar = findViewById(R.id.imvAvatar);
        this.ID = findViewById(R.id.tvID);
        this.Name = findViewById(R.id.tvName);
        this.Email = findViewById(R.id.tvEmail);
        this.Signout = findViewById(R.id.btnSignout);

        this.firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = this.firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            try {
                String photoUrl = firebaseUser.getPhotoUrl() + "?height=1000&access_token=" + AccessToken.getCurrentAccessToken().getToken();
                this.user = new User(firebaseUser.getUid(), AccessToken.getCurrentAccessToken().getToken(), photoUrl, String.valueOf(firebaseUser.getDisplayName()), "", "", String.valueOf(firebaseUser.getEmail()));

                Picasso.get().load(user.getAvatar()).into(this.Avatar);
                this.ID.setText(user.getId());
                this.Name.setText(user.getName());
                this.Email.setText(user.getEmail());

                Log.d("token", user.toString());
            } catch (Exception e) {
                Toast.makeText(FullActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("test", e.getMessage());
            }
        }
    }

    private void Event() {
        Signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                LoginManager.getInstance().logOut();
                finish();
            }
        });
    }
}
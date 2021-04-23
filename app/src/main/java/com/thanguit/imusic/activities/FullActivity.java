package com.thanguit.imusic.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;
import com.thanguit.imusic.R;
import com.thanguit.imusic.models.User;

public class FullActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

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

        this.Avatar = findViewById(R.id.imvAvatar);
        this.ID = findViewById(R.id.tvID);
        this.Name = findViewById(R.id.tvName);
        this.Email = findViewById(R.id.tvEmail);
        this.Signout = findViewById(R.id.btnSignout);


        this.mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = this.mAuth.getCurrentUser();

        if (mUser != null) {
            try {
                this.user = new User(mUser.getUid(), String.valueOf(mUser.getPhotoUrl()), String.valueOf(mUser.getDisplayName()), "", "", String.valueOf(mUser.getEmail()));

                Picasso.get().load(user.getAvatar()).into(this.Avatar);
                this.ID.setText(user.getId());
                this.Name.setText(user.getName());
                this.Email.setText(user.getEmail());

                Log.d("user", user.toString());
            } catch (Exception e) {
                Toast.makeText(FullActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("test", e.getMessage());
            }
        }

        Signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                LoginManager.getInstance().logOut();
                finish();
            }
        });
    }
}
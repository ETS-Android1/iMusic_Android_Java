package com.thanguit.imusic.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.thanguit.imusic.R;
import com.thanguit.imusic.animations.ScaleAnimation;

public class PersonalPageActivity extends AppCompatActivity {

    private ImageView ivBack;
    private Button btnLogout;

    private ScaleAnimation scaleAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_page);

        Mapping();
        Event();
    }

    private void Mapping() {
        this.ivBack = findViewById(R.id.ivBack);
        this.btnLogout = findViewById(R.id.btnLogout);
    }

    private void Event() {
        this.scaleAnimation = new ScaleAnimation(PersonalPageActivity.this, this.ivBack);
        this.scaleAnimation.Event_ImageView();
        this.ivBack.setOnClickListener(v -> finish());

        this.scaleAnimation = new ScaleAnimation(PersonalPageActivity.this, this.btnLogout);
        this.scaleAnimation.Event_Button();
        this.btnLogout.setOnClickListener(v -> {
            Open_Dialog(Gravity.CENTER);
        });
    }

    private void Open_Dialog(int gravity) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_signout);

        Window window = (Window) dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // Set màu mờ mờ cho background dialog, che đi activity chính, nhưng vẫn có thể thấy được một phần

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        dialog.setCancelable(true); // Bấm ra chỗ khác sẽ thoát dialog

        Button btnDialog1 = dialog.findViewById(R.id.btnDialog1);
        Button btnDialog2 = dialog.findViewById(R.id.btnDialog2);

        this.scaleAnimation = new ScaleAnimation(PersonalPageActivity.this, btnDialog1);
        this.scaleAnimation.Event_Button();
        btnDialog1.setOnClickListener(v -> {
            dialog.dismiss();
        });

        this.scaleAnimation = new ScaleAnimation(PersonalPageActivity.this, btnDialog2);
        this.scaleAnimation.Event_Button();
        btnDialog2.setOnClickListener(v -> {
            LoginManager.getInstance().logOut();
            finish();
            moveTaskToBack(true);

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(this, R.string.toast9, Toast.LENGTH_SHORT).show();
        });

        dialog.show(); // câu lệnh này sẽ hiển thị Dialog lên
    }
}
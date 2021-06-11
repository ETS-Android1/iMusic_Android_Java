package com.thanguit.imusic.animations;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;

import com.thanguit.imusic.R;

public class LoadingDialog {
    private Activity activity;
    private AlertDialog alertDialog;

    public LoadingDialog(Activity activity) {
        this.activity = activity;
    }

    public void Start_Loading() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.activity);
        LayoutInflater inflater = this.activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.layout_loading_dialog, null));
        builder.setCancelable(false);

        this.alertDialog = builder.create();
        this.alertDialog.show();
    }

    public void Cancel_Dialog() {
        this.alertDialog.dismiss();
    }
}

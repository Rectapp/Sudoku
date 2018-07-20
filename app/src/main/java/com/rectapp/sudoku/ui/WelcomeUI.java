package com.rectapp.sudoku.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.rectapp.sudoku.MainUI;
import com.rectapp.sudoku.R;
import com.rectapp.sudoku.base.PermissionCode;
import com.rectapp.sudoku.util.PermissionsUtil;

import java.util.ArrayList;

public class WelcomeUI extends Activity {

    private Activity mActivity = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_welcome);
        initView();
        checkPermissions();
    }

    private void initView() {
        ImageView ivWelcome = findViewById(R.id.iv_wel);
        alpha(ivWelcome, 50, 1500);
    }

    private void alpha(View view, int startOffset, int duration) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0f, 1f);
        alphaAnimation.setDuration(duration);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        //alphaAnimation.setRepeatCount(-1);
        alphaAnimation.setStartOffset(startOffset);
        alphaAnimation.setFillAfter(true);
        view.startAnimation(alphaAnimation);
    }

    private void checkPermissions() {
        ArrayList<String> permissionList = getPermissionList();
        if (!permissionList.isEmpty()) {
            String[] requestPermissions = new String[permissionList.size()];
            permissionList.toArray(requestPermissions);
            PermissionsUtil.request(mActivity, requestPermissions, PermissionCode.WELCOME, this::jumpToMain);
        } else {
            jumpToMain();
        }
    }

    private void jumpToMain() {
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(WelcomeUI.this, MainUI.class);
            startActivity(intent);
            WelcomeUI.this.finish();
        }, 2500);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionCode.WELCOME) {
            //if (grantResults.length > 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                jumpToMain();

        }
    }

    private ArrayList<String> getPermissionList() {
        ArrayList<String> requestPermissionList = new ArrayList<>();
        boolean phoneStateCheckResult = ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED;
        if (!phoneStateCheckResult) {
            requestPermissionList.add(Manifest.permission.READ_PHONE_STATE);
        }

        boolean storageCheckResult = ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        if (!storageCheckResult) {
            requestPermissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        boolean locationCheckResult = ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        if (!locationCheckResult) {
            requestPermissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        boolean wifiCheckResult = ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.CHANGE_WIFI_STATE) == PackageManager.PERMISSION_GRANTED;
        if (!wifiCheckResult) {
            requestPermissionList.add(Manifest.permission.CHANGE_WIFI_STATE);
        }

        boolean bluetoothCheckResult = ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.BLUETOOTH_ADMIN) == PackageManager.PERMISSION_GRANTED;
        if (!bluetoothCheckResult) {
            requestPermissionList.add(Manifest.permission.BLUETOOTH_ADMIN);
        }

        return requestPermissionList;
    }
}

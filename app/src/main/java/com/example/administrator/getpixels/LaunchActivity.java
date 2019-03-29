package com.example.administrator.getpixels;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

public class LaunchActivity extends AppCompatActivity {
    private static final String TAG = "LaunchActivity";
    private String[] permissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: ");
        initPermission();
    }

    private void initPermission() { // android 6.0 以上需要动态申请权限
        Log.i(TAG, "initPermission: ");
        ActivityCompat.requestPermissions(this, permissions, 123);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.i(TAG, "onRequestPermissionsResult: ");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 123) {
            if (grantResults.length == permissions.length) {
                int grantCount = 0;
                for (int grantResult : grantResults) {
                    if (grantResult == PackageManager.PERMISSION_GRANTED) {
                        grantCount++;
                    }
                }
                if (grantCount == permissions.length) {
                    entry();
                } else {
                    Toast.makeText(this, "请给APP授权，否则功能无法正常使用！", Toast.LENGTH_LONG).show();
                    settting();
                }
            }
        }
    }

    private void entry() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void settting() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }
}
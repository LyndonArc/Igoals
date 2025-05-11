package com.mtsahakis.mediaprojectiondemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.projection.MediaProjectionManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    private static final int REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button displayButton = findViewById(R.id.displayButton);
        Button startButton = findViewById(R.id.startButton);
        Button stopButton = findViewById(R.id.stopButton);

        // 显示悬浮按钮
        displayButton.setOnClickListener(v -> showFloatingButton());

        // 开始截屏
        startButton.setOnClickListener(v -> startProjection());

        // 停止截屏并隐藏悬浮按钮
        stopButton.setOnClickListener(v -> {
            stopProjection();
            removeFloatingButton();
        });
    }

    private void startProjection() {
        MediaProjectionManager mProjectionManager =
                (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        startActivityForResult(mProjectionManager.createScreenCaptureIntent(), REQUEST_CODE);
    }

    private void stopProjection() {
        stopService(ScreenCaptureService.getStopIntent(this));
    }

    private void showFloatingButton() {
        Intent intent = new Intent(this, FloatingButtonService.class);
        startService(intent);
    }

    private void removeFloatingButton() {
        Intent intent = new Intent(this, FloatingButtonService.class);
        stopService(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                startService(ScreenCaptureService.getStartIntent(this, resultCode, data));
            }
        }
    }
}

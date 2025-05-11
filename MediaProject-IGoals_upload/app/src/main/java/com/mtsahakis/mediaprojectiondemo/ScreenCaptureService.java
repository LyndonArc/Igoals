package com.mtsahakis.mediaprojectiondemo;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageFormat;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.Image;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.IBinder;
import android.util.Log;
import java.nio.ByteBuffer;

public class ScreenCaptureService extends Service {

    private static final String TAG = "ScreenCaptureService";
    private static final String ACTION = "ACTION";
    private static final String START = "START";
    private static final String STOP = "STOP";
    private static final String RESULT_CODE = "RESULT_CODE";
    private static final String DATA = "DATA";
    private MediaProjection mediaProjection;
    private ImageReader imageReader;
    private VirtualDisplay virtualDisplay;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getStringExtra(ACTION);
        if (START.equals(action)) {
            int resultCode = intent.getIntExtra(RESULT_CODE, Activity.RESULT_CANCELED);
            Intent data = intent.getParcelableExtra(DATA);
            startProjection(resultCode, data);
        } else if (STOP.equals(action)) {
            stopProjection();
            stopSelf();
        } else if ("CAPTURE".equals(action)) {
            captureScreenshot();
        }
        return START_NOT_STICKY;
    }

    private void startProjection(int resultCode, Intent data) {
        MediaProjectionManager projectionManager =
                (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        mediaProjection = projectionManager.getMediaProjection(resultCode, data);

        // 使用 ImageFormat.RGB_565 作为图像格式
        imageReader = ImageReader.newInstance(1080, 1920, ImageFormat.RGB_565, 2);
        virtualDisplay = mediaProjection.createVirtualDisplay(
                "ScreenCapture",
                1080, 1920, 320,
                DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                imageReader.getSurface(), null, null
        );
        Log.d(TAG, "Screen projection started");
    }

    private void stopProjection() {
        if (mediaProjection != null) {
            mediaProjection.stop();
            Log.d(TAG, "Screen projection stopped");
        }
    }

    private void captureScreenshot() {
        try {
            Image image = imageReader.acquireLatestImage();
            if (image != null) {
                Image.Plane[] planes = image.getPlanes();
                ByteBuffer buffer = planes[0].getBuffer();
                int width = image.getWidth();
                int height = image.getHeight();
                int pixelStride = planes[0].getPixelStride();
                int rowStride = planes[0].getRowStride();

                // 计算实际宽度
                int rowPadding = rowStride - pixelStride * width;
                Bitmap bitmap = Bitmap.createBitmap(
                        width + rowPadding / pixelStride,
                        height, Bitmap.Config.RGB_565);
                bitmap.copyPixelsFromBuffer(buffer);
                image.close();
                Log.d(TAG, "Screenshot captured");
                // 可以在此处保存图片
            }
        } catch (Exception e) {
            Log.e(TAG, "Capture failed", e);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (virtualDisplay != null) {
            virtualDisplay.release();
        }
        if (imageReader != null) {
            imageReader.close();
        }
        Log.d(TAG, "Service destroyed");
    }

    // 静态方法：获取开始服务的Intent
    public static Intent getStartIntent(Context context, int resultCode, Intent data) {
        Intent intent = new Intent(context, ScreenCaptureService.class);
        intent.putExtra(ACTION, START);
        intent.putExtra(RESULT_CODE, resultCode);
        intent.putExtra(DATA, data);
        return intent;
    }

    // 静态方法：获取停止服务的Intent
    public static Intent getStopIntent(Context context) {
        Intent intent = new Intent(context, ScreenCaptureService.class);
        intent.putExtra(ACTION, STOP);
        return intent;
    }
}

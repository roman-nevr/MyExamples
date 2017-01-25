package ru.rubicon.myexamples;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import ru.rubicon.myexamples.adapters.CameraPreview;

import static android.hardware.Camera.CameraInfo.CAMERA_FACING_BACK;

/**
 * Created by Admin on 10.01.2017.
 */

public class BarCode extends AppCompatActivity implements SurfaceHolder.Callback {

    private Camera mCamera;
    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    private boolean mIsPreviewing = false;;
    private Camera.Parameters parameters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.barcode);

        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        Button butStartPreview = (Button) findViewById(R.id.buttonStartPreview);
        Button butStopPreview = (Button) findViewById(R.id.buttonStopPreview);

        getWindow().setFormat(PixelFormat.UNKNOWN);
        mSurfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);
        // устарело. нужен для старых версий
        // mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        butStartPreview.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mIsPreviewing) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                        mCamera = Camera.open(0);
                    } else {
                        mCamera = Camera.open();
                    }
                    if (mCamera != null) {
                        try {
                            mCamera.setPreviewDisplay(mSurfaceHolder);
                            mCamera.startPreview();
                            mIsPreviewing = true;
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        butStopPreview.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mCamera != null && mIsPreviewing) {
                    mCamera.stopPreview();
                    mCamera.release();
                    mCamera = null;

                    mIsPreviewing = false;
                }
            }
        });

        mSurfaceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoFocus(Camera.Parameters.FOCUS_MODE_MACRO);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        int camera = CAMERA_FACING_BACK;
        // TODO Auto-generated method stub
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            mCamera = Camera.open(camera);
        } else {
            mCamera = Camera.open();
        }
        setCameraDisplayOrientation(camera, mCamera);
    }

    private void setCameraDisplayOrientation(int cameraId, android.hardware.Camera camera) {
        android.hardware.Camera.CameraInfo info =
                new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(cameraId, info);
        int rotation = getWindowManager().getDefaultDisplay()
                .getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0: degrees = 0; break;
            case Surface.ROTATION_90: degrees = 90; break;
            case Surface.ROTATION_180: degrees = 180; break;
            case Surface.ROTATION_270: degrees = 270; break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        System.out.println(result);
        camera.setDisplayOrientation(result);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        // TODO Auto-generated method stub
        if (mIsPreviewing) {
            mCamera.stopPreview();
            mIsPreviewing = false;
        }

        if (mCamera != null) {
            // узнаем параметры для наилучшего размера предварительного
            // просмотра
            parameters = mCamera.getParameters();
            Camera.Size bestSize = getNaturalPreviewSize(width, height, parameters);

            if (bestSize != null) {
                parameters.setPreviewSize(bestSize.width, bestSize.height);
                mCamera.setParameters(parameters);

                Toast.makeText(
                        getApplicationContext(),
                        "Установлен размер: " + bestSize.width
                                + " : " + bestSize.height,
                        Toast.LENGTH_LONG).show();
            }
            try {
                mCamera.setPreviewDisplay(mSurfaceHolder);
                mCamera.startPreview();
                mIsPreviewing = true;
                //autoFocus(Camera.Parameters.FOCUS_MODE_MACRO);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private void autoFocus(String autoFocusMode){
        if (parameters.getSupportedFocusModes().contains(autoFocusMode)) {
            parameters.setFocusMode(autoFocusMode);

            mCamera.autoFocus(new Camera.AutoFocusCallback() {
                public void onAutoFocus(boolean success, Camera camera) {
                    // TODO Использовать значение success
                    if(success){
                        toast("AutoFocus");
                    }
                }
            });
        }
    }

    private void toast(String message){
        Toast.makeText(
                getApplicationContext(),
                message,
                Toast.LENGTH_LONG).show();
    }

    private Camera.Size getNaturalPreviewSize(int width, int height,
                                           Camera.Parameters parameters) {
        Camera.Size bestSize = null;
        List<Camera.Size> sizeList = parameters.getSupportedPreviewSizes();

        bestSize = sizeList.get(0);

        for (int i = 1; i < sizeList.size(); i++) {
            if ((sizeList.get(i).width * sizeList.get(i).height) > (bestSize.width * bestSize.height)) {
                bestSize = sizeList.get(i);
            }
        }
        return bestSize;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
            mIsPreviewing = false;
        }
    }
}

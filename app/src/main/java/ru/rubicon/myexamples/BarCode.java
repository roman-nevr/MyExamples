package ru.rubicon.myexamples;

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

/**
 * Created by Admin on 10.01.2017.
 */

public class BarCode extends AppCompatActivity implements SurfaceHolder.Callback {

    private Camera mCamera;
    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    private boolean mIsPreviewing = false;;

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
        // TODO Auto-generated method stub
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            mCamera = Camera.open(0);
        } else {
            mCamera = Camera.open();
        }
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
            Camera.Parameters parameters = mCamera.getParameters();
            Camera.Size bestSize = getNaturalPreviewSize(width, height, parameters);

            if (bestSize != null) {
                parameters.setPreviewSize(width, height);
                mCamera.setParameters(parameters);

                Toast.makeText(
                        getApplicationContext(),
                        "Оптимальный размер: " + String.valueOf(bestSize.width)
                                + " : " + String.valueOf(bestSize.height),
                        Toast.LENGTH_LONG).show();
            }
            try {
                mCamera.setPreviewDisplay(mSurfaceHolder);
                mCamera.startPreview();
                mIsPreviewing = true;

                mCamera.autoFocus(new Camera.AutoFocusCallback() {
                    @Override
                    public void onAutoFocus(boolean success, Camera camera) {

                    }
                });
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private Camera.Size getBestPreviewSize(int width, int height,
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

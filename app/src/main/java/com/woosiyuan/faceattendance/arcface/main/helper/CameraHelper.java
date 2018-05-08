package com.woosiyuan.faceattendance.arcface.main.helper;

import android.hardware.Camera;
import android.util.Log;
import com.woosiyuan.faceattendance.arcface.callback.ISetupCamera;
import java.util.List;

/**
 * 说明：
 *
 * @version V1.0
 * @FileName: com.woosiyuan.faceattendance.arcface.register.helper.CameraHelper.java
 * @author: so
 * @date: 2017-12-29 10:25
 */

public class CameraHelper implements ISetupCamera {
    private static Camera              mCamera;
    private final String TAG = this.getClass().getSimpleName();

    @Override public Camera setupCamera(int mWidth_,int  mHeight_,int mCameraID,int mFormat) {
        mCamera = Camera.open(mCameraID);
        if(mCamera==null){
            return null;
        }
        else {
            try {
                Camera.Parameters parameters = mCamera.getParameters();
                parameters.setPreviewSize(mWidth_, mHeight_);
                parameters.setPreviewFormat(mFormat);

                for( Camera.Size size : parameters.getSupportedPreviewSizes()) {
                    Log.d(TAG, "SIZE:" + size.width + "x" + size.height);
                }
                for( Integer format : parameters.getSupportedPreviewFormats()) {
                    Log.d(TAG, "FORMAT:" + format);
                }

                List<int[]> fps = parameters.getSupportedPreviewFpsRange();
                for(int[] count : fps) {
                    Log.d(TAG, "T:");
                    for (int data : count) {
                        Log.d(TAG, "V=" + data);
                    }
                }
                mCamera.setParameters(parameters);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mCamera;
    }
}

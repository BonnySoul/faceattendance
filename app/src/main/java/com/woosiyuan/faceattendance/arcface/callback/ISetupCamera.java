package com.woosiyuan.faceattendance.arcface.callback;

import android.hardware.Camera;

/**
 * 说明：
 *
 * @version V1.0
 * @FileName: com.woosiyuan.faceattendance.arcface.callback.ISetupCamera.java
 * @author: so
 * @date: 2017-12-29 10:24
 */

public interface ISetupCamera  {
    Camera setupCamera(int mWidth_,int  mHeight_,int mCameraID,int mFormat);
}

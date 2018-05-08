package com.woosiyuan.faceattendance.arcface.callback;

import com.arcsoft.facedetection.AFD_FSDKFace;
import com.arcsoft.facerecognition.AFR_FSDKFace;
import java.util.List;

/**
 * 说明：
 *
 * @version V1.0
 * @FileName: com.woosiyuan.faceattendance.arcface.callback.AddFaceListener.java
 * @author: so
 * @date: 2018-01-02 14:27
 */

public interface AddFaceListener {

    void onSuccess(List<AFD_FSDKFace> mList,AFR_FSDKFace mAFD_FSDKFace);
    void onFailed(String msg);
}

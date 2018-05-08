package com.woosiyuan.faceattendance.arcface.main.model;

import com.arcsoft.facedetection.AFD_FSDKFace;
import com.arcsoft.facerecognition.AFR_FSDKFace;
import com.woosiyuan.faceattendance.arcface.callback.AddFaceListener;
import java.util.List;

/**
 * 说明：
 *
 * @version V1.0
 * @FileName: com.woosiyuan.faceattendance.arcface.register.model.IregisterModel.java
 * @author: so
 * @date: 2018-01-02 13:21
 */

public interface IregisterModel {
    List<AFD_FSDKFace> addFace(String  path, AddFaceListener mAddFaceListener);
    void registFace(String name,String tag,AFR_FSDKFace mAFR_FSDKFace);
    void delete(String name);
}

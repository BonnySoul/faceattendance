package com.woosiyuan.faceattendance.basis.callback;

import com.arcsoft.facerecognition.AFR_FSDKFace;
import com.woosiyuan.faceattendance.arcface.util.FaceRegist;
import com.woosiyuan.faceattendance.basis.entity.FaceMessage;
import java.util.List;

/**
 * 说明：
 *
 * @version V1.0
 * @FileName: com.woosiyuan.faceattendance.basis.callback.IFace.java
 * @author: so
 * @date: 2018-01-17 09:33
 */

public interface IFace {

    void deleteFace(String name);
    void addFace(String name, String tags, List<AFR_FSDKFace> list);
    List<FaceRegist> queryAllFace();
    List<FaceMessage>queryAllFaceMessage();
    String getFaceTags(String name);

}

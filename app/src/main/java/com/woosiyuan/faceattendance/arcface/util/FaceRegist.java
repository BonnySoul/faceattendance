package com.woosiyuan.faceattendance.arcface.util;

import com.arcsoft.facerecognition.AFR_FSDKFace;
import java.util.ArrayList;
import java.util.List;

/**
 * 说明：
 *
 * @version V1.0
 * @FileName: com.woosiyuan.cruise2.face.arcface.util.FaceRegist.java
 * @author: so
 * @date: 2017-12-06 13:11
 */

public class FaceRegist {

        public String             mName;
        public List<AFR_FSDKFace> mFaceList;

    public FaceRegist(String name, List<AFR_FSDKFace> faceList) {
        mName = name;
        mFaceList = faceList;
    }

    public FaceRegist(String name) {
            mName = name;
            mFaceList = new ArrayList<>();
        }
}

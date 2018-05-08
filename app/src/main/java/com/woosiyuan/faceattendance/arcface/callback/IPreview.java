package com.woosiyuan.faceattendance.arcface.callback;

import com.arcsoft.facetracking.AFT_FSDKEngine;

/**
 * 说明：
 *
 * @version V1.0
 * @FileName: com.woosiyuan.faceattendance.arcface.callback.IPreview.java
 * @author: so
 * @date: 2017-12-29 10:13
 */

public interface IPreview {
     Object onPreview(byte[] data, int width, int height);
}

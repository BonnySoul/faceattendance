package com.woosiyuan.faceattendance.basis.util;

import android.graphics.Bitmap;

/**
 * 说明：
 *
 * @version V1.0
 * @FileName: com.woosiyuan.faceattendance.basis.util.Constants.java
 * @author: so
 * @date: 2017-12-28 13:27
 */

public class Constants {

    public static final String KEY_USER_NAME_SETTING="name_setting";
    public static final String KEY_USER_TEL_SETTING="tel_setting";

    public static final String VOICE_ONE="你好，打卡成功";
    public static final String VOICE_TWO="恭喜你，人脸注册成功";

    public static  final float SIMILAR_THRESHOLD=0.4f; //认定为同一个人的阈值

    public static       String KEY_PASSWORD     ="password";//账号密码
    public static       String KEY_GO_WORK     ="gowork";//上班
    public static       String KEY_OUT_WORK     ="outwork";//下班
    public static       String KEY_USER_NAME    ="username";//用户用户用户用户名称
    public static       String KEY_USER_ADDR    ="useraddr";//用户地址
    public static       String KEY_EDIT_CODE    ="edit_code";
    public static       String KEY_USER_PICTURE ="user_img";
    public static       Bitmap user_picture ;
    public static final String ROOT_DIR_NAME    = "FaceAttendance";

    public static final int RESULT_LOAD_IMAGE = 1338;
    public static final int TAKE_PICTURE = 1339;
    public static final int CUT_PHOTO_REQUEST_CODE = 1340;


}

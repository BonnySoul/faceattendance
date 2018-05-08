package com.woosiyuan.faceattendance;

import android.app.Application;
import android.net.Uri;
import android.util.Log;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.woosiyuan.faceattendance.arcface.util.FaceDB;
import com.woosiyuan.faceattendance.basis.util.Constants;
import com.woosiyuan.faceattendance.basis.util.DirectoryManager;
import com.woosiyuan.faceattendance.basis.util.RxBus;
import com.woosiyuan.mylibrary.util.SDCardUtil;
import com.woosiyuan.mylibrary.util.SharedPreferencesUtil;

/**
 * 说明：
 *
 * @version V1.0
 * @FileName: com.woosiyuan.faceattendance.MyApplication.java
 * @author: so
 * @date: 2017-12-28 13:17
 */

public class MyApplication extends Application {
    Uri mImage;
    public         FaceDB                mFaceDB;
    private        SharedPreferencesUtil mPreferencesUtil;
    private static MyApplication         INSTANCE;

    public MyApplication() {
    }
    public static MyApplication getINSTANCE() {
        return INSTANCE;
    }

    @Override public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        this.getSharedPreferences();
        mFaceDB = new FaceDB(this.getExternalCacheDir().getPath());
        Log.d("MyApplication","onCreate");
        //noinspection SpellCheckingInspection
        // 科大讯飞SDK初始化，暂时未用到
        SpeechUtility.createUtility(this, SpeechConstant.APPID +getString(R
                .string
                .iflytek_app_id));
        DirectoryManager.init();
    }



    public SharedPreferencesUtil getSharedPreferences() {
        if(null == this.mPreferencesUtil) {
            this.mPreferencesUtil = new SharedPreferencesUtil(this.getApplicationContext(), "CruiseRobotPreferences");
        }
        return this.mPreferencesUtil;
    }

    public void setCaptureImage(Uri uri) {
        mImage = uri;
    }

    public Uri getCaptureImage() {
        return mImage;
    }
}

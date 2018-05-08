package com.woosiyuan.faceattendance.arcface.main.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Message;
import android.util.Log;
import com.arcsoft.facedetection.AFD_FSDKEngine;
import com.arcsoft.facedetection.AFD_FSDKError;
import com.arcsoft.facedetection.AFD_FSDKFace;
import com.arcsoft.facedetection.AFD_FSDKVersion;
import com.arcsoft.facerecognition.AFR_FSDKEngine;
import com.arcsoft.facerecognition.AFR_FSDKError;
import com.arcsoft.facerecognition.AFR_FSDKFace;
import com.arcsoft.facerecognition.AFR_FSDKVersion;
import com.guo.android_extend.image.ImageConverter;
import com.woosiyuan.faceattendance.MyApplication;
import com.woosiyuan.faceattendance.arcface.callback.AddFaceListener;
import com.woosiyuan.faceattendance.basis.util.BitmapUtil;
import com.woosiyuan.faceattendance.arcface.util.FaceDB;
import java.util.ArrayList;
import java.util.List;

/**
 * 说明：
 *
 * @version V1.0
 * @FileName: com.woosiyuan.faceattendance.arcface.register.model.RegisterModelImpl.java
 * @author: so
 * @date: 2018-01-02 13:32
 */

public class RegisterModelImpl implements IregisterModel {

    private boolean isFlag;
    private final String TAG="RegisterModel";
    private Context   mContext;
    private String    name;
    private Rect src = new Rect();
    private Rect dst = new Rect();
    private AFR_FSDKFace mAFR_FSDKFace;
    private final static int MSG_CODE = 0x1000;
    private final static int MSG_EVENT_REG = 0x1001;
    private final static int MSG_EVENT_NO_FACE = 0x1002;
    private final static int MSG_EVENT_NO_FEATURE = 0x1003;
    private final static int MSG_EVENT_FD_ERROR = 0x1004;
    private final static int MSG_EVENT_FR_ERROR = 0x1005;
    private Bitmap mBitmap;



    @Override public List<AFD_FSDKFace> addFace(String  path,AddFaceListener onListenerRegister) {
        mBitmap = BitmapUtil.decodeImage(path);
        byte[] data = new byte[mBitmap.getWidth() * mBitmap.getHeight() * 3 / 2];
        ImageConverter convert = new ImageConverter();
        convert.initial(mBitmap.getWidth(), mBitmap.getHeight(), ImageConverter.CP_PAF_NV21);
        if (convert.convert(mBitmap, data)) {
            Log.d(TAG, "convert ok!");
        }
        convert.destroy();
        AFD_FSDKEngine engine = new AFD_FSDKEngine();
        AFD_FSDKVersion version = new AFD_FSDKVersion();
        List<AFD_FSDKFace> result = new ArrayList<AFD_FSDKFace>();
        AFD_FSDKError err = engine.AFD_FSDK_InitialFaceEngine(FaceDB.appid, FaceDB.fd_key, AFD_FSDKEngine.AFD_OPF_0_HIGHER_EXT, 16, 5);
        if (err.getCode() != AFD_FSDKError.MOK) {
            Message reg = Message.obtain();
            reg.what = MSG_CODE;
            reg.arg1 = MSG_EVENT_FD_ERROR;
            reg.arg2 = err.getCode();
            //mUIHandler.sendMessage(reg);
            onListenerRegister.onFailed("FD初始化失败，错误码："+reg.arg2);
        }
        err = engine.AFD_FSDK_GetVersion(version);
        Log.d(TAG, "AFD_FSDK_GetVersion =" + version.toString() + ", " + err.getCode());
        err  = engine.AFD_FSDK_StillImageFaceDetection(data, mBitmap.getWidth(), mBitmap.getHeight(), AFD_FSDKEngine.CP_PAF_NV21, result);
        Log.d(TAG, "AFD_FSDK_StillImageFaceDetection =" + err.getCode() + "<" + result.size());
        if (!result.isEmpty()) {
            AFR_FSDKVersion version1 = new AFR_FSDKVersion();
            AFR_FSDKEngine engine1 = new AFR_FSDKEngine();
            AFR_FSDKFace result1 = new AFR_FSDKFace();
            AFR_FSDKError error1 = engine1.AFR_FSDK_InitialEngine(FaceDB.appid, FaceDB.fr_key);
            if (error1.getCode() != AFD_FSDKError.MOK) {
                Message reg = Message.obtain();
                reg.what = MSG_CODE;
                reg.arg1 = MSG_EVENT_FR_ERROR;
                reg.arg2 = error1.getCode();
                //mUIHandler.sendMessage(reg);
                onListenerRegister.onFailed("FR初始化失败，错误码：" + reg.arg2);
            }
            error1 = engine1.AFR_FSDK_GetVersion(version1);
            Log.d("com.arcsoft", "FR=" + version.toString() + "," + error1.getCode()); //(210, 178 - 478, 446), degree = 1　780, 2208 - 1942, 3370
            error1 = engine1.AFR_FSDK_ExtractFRFeature(data, mBitmap.getWidth(), mBitmap.getHeight(), AFR_FSDKEngine.CP_PAF_NV21, new Rect(result.get(0).getRect()), result.get(0).getDegree(), result1);
            Log.d("com.arcsoft", "Face=" + result1.getFeatureData()[0] + "," + result1.getFeatureData()[1] + "," + result1.getFeatureData()[2] + "," + error1.getCode());
            if(error1.getCode() == error1.MOK) {
                mAFR_FSDKFace = result1.clone();
                int width = result.get(0).getRect().width();
                int height = result.get(0).getRect().height();
                Bitmap face_bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
                Canvas face_canvas = new Canvas(face_bitmap);
                face_canvas.drawBitmap(mBitmap, result.get(0).getRect(), new Rect(0, 0, width, height), null);
                Message reg = Message.obtain();
                reg.what = MSG_CODE;
                reg.arg1 = MSG_EVENT_REG;
                reg.obj = face_bitmap;
                //mUIHandler.sendMessage(reg);
                //MyApplication.getINSTANCE().mFaceDB.addFace(name,mAFR_FSDKFace);
                onListenerRegister.onSuccess(result,mAFR_FSDKFace);

            } else {
                Message reg = Message.obtain();
                reg.what = MSG_CODE;
                reg.arg1 = MSG_EVENT_NO_FEATURE;
                //mUIHandler.sendMessage(reg);
                onListenerRegister.onFailed("人脸特征无法检测，请换一张图片");
            }
            error1 = engine1.AFR_FSDK_UninitialEngine();
            Log.d("com.arcsoft", "AFR_FSDK_UninitialEngine : " + error1.getCode());
        } else {
            Message reg = Message.obtain();
            reg.what = MSG_CODE;
            reg.arg1 = MSG_EVENT_NO_FACE;
            //mUIHandler.sendMessage(reg);
            onListenerRegister.onFailed("没有检测到人脸，请换一张图片");
        }
        err = engine.AFD_FSDK_UninitialFaceEngine();
        Log.d(TAG, "AFD_FSDK_UninitialFaceEngine =" + err.getCode());
        return result;
    }

    @Override public void registFace(String name, String tag, AFR_FSDKFace mAFR_FSDKFace) {
        MyApplication.getINSTANCE().mFaceDB.addFace(name,mAFR_FSDKFace);
    }

    @Override public void delete(String name) {
        MyApplication.getINSTANCE().mFaceDB.delete(name);
    }

}

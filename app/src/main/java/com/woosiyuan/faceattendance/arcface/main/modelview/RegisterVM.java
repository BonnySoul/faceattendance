package com.woosiyuan.faceattendance.arcface.main.modelview;

import android.content.Context;
import com.arcsoft.facerecognition.AFR_FSDKFace;
import com.woosiyuan.faceattendance.MyApplication;
import com.woosiyuan.faceattendance.arcface.callback.AddFaceListener;
import com.woosiyuan.faceattendance.arcface.main.model.RegisterModelImpl;
import com.woosiyuan.faceattendance.arcface.util.FaceRegist;
import com.woosiyuan.faceattendance.basis.db.manager.UserManager;
import com.woosiyuan.faceattendance.basis.entity.FaceMessage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 说明：
 *
 * @version V1.0
 * @FileName: com.woosiyuan.faceattendance.arcface.register.modelview.RegisterVM.java
 * @author: so
 * @date: 2018-01-02 14:22
 */

public class RegisterVM {

    RegisterModelImpl mRegisterModel;
    UserManager mUserManager;

    public RegisterVM(Context mContext) {
        mRegisterModel=new RegisterModelImpl();
        mUserManager = UserManager.getInstance(mContext.getApplicationContext());
    }

    /**传入人脸照片图片路径  AddFaceListener中回调结果反馈该图片是否可以检测到人脸**/
    public void addFace(String  path, AddFaceListener mAddFaceListener){
          mRegisterModel.addFace(path,mAddFaceListener);
    }

    /**图片人脸检测成功后将调用该方式将该图片人脸信息添加到人脸库**/
    public void registFace(String name,String tags,AFR_FSDKFace mAFR_FSDKFace){
        List<AFR_FSDKFace>list=new ArrayList<>();
        list.add(mAFR_FSDKFace);
        mUserManager.addFace(name,tags,list);
    }

    /**  该方法仅仅用来考勤时获取人脸库进行对比**/
    public List<FaceRegist> getAllFace(){
        //return MyApplication.getINSTANCE().mFaceDB.mRegister;
        return mUserManager.queryAllFace();
    }

    /**  该方法用来获取所有人脸信息**/
    public List<FaceMessage> getAllFaceMessage(){
        //return MyApplication.getINSTANCE().mFaceDB.mRegister;
        return mUserManager.queryAllFaceMessage();
    }

    /**  通过name删除人脸库信息**/
    public void deleteFace(String name){
        mUserManager.deleteFace(name);
        mUserManager.deleteUserAttend(name);

    }


    /**  通过name查询tags**/
    public String nameQueryTags(String name){
        return mUserManager.getFaceTags(name);
    }

    /**  通过name，或者tags查询人脸库中是否有该人脸信息**/
    public boolean isContain(String key){
        return mUserManager.isContain(key);
    }



}

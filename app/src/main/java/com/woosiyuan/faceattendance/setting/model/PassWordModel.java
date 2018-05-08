package com.woosiyuan.faceattendance.setting.model;

import com.woosiyuan.faceattendance.MyApplication;
import com.woosiyuan.faceattendance.basis.util.Constants;
import com.woosiyuan.faceattendance.setting.callback.PassWordModelImp;
import com.woosiyuan.faceattendance.setting.callback.PassWorkVmImp;
import com.woosiyuan.mylibrary.util.SharedPreferencesUtil;

/**
 * 说明：
 *
 * @version V1.0
 * @FileName: com.woosiyuan.faceattendance.setting.model.PassWordModel.java
 * @author: so
 * @date: 2018-01-18 11:00
 */

public class PassWordModel implements PassWordModelImp {

    private PassWorkVmImp mPassWorkVmImp;
    private SharedPreferencesUtil mPreferencesUtil;
    private String passWord;

    public PassWordModel(PassWorkVmImp passWorkVmImp) {
        mPassWorkVmImp = passWorkVmImp;
        mPreferencesUtil= MyApplication.getINSTANCE().getSharedPreferences();
        passWord=mPreferencesUtil.getStringValue(Constants.KEY_PASSWORD,"");
    }

    @Override public void settingPassWord(String oldPass, String newPass, String twoPass) {
        if(!oldPass.equals(passWord)){
            mPassWorkVmImp.settingFailed("原始密码输入错误");
            return;
        }
        if(!newPass.equals(twoPass)){
            mPassWorkVmImp.settingFailed("新密码两次输入不一致");
            return;
        }
        mPreferencesUtil.putStringValue(Constants.KEY_PASSWORD,newPass);
        mPassWorkVmImp.settingSuccess();

    }
}

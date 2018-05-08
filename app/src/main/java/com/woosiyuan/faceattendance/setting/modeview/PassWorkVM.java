package com.woosiyuan.faceattendance.setting.modeview;

import com.woosiyuan.faceattendance.setting.callback.PassWorkVmImp;
import com.woosiyuan.faceattendance.setting.model.PassWordModel;

/**
 * 说明：
 *
 * @version V1.0
 * @FileName: com.woosiyuan.faceattendance.setting.modeview.PassWorkVM.java
 * @author: so
 * @date: 2018-01-18 11:15
 */

public class PassWorkVM  {

    PassWordModel mPassWordModel;

    public PassWorkVM(PassWorkVmImp passWorkVmImp) {
        mPassWordModel = new PassWordModel(passWorkVmImp);
    }

    public void settingPassWord(String oldPass, String newPass, String twoPass) {
        mPassWordModel.settingPassWord(oldPass,newPass,twoPass);
    }
}

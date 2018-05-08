package com.woosiyuan.faceattendance.setting.model;

import com.woosiyuan.faceattendance.MyApplication;
import com.woosiyuan.faceattendance.basis.util.Constants;
import com.woosiyuan.faceattendance.setting.callback.WorkTimeImp;
import com.woosiyuan.mylibrary.util.SharedPreferencesUtil;

/**
 * 说明：
 *
 * @version V1.0
 * @FileName: com.woosiyuan.faceattendance.setting.model.WorkTimeModel.java
 * @author: so
 * @date: 2018-01-25 10:52
 */

public class WorkTimeModel implements WorkTimeImp {

    SharedPreferencesUtil mPreferencesUtil;

    public WorkTimeModel() {
        mPreferencesUtil = MyApplication.getINSTANCE().getSharedPreferences();
    }

    @Override public void setWorkTime(String go, String out) {
        mPreferencesUtil.putStringValue(Constants.KEY_GO_WORK,go);
        mPreferencesUtil.putStringValue(Constants.KEY_OUT_WORK,out);
    }
}

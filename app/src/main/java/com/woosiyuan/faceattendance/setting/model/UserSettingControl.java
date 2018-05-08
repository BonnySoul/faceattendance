package com.woosiyuan.faceattendance.setting.model;

import com.woosiyuan.faceattendance.MyApplication;
import com.woosiyuan.faceattendance.basis.entity.User;
import com.woosiyuan.faceattendance.basis.util.Constants;
import com.woosiyuan.faceattendance.setting.callback.IUser;
import com.woosiyuan.faceattendance.setting.callback.SettingCallback;

/**
 * 说明：
 *
 * @version V1.0
 * @FileName: com.woosiyuan.faceattendance.setting.model.UserSettingControl.java
 * @author: so
 * @date: 2017-12-28 13:12
 */

public class UserSettingControl implements IUser {

    @Override
   public void saveMsg(User mUser, SettingCallback mSettingCallback) {
       MyApplication.getINSTANCE().getSharedPreferences().getStringValue(Constants
               .KEY_USER_NAME_SETTING,mUser.getName());
       MyApplication.getINSTANCE().getSharedPreferences().getStringValue(Constants
               .KEY_USER_TEL_SETTING,mUser.getTel());
        mSettingCallback.settingSuccess();
    }
}

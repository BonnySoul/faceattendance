package com.woosiyuan.faceattendance.setting.callback;

import com.woosiyuan.faceattendance.basis.entity.User;

/**
 * 说明：
 *
 * @version V1.0
 * @FileName: com.woosiyuan.faceattendance.setting.callback.IUser.java
 * @author: so
 * @date: 2017-12-28 13:09
 */

public interface IUser {
    void saveMsg(User mUser, SettingCallback mSettingCallback);
}

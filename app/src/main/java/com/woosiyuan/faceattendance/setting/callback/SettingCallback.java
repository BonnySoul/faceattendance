package com.woosiyuan.faceattendance.setting.callback;

/**
 * 说明：
 *
 * @version V1.0
 * @FileName: com.woosiyuan.faceattendance.setting.callback.SettingCallback.java
 * @author: so
 * @date: 2017-12-28 13:13
 */

public interface SettingCallback {

    void settingSuccess();
    void settingFailed(String msg);

}

package com.woosiyuan.faceattendance.setting.modeview;

import android.content.Context;
import android.widget.Toast;
import com.woosiyuan.faceattendance.basis.entity.User;
import com.woosiyuan.faceattendance.databinding.UserActivityBinding;
import com.woosiyuan.faceattendance.setting.callback.SettingCallback;
import com.woosiyuan.faceattendance.setting.model.UserSettingControl;
import com.woosiyuan.faceattendance.setting.model.WorkTimeModel;

/**
 * 说明：
 *
 * @version V1.0
 * @FileName: com.woosiyuan.faceattendance.setting.presenter.UserSetHelper.java
 * @author: so
 * @date: 2017-12-28 14:39
 */

public class UserSetHelper implements SettingCallback{

    private UserSettingControl  mUserSettingControl;
    private WorkTimeModel mWorkTimeModel;
    private Context             mContext;
    private UserActivityBinding mActivityMainBinding;

    public UserSetHelper(Context context) {
        mContext = context;
        mWorkTimeModel=new WorkTimeModel();
    }

    public void saveMsg(User mUser){
        mUserSettingControl.saveMsg(mUser,this);
    }

    @Override public void settingSuccess() {
        Toast.makeText(mContext,"settingSuccess",Toast.LENGTH_SHORT).show();
    }

    @Override public void settingFailed(String msg) {
        Toast.makeText(mContext,"settingSuccess",Toast.LENGTH_SHORT).show();
    }

    public void saveWorkTime(String go,String out){
        mWorkTimeModel.setWorkTime(go,out);
    }
}

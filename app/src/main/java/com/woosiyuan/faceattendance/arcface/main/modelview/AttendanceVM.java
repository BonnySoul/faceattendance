package com.woosiyuan.faceattendance.arcface.main.modelview;

import android.content.Context;
import com.woosiyuan.faceattendance.basis.db.manager.UserManager;
import com.woosiyuan.faceattendance.basis.entity.User;
import java.util.ArrayList;
import java.util.List;

/**
 * 说明：
 *
 * @version V1.0
 * @FileName: com.woosiyuan.faceattendance.arcface.main.modelview.AttendanceVM.java
 * @author: so
 * @date: 2018-01-04 15:00
 */

public class AttendanceVM {

    private UserManager mUserManager;

    public AttendanceVM(Context mContext) {
        mUserManager = UserManager.getInstance(mContext.getApplicationContext());
    }
    /**  获取到所有的人脸考勤数据**/
    public List<User> getAllAttendance(){
        return mUserManager.queryAllMsmList();
    }

    /**  通过关键字（name或者tags）来获取到人脸考勤数据**/
    public List<User>getKeyAttendance(String key){
        List<User>list=new ArrayList<>();
        list=mUserManager.nameQueryMsgList(key);
        if(list.size()==0){
            list=mUserManager.timeQueryMsgList(key);
        }
        return list;
    }
    /**  删除所有的人脸考勤数据**/
    public void deleteAll(){
        mUserManager.deleteAll();
    }

}

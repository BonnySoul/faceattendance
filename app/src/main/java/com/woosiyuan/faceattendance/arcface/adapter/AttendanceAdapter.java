package com.woosiyuan.faceattendance.arcface.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.ViewGroup;
import com.woosiyuan.faceattendance.BR;
import com.woosiyuan.faceattendance.MyApplication;
import com.woosiyuan.faceattendance.R;
import com.woosiyuan.faceattendance.arcface.main.modelview.RegisterVM;
import com.woosiyuan.faceattendance.basis.BaseAdapter;
import com.woosiyuan.faceattendance.basis.BaseViewHolder;
import com.woosiyuan.faceattendance.basis.entity.User;
import com.woosiyuan.faceattendance.arcface.main.modelview.AttendanceVM;
import com.woosiyuan.faceattendance.basis.util.Constants;
import com.woosiyuan.faceattendance.basis.util.TimeUtil;
import com.woosiyuan.mylibrary.util.SharedPreferencesUtil;
import java.util.ArrayList;
import java.util.List;

/**
 * 说明：
 *
 * @version V1.0
 * @FileName: com.woosiyuan.faceattendance.arcface.adapter.AttendanceAdapter.java
 * @author: so
 * @date: 2018-01-04 14:51
 */

public class AttendanceAdapter extends BaseAdapter <User,BaseViewHolder>{

    private BaseViewHolder  mBaseViewHolder;
    private ViewDataBinding dataBinding;
    private List<User>      mList;
    private Context         mContext;
    private AttendanceVM    mAttendanceVM;
    private RegisterVM      mRegisterVM;
    private SharedPreferencesUtil mPreferencesUtil;

    public AttendanceAdapter(Context context) {
        super(context);
        mContext = context;
        mList = new ArrayList<>();
        mAttendanceVM=new AttendanceVM(context);
        mRegisterVM=new RegisterVM(context);
        mList=mAttendanceVM.getAllAttendance();
        mPreferencesUtil= MyApplication.getINSTANCE().getSharedPreferences();
    }

    public void searchData(String key){
        if(key==null||"".equals(key)){
            mList=mAttendanceVM.getAllAttendance();
        }else{
            mList=mAttendanceVM.getKeyAttendance(key);
        }
        notifyDataSetChanged();
    }


    @Override public BaseViewHolder onCreateVH(ViewGroup parent, int viewType) {
        ViewDataBinding dataBinding = DataBindingUtil.inflate(inflater, R.layout.item_attendance, parent,
                false);
        return new BaseViewHolder(dataBinding);
    }

    @Override public void onBindVH(BaseViewHolder baseViewHolder, int position) {
        mBaseViewHolder=baseViewHolder;
        dataBinding = mBaseViewHolder.getBinding();
        dataBinding.setVariable(BR.user, mList.get(position));
        dataBinding.setVariable(BR.tags,mRegisterVM.nameQueryTags(mList.get(position).getName()));
        dataBinding.setVariable(BR.position,position);
        dataBinding.setVariable(BR.adapter,this);
        dataBinding.executePendingBindings(); //防止闪烁
    }


    @Override public int getItemCount() {
        return mList.size();
    }


    /**判断打卡时间是否为正常上班时间**/
    public boolean normalWorkTime(String time){
        String goTime=mPreferencesUtil.getStringValue(Constants.KEY_GO_WORK,"12:00");
        String outTime=mPreferencesUtil.getStringValue(Constants.KEY_OUT_WORK,"12:59");
        int go=strFormatNum(goTime);
        int out=strFormatNum(outTime);
        int ti=strAttFormatNum(time);
        if(go<ti&&ti<out){
            return false;
        }else {
            return true;
        }
    }

    /**上下班时间转换为数**/
    public int strFormatNum(String string){
        String str=string.replace(":","");
        int i= Integer.parseInt(str);
        return i;
    }

    /**考勤时间转换为数**/
    public int strAttFormatNum(String string){
        String str1=string.substring(string.length()-8,string.length()-3);
        String str=str1.replace(":","");
        int i= Integer.parseInt(str);
        return i;
    }

}



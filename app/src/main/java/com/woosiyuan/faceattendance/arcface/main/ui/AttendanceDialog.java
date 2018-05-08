package com.woosiyuan.faceattendance.arcface.main.ui;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import com.woosiyuan.faceattendance.BR;
import com.woosiyuan.faceattendance.R;
import com.woosiyuan.faceattendance.basis.db.manager.UserManager;
import com.woosiyuan.faceattendance.basis.entity.User;
import com.woosiyuan.faceattendance.basis.util.Constants;
import com.woosiyuan.faceattendance.basis.util.SpeakUtil;
import com.woosiyuan.faceattendance.databinding.DialogAttendanceBinding;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;

/**
 * 说明：
 *
 * @version V1.0
 * @FileName: com.woosiyuan.faceattendance.arcface.main.ui.AttendanceDialog.java
 * @author: so
 * @date: 2018-01-04 10:09
 *  考勤时弹出的dialog界面
 */

public class AttendanceDialog extends DialogFragment{

    ViewDataBinding dataBinding;
    private User mUser;private Bitmap mBitmap;
    private static final String ATTENDANCE_MSG="ATTENDANCE_MSG";
    private static final String PICTURE_CODE="PICTURE_CODE";
    private Disposable  mDisposable;
    private UserManager mUserManager;
    DialogAttendanceBinding mBinding;
    private SpeakUtil mSpeakUtil;


    @Nullable @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dataBinding =DataBindingUtil.inflate(inflater, R.layout
                .dialog_attendance, container, false);
        mBinding=DataBindingUtil.getBinding(dataBinding.getRoot());
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        initData();
        return dataBinding.getRoot();
    }

    public static AttendanceDialog newInstance(User user, Bitmap bitmap) {
        AttendanceDialog fragment = new AttendanceDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ATTENDANCE_MSG,user);
        bundle.putParcelable(PICTURE_CODE,bitmap);
        fragment.setArguments(bundle);
        return fragment;
    }

    private void initData() {
        mSpeakUtil=SpeakUtil.getInstance(getContext());
        mUserManager= UserManager.getInstance(getContext());
        Bundle bundle = getArguments();
        mUser=bundle.getParcelable(ATTENDANCE_MSG);
        mBitmap=bundle.getParcelable(PICTURE_CODE);
        mBinding.bmp.setImageBitmap(mBitmap);
        mBinding.bmp.setRotation(270);
        mSpeakUtil.speakText(mUser.getName()+ Constants.VOICE_ONE);
        dataBinding.setVariable(BR.tags,mUserManager.getFaceTags(mUser.getName()));
        dataBinding.setVariable(BR.user,mUser);
        mUserManager.insertUser(mUser);
        if(mDisposable==null){
            mDisposable=Observable.timer(3,TimeUnit.SECONDS).subscribe(new Consumer<Long>() {
                @Override public void accept(Long aLong) throws Exception {
                    mDisposable.dispose();
                    mUser=null;
                    mBitmap=null;
                    dismiss();
                }
            });
        }
    }

}

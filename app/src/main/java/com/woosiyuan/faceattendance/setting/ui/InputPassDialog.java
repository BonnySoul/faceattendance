package com.woosiyuan.faceattendance.setting.ui;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;
import com.woosiyuan.faceattendance.MyApplication;
import com.woosiyuan.faceattendance.R;
import com.woosiyuan.faceattendance.arcface.main.ui.ManageActivity;
import com.woosiyuan.faceattendance.basis.util.Constants;
import com.woosiyuan.faceattendance.databinding.DialogInputBinding;
import com.woosiyuan.mylibrary.util.SharedPreferencesUtil;

/**
 * 说明：
 *
 * @version V1.0
 * @FileName: com.woosiyuan.faceattendance.setting.ui.InputPassDialog.java
 * @author: so
 * @date: 2018-01-18 13:38
 */

public class InputPassDialog extends DialogFragment implements View.OnClickListener {

    ViewDataBinding dataBinding;
    DialogInputBinding mDialogInputBinding;
    SharedPreferencesUtil mPreferencesUtil;
    String password;

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        dataBinding =DataBindingUtil.inflate(inflater, R.layout
                .dialog_input, container, false);
        mDialogInputBinding=DataBindingUtil.findBinding(dataBinding.getRoot());
        initData();
        return dataBinding.getRoot();
    }

    void initData(){
        mPreferencesUtil= MyApplication.getINSTANCE().getSharedPreferences();
        password=mPreferencesUtil.getStringValue(Constants.KEY_PASSWORD,"");
        mDialogInputBinding.tvOk.setOnClickListener(this);
        mDialogInputBinding.tvCancle.setOnClickListener(this);
    }

    @Override public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_ok:
                String a=mDialogInputBinding.etPass.getText().toString();
                if(TextUtils.isEmpty(a)){
                    Toast.makeText(getContext(),"请输入密码",Toast.LENGTH_SHORT).show();
                    break;
                }
                if(password.equals(a)){
                    dismiss();
                    startActivity(new Intent(getActivity(), ManageActivity.class));
                }
                break;

            case R.id.tv_cancle:
                dismiss();
                break;
        }
    }
}

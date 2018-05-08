package com.woosiyuan.faceattendance.setting.ui;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.woosiyuan.faceattendance.MyApplication;
import com.woosiyuan.faceattendance.R;
import com.woosiyuan.faceattendance.basis.BaseActivity;
import com.woosiyuan.faceattendance.basis.util.Constants;
import com.woosiyuan.faceattendance.databinding.ActivityPassWordBinding;
import com.woosiyuan.faceattendance.setting.callback.PassWordViewImp;
import com.woosiyuan.faceattendance.setting.callback.PassWorkVmImp;
import com.woosiyuan.faceattendance.setting.modeview.PassWorkVM;
import com.woosiyuan.mylibrary.util.SharedPreferencesUtil;

public class PassWordActivity extends BaseActivity implements PassWorkVmImp ,PassWordViewImp,
        View.OnClickListener {

    ActivityPassWordBinding mBinding;
    PassWorkVM mPassWorkVM;
    Context mContext;
    SharedPreferencesUtil mSharedPreferencesUtil;
    String password;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding= DataBindingUtil.setContentView(this,getLayoutResId());
        initData();
    }

    @Override public void initData() {
        mSharedPreferencesUtil=((MyApplication) getApplication()).getSharedPreferences();
        password=mSharedPreferencesUtil.getStringValue(Constants.KEY_PASSWORD,"");
        mBinding.titleBar.save.setVisibility(View.VISIBLE);
        mBinding.titleBar.title.setText("密码设置");
        mBinding.titleBar.search.setVisibility(View.GONE);
        mBinding.titleBar.back.setOnClickListener(this);
        mBinding.titleBar.save.setOnClickListener(this);
        mPassWorkVM=new PassWorkVM(this);
        mContext=this;
        if(TextUtils.isEmpty(password)){
            mBinding.linear1.setVisibility(View.GONE);
        }
    }

    @Override public int getLayoutResId() {
        return R.layout.activity_pass_word;
    }


    @Override public void settingSuccess() {
        Toast.makeText(this,"重置密码成功",Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override public void settingFailed(String error) {
        Toast.makeText(this,error,Toast.LENGTH_SHORT).show();
    }

    @Override public String getOldPass() {
        return mBinding.etOldPass.getText().toString();
    }

    @Override public String getNewPass() {
        return mBinding.etNewPass.getText().toString();
    }

    @Override public String getTwoPass() {
        return mBinding.etTwoPass.getText().toString();
    }


    @Override public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.save:
                if(!TextUtils.isEmpty(password)&&TextUtils.isEmpty(getOldPass())){
                    Toast.makeText(mContext,"请输入原始密码",Toast.LENGTH_SHORT).show();
                    return ;
                }
                if(TextUtils.isEmpty(getNewPass())){
                    Toast.makeText(mContext,"请输入新密码",Toast.LENGTH_SHORT).show();
                    return ;
                }
                if(TextUtils.isEmpty(getTwoPass())){
                    Toast.makeText(mContext,"请再次输入新密码",Toast.LENGTH_SHORT).show();
                    return ;
                }
                mPassWorkVM.settingPassWord(getOldPass(),getNewPass(),getTwoPass());
                break;
        }
    }
}

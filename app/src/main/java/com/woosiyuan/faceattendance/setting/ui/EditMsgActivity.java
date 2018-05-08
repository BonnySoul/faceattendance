package com.woosiyuan.faceattendance.setting.ui;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.woosiyuan.faceattendance.MyApplication;
import com.woosiyuan.faceattendance.R;
import com.woosiyuan.faceattendance.basis.BaseActivity;
import com.woosiyuan.faceattendance.basis.util.Constants;
import com.woosiyuan.faceattendance.databinding.ActivityEditMsgBinding;
import com.woosiyuan.mylibrary.util.SharedPreferencesUtil;

public class EditMsgActivity extends BaseActivity implements View.OnClickListener {

    ActivityEditMsgBinding mBinding;
    SharedPreferencesUtil mPreferencesUtil;
    int kindCode;
    String toastMsg,key_code;
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding= DataBindingUtil.setContentView(this,getLayoutResId());
        initData();
    }

    @Override public void initData() {
        super.initData();
        kindCode=getIntent().getIntExtra(Constants.KEY_EDIT_CODE,0);
        mBinding.appbar.search.setVisibility(View.GONE);
        mBinding.appbar.save.setVisibility(View.VISIBLE);
        mBinding.appbar.save.setOnClickListener(this);
        mBinding.appbar.back.setOnClickListener(this);
        mPreferencesUtil= MyApplication.getINSTANCE().getSharedPreferences();
        if(kindCode==0){
            mBinding.etName.setHint(R.string.user_name_hint);
            mBinding.appbar.title.setText("修改名称");
            toastMsg="请输入名称";
            key_code=Constants.KEY_USER_NAME;
        }else{
            mBinding.etName.setHint(R.string.user_addr_hint);
            toastMsg="请输入地址";
            mBinding.appbar.title.setText("修改地址");
            key_code=Constants.KEY_USER_ADDR;
        }

    }

    @Override public int getLayoutResId() {
        return R.layout.activity_edit_msg;
    }

    public String getName(){
        return mBinding.etName.getText().toString();
    }

    @Override public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.save:
                if(TextUtils.isEmpty(getName())){
                    Toast.makeText(this,toastMsg,Toast.LENGTH_SHORT).show();
                    return;
                }
                mPreferencesUtil.putStringValue(key_code,getName());
                Toast.makeText(this,"设置成功",Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }





}

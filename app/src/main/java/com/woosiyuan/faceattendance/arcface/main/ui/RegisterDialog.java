package com.woosiyuan.faceattendance.arcface.main.ui;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.arcsoft.facerecognition.AFR_FSDKFace;
import com.guo.android_extend.widget.ExtImageView;
import com.woosiyuan.faceattendance.R;
import com.woosiyuan.faceattendance.arcface.callback.RegsiterView;
import com.woosiyuan.faceattendance.arcface.main.modelview.RegisterVM;
import com.woosiyuan.faceattendance.basis.util.Constants;
import com.woosiyuan.faceattendance.basis.util.SpeakUtil;
import com.woosiyuan.mylibrary.util.StringUtil;

/**
 * 说明：
 *
 * @version V1.0
 * @FileName: com.woosiyuan.faceattendance.arcface.register.ui.RegisterDialog.java
 * @author: so
 * @date: 2018-01-02 11:43
 * 注册的弹框
 *
 */

public class RegisterDialog extends DialogFragment implements View.OnClickListener ,RegsiterView {

    private Bitmap mBitmap;
    public static final String PERSON_BITMAP_CODE="PERSON_BITMAP_CODE";
    private ExtImageView extimageview;
    private TextView tv_cancle,tv_ok;
    private RegisterVM   mRegisterMV;
    private AFR_FSDKFace mAFR_FSDKFace;
    private EditText     name,tags;
    private ViewDataBinding mViewDataBinding;
    private SpeakUtil mSpeakUtil;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mViewDataBinding=DataBindingUtil.inflate(inflater,R.layout.dialog_register,container,
                false);
        initView(mViewDataBinding.getRoot());
        return mViewDataBinding.getRoot();
    }

    private void initView(View view) {
        mSpeakUtil=SpeakUtil.getInstance(getContext());
        mRegisterMV=new RegisterVM(this.getContext());
        name=view.findViewById(R.id.name);
        tags=view.findViewById(R.id.tags);
        extimageview=view.findViewById(R.id.extimageview);
        tv_cancle=view.findViewById(R.id.tv_cancle);
        tv_ok=view.findViewById(R.id.tv_ok);
        tv_cancle.setOnClickListener(this);
        tv_ok.setOnClickListener(this);
        Bundle bundle = getArguments();
        mBitmap=bundle.getParcelable(PERSON_BITMAP_CODE);
        extimageview.setImageBitmap(mBitmap);
        if(RegisterActivity.mAFR_FSDKFace!=null){
            mAFR_FSDKFace=RegisterActivity.mAFR_FSDKFace;
        }
    }

    public static RegisterDialog newInstance(Bitmap bitmap) {
        RegisterDialog fragment = new RegisterDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelable(PERSON_BITMAP_CODE,bitmap);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_cancle:
                dismiss();
                break;
            case R.id.tv_ok:
                if(StringUtil.isEmpty(getName())){
                    Toast.makeText(this.getContext(),"请输入姓名",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(StringUtil.isEmpty(getTags())){
                    Toast.makeText(this.getContext(),"请输入编号",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(mAFR_FSDKFace==null){
                    Toast.makeText(this.getContext(),"人脸信息为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(mRegisterMV.isContain(getName())){
                    Toast.makeText(this.getContext(),"该姓名已被注册",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(mRegisterMV.isContain(getTags())){
                    Toast.makeText(this.getContext(),"该工号已被注册",Toast.LENGTH_SHORT).show();
                    return;
                }
                mSpeakUtil.speakText(Constants.VOICE_TWO);
                mRegisterMV.registFace(getName(),getTags(),mAFR_FSDKFace);
                Toast.makeText(this.getContext(),"人脸信息注册成功",Toast.LENGTH_SHORT).show();
                dismiss();
                getActivity().finish();

        }
    }




    @Override public String getName() {
        return name.getText().toString();
    }

    @Override public String getTags() {
        return tags.getText().toString();
    }
}

package com.woosiyuan.faceattendance.setting.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.woosiyuan.faceattendance.MyApplication;
import com.woosiyuan.faceattendance.R;
import com.woosiyuan.faceattendance.basis.BaseActivity;
import com.woosiyuan.faceattendance.basis.util.Constants;
import com.woosiyuan.faceattendance.basis.util.TimeUtil;
import com.woosiyuan.faceattendance.databinding.ActivityTimeSettingBinding;
import com.woosiyuan.faceattendance.setting.modeview.UserSetHelper;
import com.woosiyuan.mylibrary.util.SharedPreferencesUtil;
import com.woosiyuan.mylibrary.widget.CustomDatePicker;

public class TimeSettingActivity extends BaseActivity implements View.OnClickListener {

    private CustomDatePicker customDatePicker1, customDatePicker2;
    private ActivityTimeSettingBinding mBinding;
    private UserSetHelper              mUserSetHelper;
    private SharedPreferencesUtil mPreferencesUtil;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding=DataBindingUtil.setContentView(this,getLayoutResId());
        mBinding.titleBar.search.setVisibility(View.GONE);
        mBinding.titleBar.save.setVisibility(View.VISIBLE);
        mBinding.selectTime.setOnClickListener(this);
        mBinding.selectDate.setOnClickListener(this);
        mBinding.titleBar.save.setOnClickListener(this);
        mBinding.titleBar.back.setOnClickListener(this);
        mBinding.titleBar.title.setText("上下班时间设置");
        mUserSetHelper=new UserSetHelper(this);
        mPreferencesUtil= MyApplication.getINSTANCE().getSharedPreferences();
        initDatePicker();
    }

    @Override public int getLayoutResId() {
        return R.layout.activity_time_setting;
    }

    private void initDatePicker() {
        String go=mPreferencesUtil.getStringValue(Constants.KEY_GO_WORK,"00:00");
        String out=mPreferencesUtil.getStringValue(Constants.KEY_OUT_WORK,"00:00");
        mBinding.currentDate.setText(go);
        mBinding.currentTime.setText(out);
        customDatePicker1 = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                mBinding.currentDate.setText(TimeUtil.TimeFormat(time));
            }
        }, "2010-01-01 00:00", "2110-01-01 "+go); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker1.showSpecificTime(true); // 显示时和分
        customDatePicker1.showLittleTime();
        customDatePicker1.setIsLoop(true); // 允许循环滚动

        customDatePicker2 = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                mBinding.currentTime.setText(TimeUtil.TimeFormat(time));
            }
        }, "2010-01-01 00:00", "2110-01-01 "+out); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker2.showSpecificTime(true); // 显示时和分
        customDatePicker2.showLittleTime();
        customDatePicker2.setIsLoop(true); // 允许循环滚动
    }

    @Override public void onClick(View v) {
        switch (v.getId()) {
            case R.id.selectDate:
                // 日期格式为yyyy-MM-dd HH:mm
                customDatePicker1.show(TimeUtil.TimeFormat2()+mBinding.currentDate.getText()
                                                                                  .toString());
                break;

            case R.id.selectTime:
                // 日期格式为yyyy-MM-dd HH:mm
                customDatePicker2.show(TimeUtil.TimeFormat2()+mBinding.currentTime.getText()
                                                                                  .toString());
                break;
            case R.id.save:
                mUserSetHelper.saveWorkTime(mBinding.currentDate.getText().toString(),mBinding
                        .currentTime.getText().toString());
                Toast.makeText(this,"设置成功",Toast.LENGTH_SHORT).show();
                finish();
                break;

            case R.id.back:
                finish();
        }
    }


}

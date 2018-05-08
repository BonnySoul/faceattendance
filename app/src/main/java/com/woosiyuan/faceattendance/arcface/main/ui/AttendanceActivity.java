package com.woosiyuan.faceattendance.arcface.main.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.woosiyuan.faceattendance.R;
import com.woosiyuan.faceattendance.basis.BaseActivity;
import com.woosiyuan.faceattendance.basis.util.CustomDecoration;
import com.woosiyuan.faceattendance.databinding.ActivityAttendanceBinding;
import com.woosiyuan.faceattendance.arcface.adapter.AttendanceAdapter;
import com.woosiyuan.mylibrary.util.DpUtil;

/**
 * 说明：
 *
 * @version V1.0
 * @FileName: com.woosiyuan.faceattendance.arcface.main.ui.AttendanceActivity.java
 * @author: so
 *
 *  人脸考勤记录显示类
 * @date: 2018-01-04 14:41
 */

public class AttendanceActivity extends BaseActivity implements View.OnClickListener,
        XRecyclerView.LoadingListener {

    ActivityAttendanceBinding mActivityAttendanceBinding;
    AttendanceAdapter         mAddendanceAdapter;
    TextView title;
    XRecyclerView             attendance_recycler;



    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override public void initData() {
        super.initData();
        mActivityAttendanceBinding=DataBindingUtil.setContentView(this,getLayoutResId());
        attendance_recycler=findViewById(R.id.attendance_recycler);
        mActivityAttendanceBinding.titleBar.back.setOnClickListener(this);
        mActivityAttendanceBinding.titleBar.search.setOnClickListener(this);
        mAddendanceAdapter=new AttendanceAdapter(this);
        mActivityAttendanceBinding.attendanceRecycler.setLayoutManager(new LinearLayoutManager
                (this));
        mActivityAttendanceBinding.attendanceRecycler.addItemDecoration(new CustomDecoration(this, CustomDecoration.VERTICAL_LIST, R.drawable.divider_love, DpUtil
                .dip2px(this, 2)));
        mActivityAttendanceBinding.attendanceRecycler.setAdapter(mAddendanceAdapter);
        mActivityAttendanceBinding.attendanceRecycler.setLoadingListener(this);
        mActivityAttendanceBinding.titleBar.title.setText(getString(R.string.attendance_result));
    }

    @Override public int getLayoutResId() {
        return R.layout.activity_attendance;
    }


    @Override public void onClick(View v) {
        switch (v.getId()){
            case R.id.search:
        startActivity(new Intent(this,
            SearchAttendanceActivity.class));
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    @Override public void onRefresh() {
        attendance_recycler.refreshComplete();
    }

    @Override public void onLoadMore() {
        attendance_recycler.loadMoreComplete();
    }


    /*  void onback(){
        finish();
    }*/

}

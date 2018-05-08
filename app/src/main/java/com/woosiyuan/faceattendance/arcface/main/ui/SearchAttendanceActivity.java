package com.woosiyuan.faceattendance.arcface.main.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.woosiyuan.faceattendance.R;
import com.woosiyuan.faceattendance.basis.BaseActivity;
import com.woosiyuan.faceattendance.basis.entity.DateTellEvent;
import com.woosiyuan.faceattendance.basis.ui.SimpleCalendarDialogFragment;
import com.woosiyuan.faceattendance.basis.util.RxBus;
import com.woosiyuan.faceattendance.databinding.ActivitySearchAttendanceBinding;
import com.woosiyuan.faceattendance.arcface.adapter.AttendanceAdapter;
import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.feezu.liuli.timeselector.TimeSelector;

/**
 * 说明：
 *
 * @version V1.0
 * @FileName: com.woosiyuan.faceattendance.arcface.main.ui.AttendanceActivity.java
 * @author: so
 *
 *  根据关键字来搜索人脸考勤记录
 * @date: 2018-01-04 14:41
 */


public class SearchAttendanceActivity extends BaseActivity implements View.OnClickListener,
        TextWatcher {

    ActivitySearchAttendanceBinding mBinding;
    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();
    private static String       data;
    private        RxBus        mRxBus;
    private AttendanceAdapter mAttendanceAdapter;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding= DataBindingUtil.setContentView(this,getLayoutResId());
        mBinding.titleBar.etSearch.addTextChangedListener(this);
        mBinding.titleBar.commonSet.setOnClickListener(this);
        mBinding.titleBar.commonBack.setOnClickListener(this);
        mAttendanceAdapter=new AttendanceAdapter(this);
        mBinding.attendanceRecycler.setLayoutManager(new LinearLayoutManager
                (this));
        mBinding.attendanceRecycler.setAdapter(mAttendanceAdapter);

        getBus();
    }

    private void getBus() {
        Flowable<Object> f = RxBus.getInstance().register(Object.class);
        f.subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object integer) throws Exception {
                if(integer instanceof DateTellEvent){
                    mBinding.titleBar.etSearch.setText(((DateTellEvent) integer).getDate());
                }
            }
        });
    }

    @Override public int getLayoutResId() {
        return R.layout.activity_search_attendance;
    }

    @Override public void onClick(View v) {
        switch (v.getId()){
            case R.id.common_back:
                finish();
                break;
            case R.id.common_set:
                new SimpleCalendarDialogFragment().show(getSupportFragmentManager(), "test-simple-calendar");
                break;
        }
    }


    @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override public void afterTextChanged(Editable s) {
        mAttendanceAdapter.searchData( mBinding.titleBar.etSearch.getText().toString());
    }
}

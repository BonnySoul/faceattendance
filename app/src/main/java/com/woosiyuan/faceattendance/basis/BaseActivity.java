package com.woosiyuan.faceattendance.basis;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.woosiyuan.faceattendance.R;
import com.woosiyuan.faceattendance.basis.callback.IActivity;

/**
 * 说明：
 *
 * @version V1.0
 * @FileName: com.woosiyuan.faceattendance.basis.BaseActivity.java
 * @author: so
 * @date: 2017-12-27 14:09
 */

public class BaseActivity extends AppCompatActivity implements IActivity {

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override public int getLayoutResId() {
        return 0;
    }

    @Override public Object setEntity() {
        return null;
    }

    @Override public void onFirst() {

    }

    @Override public void initPre() {

    }

    @Override public void initData() {

    }

    @Override public void initView(Bundle var1) {

    }

    @Override public void viewClick(View var1) {

    }

    @Override public void showProgress() {

    }

    @Override public void hideProgress() {

    }

    @Override public void skipActivity(@NonNull Activity var1, @NonNull Class<?> var2) {

    }

    @Override public void skipActivity(@NonNull Activity var1, @NonNull Intent var2) {

    }

    @Override
    public void skipActivity(@NonNull Activity var1, @NonNull Class<?> var2, @NonNull Bundle var3) {

    }

    @Override public void startActivity(@NonNull Activity var1, @NonNull Class<?> var2) {

    }

    @Override public void startActivity(@NonNull Activity var1, @NonNull Intent var2) {

    }

    @Override
    public void startActivity(
            @NonNull Activity var1, @NonNull Class<?> var2, @NonNull Bundle var3) {
    }
}

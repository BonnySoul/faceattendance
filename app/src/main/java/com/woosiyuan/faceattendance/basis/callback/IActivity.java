package com.woosiyuan.faceattendance.basis.callback;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * 说明：
 *
 * @version V1.0
 * @FileName: com.woosiyuan.faceattendance.basis.callback.IActivity.java
 * @author: so
 * @date: 2017-12-28 09:37
 */

public interface IActivity {

        int getLayoutResId();

        Object setEntity();

        void onFirst();

        void initPre();

        void initData();

        void initView(Bundle var1);

        void viewClick(View var1);

        void showProgress();

        void hideProgress();

        void skipActivity(@NonNull Activity var1, @NonNull Class<?> var2);

        void skipActivity(@NonNull Activity var1, @NonNull Intent var2);

        void skipActivity(@NonNull Activity var1, @NonNull Class<?> var2, @NonNull Bundle var3);

        void startActivity(@NonNull Activity var1, @NonNull Class<?> var2);

        void startActivity(@NonNull Activity var1, @NonNull Intent var2);

        void startActivity(@NonNull Activity var1, @NonNull Class<?> var2, @NonNull Bundle var3);
}

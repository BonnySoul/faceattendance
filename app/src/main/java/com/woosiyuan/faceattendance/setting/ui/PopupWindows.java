package com.woosiyuan.faceattendance.setting.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import com.woosiyuan.faceattendance.R;
import com.woosiyuan.faceattendance.setting.callback.ChooseCallBack;

/**
 * 说明：
 *
 * @version V1.0
 * @FileName: com.woosiyuan.faceattendance.setting.ui.PopupWindows.java
 * @author: so
 * @date: 2018-01-24 15:48
 */

public class PopupWindows extends PopupWindow {

    private ChooseCallBack mChooseCallBack;

    public PopupWindows(Context mContext, View parent, final ChooseCallBack mChooseCallBack) {
        this.mChooseCallBack=mChooseCallBack;
        View view = View.inflate(mContext, R.layout.item_popupwindows, null);
        view.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_ins));
        LinearLayout ll_popup =  view.findViewById(R.id.ll_popup);
        setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        setHeight(ViewGroup.LayoutParams.FILL_PARENT);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(view);
        showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        update();
        Button bt1 = view.findViewById(R.id.item_popupwindows_camera);
        Button bt2 = view.findViewById(R.id.item_popupwindows_Photo);
        Button bt3 =  view.findViewById(R.id.item_popupwindows_cancel);
        bt1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mChooseCallBack.photo();
                dismiss();
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mChooseCallBack.album();
                dismiss();
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               dismiss();
            }
        });
    }
}

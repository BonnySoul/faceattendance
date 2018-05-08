package com.woosiyuan.mylibrary.util;

import android.content.Context;

/**
 * 说明：
 *
 * @version V1.0
 * @FileName: com.woosiyuan.mylibrary.util.DpUtil.java
 * @author: so
 * @date: 2018-01-10 10:58
 */

public class DpUtil {

    public static int dip2px(Context context, float dipValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dipValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxValue / scale + 0.5f);
    }
}

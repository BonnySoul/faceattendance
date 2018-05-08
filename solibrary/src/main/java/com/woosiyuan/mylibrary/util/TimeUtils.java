package com.woosiyuan.mylibrary.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 说明：
 *
 * @version V1.0
 * @FileName: com.woosiyuan.mylibrary.util.TimeUtils.java
 * @author: so
 * @date: 2018-01-04 10:57
 */

public class TimeUtils {

    public static String getCurrentTime(SimpleDateFormat simpleDateFormat){
        Date date=new Date();
        return  simpleDateFormat.format(date);
    }
}

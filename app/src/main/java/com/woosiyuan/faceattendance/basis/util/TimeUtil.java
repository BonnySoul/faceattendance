package com.woosiyuan.faceattendance.basis.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 说明：
 *
 * @version V1.0
 * @FileName: com.woosiyuan.faceattendance.basis.util.TimeUtil.java
 * @author: so
 * @date: 2018-01-25 10:10
 */

public class TimeUtil {

    public static String TimeFormat(String time){
        String time2="";
        if(time.length()>5){
            time2=time.substring(time.length()-5,time.length());
        }
        return time2;
    }

    public static String TimeFormat2(){
        Date date=new Date();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd ");
        return simpleDateFormat.format(date);
    }

}

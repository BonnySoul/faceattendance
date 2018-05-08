package com.woosiyuan.faceattendance.basis.util;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.support.annotation.RequiresApi;
import android.text.format.Formatter;
import com.woosiyuan.mylibrary.util.SDCardUtil;
import java.io.File;

/**
 * 说明：
 *
 * @version V1.0
 * @FileName: com.woosiyuan.faceattendance.basis.util.DirectoryManager.java
 * @author: so
 * @date: 2018-01-22 10:12
 */

public class DirectoryManager {


    public static void init(){
        SDCardUtil.setRootDirName(Constants.ROOT_DIR_NAME);
        SDCardUtil.initDir();
    }
    /**
     * 获取数据库文件的目录
     */
    public static String getDbDirPath() {
        return SDCardUtil.getDbDirPath();
    }
    /**
     * 获取缓存文件的目录
     */
    public static String getCacheDirPath() {
        return SDCardUtil.getCacheDirPath();
    }

    private static String checkDir(String path) {
        File file = new File(path);
        if (!file.exists()) {
            //noinspection ResultOfMethodCallIgnored
            file.mkdirs();
        }
        return file.getPath();
    }

    /**
     * 获取sdcard剩余容量
     * @param context s
     * @return s
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static String getSDAvailableSize(Context context){
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long availableBlocks = stat.getAvailableBlocksLong();
        return Formatter.formatFileSize(context, blockSize * availableBlocks);
    }


}

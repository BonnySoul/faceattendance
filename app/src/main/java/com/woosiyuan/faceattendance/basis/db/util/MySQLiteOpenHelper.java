package com.woosiyuan.faceattendance.basis.db.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.woosiyuan.faceattendance.basis.db.gen.DaoMaster;
import com.woosiyuan.faceattendance.basis.db.gen.FaceMessageDao;
import com.woosiyuan.faceattendance.basis.db.gen.UserDao;

/**
 * 说明：
 *
 * @version V1.0
 * @FileName: com.woosiyuan.faceattendance.basis.db.util.MySQLiteOpenHelper.java
 * @author: so
 * @date: 2018-01-22 09:22
 */

public class MySQLiteOpenHelper extends DaoMaster.OpenHelper {
    public MySQLiteOpenHelper(Context context, String name) {
        super(context, name);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //数据迁移模块
        MigrationHelper.migrate(db,
                UserDao.class,
                FaceMessageDao.class
              );
    }
}

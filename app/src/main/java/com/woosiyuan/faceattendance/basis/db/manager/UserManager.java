package com.woosiyuan.faceattendance.basis.db.manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.arcsoft.facerecognition.AFR_FSDKFace;
import com.woosiyuan.faceattendance.arcface.util.FaceRegist;
import com.woosiyuan.faceattendance.basis.callback.IFace;
import com.woosiyuan.faceattendance.basis.db.gen.DaoMaster;
import com.woosiyuan.faceattendance.basis.db.gen.DaoSession;
import com.woosiyuan.faceattendance.basis.db.gen.FaceMessageDao;
import com.woosiyuan.faceattendance.basis.db.gen.UserDao;
import com.woosiyuan.faceattendance.basis.db.util.GreenDaoContext;
import com.woosiyuan.faceattendance.basis.db.util.MySQLiteOpenHelper;
import com.woosiyuan.faceattendance.basis.entity.FaceMessage;
import com.woosiyuan.faceattendance.basis.entity.User;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.greenrobot.greendao.query.QueryBuilder;

/**
 * 说明：
 *
 * @version V1.0
 * @FileName: com.woosiyuan.faceattendance.basis.db.manager.UserManager.java
 * @author: so
 * @date: 2018-01-04 13:49
 */

public class UserManager implements IFace{

    private static final String TAG = UserManager.class.getSimpleName();
    private static final String DB_NAME = "attendance_user";
    private String dbName="attendance_user";
    private static Context context;

    //多线程中要被共享的使用volatile关键字修饰
    private volatile static UserManager manager = new UserManager();
    private static DaoMaster               sDaoMaster;
    private static DaoMaster.DevOpenHelper sHelper;
    private static DaoSession              sDaoSession;


    /**
     * 单例模式获得操作数据库对象
     * @return
     */
    public static UserManager getInstance(Context mContext){
        init(mContext);
        return manager;
    }

    public static void init(Context mContext){
        context = mContext;
    }

    /**
     * 判断是否有存在数据库，如果没有则创建
     * @return
     */
    public DaoMaster getDaoMaster(){
        if(sDaoMaster == null) {
            MySQLiteOpenHelper helper = new MySQLiteOpenHelper(new GreenDaoContext(), DB_NAME);
            sDaoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return sDaoMaster;
    }

    /**
     * 完成对数据库的添加、删除、修改、查询操作，仅仅是一个接口
     * @return
     */
    public DaoSession getDaoSession(){
        if(sDaoSession == null){
            if(sDaoMaster == null){
                sDaoMaster = getDaoMaster();
            }
            sDaoSession = sDaoMaster.newSession();
        }
        return sDaoSession;
    }

    /**
     * 打开输出日志，默认关闭
     */
    public void setDebug(){
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
    }

    /**
     * 关闭所有的操作，数据库开启后，使用完毕要关闭
     */
    public void closeConnection(){
        closeHelper();
        closeDaoSession();
    }

    public void closeHelper(){
        if(sHelper != null){
            sHelper.close();
            sHelper = null;
        }
    }

    public void closeDaoSession(){
        if(sDaoSession != null){
            sDaoSession.clear();
            sDaoSession = null;
        }
    }

    /**
     * 获取可读数据库
     */
    private SQLiteDatabase getReadableDatabase() {
        if (sHelper == null) {
            sHelper = new DaoMaster.DevOpenHelper(new GreenDaoContext(), dbName, null);
        }
        SQLiteDatabase db = sHelper.getReadableDatabase();
        return db;
    }

    /**
     * 获取可写数据库
     */
    private SQLiteDatabase getWritableDatabase() {
        if (sHelper == null) {
            sHelper = new DaoMaster.DevOpenHelper(new GreenDaoContext(), dbName, null);
        }
        SQLiteDatabase db = sHelper.getWritableDatabase();
        return db;
    }



    /**
     * 插入一条记录
     *
     * @param
     */
    public void insertUser(User bean) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserDao userDao = daoSession.getUserDao();
        userDao.insert(bean);
    }





    /**
     * 插入用户集合
     *
     * @param users
     */
    public void insertUserList(List<User> users) {
        if (users == null || users.isEmpty()) {
            return;
        }
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserDao userDao = daoSession.getUserDao();
        userDao.insertInTx(users);
    }


    /**
     * 删除一条记录
     *
     * @param user
     */
    public void deleteUser(User user) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserDao userDao = daoSession.getUserDao();
        userDao.delete(user);
    }

    /**
     * 删除该名称考勤信息
     *
     * @param
     */
    public void deleteUserAttend(String name) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserDao dao = daoSession.getUserDao();
        QueryBuilder<User> qb = dao.queryBuilder().where(UserDao.Properties.Name
                .eq(name));
        List<User> list = qb.list();
        dao.deleteInTx(list);
    }

    /**
     *
     *
     * @param
     */
    public void deleteAll() {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserDao userDao = daoSession.getUserDao();
        QueryBuilder<User> qb=userDao.queryBuilder();
        List<User> list = qb.list();
        userDao.deleteInTx(list);
    }

    /**
     * name查询用户列表
     */
    public List<User> nameQueryMsgList(String name) {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserDao userDao = daoSession.getUserDao();
        QueryBuilder<User> qb = userDao.queryBuilder().where(UserDao.Properties.Name
                .eq(name));
        List<User> list = qb.list();
        return list;
    }



    /**
     * name查询用户列表
     */
    public List<User> timeQueryMsgList(String time) {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserDao userDao = daoSession.getUserDao();
        QueryBuilder<User> qb = userDao.queryBuilder();
        List<User> list = qb.list();
        List<User> reList = new ArrayList<>();
        for(User u:list){
            String dayTime=getDayTime(u.getClockTime());
            if(time.equals(dayTime)){
                reList.add(u);
            }
        }
        return reList;
    }

    /**
     * name查询用户列表
     */
    public List<User> tagsQueryMsgList(String tags) {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserDao userDao = daoSession.getUserDao();
        QueryBuilder<User> qb = userDao.queryBuilder().where(UserDao.Properties.Tags
                .eq(tags));
        List<User> list = qb.list();
        return list;
    }

    /**
     * 查询用户记录列表
     */
    public List<User> queryAllMsmList() {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserDao userDao = daoSession.getUserDao();
        QueryBuilder<User> qb = userDao.queryBuilder();
        List<User> list = qb.list();
        return list;
    }

    public String getDayTime(String time){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        SimpleDateFormat simpleDateFormat2=new SimpleDateFormat("yyyy年M月d日");
        try {
            Date date=simpleDateFormat.parse(time);
            return simpleDateFormat2.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override public void deleteFace(String name) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        FaceMessageDao msgDao = daoSession.getFaceMessageDao();
        QueryBuilder<FaceMessage> qb = msgDao.queryBuilder().where(FaceMessageDao.Properties.MName
                .eq(name));
        List<FaceMessage> list = qb.list();
        for(FaceMessage u:list){
            msgDao.delete(u);
        }
    }

    @Override public void addFace(String name, String tags, List<AFR_FSDKFace> list) {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        FaceMessageDao dao = daoSession.getFaceMessageDao();
        dao.insert(new FaceMessage(name,tags,list.get(0).getFeatureData()));
    }




    @Override public List<FaceRegist> queryAllFace() {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        FaceMessageDao dao = daoSession.getFaceMessageDao();
        QueryBuilder<FaceMessage> qb = dao.queryBuilder();
        List<FaceMessage>list=qb.list();
        List<FaceRegist>mList=new ArrayList<>();
        for(FaceMessage f:list){
            List<AFR_FSDKFace>sList=new ArrayList<>();
            sList.add(new AFR_FSDKFace(f.getMFeatureData()));
            mList.add(new FaceRegist(f.getMName(),sList));
        }
        return  mList;
    }

    @Override public List<FaceMessage> queryAllFaceMessage() {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        FaceMessageDao dao = daoSession.getFaceMessageDao();
        QueryBuilder<FaceMessage> qb = dao.queryBuilder();
        List<FaceMessage>list=qb.list();
        return list;
    }

    @Override public String getFaceTags(String name) {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        FaceMessageDao msgDao = daoSession.getFaceMessageDao();
        QueryBuilder<FaceMessage> qb = msgDao.queryBuilder().where(FaceMessageDao.Properties.MName
                .eq(name));
        List<FaceMessage> list = qb.list();
        return  list.size()==0?"":list.get(0).getTags();
    }

    public boolean isContain(String key){
        boolean flag=false;
        List<FaceMessage> list=queryAllFaceMessage();
        for(FaceMessage f:list){
            if(f.getMName().equals(key)||f.getTags().equals(key)){
                flag=true;
            }
        }
        return flag;
    }

}

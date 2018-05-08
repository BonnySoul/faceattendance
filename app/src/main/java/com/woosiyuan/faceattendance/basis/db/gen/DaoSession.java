package com.woosiyuan.faceattendance.basis.db.gen;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.woosiyuan.faceattendance.basis.entity.FaceMessage;
import com.woosiyuan.faceattendance.basis.entity.User;

import com.woosiyuan.faceattendance.basis.db.gen.FaceMessageDao;
import com.woosiyuan.faceattendance.basis.db.gen.UserDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig faceMessageDaoConfig;
    private final DaoConfig userDaoConfig;

    private final FaceMessageDao faceMessageDao;
    private final UserDao userDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        faceMessageDaoConfig = daoConfigMap.get(FaceMessageDao.class).clone();
        faceMessageDaoConfig.initIdentityScope(type);

        userDaoConfig = daoConfigMap.get(UserDao.class).clone();
        userDaoConfig.initIdentityScope(type);

        faceMessageDao = new FaceMessageDao(faceMessageDaoConfig, this);
        userDao = new UserDao(userDaoConfig, this);

        registerDao(FaceMessage.class, faceMessageDao);
        registerDao(User.class, userDao);
    }
    
    public void clear() {
        faceMessageDaoConfig.clearIdentityScope();
        userDaoConfig.clearIdentityScope();
    }

    public FaceMessageDao getFaceMessageDao() {
        return faceMessageDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

}
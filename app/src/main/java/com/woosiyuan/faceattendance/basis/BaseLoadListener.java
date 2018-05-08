package com.woosiyuan.faceattendance.basis;

import java.util.List;

/**
 * 博客：http://www.jianshu.com/u/56db5d78044d
 */

public interface BaseLoadListener<T> {
    /**
     * 加载数据成功
     *
     * @param list
     */
    void loadSuccess(List<T> list);

    /**
     * 加载失败
     *
     * @param message
     */
    void loadFailure(String message);

    /**
     * 开始加载
     */
    void loadStart();

    /**
     * 加载结束
     */
    void loadComplete();
}

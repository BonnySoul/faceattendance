package com.woosiyuan.mylibrary.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

/**
 * 说明：
 *
 * @version V1.0
 * @FileName: com.woosiyuan.mylibrary.util.SharedPreferencesUtil.java
 * @author: so
 * @date: 2017-12-28 13:24
 */

public class SharedPreferencesUtil {
    private final SharedPreferences        mSharedPreferences;
    private final SharedPreferences.Editor mEditor;

    @SuppressLint({"CommitPrefEdits"})
    public SharedPreferencesUtil(@NonNull Context context, @NonNull String name) {
        this.mSharedPreferences = context.getSharedPreferences(name, 0);
        this.mEditor = this.mSharedPreferences.edit();
    }

    public SharedPreferences getSP() {
        return this.mSharedPreferences;
    }

    public SharedPreferences.Editor getEditor() {
        return this.mEditor;
    }

    public void putLongValue(@NonNull String key, long value) {
        this.mEditor.putLong(key, value).commit();
    }

    public void putIntValue(@NonNull String key, int value) {
        this.mEditor.putInt(key, value).commit();
    }

    public void putStringValue(@NonNull String key, @NonNull String value) {
        this.mEditor.putString(key, value).commit();
    }

    public void putBooleanValue(@NonNull String key, boolean value) {
        this.mEditor.putBoolean(key, value).commit();
    }

    public long getLongValue(@NonNull String key, long defValue) {
        return this.mSharedPreferences.getLong(key, defValue);
    }

    public int getIntValue(@NonNull String key, int defValue) {
        return this.mSharedPreferences.getInt(key, defValue);
    }

    public boolean getBooleanValue(@NonNull String key, boolean defValue) {
        return this.mSharedPreferences.getBoolean(key, defValue);
    }

    public String getStringValue(@NonNull String key, @NonNull String defValue) {
        return this.mSharedPreferences.getString(key, defValue);
    }

    public void clear() {
        this.mEditor.clear().commit();
    }

    public void remove(String key) {
        this.mEditor.remove(key).commit();
    }

}

package com.woosiyuan.faceattendance.basis.entity;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import com.woosiyuan.faceattendance.BR;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 说明：
 *
 * @version V1.0
 * @FileName: com.woosiyuan.faceattendance.basis.entity.FaceMessage.java
 * @author: so
 * @date: 2018-01-17 13:30
 */

@Entity
public class FaceMessage extends BaseObservable {

    @Id(autoincrement = true)
    private Long id;
    @Property(nameInDb = "FACE_NAME")
    public String             mName;
    @Property(nameInDb = "FACE_TAGS")
    String tags;

    @Property(nameInDb = "FACE_MSG")
    byte[] mFeatureData;

    @Generated(hash = 2013638982)
    public FaceMessage(Long id, String mName, String tags, byte[] mFeatureData) {
        this.id = id;
        this.mName = mName;
        this.tags = tags;
        this.mFeatureData = mFeatureData;
    }

    public FaceMessage(String name, String tags, byte[] featureData) {
        mName = name;
        this.tags = tags;
        mFeatureData = featureData;
    }

    @Generated(hash = 2087326725)
    public FaceMessage() {
    }

    @Bindable
    public String getMName() {
        return this.mName;
    }

    public void setMName(String mName) {
        this.mName = mName;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getTags() {
        return this.tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
        notifyPropertyChanged(BR.tags);
    }

    public byte[] getMFeatureData() {
        return this.mFeatureData;
    }

    public void setMFeatureData(byte[] mFeatureData) {
        this.mFeatureData = mFeatureData;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}


package com.woosiyuan.faceattendance.basis.entity;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;
import com.woosiyuan.faceattendance.BR;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 说明：
 *
 * @version V1.0
 * @FileName: com.woosiyuan.faceattendance.model.entity.User.java
 * @author: so
 * @date: 2017-12-28 08:57
 *
 * /**
 * Created by wangjitao on 2017/2/13 0013.
 * E-Mail：543441727@qq.com
 *
 * Bean 对象注释的解释
 *
  @Entity：告诉GreenDao该对象为实体，只有被@Entity注释的Bean类才能被dao类操作
 @Id：对象的Id，使用Long类型作为EntityId，否则会报错。(autoincrement = true)表示主键会自增，如果false就会使用旧值
 @Property：可以自定义字段名，注意外键不能使用该属性
 @NotNull：属性不能为空
 @Transient：使用该注释的属性不会被存入数据库的字段中
 @Unique：该属性值必须在数据库中是唯一值
 @Generated：编译后自动生成的构造函数、方法等的注释，提示构造函数、方法等不能被修改
 */


@Entity
public class User extends BaseObservable implements Parcelable{

    @Property(nameInDb = "USER_NAME")
    String name;
    String imgUrl;
    String passWork;
    String addr;

    @Bindable
    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
        notifyPropertyChanged(BR.addr);
    }

    @Property(nameInDb = "USER_TAGS")
    String tags;

    @Id(autoincrement = true)
    private Long id;

    public User(String name, String clockTime) {
        this.name = name;
        this.clockTime = clockTime;
    }

    @Property(nameInDb = "CLOCK_TIME")
    String clockTime;

    protected User(Parcel in) {
        name = in.readString();
        imgUrl = in.readString();
        passWork = in.readString();
        tags = in.readString();
        clockTime = in.readString();
        introduce = in.readString();
        tel = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Bindable
    public String getClockTime() {
        return clockTime;
    }

    public void setClockTime(String clockTime) {
        this.clockTime = clockTime;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public User(String name) {
        this.name = name;
    }
    public User() {
    }

    @Generated(hash = 1983837230)
    public User(String name, String imgUrl, String passWork, String addr,
            String tags, Long id, String clockTime, String introduce, String tel) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.passWork = passWork;
        this.addr = addr;
        this.tags = tags;
        this.id = id;
        this.clockTime = clockTime;
        this.introduce = introduce;
        this.tel = tel;
    }

    @Bindable
    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
        notifyPropertyChanged(BR.tags);
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Bindable
    public String getPassWork() {
        return passWork;
    }

    public void setPassWork(String passWork) {
        this.passWork = passWork;
    }

    @Bindable
    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    @Bindable
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
        notifyPropertyChanged(BR.tel);
    }

    String introduce;
    String tel;

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(imgUrl);
        dest.writeString(passWork);
        dest.writeString(tags);
        dest.writeString(clockTime);
        dest.writeString(introduce);
        dest.writeString(tel);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User(Builder builder) {
        this.name=builder.name;
        this.tags=builder.tags;
        this.clockTime=builder.clockTime;
    }

    public static class Builder{
        private String name;
        private  String tags;
        private String clockTime;

        public Builder name(String name){
            this.name=name;
            return this;
        }
        public Builder tags(String tags){
            this.tags=tags;
            return this;
        }
        public Builder clockTime(String clockTime){
            this.clockTime=clockTime;
            return this;
        }

        public User build(){
            return new User();
        }


    }


}

package com.woosiyuan.faceattendance.setting.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.woosiyuan.faceattendance.MyApplication;
import com.woosiyuan.faceattendance.R;
import com.woosiyuan.faceattendance.arcface.main.ui.RegisterActivity;
import com.woosiyuan.faceattendance.basis.BaseActivity;
import com.woosiyuan.faceattendance.basis.entity.User;
import com.woosiyuan.faceattendance.basis.util.Bimp;
import com.woosiyuan.faceattendance.basis.util.BitmapUtil;
import com.woosiyuan.faceattendance.basis.util.Constants;
import com.woosiyuan.faceattendance.basis.util.DirectoryManager;
import com.woosiyuan.faceattendance.basis.util.GlideCircleTransform;
import com.woosiyuan.faceattendance.basis.util.ImageTools;
import com.woosiyuan.faceattendance.basis.util.PictureUtil;
import com.woosiyuan.faceattendance.basis.util.RoundBitmap;
import com.woosiyuan.faceattendance.databinding.UserActivityBinding;
import com.woosiyuan.faceattendance.setting.callback.ChooseCallBack;
import com.woosiyuan.faceattendance.setting.modeview.UserSetHelper;
import com.woosiyuan.mylibrary.util.SharedPreferencesUtil;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;

/**
 * 说明：
 *
 * @version V1.0
 * @FileName: com.woosiyuan.faceattendance.view.setting.UserSettingActivity.java
 * @author: so
 * @date: 2017-12-27 14:08
 */

public class UserSettingActivity extends BaseActivity implements View.OnClickListener {

    private User                mUser;
    private UserSetHelper       mUserSetHelper;
    private UserActivityBinding binding;
    RequestOptions options;
    SharedPreferencesUtil mPreferencesUtil;
    private final String TAG = this.getClass().toString();
    private static final int RESULT_LOAD_IMAGE = 11;
    private Bitmap resultBitmap;
    private static final int SCALE = 5;//照片缩小比例
    private File file;
    private static final int    TAKE_PICTURE   = 0;
    private static final int    CHOOSE_PICTURE = 1;
    private static final int    CROP           = 2;
    private static final int    CROP_PICTURE   = 3;
    int REQUEST_CODE;
    private              String fileName       = null;
    private UserSettingActivity mActivity;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,getLayoutResId());
        initData();
    }

    @Override public void initData() {
        setOnclick();
        mActivity=this;
        binding.appbar.search.setVisibility(View.GONE);
        binding.appbar.title.setText("个人信息");
        mPreferencesUtil=MyApplication.getINSTANCE().getSharedPreferences();
        binding.setUser((User) setEntity());
        mUserSetHelper=new UserSetHelper(this.getBaseContext());
        mUser.setName(mPreferencesUtil.getStringValue(Constants.KEY_USER_NAME,getString(R.string
                .user_name)));
        mUser.setTel(mPreferencesUtil.getStringValue(Constants.KEY_USER_ADDR,getString(R.string
                .user_addr)));
        options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.wsy_logo)
                .error(R.mipmap.ic_launcher)
                .priority(Priority.HIGH)
                .transform(new GlideCircleTransform());
        if(Constants.user_picture==null){
            setCriclePicture(R.mipmap.wsy_logo);
        }else{
            binding.picture.setImageBitmap(Constants.user_picture);
        }

    }

    public void setOnclick(){
        binding.appbar.back.setOnClickListener(this);
        binding.userName.setOnClickListener(this);
        binding.userTel.setOnClickListener(this);
        binding.picture.setOnClickListener(this);
    }

    @Override public int getLayoutResId() {
        return R.layout.user_activity;
    }

    @Override public Object setEntity() {
        return mUser=new User();
    }

    @Override public void onClick(View v) {
        Intent intent=new Intent(this,EditMsgActivity.class);
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.user_name:
                intent.putExtra(Constants.KEY_EDIT_CODE,0);
                startActivity(intent);
                break;
            case R.id.user_tel:
                intent.putExtra(Constants.KEY_EDIT_CODE,1);
                startActivity(intent);
                break;
            case R.id.picture:
                new PopupWindows(this,binding.picture, new ChooseCallBack() {
                    @Override public void album() {
                        Intent intent;
                            REQUEST_CODE = CHOOSE_PICTURE;
                        if (Build.VERSION.SDK_INT < 19) {
                            intent = new Intent(Intent.ACTION_GET_CONTENT);
                            intent.setType("image/*");
                        } else {
                            intent = new Intent(
                                    Intent.ACTION_PICK,
                                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        }
                        startActivityForResult(intent, REQUEST_CODE);
                    }
                    @Override public void photo() {
                      file=PictureUtil.getINSTANCE().toPhoto(mActivity);
                    }
                    @Override public void cancle() {
                    }
                });
                break;
        }
    }

    public void setCriclePicture(Object object){
        Glide.with(this).load(object).apply(options).into(binding.picture);
    }

    @Override protected void onResume() {
        super.onResume();
        binding.userName.setText(mPreferencesUtil.getStringValue(Constants.KEY_USER_NAME,getString(R
                .string
                .user_name)));
        mUser.setTel(mPreferencesUtil.getStringValue(Constants.KEY_USER_ADDR,getString(R.string
                .user_addr)));
    }



    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case TAKE_PICTURE:
                    //将保存在本地的图片取出并缩小后显示在界面上
                    String path = file.getAbsolutePath();
                    Bitmap bitmap = BitmapFactory.decodeFile(path);
                    Bitmap newBitmap = ImageTools.zoomBitmap(bitmap, bitmap.getWidth() / SCALE,
                            bitmap.getHeight() / SCALE);
                    //由于Bitmap内存占用较大，这里需要回收内存，否则会报out of memory异常
                    //将处理过的图片显示在界面上，并保存到本地
                    resultBitmap = RoundBitmap.toRoundBitmap(newBitmap);
                    Constants.user_picture=resultBitmap;
                    binding.picture.setImageBitmap(resultBitmap);
                    bitmap.recycle();
                    break;

                case CHOOSE_PICTURE:
                    ContentResolver resolver = this.getContentResolver();
                    //照片的原始资源地址
                    Uri originalUri = data.getData();
                    try {
                        String[] proj = { MediaStore.Images.Media.DATA };
                        //使用ContentProvider通过URI获取原始图片
                        Bitmap photo = MediaStore.Images.Media.getBitmap(resolver, originalUri);
                        if (photo != null) {
                            //为防止原始图片过大导致内存溢出，这里先缩小原图显示，然后释放原始Bitmap占用的内存
                            Bitmap smallBitmap = ImageTools.zoomBitmap(photo,
                                    photo.getWidth() / SCALE, photo.getHeight() / SCALE);
                            //释放原始图片占用的内存，防止out of memory异常发生
                            photo.recycle();
                            resultBitmap = RoundBitmap.toRoundBitmap(smallBitmap);
                            Constants.user_picture=resultBitmap;
                            binding.picture.setImageBitmap(resultBitmap);
                        }

                        Cursor cursor = resolver.query(originalUri, proj, null, null, null);
                        // 按我个人理解 这个是获得用户选择的图片的索引值
                        int column_index = cursor.getColumnIndexOrThrow(
                                MediaStore.Images.Media.DATA);
                        // 将光标移至开头 ，这个很重要，不小心很容易引起越界
                        cursor.moveToFirst();
                        // 最后根据索引值获取图片路径
                        String imgPath = cursor.getString(column_index);
                        fileName = imgPath.substring(imgPath.lastIndexOf("/") + 1,
                                imgPath.length());
                        file = new File(imgPath);
                        cursor.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                case CROP:
                    Uri uri = null;
                    if (data != null) {
                        uri = data.getData();
                        System.out.println("Data");
                    } else {
                        System.out.println("File");
                        String fileName = this.getSharedPreferences("temp",
                                Context.MODE_WORLD_WRITEABLE).getString("tempName", "");
                        uri = Uri.fromFile(
                                new File(Environment.getExternalStorageDirectory(), fileName));
                    }
                    PictureUtil.getINSTANCE().cropImage(this,uri, 500, 500, CROP_PICTURE);
                    break;

                case CROP_PICTURE:
                    Bitmap photo = null;
                    Uri photoUri = data.getData();
                    if (photoUri != null) {
                        photo = BitmapFactory.decodeFile(photoUri.getPath());
                    }
                    //if (photo == null) {
                    Bundle extra = data.getExtras();
                    if (extra != null) {
                        photo = (Bitmap) extra.get("data");
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        //}
                    }
                    resultBitmap = RoundBitmap.toRoundBitmap(photo);
                    Constants.user_picture=resultBitmap;
                    binding.picture.setImageBitmap(resultBitmap);
                    break;
                default:
                    break;
            }
        }
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        //resultBitmap.recycle();
    }
}

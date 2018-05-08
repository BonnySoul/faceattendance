package com.woosiyuan.faceattendance.arcface.main.ui;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.woosiyuan.faceattendance.MyApplication;
import com.woosiyuan.faceattendance.R;
import com.woosiyuan.faceattendance.arcface.adapter.PeopleAdapter;
import com.woosiyuan.faceattendance.basis.util.BitmapUtil;
import com.woosiyuan.faceattendance.basis.util.PictureUtil;
import com.woosiyuan.faceattendance.basis.BaseActivity;
import com.woosiyuan.faceattendance.basis.util.CustomDecoration;
import com.woosiyuan.faceattendance.setting.callback.ChooseCallBack;
import com.woosiyuan.faceattendance.setting.ui.PopupWindows;
import com.woosiyuan.mylibrary.util.DpUtil;

/**
 * 说明：
 *
 * @version V1.0
 * @FileName: com.woosiyuan.faceattendance.arcface.main.ui.AttendanceActivity.java
 * @author: so
 *
 *  人脸库数据管理类
 * @date: 2018-01-04 14:41
 */

public class ManageActivity extends BaseActivity implements View.OnClickListener,
        XRecyclerView.LoadingListener {

    TextView back,search;
    ImageView add_face;
    TextView title;
    private static final int REQUEST_CODE_IMAGE_CAMERA = 1;
    private static final int REQUEST_CODE_IMAGE_OP = 2;
    private static final int REQUEST_CODE_OP = 3;
    private final String TAG = this.getClass().toString();
    private XRecyclerView people_recycleview;
    private PeopleAdapter mPeopleAdapter;



    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);
        add_face=findViewById(R.id.add_face);
        title=findViewById(R.id.title);
        title.setText(R.string.manager_face);
        back=findViewById(R.id.back);
        search=findViewById(R.id.search);
        search.setVisibility(View.GONE);
        people_recycleview=findViewById(R.id.people_recycleview);
        add_face.setOnClickListener(this);
        back.setOnClickListener(this);
        mPeopleAdapter = new PeopleAdapter(this);
        people_recycleview.setLayoutManager(new LinearLayoutManager(this));
        people_recycleview.addItemDecoration(new CustomDecoration(this, CustomDecoration.VERTICAL_LIST, R.drawable.divider_love, DpUtil
                .dip2px(this, 2)));
        people_recycleview.setAdapter(mPeopleAdapter);
        people_recycleview.setLoadingListener(this);
    }


    @Override public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.add_face:
                new PopupWindows(this, add_face, new ChooseCallBack() {
                    @Override public void album() {
                        Intent getImageByalbum = new Intent(Intent.ACTION_GET_CONTENT);
                        getImageByalbum.addCategory(Intent.CATEGORY_OPENABLE);
                        getImageByalbum.setType("image/jpeg");
                        startActivityForResult(getImageByalbum, REQUEST_CODE_IMAGE_OP);
                    }
                    @Override public void photo() {
                        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                        ContentValues values = new ContentValues(1);
                        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                        Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                        MyApplication.getINSTANCE().setCaptureImage(uri);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        startActivityForResult(intent, REQUEST_CODE_IMAGE_CAMERA);
                    }
                    @Override public void cancle() {
                    }
                });
                break;
            case R.id.search:
                mPeopleAdapter.setShowDelete(true);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_IMAGE_OP && resultCode == RESULT_OK) {
            Uri mPath = data.getData();
            String file = PictureUtil.getINSTANCE().getPath(this,mPath);
            Bitmap bmp = BitmapUtil.decodeImage(file);
            if (bmp == null || bmp.getWidth() <= 0 || bmp.getHeight() <= 0 ) {
                Log.e(TAG, "error");
            } else {
                Log.i(TAG, "bmp [" + bmp.getWidth() + "," + bmp.getHeight());
            }
            startRegister(bmp, file);
        } else if (requestCode == REQUEST_CODE_OP) {
            Log.i(TAG, "RESULT =" + resultCode);
            if (data == null) {
                return;
            }
            Bundle bundle = data.getExtras();
            String path = bundle.getString("imagePath");
            Log.i(TAG, "path="+path);
        } else if (requestCode == REQUEST_CODE_IMAGE_CAMERA && resultCode == RESULT_OK) {
            Uri mPath = MyApplication.getINSTANCE().getCaptureImage();
            if(mPath==null){
                Toast.makeText(this,"路径获取异常",Toast.LENGTH_SHORT).show();
                return;
            }
            String file = PictureUtil.getINSTANCE().getPath(this,mPath);
            Bitmap bmp = BitmapUtil.decodeImage(file);
            startRegister(bmp, file);
        }
    }

    /**
     * @param mBitmap
     */
    private void startRegister(Bitmap mBitmap, String file) {
        Intent it = new Intent(this, RegisterActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("imagePath", file);
        it.putExtras(bundle);
        startActivityForResult(it, REQUEST_CODE_OP);
        finish();
    }

   @Override public void onRefresh() {
       people_recycleview.refreshComplete();
      }

    @Override public void onLoadMore() {
        people_recycleview.loadMoreComplete();
    }
}

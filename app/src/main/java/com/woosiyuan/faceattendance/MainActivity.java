package com.woosiyuan.faceattendance;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import com.arcsoft.ageestimation.ASAE_FSDKAge;
import com.arcsoft.ageestimation.ASAE_FSDKEngine;
import com.arcsoft.ageestimation.ASAE_FSDKError;
import com.arcsoft.ageestimation.ASAE_FSDKFace;
import com.arcsoft.ageestimation.ASAE_FSDKVersion;
import com.arcsoft.facerecognition.AFR_FSDKEngine;
import com.arcsoft.facerecognition.AFR_FSDKError;
import com.arcsoft.facerecognition.AFR_FSDKFace;
import com.arcsoft.facerecognition.AFR_FSDKMatching;
import com.arcsoft.facerecognition.AFR_FSDKVersion;
import com.arcsoft.facetracking.AFT_FSDKEngine;
import com.arcsoft.facetracking.AFT_FSDKError;
import com.arcsoft.facetracking.AFT_FSDKFace;
import com.arcsoft.facetracking.AFT_FSDKVersion;
import com.arcsoft.genderestimation.ASGE_FSDKEngine;
import com.arcsoft.genderestimation.ASGE_FSDKError;
import com.arcsoft.genderestimation.ASGE_FSDKFace;
import com.arcsoft.genderestimation.ASGE_FSDKGender;
import com.arcsoft.genderestimation.ASGE_FSDKVersion;
import com.guo.android_extend.java.AbsLoop;
import com.guo.android_extend.java.ExtByteArrayOutputStream;
import com.guo.android_extend.widget.CameraFrameData;
import com.guo.android_extend.widget.CameraSurfaceView;
import com.woosiyuan.faceattendance.arcface.callback.IImageNV21;
import com.woosiyuan.faceattendance.arcface.main.helper.CameraHelper;
import com.woosiyuan.faceattendance.arcface.main.helper.DetectionHelper;
import com.woosiyuan.faceattendance.arcface.main.modelview.RegisterVM;
import com.woosiyuan.faceattendance.arcface.main.ui.ManageActivity;
import com.woosiyuan.faceattendance.arcface.util.FaceDB;
import com.woosiyuan.faceattendance.arcface.util.FaceRegist;
import com.woosiyuan.faceattendance.basis.entity.User;
import com.woosiyuan.faceattendance.arcface.main.ui.AttendanceDialog;
import com.woosiyuan.faceattendance.basis.util.Constants;
import com.woosiyuan.faceattendance.basis.util.SpeakUtil;
import com.woosiyuan.faceattendance.databinding.ActivityMainBinding;
import com.woosiyuan.faceattendance.arcface.main.ui.AttendanceActivity;
import com.woosiyuan.faceattendance.setting.ui.InputPassDialog;
import com.woosiyuan.faceattendance.setting.ui.PassWordActivity;
import com.woosiyuan.faceattendance.setting.ui.TimeSettingActivity;
import com.woosiyuan.faceattendance.setting.ui.UserSettingActivity;
import com.woosiyuan.mylibrary.util.SharedPreferencesUtil;
import com.woosiyuan.mylibrary.util.TimeUtils;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        CameraSurfaceView.OnCameraListener, View.OnTouchListener, IImageNV21, View.OnClickListener {

    private ActivityMainBinding mBinding;
    private CameraHelper mCameraHelper;
    private int mWidth, mHeight, mFormat;
    private final String TAG = this.getClass().getSimpleName();

    AFT_FSDKVersion       version        = new AFT_FSDKVersion();
    AFT_FSDKEngine        engine         = new AFT_FSDKEngine();
    ASAE_FSDKVersion      mAgeVersion    = new ASAE_FSDKVersion();
    ASAE_FSDKEngine       mAgeEngine     = new ASAE_FSDKEngine();
    ASGE_FSDKVersion      mGenderVersion = new ASGE_FSDKVersion();
    ASGE_FSDKEngine       mGenderEngine  = new ASGE_FSDKEngine();
    List<AFT_FSDKFace>    result         = new ArrayList<>();
    List<ASAE_FSDKAge>    ages           = new ArrayList<>();
    List<ASGE_FSDKGender> genders        = new ArrayList<>();

    byte[] mImageNV21 = null;
    AFT_FSDKFace mAFT_FSDKFace = null;
    FRAbsLoop mFRAbsLoop = null;
    private AttendanceDialog mAttendanceDialog;
    private InputPassDialog mInputPassDialog;
    private Camera mCamera;
    private String lastName="";
    private long lastTime;
    private RegisterVM mRegisterVM;
    private SharedPreferencesUtil mPreferencesUtil;
    private String password;
    private ImageView headImg;
    private TextView user_name,user_address;
    private  NavigationView navigationView;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ininData();
    }

    private void ininData() {
        mPreferencesUtil=MyApplication.getINSTANCE().getSharedPreferences();
        View headerView = navigationView.getHeaderView(0);
        headImg=headerView.findViewById(R.id.imageView);
        if(Constants.user_picture!=null){
            headImg.setImageBitmap(Constants.user_picture);
        }
        user_name=headerView.findViewById(R.id.user_name);
        user_address=headerView.findViewById(R.id.user_address);
        headImg.setOnClickListener(this);
        user_name.setText(mPreferencesUtil.getStringValue(Constants.KEY_USER_NAME,getString(R.string
                .user_name)));
        user_address.setText(mPreferencesUtil.getStringValue(Constants.KEY_USER_ADDR,getString(R.string
                .user_addr)));
        password=mPreferencesUtil.getStringValue(Constants.KEY_PASSWORD,"");
        mInputPassDialog=new InputPassDialog();
        mRegisterVM=new RegisterVM(this);
        mWidth = 1280;
        mHeight = 960;
        mBinding.surfaceView.setOnCameraListener(this);
        mBinding.glsurfaceView.setOnTouchListener(this);
        mBinding.surfaceView.setupGLSurafceView(mBinding.glsurfaceView, true, false, 270);
        mBinding.surfaceView.debug_print_fps(true, false);
        mCameraHelper=new CameraHelper();

        AFT_FSDKError err = engine.AFT_FSDK_InitialFaceEngine(FaceDB.appid, FaceDB.ft_key, AFT_FSDKEngine.AFT_OPF_0_HIGHER_EXT, 16, 5);
        Log.d(TAG, "AFT_FSDK_InitialFaceEngine =" + err.getCode());
        err = engine.AFT_FSDK_GetVersion(version);
        Log.d(TAG, "AFT_FSDK_GetVersion:" + version.toString() + "," + err.getCode());

        ASAE_FSDKError error = mAgeEngine.ASAE_FSDK_InitAgeEngine(FaceDB.appid, FaceDB.age_key);
        Log.d(TAG, "ASAE_FSDK_InitAgeEngine =" + error.getCode());
        error = mAgeEngine.ASAE_FSDK_GetVersion(mAgeVersion);
        Log.d(TAG, "ASAE_FSDK_GetVersion:" + mAgeVersion.toString() + "," + error.getCode());

        ASGE_FSDKError error1 = mGenderEngine.ASGE_FSDK_InitgGenderEngine(FaceDB.appid, FaceDB.gender_key);
        Log.d(TAG, "ASGE_FSDK_InitgGenderEngine =" + error1.getCode());
        error1 = mGenderEngine.ASGE_FSDK_GetVersion(mGenderVersion);
        Log.d(TAG, "ASGE_FSDK_GetVersion:" + mGenderVersion.toString() + "," + error1.getCode());

        mFRAbsLoop = new FRAbsLoop();
        mFRAbsLoop.start();

    }

    @Override public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            super.onBackPressed();
        }
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            if(null!=mAttendanceDialog&&mAttendanceDialog.isVisible()){
                mAttendanceDialog.dismiss();
            }
            if(TextUtils.isEmpty(password)){
                   Intent intent=new Intent(this,ManageActivity.class);
                    startActivity(intent);
            }else{
                showInputDiolog();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody") @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_camera) {
            if(null!=mAttendanceDialog&&mAttendanceDialog.isVisible()){
                mAttendanceDialog.dismiss();
            }
            startActivity(new Intent(this,AttendanceActivity.class));
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            startActivity(new Intent(this,PassWordActivity.class));
        } else if (id == R.id.nav_slideshow) {
            startActivity(new Intent(this,TimeSettingActivity.class));
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override public void onClick(View v) {
        startActivity(new Intent(this, UserSettingActivity.class));
    }



    @Override protected void onPause() {
        super.onPause();
        Log.d("onPause","onPause");
    }

    @Override public Camera setupCamera() {
        mCamera=mCameraHelper.setupCamera(mWidth,mHeight,1, ImageFormat.NV21);
        return  mCamera;
    }

    @Override public void setupChanged(int format, int width, int height) {

    }

    @Override public boolean startPreviewLater() {
        return false;
    }

    @Override
    public Object onPreview(byte[] data, int width, int height, int format, long timestamp) {
        AFT_FSDKError err = engine.AFT_FSDK_FaceFeatureDetect(data, width, height, AFT_FSDKEngine.CP_PAF_NV21, result);
        Log.d(TAG, "AFT_FSDK_FaceFeatureDetect =" + err.getCode());
        Log.d(TAG, "Face=" + result.size());
        for (AFT_FSDKFace face : result) {
            Log.d(TAG, "Face:" + face.toString());
        }
        if (mImageNV21 == null) {
            if (!result.isEmpty()) {
                mAFT_FSDKFace = result.get(0).clone();
                mImageNV21 = data.clone();
            } else {
            }
        }
        Rect[] rects = new Rect[result.size()];
        for (int i = 0; i < result.size(); i++) {
            rects[i] = new Rect(result.get(i).getRect());
        }
        result.clear();
        return rects;
    }

    @Override public void onBeforeRender(CameraFrameData data) {

    }

    @Override public void onAfterRender(CameraFrameData data) {
        mBinding.glsurfaceView.getGLES2Render().draw_rect((Rect[])data.getParams(), Color.GREEN, 2);
    }

    @Override public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    @Override public byte[] getImageNV21() {
        return new byte[0];
    }

    class FRAbsLoop extends AbsLoop {
        AFR_FSDKVersion     version  = new AFR_FSDKVersion();
        AFR_FSDKEngine      engine   = new AFR_FSDKEngine();
        AFR_FSDKFace        result   = new AFR_FSDKFace();
        List<ASAE_FSDKFace> face1    = new ArrayList<>();
        List<ASGE_FSDKFace> face2    = new ArrayList<>();

        @Override
        public void setup() {
            AFR_FSDKError error = engine.AFR_FSDK_InitialEngine(FaceDB.appid, FaceDB.fr_key);
            Log.d(TAG, "AFR_FSDK_InitialEngine = " + error.getCode());
            error = engine.AFR_FSDK_GetVersion(version);
            Log.d(TAG, "FR=" + version.toString() + "," + error.getCode()); //(210, 178 - 478, 446), degree = 1　780, 2208 - 1942, 3370
        }

        @Override
        public void loop() {
            if (mImageNV21 != null) {
                List<FaceRegist>    mResgist =mRegisterVM.getAllFace();
                long time = System.currentTimeMillis();
                AFR_FSDKError error = engine.AFR_FSDK_ExtractFRFeature(mImageNV21, mWidth, mHeight, AFR_FSDKEngine.CP_PAF_NV21, mAFT_FSDKFace.getRect(), mAFT_FSDKFace.getDegree(), result);
                Log.d(TAG, "AFR_FSDK_ExtractFRFeature cost :" + (System.currentTimeMillis() - time) + "ms");
                Log.d(TAG, "Face=" + result.getFeatureData()[0] + "," + result.getFeatureData()[1] + "," + result.getFeatureData()[2] + "," + error.getCode());
                AFR_FSDKMatching score = new AFR_FSDKMatching();
                float max = 0.0f;
                String name = null;
                for (FaceRegist fr : mResgist) {
                    for (AFR_FSDKFace face : fr.mFaceList) {
                        error = engine.AFR_FSDK_FacePairMatching(result, face, score);
                        Log.d(TAG,  "Score:" + score.getScore() + ", AFR_FSDK_FacePairMatching=" + error.getCode());
                        if (max < score.getScore()) {
                            max = score.getScore();
                            name = fr.mName;
                        }
                    }
                }
                //age & gender
                face1.clear();
                face2.clear();
                face1.add(new ASAE_FSDKFace(mAFT_FSDKFace.getRect(), mAFT_FSDKFace.getDegree()));
                face2.add(new ASGE_FSDKFace(mAFT_FSDKFace.getRect(), mAFT_FSDKFace.getDegree()));
                ASAE_FSDKError error1 = mAgeEngine.ASAE_FSDK_AgeEstimation_Image(mImageNV21, mWidth, mHeight, AFT_FSDKEngine.CP_PAF_NV21, face1, ages);
                ASGE_FSDKError error2 = mGenderEngine.ASGE_FSDK_GenderEstimation_Image(mImageNV21, mWidth, mHeight, AFT_FSDKEngine.CP_PAF_NV21, face2, genders);
                Log.d(TAG, "ASAE_FSDK_AgeEstimation_Image:" + error1.getCode() + ",ASGE_FSDK_GenderEstimation_Image:" + error2.getCode());
                Log.d(TAG, "age:" + ages.get(0).getAge() + ",gender:" + genders.get(0).getGender());
                final String age = ages.get(0).getAge() == 0 ? "年龄未知" : ages.get(0).getAge() + "岁";
                final String gender = genders.get(0).getGender() == -1 ? "性别未知" : (genders.get(0).getGender() == 0 ? "男" : "女");

                //crop
                byte[] data = mImageNV21;
                YuvImage yuv = new YuvImage(data, ImageFormat.NV21, mWidth, mHeight, null);
                ExtByteArrayOutputStream ops = new ExtByteArrayOutputStream();
                yuv.compressToJpeg(mAFT_FSDKFace.getRect(), 80, ops);
                final Bitmap bmp = BitmapFactory.decodeByteArray(ops.getByteArray(), 0, ops.getByteArray().length);
                try {
                    ops.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (max > Constants.SIMILAR_THRESHOLD) {
                    //fr success.
                    final float max_score = max;
                    Log.d(TAG, "fit Score:" + max + ", NAME:" + name);

                    if(null==mAttendanceDialog||!mAttendanceDialog.isVisible()){
                        if(lastName.equals(name)&&!isSuit()){
                            return;
                        }
                        lastName=name;
                        lastTime=System.currentTimeMillis();
                        try{
                            showAttendanceDialog(name,bmp);
                        }catch (Exception e){
                            Log.d("showAttendanceDialog",e.getMessage());
                        }
                    }
                } else {
                    final String mNameShow = "未识别";
                }
                mImageNV21 = null;
            }
        }

        @Override
        public void over() {
            AFR_FSDKError error = engine.AFR_FSDK_UninitialEngine();
            Log.d(TAG, "AFR_FSDK_UninitialEngine : " + error.getCode());
        }
    }


    private void showAttendanceDialog(String name,Bitmap bmp){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        mAttendanceDialog= new AttendanceDialog().newInstance(new User(name, TimeUtils
                .getCurrentTime(simpleDateFormat)),bmp);
        mAttendanceDialog.show(getSupportFragmentManager(),
                                      AttendanceDialog.class.getSimpleName());
    }

    private void showInputDiolog(){
        mInputPassDialog.show(getSupportFragmentManager(),
                InputPassDialog.class.getSimpleName());
    }

    public boolean isSuit(){
        if(System.currentTimeMillis()-lastTime>1000*10){
            return true;
        }else {
            return false;
        }
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        if(mCamera!=null){
            mCamera.release();
        }
        if(mFRAbsLoop!=null){
            mFRAbsLoop.shutdown();
        }
        SpeakUtil.getInstance(this).onDestory();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override protected void onResume() {
        super.onResume();
        user_name.setText(mPreferencesUtil.getStringValue(Constants.KEY_USER_NAME,getString(R.string
                .user_name)));
        user_address.setText(mPreferencesUtil.getStringValue(Constants.KEY_USER_ADDR,getString(R.string
                .user_addr)));
        if(Constants.user_picture!=null){
            headImg.setImageBitmap(Constants.user_picture);
        }
    }
}

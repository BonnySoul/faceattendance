package com.woosiyuan.faceattendance;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;
import com.woosiyuan.faceattendance.arcface.main.modelview.RegisterVM;
import java.util.ArrayList;
import java.util.List;

/**
 * 说明：
 *
 * @version V1.0
 * @FileName: com.woosiyuan.faceattendance.PermissionAcitivity.java
 * @author: so
 * @date: 2018-01-10 11:30
 */

public class PermissionAcitivity extends Activity {
    public static int PERMISSION_REQ = 0x123456;
    private RegisterVM mRegisterVM;

    private String[] mPermission = new String[] {
            Manifest.permission.INTERNET,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private List<String> mRequestPermission = new ArrayList<String>();

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
    ;@Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        mRegisterVM=new RegisterVM(this);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            for (String one : mPermission) {
                if (PackageManager.PERMISSION_GRANTED != this.checkPermission(one, Process.myPid(), Process.myUid())) {
                    mRequestPermission.add(one);
                }
            }
            if (!mRequestPermission.isEmpty()) {
                this.requestPermissions(mRequestPermission.toArray(new String[mRequestPermission.size()]), PERMISSION_REQ);
                return ;
            }
        }
        if(checkCamera()){
            startActiviy();
        }else{
            Toast.makeText(getApplicationContext(),"相机无法打开",Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions,  int[] grantResults) {
        // 版本兼容
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.M) {
            return;
        }
        if (requestCode == PERMISSION_REQ) {
            for (int i = 0; i < grantResults.length; i++) {
                for (String one : mPermission) {
                    if (permissions[i].equals(one) && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        mRequestPermission.remove(one);
                    }
                }
            }
            startActiviy();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PERMISSION_REQ) {
            if (resultCode == 0) {
                this.finish();
            }
        }
    }

    public void startActiviy() {
        if (mRequestPermission.isEmpty()) {
            final ProgressDialog mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setTitle("loading register data...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Log.d("PermissionAcitivity---",MyApplication.getINSTANCE().mFaceDB.mRegister
                            .size()+"---");
                    MyApplication.getINSTANCE().mFaceDB.loadFaces();
                    //uploadDate();
                    Log.d("PermissionAcitivity---",MyApplication.getINSTANCE().mFaceDB.mRegister
                            .size()+"---loadFaces");
                    PermissionAcitivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mProgressDialog.cancel();
                            Intent intent = new Intent(PermissionAcitivity.this, MainActivity.class);
                            startActivityForResult(intent, PERMISSION_REQ);
                        }
                    });
                }
            }).start();
        } else {
            Toast.makeText(this, "PERMISSION DENIED!", Toast.LENGTH_LONG).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    PermissionAcitivity.this.finish();
                }
            }, 3000);
        }
    }

    public boolean checkCamera(){
        try {
            Camera mCamera = Camera.open(0);
            mCamera.release();
            return true;
        }catch (Exception e){
            return false;
        }
    }

}


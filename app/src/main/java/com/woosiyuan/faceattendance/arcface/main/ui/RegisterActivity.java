package com.woosiyuan.faceattendance.arcface.main.ui;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.widget.EditText;
import android.widget.Toast;
import com.arcsoft.facedetection.AFD_FSDKFace;
import com.arcsoft.facerecognition.AFR_FSDKFace;
import com.guo.android_extend.widget.ExtImageView;
import com.woosiyuan.faceattendance.R;
import com.woosiyuan.faceattendance.arcface.callback.AddFaceListener;
import com.woosiyuan.faceattendance.arcface.main.modelview.RegisterVM;
import com.woosiyuan.faceattendance.basis.util.BitmapUtil;
import com.woosiyuan.faceattendance.basis.BaseActivity;
import com.woosiyuan.faceattendance.databinding.ActivityRegisterBinding;
import java.util.List;

/**
 * 说明：
 *
 * @version V1.0
 * @FileName: com.woosiyuan.faceattendance.arcface.main.ui.AttendanceActivity.java
 * @author: so
 *
 *  人脸注册的界面
 * @date: 2018-01-04 14:41
 */

public class RegisterActivity extends BaseActivity implements SurfaceHolder.Callback {

    ActivityRegisterBinding binding;
    private SurfaceHolder mSurfaceHolder;
    private RegisterVM    mRegisterMV;
    private Bitmap        mBitmap;
    private String        mFilePath;
    private Rect src = new Rect();
    private Rect dst = new Rect();
    private final String TAG = this.getClass().toString();
    public static AFR_FSDKFace mAFR_FSDKFace;
    private boolean isFrist=true;
    private Context mContext;
    private EditText mEditText;
    private ExtImageView mExtImageView;
    private Bitmap face_bitmap;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,getLayoutResId());
        binding.surfaceView.getHolder().addCallback(this);
        mContext=this;
        if (!getIntentData(getIntent().getExtras())) {
            Log.e(TAG, "getIntentData fail!");
            this.finish() ;
        }
        mRegisterMV=new RegisterVM(this);

    }


    /**
     * @note bundle data :
     * String imagePath
     *
     * @param bundle
     */
    private boolean getIntentData(Bundle bundle) {
        try {
            mFilePath = bundle.getString("imagePath");
            mBitmap = BitmapUtil.decodeImage(mFilePath);
            src.set(0,0,mBitmap.getWidth(),mBitmap.getHeight());
            if (mFilePath == null || mFilePath.isEmpty()) {
                return false;
            }
            Log.i(TAG, "getIntentData:" + mFilePath);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    private void addFace(){
        mRegisterMV.addFace(mFilePath, new AddFaceListener() {
            @Override public void onSuccess(List<AFD_FSDKFace> mList,AFR_FSDKFace mAFD_FSDKFace) {
                mAFR_FSDKFace=mAFD_FSDKFace;
                drawRect(mList);
            }

            @Override public void onFailed(String msg) {
                Toast.makeText(mContext,"没有检测到人脸",Toast.LENGTH_SHORT).show();
                finish();
                Log.d("addFace","onFailed");
            }
        });
    }

    private void showDialog(Bitmap bitmap){
        new RegisterDialog().newInstance(bitmap)
                                     .show(getSupportFragmentManager(),
                                             RegisterDialog.class.getSimpleName());
    }


    public void drawRect(List<AFD_FSDKFace> result){
        while (mSurfaceHolder != null) {
            Canvas canvas = mSurfaceHolder.lockCanvas();
            if (canvas != null) {
                Paint mPaint = new Paint();
                boolean fit_horizontal = canvas.getWidth() / (float) src.width() <
                                                 canvas.getHeight() / (float) src.height() ? true : false;
                float scale = 1.0f;
                if (fit_horizontal) {
                    scale = canvas.getWidth() / (float) src.width();
                    dst.left = 0;
                    dst.top = (canvas.getHeight() - (int) (src.height() * scale)) / 2;
                    dst.right = dst.left + canvas.getWidth();
                    dst.bottom = dst.top + (int) (src.height() * scale);
                } else {
                    scale = canvas.getHeight() / (float) src.height();
                    dst.left = (canvas.getWidth() - (int) (src.width() * scale)) / 2;
                    dst.top = 0;
                    dst.right = dst.left + (int) (src.width() * scale);
                    dst.bottom = dst.top + canvas.getHeight();
                }

                canvas.drawBitmap(mBitmap, src, dst, mPaint);
                canvas.save();
                canvas.scale((float) dst.width() / (float) src.width(), (float) dst.height() / (float) src.height());
                canvas.translate(dst.left / scale, dst.top / scale);
                for (AFD_FSDKFace face : result) {
                    mPaint.setColor(Color.RED);
                    mPaint.setStrokeWidth(10.0f);
                    mPaint.setStyle(Paint.Style.STROKE);
                    canvas.drawRect(face.getRect(), mPaint);
                }

                int width = result.get(0).getRect().width();
                int height = result.get(0).getRect().height();
                face_bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
                Canvas face_canvas = new Canvas(face_bitmap);
                face_canvas.drawBitmap(mBitmap, result.get(0).getRect(), new Rect(0, 0, width, height), null);
                canvas.restore();
                mSurfaceHolder.unlockCanvasAndPost(canvas);
                showDialog(face_bitmap);
                break;
            }
        }
    }


    @Override public int getLayoutResId() {
        return R.layout.activity_register;
    }

    @Override public void surfaceCreated(SurfaceHolder holder) {
        mSurfaceHolder = holder;
        if(isFrist){
            addFace();
            isFrist=false;
        }

    }

    @Override public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override public void surfaceDestroyed(SurfaceHolder holder) {
        mSurfaceHolder = null;
    }
}

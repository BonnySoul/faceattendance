package com.woosiyuan.faceattendance.basis.util;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechEvent;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.cloud.util.ResourceUtil;

/**
 * 说明：
 *
 * @version V1.0
 * @FileName: com.woosiyuan.cruise2.util.SpeakUtil.java
 * @author: so
 * @date: 2017-09-15 17:33
 */

public class SpeakUtil {

    //创建一个TTS
    private static SpeechSynthesizer mTts;
    private static String mEngineType = SpeechConstant.TYPE_LOCAL;
    // 默认发音人
    private static String voicer      = "xiaoyan";
    private static Context context;
    private final String TAG ="SpeakUtil";
    private static SpeakUtil mSpeakUtil;
    private static final String VOICESPEED  ="50";
    private static final String VOICEPITCH  ="50";
    private static final String VOICEVOLUME ="80";

    public static SpeakUtil getInstance(Context mContext) {
        if(mSpeakUtil==null||mTts==null){
            mSpeakUtil=new SpeakUtil();
            context=mContext.getApplicationContext();
            mTts = SpeechSynthesizer.createSynthesizer(context, mTtsInitListener);
            setParam();
        }
       return mSpeakUtil;
    }

    public void speakText(String text){
        mTts.startSpeaking(text, new SynthesizerListener() {
            @Override public void onSpeakBegin() {
                Log.d(TAG,"onSpeakBegin");
            }

            @Override public void onBufferProgress(int i, int i1, int i2, String s) {
                Log.d(TAG,"onBufferProgress");
            }

            @Override public void onSpeakPaused() {
                Log.d(TAG,"onSpeakPaused");
            }

            @Override public void onSpeakResumed() {
                Log.d(TAG,"onSpeakResumed");
            }

            @Override public void onSpeakProgress(int i, int i1, int i2) {
                Log.d(TAG,"onSpeakProgress");
            }

            @Override public void onCompleted(SpeechError speechError) {
                Log.d(TAG,"onCompleted");
            }

            @Override public void onEvent(int onEvent, int i1, int i2, Bundle bundle) {
                Log.d(TAG,"onEvent: "+onEvent+" i1:"+i1+" i2:"+i2);
                	if (SpeechEvent.EVENT_SESSION_ID == onEvent) {
                		String sid = bundle.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
                		Log.d(TAG, "session id =" + sid);
                	}
            }
        });
    }


    private static void setParam(){
        if(mTts==null){
            return;
        }
        // 清空参数
        mTts.setParameter(SpeechConstant.PARAMS, null);
        mTts.setParameter( SpeechConstant.ENGINE_MODE, mEngineType );
        // 根据合成引擎设置相应参数
        if(mEngineType.equals(SpeechConstant.TYPE_CLOUD)) {
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
            // 设置在线合成发音人
            mTts.setParameter(SpeechConstant.VOICE_NAME, voicer);
            //设置合成语速
            mTts.setParameter(SpeechConstant.SPEED,VOICESPEED);
            //设置合成音调
            mTts.setParameter(SpeechConstant.PITCH, VOICEPITCH);
            //设置合成音量
            mTts.setParameter(SpeechConstant.VOLUME, VOICEVOLUME);
        }else {
            //设置使用本地引擎
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_LOCAL);
            //设置发音人资源路径
            mTts.setParameter(ResourceUtil.TTS_RES_PATH,getResourcePath());
            //设置发音人
            mTts.setParameter(SpeechConstant.VOICE_NAME,voicer);
            /**
             * TODO 本地合成不设置语速、音调、音量，默认使用语记设置
             * 开发者如需自定义参数，请参考在线合成参数设置
             */
        }
        //设置播放器音频流类型
        mTts.setParameter(SpeechConstant.STREAM_TYPE, "3");
        // 设置播放合成音频打断音乐播放，默认为true
        mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mTts.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory()+"/msc/tts.wav");
    }


    //获取发音人资源路径
    private static String getResourcePath(){
        StringBuffer tempBuffer = new StringBuffer();
        //合成通用资源
        tempBuffer.append(ResourceUtil.generateResourcePath(context, ResourceUtil.RESOURCE_TYPE.assets,
                "tts/common.jet"));
        tempBuffer.append(";");
        //发音人资源
        tempBuffer.append(
                ResourceUtil.generateResourcePath(context, ResourceUtil.RESOURCE_TYPE.assets, "tts/"+voicer+".jet"));
        return tempBuffer.toString();
    }

    /**
     * 初始化监听。
     */
    private static InitListener mTtsInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            Log.d("Tag", "InitListener init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                Log.d(" Log.d","初始化失败,错误码："+code);
            } else {
                // 初始化成功，之后可以调用startSpeaking方法
                // 注：有的开发者在onCreate方法中创建完合成对象之后马上就调用startSpeaking进行合成，
                // 正确的做法是将onCreate中的startSpeaking调用移至这里
            }
        }
    };

    public void onDestory(){
        if( null != mTts ){
            mTts.stopSpeaking();
            // 退出时释放连接
            mTts.destroy();
        }
    }

}

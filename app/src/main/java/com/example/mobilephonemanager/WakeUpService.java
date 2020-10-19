package com.example.mobilephonemanager;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import com.baidu.aip.asrwakeup3.core.wakeup.MyWakeup;
import com.baidu.aip.asrwakeup3.core.wakeup.listener.IWakeupListener;
import com.baidu.aip.asrwakeup3.core.wakeup.listener.RecogWakeupListener;
import com.baidu.speech.asr.SpeechConstant;

import java.util.HashMap;
import java.util.Map;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static com.baidu.aip.asrwakeup3.core.recog.IStatus.STATUS_NONE;

public class WakeUpService extends Service {
    protected MyWakeup myWakeup;
    private int status = STATUS_NONE;
    private WakeUpBinder wakeUpBinder=new WakeUpBinder();
    public static final String TAG="WakeUpService";
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
           // Intent intent=new Intent(WakeUpService.this,MainActivity.class);
            //intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
            //Log.d(TAG,"is handle");
            //startActivity(intent);
            //onDestroy();
            //stopSelf();
            final Intent intent = getPackageManager().getLaunchIntentForPackage(getPackageName());
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

            Log.d(TAG,"stop self");
        }
    };
    class WakeUpBinder extends Binder{
        // 点击“开始识别”按钮
        // 基于DEMO唤醒词集成第2.1, 2.2 发送开始事件开始唤醒
        public void start() {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put(SpeechConstant.WP_WORDS_FILE, "assets:///return.bin");
            // "assets:///WakeUp.bin" 表示WakeUp.bin文件定义在assets目录下

            // params.put(SpeechConstant.ACCEPT_AUDIO_DATA,true);
            // params.put(SpeechConstant.IN_FILE,"res:///com/baidu/android/voicedemo/wakeup.pcm");
            // params里 "assets:///WakeUp.bin" 表示WakeUp.bin文件定义在assets目录下
            myWakeup.start(params);
        }
    }

    public WakeUpService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        IWakeupListener listener = new RecogWakeupListener(handler);
        myWakeup = new MyWakeup(this, listener);
        Log.d(TAG,"init wakeupservice is ok");
    }

    @Override
    public void onDestroy() {
        if(myWakeup!=null){
            myWakeup.release();
        }
        Log.d(TAG,"is Destory");
        super.onDestroy();

    }

    @Override
    public IBinder onBind(Intent intent) {
        return wakeUpBinder;
    }
}

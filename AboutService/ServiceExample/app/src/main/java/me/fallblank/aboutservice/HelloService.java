package me.fallblank.aboutservice;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;

/**
 * Created by fallb on 2016/6/23.
 */
public class HelloService extends Service {
    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;
    private PendingIntent mPendingIntent;


    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }


        @Override public void handleMessage(Message msg) {
            long endTime = System.currentTimeMillis() + 5 * 1000;
            while (System.currentTimeMillis() < endTime) {
                synchronized (this) {
                    try {
                        wait(endTime - System.currentTimeMillis());
                    } catch (Exception e) {
                    }
                }
                //只有当stopSelf的startId等于最后一个id时才会终止服务
                stopSelf(msg.arg1);
            }
        }
    }


    @Override public void onCreate() {
        LogUtil.d("HelloService --onCreate");
        HandlerThread handlerThread = new HandlerThread("HelloService");
        handlerThread.start();
        mServiceLooper = handlerThread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
    }


    @Override public int onStartCommand(Intent intent, int flags, int startId) {
        if (mPendingIntent == null) {
            mPendingIntent = intent.getParcelableExtra("broadcast");
            LogUtil.d("mPendingInteng = " + (mPendingIntent == null));

        }
        LogUtil.d("HelloService --onStartCommand");
        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        mServiceHandler.sendMessage(msg);
        return START_STICKY;
    }


    @Nullable @Override public IBinder onBind(Intent intent) {
        return null;
    }


    @Override public void onDestroy() {
        try {
            mPendingIntent.send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
        LogUtil.d("HelloService --onDestory");
    }
}

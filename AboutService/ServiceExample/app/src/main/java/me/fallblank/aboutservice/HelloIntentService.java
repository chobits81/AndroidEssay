package me.fallblank.aboutservice;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by fallb on 2016/6/23.
 */
public class HelloIntentService extends IntentService {

    public HelloIntentService() {
        super("HelloIntentService");
    }


    @Override protected void onHandleIntent(Intent intent) {
        long endTime = System.currentTimeMillis() + 5 * 1000;
        while (System.currentTimeMillis() < endTime) {
            synchronized (this) {
                try {
                    wait(endTime - System.currentTimeMillis());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @Override public void onCreate() {
        super.onCreate();
        LogUtil.d("IntentService --onCreate");
    }


    @Override public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.d("IntentService --onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }


    @Override public void onDestroy() {
        super.onDestroy();
        LogUtil.d("IntentService --onDestory");
    }
}

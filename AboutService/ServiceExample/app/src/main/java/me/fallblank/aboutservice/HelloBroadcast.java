package me.fallblank.aboutservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by fallb on 2016/6/23.
 */
public class HelloBroadcast extends BroadcastReceiver {
    @Override public void onReceive(Context context, Intent intent) {
        LogUtil.d("Received info,Thread:" + Thread.currentThread().getName());
    }
}

package me.fallblank.messengerserviceclient;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Messenger mMessenger;
    boolean mMessageServiceBound;

    ServiceConnection mMessageConnection = new ServiceConnection() {
        @Override public void onServiceConnected(ComponentName name, IBinder service) {
            mMessenger = new Messenger(service);
            mMessageServiceBound = true;
        }


        @Override public void onServiceDisconnected(ComponentName name) {
            mMessenger = null;
            mMessageServiceBound = false;
        }
    };

    Button mBtnMessengerService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent();
        intent.setClassName("me.fallblank.aboutservice",
            "me.fallblank.aboutservice.MessageService");
        bindService(intent, mMessageConnection, BIND_AUTO_CREATE);
        mBtnMessengerService = (Button) findViewById(R.id.btn_ms);
        mBtnMessengerService.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Log.d("fallblank", "clicked");
                if (mMessageServiceBound) {
                    Message msg = Message.obtain(null, 1, 0, 0);
                    try {
                        mMessenger.send(msg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                   launchapp(getApplicationContext());
                }
            }
        });
    }

    public static final String APP_PACKAGE_NAME = "me.fallblank.aboutservice";//包名

    /**
     * @param context
     */
    public static void launchapp(Context context) {
        // 判断是否安装过App，否则去市场下载
        if (isAppInstalled(context, APP_PACKAGE_NAME)) {
            context.startActivity(context.getPackageManager().getLaunchIntentForPackage(APP_PACKAGE_NAME));
        } else {
            goToMarket(context, APP_PACKAGE_NAME);
        }
    }

    /**
     * 检测某个应用是否安装
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isAppInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    /**
     * 去市场下载页面
     */
    public static void goToMarket(Context context, String packageName) {
        Uri uri = Uri.parse("market://details?id=" + packageName);
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
        }
    }


    @Override protected void onDestroy() {
        super.onDestroy();
        if (mMessageServiceBound) {
            unbindService(mMessageConnection);
            mMessageServiceBound = false;
        }
    }
}

package me.fallblank.aboutservice;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button mBtnIntentService;
    Button mBtnCustomService;
    Button mBtnBoundService;
    Button mBtnMessageService;
    private static final String SERVICE_FINISHED = "service_finished";

    HelloBroadcast mBroadcast;
    BoundService mService;
    boolean mBound;
    Messenger mMessenger;
    boolean mMessageServiceBound;

    ServiceConnection mConnection = new ServiceConnection() {
        @Override public void onServiceConnected(ComponentName name, IBinder service) {
            BoundService.LocalBinder binder = (BoundService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }


        @Override public void onServiceDisconnected(ComponentName name) {
            mBound = false;
        }
    };

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnIntentService = (Button) findViewById(R.id.btn_is);
        mBtnCustomService = (Button) findViewById(R.id.btn_cs);
        mBtnBoundService = (Button) findViewById(R.id.btn_bs);
        mBtnMessageService = (Button) findViewById(R.id.btn_ms);

        mBtnIntentService.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HelloIntentService.class);
                startService(intent);
            }
        });

        mBtnCustomService.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HelloService.class);
                Intent broadcastIntent = new Intent();
                broadcastIntent.setAction(SERVICE_FINISHED);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0,
                    broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                intent.putExtra("broadcast", pendingIntent);
                startService(intent);
            }
        });

        mBtnBoundService.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (mBound) {
                    LogUtil.d("" + mService.getRandomNumber());
                }
            }
        });

        mBtnMessageService.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                LogUtil.d("clicked");
                if (mMessageServiceBound) {
                    Message msg = Message.obtain(null, MessageService.MSG_SAY_HELLO, 0, 0);
                    try {
                        mMessenger.send(msg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        mBroadcast = new HelloBroadcast();
        IntentFilter filter = new IntentFilter();
        filter.addAction(SERVICE_FINISHED);
        registerReceiver(mBroadcast, filter);
    }


    @Override protected void onStart() {
        super.onStart();
        Intent i = new Intent(this, BoundService.class);
        bindService(i, mConnection, Context.BIND_AUTO_CREATE);
        Intent intent = new Intent(this, MessageService.class);
        bindService(intent, mMessageConnection, BIND_AUTO_CREATE);
    }


    @Override protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcast);
        unbindService(mConnection);
        if (mMessageServiceBound) {
            unbindService(mMessageConnection);
            mMessageServiceBound = false;
        }
    }
}

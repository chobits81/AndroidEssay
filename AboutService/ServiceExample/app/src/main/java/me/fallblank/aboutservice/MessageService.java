package me.fallblank.aboutservice;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by fallb on 2016/6/24.
 */
public class MessageService extends Service {
    static final int MSG_SAY_HELLO = 1;

    class IncomingHandler extends Handler{
        @Override public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_SAY_HELLO:
                    LogUtil.d("From MessageService:hello");
                    System.out.println("hello");
                    Toast.makeText(getApplicationContext(),"2333",Toast.LENGTH_LONG).show();
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    final Messenger mMessenger = new Messenger(new IncomingHandler());


    @Override public void onCreate() {
        LogUtil.d("MessageService:onCreate");
        super.onCreate();
    }


    @Nullable @Override public IBinder onBind(Intent intent) {
        LogUtil.d("From MessageService:onBind");
        return mMessenger.getBinder();
    }
}

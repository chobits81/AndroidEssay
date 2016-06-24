package me.fallblank.aboutservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import java.util.Random;

public class BoundService extends Service {

    private final IBinder mBinder = new LocalBinder();
    private final Random mGenerator = new Random();

    public BoundService() {
    }


    public class LocalBinder extends Binder {
        BoundService getService() {
            return BoundService.this;
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mBinder;
    }

    public int getRandomNumber(){
        return mGenerator.nextInt(100);
    }

}

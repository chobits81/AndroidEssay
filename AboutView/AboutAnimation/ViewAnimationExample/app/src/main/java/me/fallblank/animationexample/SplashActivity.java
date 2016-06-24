package me.fallblank.animationexample;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author fallblank
 * you can edit everything as you like!
 */

public class SplashActivity extends AppCompatActivity {
    @Bind(R.id.im_splash) ImageView mSplash;
    Handler mHandler;


    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        ButterKnife.bind(this);
        mHandler = new Handler();
        //让图片显示一秒钟，然后开始动画
        mHandler.postDelayed(new Runnable() {
            @Override public void run() {
                //使用动画集，让多个动画同时启动
                AnimationSet animationSet = new AnimationSet(true);
                animationSet.setDuration(2000);
                AlphaAnimation alpha = new AlphaAnimation(1, 0);
                alpha.setDuration(2000);
                animationSet.addAnimation(alpha);
                ScaleAnimation scale = new ScaleAnimation(1, 2, 1, 2, Animation.RELATIVE_TO_SELF,
                    0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
                scale.setDuration(2000);
                animationSet.addAnimation(scale);
                mSplash.startAnimation(animationSet);
            }
        }, 2000);
        //多少时间后启动主activity
        mHandler.postDelayed(new Runnable() {
            @Override public void run() {
                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                //在新的应用栈中启动activity
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                //销毁这个开始屏幕，这个很重要不然按下返回键会回退到启动屏
                SplashActivity.this.finish();
            }
        }, 3800);
    }

}
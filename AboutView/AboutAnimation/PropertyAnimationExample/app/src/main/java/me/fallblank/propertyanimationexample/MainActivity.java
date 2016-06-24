package me.fallblank.propertyanimationexample;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.fallblank.propertyanimationexample.utils.LogUtil;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.btn_bg) Button mGradientBg;
    @Bind(R.id.btn_cl) Button mChangelocation;
    ObjectAnimator mObjectAnimator;
    ValueAnimator mValueAnimator;


    @TargetApi(Build.VERSION_CODES.LOLLIPOP) @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        final View view = findViewById(android.R.id.content);
        final Drawable bgd = view.getBackground();
        mObjectAnimator = ObjectAnimator.ofArgb(view, "backgroundColor", 0x881DDA38, 0x88D48AB2);
        mObjectAnimator.setDuration(5 * 1000);
        mObjectAnimator.setRepeatMode(ObjectAnimator.REVERSE);
        mObjectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setBackground(bgd);
            }
        });

        final float X = 0, Y = 500;
        LogUtil.d("X,Y = "+X+","+Y);
        mValueAnimator = ValueAnimator.ofFloat(0f, 1f);
        mValueAnimator.setDuration(3 * 1000);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override public void onAnimationUpdate(ValueAnimator animation) {
                mChangelocation.setY(Y * (Float)animation.getAnimatedValue());
                mChangelocation.invalidate();
            }
        });
        mValueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mChangelocation.setY(Y);
            }
        });

    }


    @OnClick(R.id.btn_bg) void onGradientBgClicked() {
        mObjectAnimator.start();
    }


    @OnClick(R.id.btn_cl) void onChangeLocationClicked() {
        mValueAnimator.start();
    }
}

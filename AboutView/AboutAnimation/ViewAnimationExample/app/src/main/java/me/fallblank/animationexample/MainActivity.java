package me.fallblank.animationexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author fallblank
 * you can edit everything as you like!
 */

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.im_stage) ImageView mStage;
    @Bind(R.id.btn_alpha) Button mAlpha;
    @Bind(R.id.btn_rotate) Button mRotate;
    @Bind(R.id.btn_scale) Button mScale;
    @Bind(R.id.btn_translate) Button mTranslate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }


    /**
     * 触发Alpha（透明）动画
     */
    @OnClick(R.id.btn_alpha) void onAlphaClicked() {
        AlphaAnimation alpha = new AlphaAnimation(0, 1);
        alpha.setDuration(2000);
        mStage.startAnimation(alpha);
    }


    /**
     * 触发Rotate（旋转动画）
     */
    @OnClick(R.id.btn_rotate) void onRotateClicked() {
        RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(2000);
        mStage.startAnimation(rotate);
    }


    /**
     * 触发Translate（位移）动画
     */
    @OnClick(R.id.btn_translate) void onTranslateClicked() {
        TranslateAnimation translate = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f,
            Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 0f,
            Animation.RELATIVE_TO_SELF, 1f);
        translate.setDuration(2000);
        mStage.startAnimation(translate);
    }


    /**
     * 触发Scale（缩放）动画
     */
    @OnClick(R.id.btn_scale) void onScaleClicked(){
        ScaleAnimation scale = new ScaleAnimation(0,1,0,1,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        scale.setDuration(2000);
        mStage.startAnimation(scale);
    }
}

package com.test.question.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;


import com.test.question.R;
import com.test.question.utils.StatusBarUtil;

import java.io.File;

public class WelcomeActivity extends AppCompatActivity {

    private ImageView iv_guide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置窗体全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);
        //使状态栏半透明
        // 适用于图片作为背景的界面,此时需要图片填充到状态栏
        StatusBarUtil.setTranslucent(WelcomeActivity.this,0);
        iv_guide = (ImageView) findViewById(R.id.iv_guide);
        initFile();
        //缩放动画
        final ScaleAnimation scaleAnim = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        //如果值为true，控件则保持动画结束的状态
        scaleAnim.setFillAfter(true);
        //动画的执行时间
        scaleAnim.setDuration(1200);
        scaleAnim.setAnimationListener(new Animation.AnimationListener() {
            //动画执行前
            @Override
            public void onAnimationStart(Animation animation) {

            }
            //动画执行后
            @Override
            public void onAnimationEnd(Animation animation) {
               startActivity(new Intent(WelcomeActivity.this,LoginActivity.class));
               finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        iv_guide.startAnimation(scaleAnim);
    }
    private void initFile() {
        File dir = new File("/sdcard/food");
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
}

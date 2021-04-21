package com.livescore.CricDream.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.livescore.CricDream.R;

public class SplashActivity extends Activity {

    LottieAnimationView mLottieAnimationView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mLottieAnimationView = findViewById(R.id.lottie_animation_view);
        changeAnim();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, 5000);
    }

    private void changeAnim() {

        Handler  handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run()
            {
                String animFileName = "empty_status.json";
                LottieComposition.Factory.fromAssetFileName(SplashActivity.this, animFileName, composition -> {
                    mLottieAnimationView.setComposition(composition);
                    mLottieAnimationView.playAnimation();
                });
            }
        };
        handler.postDelayed(runnable,  1);
    }

    @Override
    protected void onDestroy() {
        mLottieAnimationView.cancelAnimation();
        mLottieAnimationView = null;
        super.onDestroy();
    }
}


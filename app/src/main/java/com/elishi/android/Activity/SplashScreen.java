package com.elishi.android.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import com.elishi.android.Common.Utils;
import com.elishi.android.R;
import com.elishi.android.databinding.ActivitySplashScreenBinding;

public class SplashScreen extends AppCompatActivity {
    private Context context = this;
    private ActivitySplashScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Utils.getSharedPreference(context, "mode").equals("night")) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        Utils.loadLocal(context);
        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setFillAfter(true);
        animation.setDuration(1200);
        binding.getRoot().startAnimation(animation);


        setListener();
    }

    private void setListener() {
        binding.motionLayout.setTransitionListener(new MotionLayout.TransitionListener() {
            @Override
            public void onTransitionStarted(MotionLayout motionLayout, int startId, int endId) {

            }

            @Override
            public void onTransitionChange(MotionLayout motionLayout, int startId, int endId, float progress) {
                if (startId == R.id.start) {
                    binding.logo.setImageResource(R.color.logo_third_color);
                    binding.logo.setScaleType(ImageView.ScaleType.CENTER_CROP);

                    if (progress == 1.0f) {
                        binding.logo.setImageResource(R.drawable.ic_launcher_foreground);
                        binding.logo.setScaleType(ImageView.ScaleType.CENTER);
                    }
                }
            }

            @Override
            public void onTransitionCompleted(MotionLayout motionLayout, int currentId) {
                if (currentId == R.id.fourth) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                            Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }, 3000);
                }
            }

            @Override
            public void onTransitionTrigger(MotionLayout motionLayout, int triggerId, boolean positive, float progress) {

            }
        });
    }
}
    
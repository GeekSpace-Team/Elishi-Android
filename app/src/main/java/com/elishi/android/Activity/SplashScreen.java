package com.elishi.android.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import com.elishi.android.Common.Click;
import com.elishi.android.Common.Utils;
import com.elishi.android.Fragment.Settings.Settings;
import com.elishi.android.Modal.Home.BannerSlider;
import com.elishi.android.R;
import com.elishi.android.databinding.ActivitySplashScreenBinding;
import com.google.android.gms.common.wrappers.InstantApps;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;

public class SplashScreen extends AppCompatActivity {
    private Context context = this;
    private ActivitySplashScreenBinding binding;
    final String STATUS_INSTALLED = "installed";
    final String STATUS_INSTANT = "instant";
    final String ANALYTICS_USER_PROP = "app_type";

    private FirebaseAnalytics firebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.loadLocal(context);
        if (Utils.getSharedPreference(context, "mode").equals("night")) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            //bundle must contain all info sent in "data" field of the notification
            if (bundle.get("url") != null) {
                finish();
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                intent.putExtra("url",bundle.get("url").toString());
                startActivity(intent);
                return;
            }
        }

        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        // Get deep link from result (may be null if no link is found)
                        Uri deepLink = null;
                        if (pendingDynamicLinkData != null) {
                            deepLink = pendingDynamicLinkData.getLink();
                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "getDynamicLink:onFailure", e);
                    }
                });


        firebaseAnalytics = FirebaseAnalytics.getInstance(this);

        // Determine the current app context, either installed or instant, then
        // set the corresponding user property for Google Analytics.
        if (InstantApps.isInstantApp(this)) {
            firebaseAnalytics.setUserProperty(ANALYTICS_USER_PROP, STATUS_INSTANT);
        } else {
            firebaseAnalytics.setUserProperty(ANALYTICS_USER_PROP, STATUS_INSTALLED);
        }

        Bundle bundle2 = new Bundle();
        bundle2.putString(FirebaseAnalytics.Param.SCREEN_NAME, "SplashScreen");
        bundle2.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "SplashScreen");
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);


        checkMode();
        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setFillAfter(true);
        animation.setDuration(1200);
        binding.getRoot().startAnimation(animation);


        setListener();
    }



    private void checkMode() {
        int nightModeFlags =
                getResources().getConfiguration().uiMode &
                        Configuration.UI_MODE_NIGHT_MASK;
        View view = getWindow().getDecorView();
        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    view.setSystemUiVisibility(view.getSystemUiVisibility() & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                }
                break;

            case Configuration.UI_MODE_NIGHT_NO:
            case Configuration.UI_MODE_NIGHT_UNDEFINED:

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    view.setSystemUiVisibility(view.getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                }
                break;
        }
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
                        binding.logo.setImageResource(R.mipmap.ic_launcher_foreground);
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
                            if(Utils.getSharedPreference(context,"firstTime").equals("-1")){
                                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(SplashScreen.this, SettingsActivity.class);
                                startActivity(intent);
                            }
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
    
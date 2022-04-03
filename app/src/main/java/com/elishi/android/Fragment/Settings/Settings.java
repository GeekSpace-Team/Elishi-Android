package com.elishi.android.Fragment.Settings;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.elishi.android.Activity.MainActivity;
import com.elishi.android.Common.Utils;
import com.elishi.android.R;
import com.elishi.android.databinding.FragmentSettingsBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.wuyr.rippleanimation.RippleAnimation;


public class Settings extends Fragment {

    private FragmentSettingsBinding binding;
    private Context context;
    private Boolean isNightMode=false;
    public Settings() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentSettingsBinding.inflate(getLayoutInflater(),container,false);
        context=getContext();
        checkTheme();
        setListener();
        checkLang();
        return binding.getRoot();
    }

    private void checkTheme() {
        int currentNightMode = getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK;
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_NO:
            case Configuration.UI_MODE_NIGHT_UNDEFINED:
                isNightMode=false;
                binding.mode.setImageResource(R.drawable.ic_baseline_nights_stay_24);
                break;
            case Configuration.UI_MODE_NIGHT_YES:
                isNightMode=true;
                binding.mode.setImageResource(R.drawable.ic_baseline_wb_sunny_24);
                break;
        }
    }

    private void setListener() {
        binding.mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RippleAnimation.create(view).setDuration(800).setOnAnimationEndListener(new RippleAnimation.OnAnimationEndListener() {
                    @Override
                    public void onAnimationEnd() {
                        if(!isNightMode)
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        else
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        getActivity().getWindow().setWindowAnimations(R.style.WindowAnimationFadeInOut);
                        MainActivity.isChangeTheme=true;
                    }
                }).start();
                if(isNightMode){
                    binding.bg.setBackgroundColor(Color.parseColor("#F0F4F4"));
                    binding.back.setColorFilter(ContextCompat.getColor(context, R.color.realBlack), android.graphics.PorterDuff.Mode.SRC_IN);
                    binding.mode.setColorFilter(ContextCompat.getColor(context, R.color.realBlack), android.graphics.PorterDuff.Mode.SRC_IN);
                    binding.title.setTextColor(context.getResources().getColor(R.color.realBlack));
                    binding.ruLangTv.setTextColor(context.getResources().getColor(R.color.realBlack));
                    binding.tmLangTv.setTextColor(context.getResources().getColor(R.color.realBlack));
                    binding.enLangTv.setTextColor(context.getResources().getColor(R.color.realBlack));
                    binding.selectLanguage.setTextColor(context.getResources().getColor(R.color.realBlack));
                    binding.mode.setImageResource(R.drawable.ic_baseline_nights_stay_24);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        Window window = getActivity().getWindow();
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                        window.setStatusBarColor(Color.parseColor("#F0F4F4"));
                        window.setNavigationBarColor(Color.parseColor("#F0F4F4"));
                    }
                    Drawable unwrappedDrawable = AppCompatResources.getDrawable(context, R.drawable.bottom_nav_bg);
                    Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
                    DrawableCompat.setTint(wrappedDrawable, Color.parseColor("#FFFFFF"));
                    BottomNavigationView bottomNavigationView=getActivity().findViewById(R.id.bottomNavigation);
                    RelativeLayout main=getActivity().findViewById(R.id.main);
                    bottomNavigationView.setBackground(wrappedDrawable);
                    main.setBackgroundColor(Color.parseColor("#F0F4F4"));
                    isNightMode=false;
                    Utils.setPreference("mode","light",context);
                } else{
                    binding.bg.setBackgroundColor(Color.parseColor("#121212"));
                    binding.back.setColorFilter(ContextCompat.getColor(context, R.color.realWhite), android.graphics.PorterDuff.Mode.SRC_IN);
                    binding.mode.setColorFilter(ContextCompat.getColor(context, R.color.realWhite), android.graphics.PorterDuff.Mode.SRC_IN);
                    binding.title.setTextColor(context.getResources().getColor(R.color.realWhite));
                    binding.ruLangTv.setTextColor(context.getResources().getColor(R.color.realWhite));
                    binding.tmLangTv.setTextColor(context.getResources().getColor(R.color.realWhite));
                    binding.enLangTv.setTextColor(context.getResources().getColor(R.color.realWhite));
                    binding.selectLanguage.setTextColor(context.getResources().getColor(R.color.realWhite));
                    binding.mode.setImageResource(R.drawable.ic_baseline_wb_sunny_24);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        Window window = getActivity().getWindow();
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                        window.setStatusBarColor(Color.parseColor("#121212"));
                        window.setNavigationBarColor(Color.parseColor("#121212"));
                    }
                    Drawable unwrappedDrawable = AppCompatResources.getDrawable(context, R.drawable.bottom_nav_bg);
                    Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
                    DrawableCompat.setTint(wrappedDrawable, Color.parseColor("#1F1B24"));
                    BottomNavigationView bottomNavigationView=getActivity().findViewById(R.id.bottomNavigation);
                    RelativeLayout main=getActivity().findViewById(R.id.main);
                    bottomNavigationView.setBackground(wrappedDrawable);
                    main.setBackgroundColor(Color.parseColor("#121212"));
                    isNightMode=true;
                    Utils.setPreference("mode","night",context);
                }




            }
        });

        binding.tmLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Utils.getLanguage(context).equals("tm") && !Utils.getLanguage(context).isEmpty()){
                    Utils.setLocale("tm",context);
                    restart();
                    checkLang();
                }
            }
        });

        binding.ruLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Utils.getLanguage(context).equals("ru")){
                    Utils.setLocale("ru",context);
                    restart();
                    checkLang();
                }
            }
        });

        binding.enLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Utils.getLanguage(context).equals("en")){
                    Utils.setLocale("en",context);
                    restart();
                    checkLang();
                }
            }
        });


    }

    private void checkLang() {
        if(Utils.getLanguage(context).equals("tm") || Utils.getLanguage(context).isEmpty()){
            setAllDisable();
            binding.tmLangContainer.setAlpha(1f);
            binding.tmLang.setBackgroundResource(R.drawable.gradient_cyrcle);
        }
        if(Utils.getLanguage(context).equals("ru")){
            setAllDisable();
            binding.ruLangContainer.setAlpha(1f);
            binding.ruLang.setBackgroundResource(R.drawable.gradient_cyrcle);
        }
        if(Utils.getLanguage(context).equals("en")){
            setAllDisable();
            binding.enLangContainer.setAlpha(1f);
            binding.enLang.setBackgroundResource(R.drawable.gradient_cyrcle);
        }
    }

    private void setAllDisable() {
        binding.tmLangContainer.setAlpha(0.3f);
        binding.tmLang.setBackgroundResource(R.drawable.disble_cyrcle);
        binding.ruLangContainer.setAlpha(0.3f);
        binding.ruLang.setBackgroundResource(R.drawable.disble_cyrcle);
        binding.enLangContainer.setAlpha(0.3f);
        binding.enLang.setBackgroundResource(R.drawable.disble_cyrcle);

    }

    private void restart(){
        getActivity().recreate();
        getActivity().getWindow().setWindowAnimations(R.style.WindowAnimationFadeInOut);
        MainActivity.isChangeTheme=true;
    }



}
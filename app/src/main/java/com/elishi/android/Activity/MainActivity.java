package com.elishi.android.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.elishi.android.Common.Utils;
import com.elishi.android.Fragment.AddProductFragment;
import com.elishi.android.Fragment.CategoryFragment;
import com.elishi.android.Fragment.Constant.ConstantPage;
import com.elishi.android.Fragment.FavoritesFragment;
import com.elishi.android.Fragment.Holiday.HolidayView;
import com.elishi.android.Fragment.HomeFragment;
import com.elishi.android.Fragment.Product.Products;
import com.elishi.android.Fragment.Profile.MyProfile;
import com.elishi.android.Fragment.Profile.ProfileRootFragment;
import com.elishi.android.Fragment.Settings.Settings;
import com.elishi.android.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.messaging.FirebaseMessaging;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private Context context = this;
    public static Fragment firstFragment = new HomeFragment();
    public static Fragment secondFragment = new CategoryFragment();
    public static Fragment thirdFragment = new AddProductFragment();
    public static Fragment fourthFragment = new FavoritesFragment();
    public static Fragment fifthFragment = new ProfileRootFragment();
    public static Boolean isChangeTheme=false;
    private FirebaseAnalytics mFirebaseAnalytics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "some_test_id");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "some_test_name");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "some_test_image");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);


        FirebaseMessaging.getInstance().subscribeToTopic("general")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                });

        getWindow().setWindowAnimations(R.style.WindowAnimationFadeInOut);
        setContentView(R.layout.activity_main);
        initComponents();
        bottomNavSettings();


    }


    private void initComponents() {
        bottomNavigationView = findViewById(R.id.bottomNavigation);
    }

    private void bottomNavSettings() {
        changeFragment(firstFragment, firstFragment.getClass().getSimpleName());
        bottomNavigationView.setItemIconTintList(null);
        setBottomNavigationItems(context, bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                checkBottomNav(item.getItemId());
                return true;
            }
        });
    }

    private void checkBottomNav(int id) {
        switch (id) {
            case R.id.home:
                changeFragment(firstFragment, firstFragment.getClass().getSimpleName());
                break;
            case R.id.category:
                changeFragment(secondFragment, secondFragment.getClass().getSimpleName());
                break;
            case R.id.add:
                changeFragment(thirdFragment, thirdFragment.getClass().getSimpleName());
                break;
            case R.id.fav:
                changeFragment(fourthFragment, fourthFragment.getClass().getSimpleName());
                break;
            case R.id.profile:
                changeFragment(fifthFragment, fifthFragment.getClass().getSimpleName());
                break;

        }
    }


    public void setBottomNavigationItems(final Context context, final View v) {
        try {
            if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    setBottomNavigationItems(context, child);
                }
            } else if (v instanceof ImageView) {
                ViewCompat.setElevation(v, 32f);

            }
        } catch (Exception e) {
        }
    }

    public void changeFragment(Fragment fragment, String tagFragmentName) {

        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        Fragment currentFragment = mFragmentManager.getPrimaryNavigationFragment();
        if (currentFragment != null) {
            fragmentTransaction.hide(currentFragment);
        }

        Fragment fragmentTemp = mFragmentManager.findFragmentByTag(tagFragmentName);
        if (fragmentTemp == null) {
            fragmentTemp = fragment;
            fragmentTransaction.add(R.id.content, fragmentTemp, tagFragmentName);
        } else {
            fragmentTransaction.show(fragmentTemp);
        }
        fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        fragmentTransaction.setPrimaryNavigationFragment(fragmentTemp);
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.commitNowAllowingStateLoss();
    }

    public static void showSplash(Activity activity) {
        BottomNavigationView bottomNavigationView = activity.findViewById(R.id.bottomNavigation);
        bottomNavigationView.setVisibility(View.GONE);
    }

    public static void hideSplash(Activity activity) {
        BottomNavigationView bottomNavigationView = activity.findViewById(R.id.bottomNavigation);
        bottomNavigationView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("indexBottomNav", bottomNavigationView.getSelectedItemId());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        try {
            Integer indexBottomNav = savedInstanceState.getInt("indexBottomNav");
            if (indexBottomNav != null) {
                if(indexBottomNav==R.id.profile){
                    fifthFragment=new Settings();
                    isChangeTheme=false;
                }
                checkBottomNav(indexBottomNav);
            } else {
                changeFragment(firstFragment, firstFragment.getClass().getSimpleName());
            }
        } catch (Exception ex){
            ex.printStackTrace();
            changeFragment(firstFragment, firstFragment.getClass().getSimpleName());
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        firstFragment = new HomeFragment();
        secondFragment = new CategoryFragment();
        thirdFragment = new AddProductFragment();
        fourthFragment = new FavoritesFragment();
        fifthFragment = new ProfileRootFragment();
    }

    @Override
    public void onBackPressed() {
        HomeFragment home = (HomeFragment) getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getSimpleName());
        CategoryFragment category = (CategoryFragment) getSupportFragmentManager().findFragmentByTag(CategoryFragment.class.getSimpleName());
        FavoritesFragment favourite = (FavoritesFragment) getSupportFragmentManager().findFragmentByTag(FavoritesFragment.class.getSimpleName());
        ProfileRootFragment profilePage = (ProfileRootFragment) getSupportFragmentManager().findFragmentByTag(ProfileRootFragment.class.getSimpleName());
        AddProductFragment addProductFragment = (AddProductFragment) getSupportFragmentManager().findFragmentByTag(AddProductFragment.class.getSimpleName());

        if ((home != null && home.isVisible())) {
            finish();
            getWindow().setWindowAnimations(R.style.WindowAnimationFadeInOut);
        }
        if((category != null && category.isVisible()) || (favourite != null && favourite.isVisible()) || (addProductFragment != null && addProductFragment.isVisible()) || (profilePage != null && profilePage.isVisible())){
            bottomNavigationView.setSelectedItemId(R.id.home);
        }

        Products products = (Products) getSupportFragmentManager().findFragmentByTag(Products.class.getSimpleName());
        if(products!=null && products.isVisible()){
            Utils.removeShow(new CategoryFragment(),CategoryFragment.class.getSimpleName(),getSupportFragmentManager(),R.id.content);
            secondFragment=new CategoryFragment();
        }


        HolidayView holidayView = (HolidayView) getSupportFragmentManager().findFragmentByTag(HolidayView.class.getSimpleName());
        if(holidayView!=null && holidayView.isVisible()){
            Utils.removeShow(new ProfileRootFragment(),ProfileRootFragment.class.getSimpleName(),getSupportFragmentManager(),R.id.content);
            fifthFragment=new ProfileRootFragment();
        }


        ConstantPage constantPage = (ConstantPage) getSupportFragmentManager().findFragmentByTag(ConstantPage.class.getSimpleName());
        if(constantPage!=null && constantPage.isVisible()){
            Utils.removeShow(new ProfileRootFragment(),ProfileRootFragment.class.getSimpleName(),getSupportFragmentManager(),R.id.content);
            fifthFragment=new ProfileRootFragment();
        }

        Settings settings = (Settings) getSupportFragmentManager().findFragmentByTag(Settings.class.getSimpleName());
        if(settings!=null && settings.isVisible()){
            Utils.removeShow(new ProfileRootFragment(),ProfileRootFragment.class.getSimpleName(),getSupportFragmentManager(),R.id.content);
            fifthFragment=new ProfileRootFragment();
        }

    }
}
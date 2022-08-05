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
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.elishi.android.Api.APIClient;
import com.elishi.android.Api.ApiInterface;
import com.elishi.android.Common.Click;
import com.elishi.android.Common.Utils;
import com.elishi.android.Fragment.AddProductFragment;
import com.elishi.android.Fragment.CategoryFragment;
import com.elishi.android.Fragment.Constant.ConstantPage;
import com.elishi.android.Fragment.FavoritesFragment;
import com.elishi.android.Fragment.Holiday.HolidayView;
import com.elishi.android.Fragment.HomeFragment;
import com.elishi.android.Fragment.Product.Products;
import com.elishi.android.Fragment.Profile.EditProfile;
import com.elishi.android.Fragment.Profile.MyProfile;
import com.elishi.android.Fragment.Profile.ProfileRootFragment;
import com.elishi.android.Fragment.Settings.Settings;
import com.elishi.android.Modal.Home.BannerSlider;
import com.elishi.android.Modal.Home.NotifBody;
import com.elishi.android.Modal.Response.GBody;
import com.elishi.android.R;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.messaging.FirebaseMessaging;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private static MainActivity INSTANCE;
    private LinearProgressIndicator pagination;
    private String topic="topic";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.loadLocal(context);
        INSTANCE=this;
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "some_test_id");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "some_test_name");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "some_test_image");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        checkMode();

        fireBaseMessage();


        getWindow().setWindowAnimations(R.style.WindowAnimationFadeInOut);
        setContentView(R.layout.activity_main);
        initComponents();
        bottomNavSettings();

        notifToken();
        checkIntent();
        setListener();
    }

    private void setListener() {
        if(!Utils.getSharedPreference(context,"finishTour").equals("1")){
            homeTour();
        }
    }

    private void homeTour() {
        TapTargetView.showFor(this,                 // `this` is an Activity
                TapTarget.forView(bottomNavigationView.findViewById(R.id.home), context.getResources().getString(R.string.home_intro_title),
                        context.getResources().getString(R.string.home_message))
                        .outerCircleColor(R.color.second)      // Specify a color for the outer circle
                        .outerCircleAlpha(0.96f)            // Specify the alpha amount for the outer circle
                        .targetCircleColor(R.color.realWhite)   // Specify a color for the target circle
                        .titleTextSize(20)                  // Specify the size (in sp) of the title text
                        .titleTextColor(R.color.realWhite)      // Specify the color of the title text
                        .descriptionTextSize(10)            // Specify the size (in sp) of the description text
                        .descriptionTextColor(R.color.realWhite)  // Specify the color of the description text
                        .textColor(R.color.realWhite)            // Specify a color for both the title and description text
                        .textTypeface(Utils.getMediumFont(context))  // Specify a typeface for the text
                        .dimColor(R.color.realBlack)            // If set, will dim behind the view with 30% opacity of the given color
                        .drawShadow(true)                   // Whether to draw a drop shadow or not
                        .cancelable(false)                  // Whether tapping outside the outer circle dismisses the view
                        .tintTarget(true)                   // Whether to tint the target view's color
                        .transparentTarget(false)           // Specify whether the target is transparent (displays the content underneath)
                        .targetRadius(60),                  // Specify the target radius (in dp)
                new TapTargetView.Listener() {          // The listener can listen for regular clicks, long clicks or cancels
                    @Override
                    public void onTargetClick(TapTargetView view) {
                        super.onTargetClick(view);
                        categoryTour();
                    }
                });
    }

    private void categoryTour() {
        TapTargetView.showFor(this,                 // `this` is an Activity
                TapTarget.forView(bottomNavigationView.findViewById(R.id.category), context.getResources().getString(R.string.category),
                        context.getResources().getString(R.string.category_message))
                        .outerCircleColor(R.color.second)      // Specify a color for the outer circle
                        .outerCircleAlpha(0.96f)            // Specify the alpha amount for the outer circle
                        .targetCircleColor(R.color.realWhite)   // Specify a color for the target circle
                        .titleTextSize(20)                  // Specify the size (in sp) of the title text
                        .titleTextColor(R.color.realWhite)      // Specify the color of the title text
                        .descriptionTextSize(10)            // Specify the size (in sp) of the description text
                        .descriptionTextColor(R.color.realWhite)  // Specify the color of the description text
                        .textColor(R.color.realWhite)            // Specify a color for both the title and description text
                        .textTypeface(Utils.getMediumFont(context))  // Specify a typeface for the text
                        .dimColor(R.color.realBlack)            // If set, will dim behind the view with 30% opacity of the given color
                        .drawShadow(true)                   // Whether to draw a drop shadow or not
                        .cancelable(false)                  // Whether tapping outside the outer circle dismisses the view
                        .tintTarget(true)                   // Whether to tint the target view's color
                        .transparentTarget(false)           // Specify whether the target is transparent (displays the content underneath)
                        .targetRadius(60),                  // Specify the target radius (in dp)
                new TapTargetView.Listener() {          // The listener can listen for regular clicks, long clicks or cancels
                    @Override
                    public void onTargetClick(TapTargetView view) {
                        super.onTargetClick(view);
                        addProductTour();
                        bottomNavigationView.setSelectedItemId(R.id.category);
                    }
                });
    }

    private void addProductTour() {
        TapTargetView.showFor(this,                 // `this` is an Activity
                TapTarget.forView(bottomNavigationView.findViewById(R.id.add), context.getResources().getString(R.string.add_product),
                        context.getResources().getString(R.string.add_product_message))
                        .outerCircleColor(R.color.second)      // Specify a color for the outer circle
                        .outerCircleAlpha(0.96f)            // Specify the alpha amount for the outer circle
                        .targetCircleColor(R.color.realWhite)   // Specify a color for the target circle
                        .titleTextSize(20)                  // Specify the size (in sp) of the title text
                        .titleTextColor(R.color.realWhite)      // Specify the color of the title text
                        .descriptionTextSize(10)            // Specify the size (in sp) of the description text
                        .descriptionTextColor(R.color.realWhite)  // Specify the color of the description text
                        .textColor(R.color.realWhite)            // Specify a color for both the title and description text
                        .textTypeface(Utils.getMediumFont(context))  // Specify a typeface for the text
                        .dimColor(R.color.realBlack)            // If set, will dim behind the view with 30% opacity of the given color
                        .drawShadow(true)                   // Whether to draw a drop shadow or not
                        .cancelable(false)                  // Whether tapping outside the outer circle dismisses the view
                        .tintTarget(true)                   // Whether to tint the target view's color
                        .transparentTarget(false)           // Specify whether the target is transparent (displays the content underneath)
                        .targetRadius(60),                  // Specify the target radius (in dp)
                new TapTargetView.Listener() {          // The listener can listen for regular clicks, long clicks or cancels
                    @Override
                    public void onTargetClick(TapTargetView view) {
                        super.onTargetClick(view);
                        favouriteTour();
                        bottomNavigationView.setSelectedItemId(R.id.add);
                    }
                });
    }

    private void favouriteTour() {
        TapTargetView.showFor(this,                 // `this` is an Activity
                TapTarget.forView(bottomNavigationView.findViewById(R.id.fav), context.getResources().getString(R.string.favorites),
                        context.getResources().getString(R.string.favorites_message))
                        .outerCircleColor(R.color.second)      // Specify a color for the outer circle
                        .outerCircleAlpha(0.96f)            // Specify the alpha amount for the outer circle
                        .targetCircleColor(R.color.realWhite)   // Specify a color for the target circle
                        .titleTextSize(20)                  // Specify the size (in sp) of the title text
                        .titleTextColor(R.color.realWhite)      // Specify the color of the title text
                        .descriptionTextSize(10)            // Specify the size (in sp) of the description text
                        .descriptionTextColor(R.color.realWhite)  // Specify the color of the description text
                        .textColor(R.color.realWhite)            // Specify a color for both the title and description text
                        .textTypeface(Utils.getMediumFont(context))  // Specify a typeface for the text
                        .dimColor(R.color.realBlack)            // If set, will dim behind the view with 30% opacity of the given color
                        .drawShadow(true)                   // Whether to draw a drop shadow or not
                        .cancelable(false)                  // Whether tapping outside the outer circle dismisses the view
                        .tintTarget(true)                   // Whether to tint the target view's color
                        .transparentTarget(false)           // Specify whether the target is transparent (displays the content underneath)
                        .targetRadius(60),                  // Specify the target radius (in dp)
                new TapTargetView.Listener() {          // The listener can listen for regular clicks, long clicks or cancels
                    @Override
                    public void onTargetClick(TapTargetView view) {
                        super.onTargetClick(view);
                        profileTour();
                        bottomNavigationView.setSelectedItemId(R.id.fav);
                    }
                });
    }

    private void profileTour() {
        TapTargetView.showFor(this,                 // `this` is an Activity
                TapTarget.forView(bottomNavigationView.findViewById(R.id.profile), context.getResources().getString(R.string.profile),
                        context.getResources().getString(R.string.profile_message))
                        .outerCircleColor(R.color.second)      // Specify a color for the outer circle
                        .outerCircleAlpha(0.96f)            // Specify the alpha amount for the outer circle
                        .targetCircleColor(R.color.realWhite)   // Specify a color for the target circle
                        .titleTextSize(20)                  // Specify the size (in sp) of the title text
                        .titleTextColor(R.color.realWhite)      // Specify the color of the title text
                        .descriptionTextSize(10)            // Specify the size (in sp) of the description text
                        .descriptionTextColor(R.color.realWhite)  // Specify the color of the description text
                        .textColor(R.color.realWhite)            // Specify a color for both the title and description text
                        .textTypeface(Utils.getMediumFont(context))  // Specify a typeface for the text
                        .dimColor(R.color.realBlack)            // If set, will dim behind the view with 30% opacity of the given color
                        .drawShadow(true)                   // Whether to draw a drop shadow or not
                        .cancelable(false)                  // Whether tapping outside the outer circle dismisses the view
                        .tintTarget(true)                   // Whether to tint the target view's color
                        .transparentTarget(false)           // Specify whether the target is transparent (displays the content underneath)
                        .targetRadius(60),                  // Specify the target radius (in dp)
                new TapTargetView.Listener() {          // The listener can listen for regular clicks, long clicks or cancels
                    @Override
                    public void onTargetClick(TapTargetView view) {
                        super.onTargetClick(view);
                        Utils.setPreference("finishTour","1",context);
                        bottomNavigationView.setSelectedItemId(R.id.profile);
                    }
                });
    }

    private void notifToken() {
        if(!Utils.getSharedPreference(context,"tkn").isEmpty()){
            NotifBody notifBody=new NotifBody(Utils.getSharedPreference(context,"notif_token"));
            ApiInterface apiInterface= APIClient.getClient().create(ApiInterface.class);
            Call<GBody<ResponseBody>> call=apiInterface.updateNotificationToken("Bearer "+Utils.getSharedPreference(context,"tkn"),notifBody);
            call.enqueue(new Callback<GBody<ResponseBody>>() {
                @Override
                public void onResponse(Call<GBody<ResponseBody>> call, Response<GBody<ResponseBody>> response) {

                }

                @Override
                public void onFailure(Call<GBody<ResponseBody>> call, Throwable t) {

                }
            });
        }
    }

    private void fireBaseMessage() {
        try {
            if (!Utils.getSharedPreference(context, "tkn").isEmpty()) {
                int user_id = Integer.parseInt(Utils.getSharedPreference(context, "userId"));
                int sum = (user_id / 1000) + 1;
                topic = "topic" + sum;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        FirebaseMessaging.getInstance().subscribeToTopic(topic).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.e("Success ", topic + "");
            }
        });
    }

    private void checkIntent() {
        String url = getIntent().getStringExtra("url");
        if(url!=null && !url.trim().isEmpty()){
            try {
                BannerSlider bannerSlider=new BannerSlider(0,"","","",
                        0,0,url,"","",0);
                Click.bannerClick(bannerSlider,context);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
        }

    }



    public static MainActivity get(){
        return INSTANCE;
    }

    public BottomNavigationView getBottomNavigationView(){
        return bottomNavigationView;
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


    private void initComponents() {
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        pagination = findViewById(R.id.pagination);
    }

    public LinearProgressIndicator getPagination(){
        return pagination;
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
                if(indexBottomNav==R.id.profile && isChangeTheme){
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
            HomeFragment.isFirst=true;
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

        EditProfile editProfile = (EditProfile) getSupportFragmentManager().findFragmentByTag(EditProfile.class.getSimpleName());
        if(editProfile!=null && editProfile.isVisible()){
            Utils.removeShow(new ProfileRootFragment(),ProfileRootFragment.class.getSimpleName(),getSupportFragmentManager(),R.id.content);
            fifthFragment=new ProfileRootFragment();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.loadLocal(context);
    }
}
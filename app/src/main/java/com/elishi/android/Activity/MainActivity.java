package com.elishi.android.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.elishi.android.Fragment.AddProductFragment;
import com.elishi.android.Fragment.CategoryFragment;
import com.elishi.android.Fragment.FavoritesFragment;
import com.elishi.android.Fragment.HomeFragment;
import com.elishi.android.Fragment.Profile.ProfileRootFragment;
import com.elishi.android.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private Context context = this;
    public static Fragment firstFragment = new HomeFragment();
    public static Fragment secondFragment = new CategoryFragment();
    public static Fragment thirdFragment = new AddProductFragment();
    public static Fragment fourthFragment = new FavoritesFragment();
    public static Fragment fifthFragment = new ProfileRootFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                switch (item.getItemId()) {
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
                return true;
            }
        });
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


}
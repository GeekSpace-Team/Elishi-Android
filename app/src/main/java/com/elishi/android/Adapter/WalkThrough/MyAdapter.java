package com.elishi.android.Adapter.WalkThrough;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;


import com.elishi.android.Fragment.WalkThrough.FirstPage;
import com.elishi.android.Fragment.WalkThrough.SecondPage;
import com.elishi.android.Fragment.WalkThrough.ThirdPage;

import org.jetbrains.annotations.NotNull;

public class MyAdapter extends FragmentStateAdapter {


    public MyAdapter(@NonNull @NotNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @NotNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                FirstPage firstPage = new FirstPage();
                return firstPage;
            case 1:
                SecondPage secondPage = new SecondPage();
                return secondPage;
            case 2:
                ThirdPage thirdPage = new ThirdPage();
                return thirdPage;
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}

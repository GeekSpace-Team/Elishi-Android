package com.elishi.android.Fragment.Profile;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.elishi.android.Adapter.Profile.TabAdapter;
import com.elishi.android.Common.Utils;
import com.elishi.android.R;
import com.google.android.material.tabs.TabLayout;


public class RegistRoot extends Fragment {

    private View view;
    private Context context;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private View tabView;
    private RelativeLayout bg;
    private TextView tv,splashTitle2;
    public static RegistRoot INSTANCE;

    public RegistRoot() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_regist_root, container, false);
        context = getContext();
        INSTANCE=this;
        initComponents();
        setTabs();
        setFonts();
        setFontsToTabs();
        return view;
    }

    public static RegistRoot get(){
        return INSTANCE;
    }

    public TabLayout getTabLayout(){
        return tabLayout;
    }

    public ViewPager getViewPager(){
        return viewPager;
    }

    private void setFontsToTabs() {
        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface(Utils.getMediumFont(context));
                }
            }
        }
    }

    private void setFonts() {
        splashTitle2.setTypeface(Utils.getMediumFont(context));
    }

    private void setTabs() {
        tabView = LayoutInflater.from(context).inflate(R.layout.tab, null);
        bg=tabView.findViewById(R.id.bg);
        tv=tabView.findViewById(R.id.tv);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setActiveTab(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                setPassiveTab(tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        tabLayout.setupWithViewPager(viewPager, false);
        TabAdapter adapter = new TabAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFragment(new Login(), context.getResources().getString(R.string.login));
        adapter.addFragment(new CreateAccountRoot(), context.getResources().getString(R.string.create_account));
        viewPager.setAdapter(adapter);
    }

    private void setPassiveTab(int i) {
        View tabView = tabLayout.getTabAt(i).view;
        tabView.setBackgroundColor(context.getResources().getColor(R.color.first));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tabView.setElevation(0f);
        }
    }

    private void setActiveTab(int i) {
        View tabView = tabLayout.getTabAt(i).view;
        tabView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.tab_indicator));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tabView.setElevation(12f);
        }
    }

    private void initComponents() {
        tabLayout = view.findViewById(R.id.tab_layout);
        viewPager = view.findViewById(R.id.pager);
        splashTitle2 = view.findViewById(R.id.splashTitle2);
    }


}
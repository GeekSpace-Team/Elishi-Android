package com.elishi.android.Fragment.Profile;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.elishi.android.Adapter.Profile.TabAdapter;
import com.elishi.android.Common.Utils;
import com.elishi.android.Fragment.CategoryFragment;
import com.elishi.android.Fragment.HomeFragment;
import com.elishi.android.Listener.AppBarStateChangeListener;
import com.elishi.android.R;
import com.elishi.android.databinding.FragmentMyProfileBinding;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;


public class MyProfile extends Fragment {

    private View view;
    private Context context;
    private FragmentMyProfileBinding binding;
    private boolean isImageLoaded=false;
    private String profileImageUrl="https://images.pexels.com/photos/1704488/pexels-photo-1704488.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500";
    private FragmentManager fragmentManager;
    public MyProfile() {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentMyProfileBinding.inflate(inflater,container,false);
        view=binding.getRoot();
        context=getContext();
        setListener();
        setTabs();
        setImage(profileImageUrl);
        setColors();
        setFonts();
        return view;
    }

    private void setFonts() {
        binding.username.setTypeface(Utils.getRegularFont(context));
        binding.location.setTypeface(Utils.getRegularFont(context));
        binding.phoneNumber.setTypeface(Utils.getRegularFont(context));
        binding.title.setTypeface(Utils.getBoldFont(context));
    }

    private void setColors() {
        if(!profileImageUrl.trim().isEmpty()) {
            if(isImageLoaded)
                setColorToolbar(R.color.white);
            else
                setColorToolbar(R.color.fourth);
        } else{
            setColorToolbar(R.color.fourth);
        }
    }

    private void setImage(String profileImageUrl) {

        Glide.with(context)
                .load(profileImageUrl)
                .placeholder(R.drawable.image_round_bg)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {

                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        if(!profileImageUrl.trim().isEmpty()){
                            BitmapDrawable drawable = (BitmapDrawable) resource;
                            Bitmap bitmap = drawable.getBitmap();
                            Bitmap blurred = Utils.blurRenderScript(bitmap, 25,context);//second parametre is radius
                            binding.blurImage.setImageBitmap(blurred);
                            setColorToolbar(R.color.white);
                        } else{
                            setColorToolbar(R.color.fourth);
                        }
                        isImageLoaded=true;
                        return true;
                    }
                }).into(binding.blurImage);

        Glide.with(context)
                .load(profileImageUrl)
                .placeholder(R.drawable.ic_profile_image_placeholder)
                .into(binding.profileImage);
    }

    private void setTabs() {
        binding.tabLayout1.setupWithViewPager(binding.viewPager, false);
        binding.tabLayout2.setupWithViewPager(binding.viewPager, false);
        TabAdapter adapter = new TabAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFragment(new MyProducts(), "");
        adapter.addFragment(new MyHolidays(), "");
        binding.viewPager.setAdapter(adapter);

        int colorInt = getResources().getColor(R.color.second);
        ColorStateList csl = ColorStateList.valueOf(colorInt);
        Drawable drawable=tintDrawable(context,R.drawable.ic_my_products,csl);

        binding.tabLayout1.getTabAt(0).setIcon(drawable);
        binding.tabLayout1.getTabAt(1).setIcon(R.drawable.ic_my_holidays);
        binding.tabLayout2.getTabAt(0).setIcon(drawable);
        binding.tabLayout2.getTabAt(1).setIcon(R.drawable.ic_my_holidays);

        setTabsColor(0,1);
    }

    private void setListener() {
        binding.appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if(state==State.COLLAPSED){
                    binding.tabLayout1.setVisibility(View.VISIBLE);
                    binding.tabLayout2.setVisibility(View.GONE);
                    setColorToolbar(R.color.fourth);
                } else if(state==State.EXPANDED){
                    binding.tabLayout1.setVisibility(View.GONE);
                    binding.tabLayout2.setVisibility(View.VISIBLE);
                    setColors();
                }
            }
        });

        binding.menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheetDialog();
            }
        });


        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int unSelected=1;
                if(position==1){
                    unSelected=0;
                }
                setTabsColor(position,unSelected);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void showBottomSheetDialog() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(R.layout.menu_dialog);
        TextView settingsTV=bottomSheetDialog.findViewById(R.id.settingsTV);
        TextView aboutTV=bottomSheetDialog.findViewById(R.id.aboutTV);
        TextView helpTV=bottomSheetDialog.findViewById(R.id.helpTV);
        TextView editTV=bottomSheetDialog.findViewById(R.id.editTV);
        TextView logoutTV=bottomSheetDialog.findViewById(R.id.logoutTV);
        settingsTV.setTypeface(Utils.getRegularFont(context));
        aboutTV.setTypeface(Utils.getRegularFont(context));
        helpTV.setTypeface(Utils.getRegularFont(context));
        editTV.setTypeface(Utils.getRegularFont(context));
        logoutTV.setTypeface(Utils.getRegularFont(context));
        bottomSheetDialog.show();
    }

    private void setTabsColor(int position, int unSelected) {
        int tabIconColor = ContextCompat.getColor(context, R.color.second);
        binding.tabLayout1.getTabAt(position).getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);

        tabIconColor = ContextCompat.getColor(context, R.color.text_color);
        binding.tabLayout1.getTabAt(unSelected).getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);

        tabIconColor = ContextCompat.getColor(context, R.color.second);
        binding.tabLayout2.getTabAt(position).getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);

        tabIconColor = ContextCompat.getColor(context, R.color.text_color);
        binding.tabLayout2.getTabAt(unSelected).getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
    }

    private void setColorToolbar(int color){
        binding.title.setTextColor(context.getResources().getColor(color));
        binding.username.setTextColor(context.getResources().getColor(color));
        binding.location.setTextColor(context.getResources().getColor(color));
        binding.phoneNumber.setTextColor(context.getResources().getColor(color));
        binding.menuIcon.setColorFilter(ContextCompat.getColor(context, color), android.graphics.PorterDuff.Mode.SRC_IN);
    }

    private Drawable tintDrawable(Context context, @DrawableRes int resId, ColorStateList stateList) {
        Drawable drawable = DrawableCompat.wrap(ContextCompat.getDrawable(context, resId)).mutate();
        DrawableCompat.setTintList(drawable, stateList);
        DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_IN);
        return drawable;
    }
}
package com.elishi.android.Activity.Product;

import static com.elishi.android.Common.Constant.BANNER_DELAY;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.transition.Fade;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.elishi.android.Adapter.Home.BannerSliderAdapter;
import com.elishi.android.Adapter.Product.MultiSizeProductAdapter;
import com.elishi.android.Adapter.Product.ProductImage;
import com.elishi.android.Listener.AppBarStateChangeListener;
import com.elishi.android.Modal.Home.BannerSlider;
import com.elishi.android.Modal.Product.Product;
import com.elishi.android.R;
import com.elishi.android.databinding.ActivityProductPageBinding;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.hannesdorfmann.swipeback.Position;
import com.hannesdorfmann.swipeback.SwipeBack;
import com.hannesdorfmann.swipeback.transformer.SlideSwipeBackTransformer;
import com.zhpan.indicator.IndicatorView;
import com.zhpan.indicator.enums.IndicatorSlideMode;
import com.zhpan.indicator.enums.IndicatorStyle;
import com.zhpan.indicator.option.IndicatorOptions;

import java.util.ArrayList;
import java.util.List;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

public class ProductPage extends AppCompatActivity {
    private ActivityProductPageBinding binding;
    private Context context = this;
    private ArrayList<String> smallImages = new ArrayList<>();
    private ArrayList<String> largeImages = new ArrayList<>();
    private int mPagerPosition;
    private int mPagerPosition2;
    private int mPagerOffsetPixels;
    private ArrayList<Product> alsoProducts = new ArrayList<>();
    private RecyclerView alsoProductRec;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductPageBinding.inflate(getLayoutInflater());
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(binding.getRoot());
        SwipeBack.attach(this, Position.LEFT)
                .setDrawOverlay(false)
                .setDividerEnabled(false)
                .setDividerAsSolidColor(Color.WHITE)
                .setDividerSize(2)
                .setSwipeBackTransformer(new SlideSwipeBackTransformer())
                .setContentView(binding.getRoot())
                .setSwipeBackView(R.layout.back_view)
                .setOnInterceptMoveEventListener(
                        new SwipeBack.OnInterceptMoveEventListener() {
                            @Override
                            public boolean isViewDraggable(View v, int dx,
                                                           int x, int y) {
                                if (v == binding.bannerSlider || v==alsoProductRec) {
                                    return !(mPagerPosition == 0 && mPagerOffsetPixels == 0) || dx < 0;
                                }

                                return false;
                            }
                        });
        setBanner();
        setListener();
        setViews();

    }






    private void setViews() {
    }

    private void setListener() {
        binding.appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.COLLAPSED) {
                    setColorToolbar(R.color.fourth);
                } else if (state == State.EXPANDED) {
                    setColorToolbar(R.color.white);
                }
            }
        });

    }

    private void setColorToolbar(int color) {
        binding.back.setColorFilter(ContextCompat.getColor(context, color), android.graphics.PorterDuff.Mode.SRC_IN);
    }


    private void setBanner() {
        smallImages.add("https://i.etsystatic.com/19311499/r/il/b4be61/1839533908/il_1588xN.1839533908_lgk6.jpg");
        smallImages.add("https://i.etsystatic.com/19311499/r/il/99ac24/2220891021/il_794xN.2220891021_i8vz.jpg");
        smallImages.add("https://i.etsystatic.com/19311499/r/il/2ceeb6/2565076494/il_794xN.2565076494_7yrp.jpg");
        smallImages.add("https://i.etsystatic.com/19311499/r/il/db8640/3065683283/il_794xN.3065683283_96o0.jpg");
        smallImages.add("https://i.etsystatic.com/19311499/r/il/d16cf9/3042568784/il_794xN.3042568784_ot2m.jpg");
        smallImages.add("https://i.etsystatic.com/19311499/r/il/30f724/2565081142/il_794xN.2565081142_f0ah.jpg");
        smallImages.add("https://i.etsystatic.com/19311499/r/il/cff66d/2377425229/il_794xN.2377425229_bway.jpg");
        setIndicators();
        binding.bannerSlider.setAdapter(new ProductImage(smallImages, context));
        binding.bannerSlider.setClipToPadding(false);
        binding.bannerSlider.setClipChildren(false);
        binding.bannerSlider.setOffscreenPageLimit(3);
        binding.bannerSlider.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });
        binding.bannerSlider.setPageTransformer(compositePageTransformer);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            binding.bannerSlider.setNestedScrollingEnabled(false);
        }
        binding.bannerSlider.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                binding.indicatorView.onPageSelected(position);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                binding.indicatorView.onPageScrolled(position, positionOffset, positionOffsetPixels);
                mPagerPosition = position;
                mPagerOffsetPixels = positionOffsetPixels;
            }
        });

    }

    private void setIndicators() {
        IndicatorOptions options = new IndicatorOptions();
        options.setSliderColor(context.getResources().getColor(R.color.third), context.getResources().getColor(R.color.second));
        options.setSliderHeight(context.getResources().getDimension(R.dimen.sliderCircle));
        options.setSliderWidth(context.getResources().getDimension(R.dimen.sliderCircle), context.getResources().getDimension(R.dimen.sliderRound));
        options.setSlideMode(IndicatorSlideMode.SCALE);
        options.setIndicatorStyle(IndicatorStyle.ROUND_RECT);
        options.setPageSize(smallImages.size());
        binding.indicatorView.setIndicatorOptions(options);
        binding.indicatorView.notifyDataChanged();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
    }
}
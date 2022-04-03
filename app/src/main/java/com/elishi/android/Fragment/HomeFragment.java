package com.elishi.android.Fragment;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.transition.Fade;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.L;
import com.bumptech.glide.Glide;
import com.elishi.android.Activity.Search.SearchPage;
import com.elishi.android.Adapter.Home.BannerSliderAdapter;
import com.elishi.android.Adapter.Home.EventAdapter;
import com.elishi.android.Adapter.Product.MultiSizeProductAdapter;
import com.elishi.android.Adapter.Category.ShortCategoryAdapter;
import com.elishi.android.Adapter.Home.VipAdapter;
import com.elishi.android.Api.APIClient;
import com.elishi.android.Api.ApiInterface;
import com.elishi.android.Common.AppSnackBar;
import com.elishi.android.Common.Constant;
import com.elishi.android.Common.EventType;
import com.elishi.android.Common.PlaceHolderColors;
import com.elishi.android.Common.Utils;
import com.elishi.android.Modal.Home.Ads;
import com.elishi.android.Modal.Home.BannerSlider;
import com.elishi.android.Modal.Category.Category;
import com.elishi.android.Modal.Home.Event;
import com.elishi.android.Modal.Home.EventProducts;
import com.elishi.android.Modal.Home.EventProductsBody;
import com.elishi.android.Modal.Home.Events;
import com.elishi.android.Modal.Home.GetHome;
import com.elishi.android.Modal.Product.Product;
import com.elishi.android.Modal.Profile.User;
import com.elishi.android.Modal.Response.GBody;
import com.elishi.android.R;
import com.elishi.android.databinding.FragmentHomeBinding;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.zhpan.indicator.IndicatorView;
import com.zhpan.indicator.enums.IndicatorSlideMode;
import com.zhpan.indicator.enums.IndicatorStyle;
import com.zhpan.indicator.option.IndicatorOptions;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.elishi.android.Common.Constant.BANNER_DELAY;

public class HomeFragment extends Fragment {

    private View view;
    private Context context;
    private NestedScrollView scroll;
    private ViewPager2 viewPager2;
    private Handler sliderHandler = new Handler();
    private List<BannerSlider> sliderItems = new ArrayList<>();
    private Button oldActiveIndicator;
    private Integer firstSize = 0;
    private RecyclerView shortCategory, new_products, vipUsers, trend_products;
    private ArrayList<Category> shortCategories = new ArrayList<>();
    private ArrayList<Product> newProducts = new ArrayList<>();
    private ArrayList<Product> trendProducts = new ArrayList<>();
    private ArrayList<User> vipUsersList = new ArrayList<>();
    private TextView newProductsTitle, trendProductsTitle;
    private FragmentHomeBinding binding;
    private IndicatorView indicator_view;
    private ArrayList<Ads> miniAds = new ArrayList<>();
    private ArrayList<Ads> largeAds = new ArrayList<>();
    private Integer miniAdsPos = 0;
    private ArrayList<EventProducts> eventProducts = new ArrayList<>();
    private int adsPos = 0;
    private int eventPos = 0;
    private int eventProductsPos = 0;
    private ArrayList<Events> events = new ArrayList<>();
    private Boolean isEvent = true;
    private GBody<GetHome> body;

    public HomeFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        view = binding.getRoot();
//        view = inflater.inflate(R.layout.fragment_home, container, false);

        context = getContext();

        initComponents();
        setListener();
        setFonts();
        getHome();

        return view;
    }

    private void getHome() {
        isEvent = true;
        hideError();
        binding.loading.setVisibility(View.VISIBLE);
        view.findViewById(R.id.noInternetContainer).setVisibility(View.GONE);
        binding.secondCon.setVisibility(View.GONE);
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<GBody<GetHome>> call = apiInterface.getHome("android", "Bearer " + Utils.getSharedPreference(context, "tkn"));
        call.enqueue(new Callback<GBody<GetHome>>() {
            @Override
            public void onResponse(Call<GBody<GetHome>> call, Response<GBody<GetHome>> response) {
                if (response.isSuccessful() && !response.body().getError() && response.body().getBody() != null) {
                    binding.swipeRefresh.setRefreshing(false);
                    hideError();
                    body = response.body();
                    if (body.getBody().getBanner() != null && body.getBody().getBanner().size() > 0) {
                        sliderItems = body.getBody().getBanner();
                        binding.bannerSlider.setVisibility(View.VISIBLE);
                    } else {
                        binding.bannerSlider.setVisibility(View.GONE);
                    }
                    if (body.getBody().getMain_category() != null && body.getBody().getMain_category().size() > 0) {
                        shortCategories = body.getBody().getMain_category();
                        binding.shortCategory.setVisibility(View.VISIBLE);
                    } else {
                        binding.shortCategory.setVisibility(View.GONE);
                    }
                    if (body.getBody().getNewProducts() != null && body.getBody().getNewProducts().size() > 0) {
                        newProducts = body.getBody().getNewProducts();
                        binding.newProducts.setVisibility(View.VISIBLE);
                        binding.newProductsTitle.setVisibility(View.VISIBLE);
                    } else {
                        binding.newProducts.setVisibility(View.GONE);
                        binding.newProductsTitle.setVisibility(View.GONE);
                    }
                    if (body.getBody().getTrendProducts() != null && body.getBody().getTrendProducts().size() > 0) {
                        trendProducts = body.getBody().getTrendProducts();
                        binding.trendProducts.setVisibility(View.VISIBLE);
                        binding.trendProductsTitle.setVisibility(View.VISIBLE);
                    } else {
                        binding.trendProducts.setVisibility(View.GONE);
                        binding.trendProductsTitle.setVisibility(View.GONE);
                    }
//                    if (body.getBody().getVip_users() != null && body.getBody().getVip_users().size() > 0) {
//                        vipUsersList = body.getBody().getVip_users();
//                        binding.vipUsers.setVisibility(View.VISIBLE);
//                    } else {
//                        binding.vipUsers.setVisibility(View.GONE);
//                    }
                    eventProducts.clear();
                    if (body.getBody().getEventProducts() != null && body.getBody().getEventProducts().size() > 0) {
                        eventProducts.addAll(body.getBody().getEventProducts());
                    }
                    miniAds.clear();
                    largeAds.clear();
                    if (body.getBody().getAds() != null && body.getBody().getAds().size() > 0) {
                        for (Ads ads : body.getBody().getAds()) {
                            if (ads.getStatus().equals("home_mini"))
                                miniAds.add(ads);
                            if (ads.getStatus().equals("home_large")) {
                                largeAds.add(ads);
                            }
                        }
                        binding.miniAds.setVisibility(View.VISIBLE);
                    } else {
                        binding.miniAds.setVisibility(View.GONE);
                    }

                    events.clear();
                    eventPos = 0;
                    eventProductsPos = 0;
                    adsPos = 0;
                    ready();
                } else if (response.body().getError()) {
                    String msg = Utils.checkMessage(context, response.body().getMessage());
                    showSnackbar(msg);
                    showError();
                } else {
                    String msg = Utils.checkMessage(context, response.body().getMessage());
                    showSnackbar(msg);
                    showError();
                }

            }

            @Override
            public void onFailure(Call<GBody<GetHome>> call, Throwable t) {
                showError();
                binding.swipeRefresh.setRefreshing(false);
            }
        });

    }


    private void showSnackbar(String msg) {
        if (!msg.isEmpty()) {
            AppSnackBar snackBar = new AppSnackBar(context, view);
            snackBar.setTitle(msg);
            snackBar.actionText(R.string.cancel);
            snackBar.show();
        }
    }

    private void showError() {
        view.findViewById(R.id.noInternetContainer).setVisibility(View.VISIBLE);
        binding.secondCon.setVisibility(View.GONE);
        binding.loading.setVisibility(View.GONE);
    }

    private void hideError() {
        view.findViewById(R.id.noInternetContainer).setVisibility(View.GONE);
        binding.secondCon.setVisibility(View.VISIBLE);
        binding.loading.setVisibility(View.GONE);
        binding.swipeRefresh.setRefreshing(false);
    }

    private void ready() {
        setShortCategory();
        setNewProducts();
        setVipUsers();
        setTrendProducts();
        setBanner();
        if (miniAds.size() <= 0) {
            binding.miniAds.setVisibility(View.GONE);
        } else {
            setMiniAds();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                binding.miniAds.setClipToOutline(true);
            }
        }

        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                int pos = 0;
                for (Event event : body.getBody().getEvents()) {
                    events.add(new Events(event, EventType.EVENT));
                }

                if (events.size() > 0) {
                    for (int i = 0; i < events.size(); i++) {
                        if(i%2==0 && i>0 && eventProductsPos<eventProducts.size()){
                            events.add(i,new Events(eventProducts.get(eventProductsPos), EventType.EVENT_PRODUCTS));
                            eventProductsPos++;
                        }
                    }
                    if(eventProductsPos<eventProducts.size()-1){
                        for(int k=eventProductsPos;k<eventProducts.size();k++){
                            events.add(new Events(eventProducts.get(k), EventType.EVENT_PRODUCTS));
                        }
                    }
                } else {
                    for (EventProducts eventProducts1 : eventProducts) {
                        events.add(new Events(eventProducts1, EventType.EVENT_PRODUCTS));
                    }
                }

                if(events.size()>0){
                    for (int i = 0; i < events.size(); i++) {
                        if(pos==8 && i>0 && adsPos<largeAds.size()){
                            events.add(i,new Events(largeAds.get(adsPos), EventType.ADS));
                            adsPos++;
                            pos=0;
                        }
                        pos++;
                    }
                    if(adsPos<largeAds.size()-1){
                        for(int k=adsPos;k<largeAds.size();k++){
                            events.add(new Events(largeAds.get(k), EventType.ADS));
                        }
                    }
                } else {
                    for(Ads ads:largeAds){
                        events.add(new Events(ads,EventType.ADS));
                    }
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.events.setAdapter(new EventAdapter(events, context));
                        binding.events.setLayoutManager(new LinearLayoutManager(context));
//                        binding.events.setNestedScrollingEnabled(false);
                        binding.events.setOverScrollMode(View.OVER_SCROLL_NEVER);
                    }
                });
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                thread.start();
            }
        },1200);





    }



    private void setListener() {
        view.findViewById(R.id.retry).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getHome();
            }
        });
        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getHome();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setVipUsers() {
        VipAdapter vipAdapter = new VipAdapter(vipUsersList, context);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
        vipUsers.setAdapter(vipAdapter);
        vipUsers.setLayoutManager(linearLayoutManager);
    }

    private void setNewProducts() {
        MultiSizeProductAdapter productAdapter = new MultiSizeProductAdapter(newProducts, context, true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
        new_products.setAdapter(productAdapter);
        new_products.setLayoutManager(linearLayoutManager);
    }

    private void setTrendProducts() {
        MultiSizeProductAdapter productAdapter = new MultiSizeProductAdapter(trendProducts, context, true);
        trend_products.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        trend_products.setAdapter(productAdapter);
    }

    private void setShortCategory() {
        ShortCategoryAdapter shortCategoryAdapter = new ShortCategoryAdapter(shortCategories, context);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
        shortCategory.setAdapter(shortCategoryAdapter);
        shortCategory.setLayoutManager(layoutManager);
    }


    private void initComponents() {
        scroll = view.findViewById(R.id.scroll);
        viewPager2 = view.findViewById(R.id.bannerSlider);
        indicator_view = view.findViewById(R.id.indicator_view);
        shortCategory = view.findViewById(R.id.shortCategory);
        new_products = view.findViewById(R.id.new_products);
        newProductsTitle = view.findViewById(R.id.newProductsTitle);
        vipUsers = view.findViewById(R.id.vipUsers);
        trendProductsTitle = view.findViewById(R.id.trendProductsTitle);
        trend_products = view.findViewById(R.id.trend_products);
    }

    private void setFonts() {
        newProductsTitle.setTypeface(Utils.getMediumFont(context));
        trendProductsTitle.setTypeface(Utils.getMediumFont(context));
        binding.searchEdit.setTypeface(Utils.getRegularFont(context));
    }


    private GridLayoutManager getLayoutManager() {
        GridLayoutManager glm = new GridLayoutManager(context, 2);
        glm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if ((position + 1) % 5 == 0) {
                    return 2;
                }
                return 1;
            }
        });
        return glm;
    }


    private void setBanner() {
        setIndicators();
        viewPager2.setAdapter(new BannerSliderAdapter(sliderItems, viewPager2, context));
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });
        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, BANNER_DELAY); // slide duration 2 seconds
                indicator_view.onPageSelected(sliderItems.get(position).getPosition());
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                indicator_view.onPageScrolled(sliderItems.get(position).getPosition(), positionOffset, positionOffsetPixels);
            }
        });

    }

    private void setIndicators() {
        int pos = 0;
        for (BannerSlider slider : sliderItems) {
            slider.setPosition(pos);
            sliderItems.set(pos, slider);
            pos++;
        }
        IndicatorOptions options = new IndicatorOptions();
        options.setSliderColor(context.getResources().getColor(R.color.third), context.getResources().getColor(R.color.second));
        options.setSliderHeight(context.getResources().getDimension(R.dimen.sliderCircle));
        options.setSliderWidth(context.getResources().getDimension(R.dimen.sliderCircle), context.getResources().getDimension(R.dimen.sliderRound));
        options.setSlideMode(IndicatorSlideMode.SCALE);
        options.setIndicatorStyle(IndicatorStyle.ROUND_RECT);
        options.setPageSize(sliderItems.size());
        indicator_view.setIndicatorOptions(options);
        indicator_view.notifyDataChanged();
    }

    private LinearLayout.LayoutParams getActiveIndicatorParams() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(30, 0, 0, 0);
        params.width = 40;
        params.height = 15;
        return params;
    }

    private LinearLayout.LayoutParams getInActiveIndicatorParams() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(30, 0, 0, 0);
        params.width = 15;
        params.height = 15;
        return params;
    }

    private void setViewPagerPosition(int position) {
        viewPager2.setCurrentItem(position, true);
    }


    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
            setMiniAds();

        }
    };

    private void setMiniAds() {
        if (miniAds.size() > 0 && miniAdsPos < miniAds.size()) {
            final int min = 0;
            final int max = PlaceHolderColors.PLACEHOLDERS.length - 1;
            final int r = new Random().nextInt((max - min) + 1) + min;
            String imageUrl = miniAds.get(miniAdsPos).getAds_image();
            String extension = "";
            if (imageUrl.contains(".")) {
                extension = imageUrl.substring(imageUrl.lastIndexOf("."));
            }
            if (extension.toLowerCase().contains("gif")) {
                Glide.with(context)
                        .asGif()
                        .load(Constant.IMAGE_URL + imageUrl)
                        .placeholder(PlaceHolderColors.PLACEHOLDERS[r])
                        .thumbnail(0.25f)
                        .into(binding.miniAds);
            } else {
                Glide.with(context)
                        .load(Constant.IMAGE_URL + imageUrl)
                        .placeholder(PlaceHolderColors.PLACEHOLDERS[r])
                        .thumbnail(0.25f)
                        .into(binding.miniAds);
            }
            miniAdsPos++;
        } else {
            miniAdsPos=0;
            setMiniAds();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        sliderHandler.postDelayed(sliderRunnable, BANNER_DELAY);
    }

    @Override
    public void onStop() {
        super.onStop();
        viewPager2.setCurrentItem(0);
    }
}
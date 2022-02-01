package com.elishi.android.Fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.elishi.android.Adapter.Home.BannerSliderAdapter;
import com.elishi.android.Adapter.Product.MultiSizeProductAdapter;
import com.elishi.android.Adapter.Product.ProductAdapter;
import com.elishi.android.Adapter.Category.ShortCategoryAdapter;
import com.elishi.android.Adapter.Home.VipAdapter;
import com.elishi.android.Common.Utils;
import com.elishi.android.Modal.Home.BannerSlider;
import com.elishi.android.Modal.Category.Category;
import com.elishi.android.Modal.Product.Product;
import com.elishi.android.Modal.Profile.User;
import com.elishi.android.R;
import com.elishi.android.databinding.FragmentHomeBinding;
import com.zhpan.indicator.IndicatorView;
import com.zhpan.indicator.enums.IndicatorSlideMode;
import com.zhpan.indicator.enums.IndicatorStyle;
import com.zhpan.indicator.option.IndicatorOptions;

import java.util.ArrayList;
import java.util.List;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

import static com.elishi.android.Common.Constant.BANNER_DELAY;

public class HomeFragment extends Fragment {

    private View view;
    private Context context;
    private TextView  splashTitle2;
    private ScrollView scroll;
    private ViewPager2 viewPager2;
    private Handler sliderHandler = new Handler();
    private List<BannerSlider> sliderItems=new ArrayList<>();
    private Button oldActiveIndicator;
    private Integer firstSize=0;
    private RecyclerView shortCategory,new_products,vipUsers,trend_products;
    private ArrayList<Category> shortCategories=new ArrayList<>();
    private ArrayList<Product> newProducts=new ArrayList<>();
    private ArrayList<Product> trendProducts=new ArrayList<>();
    private ArrayList<User> vipUsersList=new ArrayList<>();
    private TextView newProductsTitle,trendProductsTitle,seeAll;
    private FragmentHomeBinding binding;
    private IndicatorView indicator_view;
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
        setRecycler();
        setFonts();
        setShortCategory();
        request();
        setNewProducts();
        setVipUsers();
        setTrendProducts();
        setBanner();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }

    private void setVipUsers() {
        vipUsersList.clear();
        vipUsersList.add(new User(1,"Halil","https://i.pinimg.com/originals/c5/ad/78/c5ad786533975271c16c365c87d7e7a5.jpg","vip"));
        vipUsersList.add(new User(1,"Jeyhun","https://uifaces.co/our-content/donated/n4Ngwvi7.jpg","vip"));
        vipUsersList.add(new User(1,"Jelil","https://www.ilounge.com/wp-content/uploads/2020/09/eYVoSxV5.jpg","vip"));
        vipUsersList.add(new User(1,"Tylla","https://images.pexels.com/photos/415829/pexels-photo-415829.jpeg?crop=faces&fit=crop&h=200&w=200&auto=compress&cs=tinysrgb","vip"));
        vipUsersList.add(new User(1,"Annadurdy","https://media.carnegie.org/filer_public_thumbnails/filer_public/29/69/29691a84-ff9a-4aa9-98f7-8efdc6495bbd/jim_carrey_headshot.jpeg__1200x630_q85_subsampling-2.jpg","vip"));
        vipUsersList.add(new User(1,"Mahri","https://images.unsplash.com/photo-1494790108377-be9c29b29330?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&fit=crop&h=200&w=200&s=707b9c33066bf8808c934c8ab394dff6","vip"));
        vipUsersList.add(new User(1,"Selbi","https://images.unsplash.com/photo-1496081081095-d32308dd6206?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&fit=crop&h=200&w=200&s=dd302358c7e18c27c4086e97caf85781","vip"));
        VipAdapter vipAdapter=new VipAdapter(vipUsersList,context);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false);
        vipUsers.setAdapter(vipAdapter);
        vipUsers.setLayoutManager(linearLayoutManager);
        vipUsers.setNestedScrollingEnabled(false);
        OverScrollDecoratorHelper.setUpOverScroll(vipUsers, OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL);
    }

    private void setNewProducts() {
        newProducts.clear();
        newProducts.add(new Product(1,"Nagşy maýda küýze",134.0,"https://bulbandkey.com/blog/wp-content/uploads/2020/05/Tips-For-Home-handicraft-Feature-.jpg",true));
        newProducts.add(new Product(1,"Nagşy maýda küýze",134.0,"https://www.prattya.com/wp-content/uploads/2021/04/1_7YxApDhxLhfmX4x_bKzZEQ.jpeg",true));
        newProducts.add(new Product(1,"Nagşy maýda küýze",134.0,"https://www.crazypatterns.net/uploads/cache/items/2018/01/35661/needle-work-handicraft-bucket-600x450.jpg",false));
        newProducts.add(new Product(1,"Nagşy maýda küýze",134.0,"https://bulbandkey.com/blog/wp-content/uploads/2020/05/Tips-For-Home-handicraft-Feature-.jpg",false));
        newProducts.add(new Product(1,"Nagşy maýda küýze",134.0,"https://bulbandkey.com/blog/wp-content/uploads/2020/05/Tips-For-Home-handicraft-Feature-.jpg",true));
        newProducts.add(new Product(1,"Nagşy maýda küýze",134.0,"https://bulbandkey.com/blog/wp-content/uploads/2020/05/Tips-For-Home-handicraft-Feature-.jpg",false));
        newProducts.add(new Product(1,"Nagşy maýda küýze",134.0,"https://bulbandkey.com/blog/wp-content/uploads/2020/05/Tips-For-Home-handicraft-Feature-.jpg",true));
        newProducts.add(new Product(1,"Nagşy maýda küýze",134.0,"https://bulbandkey.com/blog/wp-content/uploads/2020/05/Tips-For-Home-handicraft-Feature-.jpg",false));
        newProducts.add(new Product(1,"Nagşy maýda küýze",134.0,"https://bulbandkey.com/blog/wp-content/uploads/2020/05/Tips-For-Home-handicraft-Feature-.jpg",true));
        ProductAdapter productAdapter=new ProductAdapter(newProducts,context,true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false);
        new_products.setAdapter(productAdapter);
        new_products.setLayoutManager(linearLayoutManager);
        new_products.setNestedScrollingEnabled(false);
        OverScrollDecoratorHelper.setUpOverScroll(new_products, OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL);
    }
    private void setTrendProducts() {
        trendProducts.clear();
        trendProducts.add(new Product(1,"Nagşy maýda küýze",134.0,"https://p4.wallpaperbetter.com/wallpaper/465/666/514/photography-depth-of-field-handicraft-wallpaper-preview.jpg",true));
        trendProducts.add(new Product(1,"Nagşy maýda küýze",134.0,"https://images.pexels.com/photos/1117272/pexels-photo-1117272.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",true));
        trendProducts.add(new Product(1,"Nagşy maýda küýze",134.0,"https://www.trtavaz.com.tr/uploads/photos/2019/02/15/bb47aa3470d340fbb12120fff1451a50.gif?w=640",false));
        trendProducts.add(new Product(1,"Nagşy maýda küýze",134.0,"https://i.pinimg.com/originals/99/1e/a7/991ea7a64d1e70cb148bbc50f73a529f.jpg",false));
        trendProducts.add(new Product(1,"Nagşy maýda küýze",134.0,"https://i2.wp.com/www.atavatan-turkmenistan.com/wp-content/uploads/2020/09/2-21.jpg?fit=591%2C392&ssl=1",true));
        trendProducts.add(new Product(1,"Nagşy maýda küýze",134.0,"https://www.ylymly.com/wp-content/uploads/2019/02/turkmen-shay-saypaeleri.jpg",false));
        trendProducts.add(new Product(1,"Nagşy maýda küýze",134.0,"https://www.turkmenmetbugat.gov.tm/storage/articles/1845/uUQ9bO954oxkG2c4AKcwjGFORv10xcBryjfpQDsJ9vAJYhNdaNuWI9lr2LsJ.jpg",true));
        trendProducts.add(new Product(1,"Nagşy maýda küýze",134.0,"https://ashgabat.in/wp-content/uploads/2021/06/6c4a03801ce7c36bcc4bbe76f539783b-450x3002-3.jpg",false));
        trendProducts.add(new Product(1,"Nagşy maýda küýze",134.0,"https://i.mycdn.me/i?r=AzEOxUXG5QgodWC3x6hM10CkvXQVcFiK4Dn3ujHQVNkWc_apV-FlrPFK6NMUXEEsv2I&fn=sqr_288",true));
        trendProducts.add(new Product(1,"Nagşy maýda küýze",134.0,"https://p4.wallpaperbetter.com/wallpaper/465/666/514/photography-depth-of-field-handicraft-wallpaper-preview.jpg",true));
        trendProducts.add(new Product(1,"Nagşy maýda küýze",134.0,"https://images.pexels.com/photos/1117272/pexels-photo-1117272.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",true));
        trendProducts.add(new Product(1,"Nagşy maýda küýze",134.0,"https://www.trtavaz.com.tr/uploads/photos/2019/02/15/bb47aa3470d340fbb12120fff1451a50.gif?w=640",false));
        trendProducts.add(new Product(1,"Nagşy maýda küýze",134.0,"https://i.pinimg.com/originals/99/1e/a7/991ea7a64d1e70cb148bbc50f73a529f.jpg",false));
        trendProducts.add(new Product(1,"Nagşy maýda küýze",134.0,"https://i2.wp.com/www.atavatan-turkmenistan.com/wp-content/uploads/2020/09/2-21.jpg?fit=591%2C392&ssl=1",true));
        trendProducts.add(new Product(1,"Nagşy maýda küýze",134.0,"https://www.ylymly.com/wp-content/uploads/2019/02/turkmen-shay-saypaeleri.jpg",false));
        trendProducts.add(new Product(1,"Nagşy maýda küýze",134.0,"https://www.turkmenmetbugat.gov.tm/storage/articles/1845/uUQ9bO954oxkG2c4AKcwjGFORv10xcBryjfpQDsJ9vAJYhNdaNuWI9lr2LsJ.jpg",true));
        trendProducts.add(new Product(1,"Nagşy maýda küýze",134.0,"https://ashgabat.in/wp-content/uploads/2021/06/6c4a03801ce7c36bcc4bbe76f539783b-450x3002-3.jpg",false));
        trendProducts.add(new Product(1,"Nagşy maýda küýze",134.0,"https://i.mycdn.me/i?r=AzEOxUXG5QgodWC3x6hM10CkvXQVcFiK4Dn3ujHQVNkWc_apV-FlrPFK6NMUXEEsv2I&fn=sqr_288",true));

//        GridLayoutManager gridLayoutManager=new GridLayoutManager(context, 2);
        StaggeredGridLayoutManager sGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        MultiSizeProductAdapter productAdapter=new MultiSizeProductAdapter(trendProducts,context,false);
        trend_products.setLayoutManager(sGridLayoutManager);
        trend_products.setAdapter(productAdapter);
        trend_products.setNestedScrollingEnabled(false);
    }

    private void setShortCategory() {
        shortCategories.clear();
        shortCategories.add(new Category(1,"https://images2.minutemediacdn.com/image/upload/c_crop,h_2164,w_3864,x_0,y_412/v1554737166/shape/mentalfloss/89961-istock-871610664.jpg?itok=1w3eWoI8","Sowgatlar"));
        shortCategories.add(new Category(2,"https://www.history.com/.image/ar_16:9%2Cc_fill%2Ccs_srgb%2Cfl_progressive%2Cq_auto:good%2Cw_1200/MTc4MjEwMDg3NDg2MTA1MTk3/chocolate-gettyimages-473741340.jpg","Konditer"));
        shortCategories.add(new Category(3,"https://nmk.com.tm/storage/app/uploads/public/612/cae/0b0/612cae0b09e82466046095.jpg","Tikincilik"));
        shortCategories.add(new Category(4,"https://lh3.googleusercontent.com/proxy/olz8eip07-MD1-LXJMbIZlUC307UJ0Uof97IaudeW_8rRQ-KW9FuQ9oOdfZfyRuV9AnOHx55LFjIcYpA0YY-jltT0NXoZBBPg_RY5fq_9ML05Dwqv9oMDg","Sergiler"));
        ShortCategoryAdapter shortCategoryAdapter=new ShortCategoryAdapter(shortCategories,context);
        LinearLayoutManager layoutManager=new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false);
        shortCategory.setAdapter(shortCategoryAdapter);
        shortCategory.setLayoutManager(layoutManager);
        shortCategory.setNestedScrollingEnabled(false);
        OverScrollDecoratorHelper.setUpOverScroll(shortCategory, OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL);
    }

    private void request() {
        hideSplash();
    }




    private void setRecycler() {

    }

    private void initComponents() {
        splashTitle2 = view.findViewById(R.id.splashTitle2);
        scroll = view.findViewById(R.id.scroll);
        viewPager2 = view.findViewById(R.id.bannerSlider);
        indicator_view = view.findViewById(R.id.indicator_view);
        shortCategory = view.findViewById(R.id.shortCategory);
        new_products = view.findViewById(R.id.new_products);
        newProductsTitle = view.findViewById(R.id.newProductsTitle);
        vipUsers = view.findViewById(R.id.vipUsers);
        trendProductsTitle = view.findViewById(R.id.trendProductsTitle);
        seeAll = view.findViewById(R.id.seeAll);
        trend_products = view.findViewById(R.id.trend_products);
        OverScrollDecoratorHelper.setUpOverScroll(scroll);

    }

    private void setFonts() {
        splashTitle2.setTypeface(Utils.getBoldFont(context));
        newProductsTitle.setTypeface(Utils.getMediumFont(context));
        trendProductsTitle.setTypeface(Utils.getMediumFont(context));
        seeAll.setTypeface(Utils.getRegularFont(context));
    }

    private void showSplash() {
    }

    private void hideSplash() {
    }




    private void setBanner() {
        sliderItems.clear();
        sliderItems.add(new BannerSlider(1, "https://m.media-amazon.com/images/I/71hYZle8pUL._SL1500_.jpg",0));
        sliderItems.add(new BannerSlider(1, "https://www.bannerbatterien.com/upload/filecache/Banner-Batterien-Windrder2-web_06b2d8d686e91925353ddf153da5d939.webp",0));
        sliderItems.add(new BannerSlider(1, "https://www.phdmedia.com/russia/wp-content/uploads/sites/74/2017/08/Banner-for-TWiD-4-e1499162744863.jpg",0));
        sliderItems.add(new BannerSlider(1, "https://w.wallha.com/ws/12/gXcqyb8U.jpg",0));

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            viewPager2.setNestedScrollingEnabled(false);
        }
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
        int pos=0;
        for(BannerSlider slider:sliderItems){
            slider.setPosition(pos);
            sliderItems.set(pos,slider);
            pos++;
        }

        IndicatorOptions options=new IndicatorOptions();
        options.setSliderColor(context.getResources().getColor(R.color.third),context.getResources().getColor(R.color.second));
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
        }
    };

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
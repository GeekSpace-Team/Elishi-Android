package com.elishi.android.Activity.Product;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.ecloud.pulltozoomview.PullToZoomBase;
import com.ecloud.pulltozoomview.PullToZoomScrollViewEx;
import com.elishi.android.Adapter.Product.DescriptionAdapter;
import com.elishi.android.Adapter.Product.MultiSizeProductAdapter;
import com.elishi.android.Adapter.Product.ProductImage;
import com.elishi.android.Adapter.Product.SingleProductImageAdapter;
import com.elishi.android.Api.APIClient;
import com.elishi.android.Api.ApiInterface;
import com.elishi.android.Common.ProductStatus;
import com.elishi.android.Common.Utils;
import com.elishi.android.Fragment.Product.Description;
import com.elishi.android.Modal.Favorite.FavBody;
import com.elishi.android.Modal.Product.GetSingleProduct;
import com.elishi.android.Modal.Product.Product;
import com.elishi.android.Modal.Response.GBody;
import com.elishi.android.R;
import com.elishi.android.View.CustomViews.AppTextView;
import com.elishi.android.databinding.ProductOptionsBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.datepicker.MaterialStyledDatePickerDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.hrskrs.instadotlib.InstaDotView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.zhy.base.fileprovider.FileProvider7;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import kr.co.prnd.readmore.ReadMoreTextView;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductView extends AppCompatActivity {
    private PullToZoomScrollViewEx scrollView;
    private Context context = this;
    private ArrayList<String> smallImages = new ArrayList<>();
    private ArrayList<String> largeImages = new ArrayList<>();
    private int mPagerPosition;
    private int mPagerPosition2;
    private int mPagerOffsetPixels;
    private ArrayList<Product> alsoProducts = new ArrayList<>();
    private RecyclerView alsoProductRec,similarRec,imagesRec;
    private LinearLayoutManager linearLayoutManager;
    private ViewPager2 bannerSlider;
    private InstaDotView indicator_view;
    private float firstScrollState=0f;
    private int mScreenWidth;
    private RelativeLayout toolbar;
    private RoundedImageView profileImage;
    private int imageRotate=0;
    private int oldY=0;
    private ImageView back1,more1;
    private ImageView back,more;
    private ReadMoreTextView description;
    private LottieAnimationView loading;
    private MotionLayout noInternetContainer;
    private MaterialCardView retry;
    private ArrayList<Product> similarProducts=new ArrayList<>();
    private Product product;
    private String id;
    private AppTextView name,price;
    private GetSingleProduct getSingleProduct;
    private LinearLayout imagesContainer,descContainer,similarContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.loadLocal(this);
        Utils.transparentStatusBar(this,true,true);
        checkMode();
        setContentView(R.layout.activity_product_view);
        id=getIntent().getStringExtra("id");
        loadViewForCode();
        scrollView = (PullToZoomScrollViewEx) findViewById(R.id.scroll_view);
        initComponents();
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        int mScreenHeight = localDisplayMetrics.heightPixels;
        mScreenWidth = localDisplayMetrics.widthPixels;
        LinearLayout.LayoutParams localObject = new LinearLayout.LayoutParams(mScreenWidth, (int) (14.0F * (mScreenWidth / 16.0F)));
        scrollView.setHeaderLayoutParams(localObject);
        setListener();
        handleIntent(getIntent());
        request();
    }

    private void request() {
        noInternetContainer.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);
        toolbar.setAlpha(1.0f);
        ApiInterface apiInterface= APIClient.getClient().create(ApiInterface.class);
        Call<GBody<GetSingleProduct>> call=apiInterface.getSingleProduct(id,"Bearer "+Utils.getSharedPreference(context,"tkn"));
        call.enqueue(new Callback<GBody<GetSingleProduct>>() {
            @Override
            public void onResponse(Call<GBody<GetSingleProduct>> call, Response<GBody<GetSingleProduct>> response) {
                if(response.isSuccessful() && response.body()!=null && response.body().getBody()!=null && response.body().getBody().getProduct()!=null){
                    scrollView.setVisibility(View.VISIBLE);
                    getSingleProduct=response.body().getBody();
                    product=response.body().getBody().getProduct();
                    if(response.body().getBody().getSimilar()!=null) {
                        similarProducts = response.body().getBody().getSimilar();
                    }
                    smallImages.clear();
                    largeImages.clear();
                    for(Product.Images img: product.getImages()){
                        smallImages.add(img.getSmall_image());
                        largeImages.add(img.getLarge_image());
                    }
                    if(product.getDescription()!=null && !product.getDescription().trim().isEmpty()){
                        description.setText(product.getDescription());
                    }
                    if(product.getProduct_name()!=null && !product.getProduct_name().trim().isEmpty()){
                        name.setText(product.getProduct_name());
                    }
                    if(product.getPrice()!=null && !product.getPrice().toString().trim().isEmpty()){
                        price.setText(product.getPrice()+" TMT");
                    }
                    setBanner();
                    pullListener();
                    setFonts();
                    setImages();
                    setSimilar();
                    Description description=Description.newInstance("1");
                    description.product=product;
                    description.getSingleProduct=getSingleProduct;
                    description.similarProducts=similarProducts;
                    getSupportFragmentManager().beginTransaction().replace(R.id.details,description,Description.class.getSimpleName()).commit();
                    if(product.getDescription()==null || product.getDescription().trim().isEmpty()){
                        descContainer.setVisibility(View.GONE);
                    }
                } else{
                    noInternetContainer.setVisibility(View.VISIBLE);
                }
                loading.setVisibility(View.GONE);
                toolbar.setAlpha(0.0f);
            }

            @Override
            public void onFailure(Call<GBody<GetSingleProduct>> call, Throwable t) {
                noInternetContainer.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setSimilar() {
        if(similarProducts!=null && similarProducts.size()>0){
            similarContainer.setVisibility(View.VISIBLE);
            MultiSizeProductAdapter productAdapter = new MultiSizeProductAdapter(similarProducts, context, false);
            StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
            similarRec.setAdapter(productAdapter);
            similarRec.setNestedScrollingEnabled(false);
            similarRec.setLayoutManager(staggeredGridLayoutManager);
        } else {
            similarContainer.setVisibility(View.GONE);
        }
    }

    private void setImages() {
        if(product.getImages()!=null && product.getImages().size()>0){
            imagesContainer.setVisibility(View.VISIBLE);
            imagesRec.setNestedScrollingEnabled(false);
            StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL);
            staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
            imagesRec.setLayoutManager(staggeredGridLayoutManager);
            imagesRec.setAdapter(new SingleProductImageAdapter(product.getImages(),context));
        } else {
            imagesContainer.setVisibility(View.GONE);
        }
    }

    private void setListener() {
        more1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptions();
            }
        });

        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptions();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request();
            }
        });
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        String appLinkAction = intent.getAction();
        Uri appLinkData = intent.getData();
        if (Intent.ACTION_VIEW.equals(appLinkAction) && appLinkData != null){
            try{
                id = appLinkData.getLastPathSegment();
                request();
            } catch (Exception ex){
                ex.printStackTrace();
            }

        }
    }

    private void showOptions() {
        Utils.loadLocal(context);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        ProductOptionsBinding optionsBinding=ProductOptionsBinding.inflate(LayoutInflater.from(context));
        optionsBinding.addButton.setTypeface(Utils.getMediumFont(context));
        optionsBinding.callButton.setTypeface(Utils.getMediumFont(context));
        optionsBinding.shareProductButton.setTypeface(Utils.getMediumFont(context));
        optionsBinding.shareAsImageButton.setTypeface(Utils.getMediumFont(context));

        if(Utils.getSharedPreference(context,"tkn").isEmpty()){
            optionsBinding.addButton.setVisibility(View.GONE);
            optionsBinding.addFavCon.setVisibility(View.GONE);
        }
        if(product!=null){
            optionsBinding.callButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(product.getPhone_number()!=null && !product.getPhone_number().trim().isEmpty()){
                        Intent intent=new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:"+product.getPhone_number()));
                        startActivity(intent);
                    } else {
                        Intent intent=new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:"+product.getUser_phone_number()));
                        startActivity(intent);
                    }
                }
            });
            optionsBinding.addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dialog progressDialog = Utils.getDialogProgressBar(context);
                    progressDialog.show();
                    bottomSheetDialog.dismiss();
                    ApiInterface apiInterface=APIClient.getClient().create(ApiInterface.class);
                    Call<ResponseBody> call=apiInterface.addFav(new FavBody(id),"Bearer "+Utils.getSharedPreference(context,"tkn"));
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if(response.isSuccessful()){
                                product.setIsfav(1);
                            }
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            progressDialog.dismiss();
                        }
                    });
                }
            });
            if(product.getIsfav()>0){
                optionsBinding.addFav.setText(context.getResources().getString(R.string.removeFav));
                optionsBinding.addButton.setText(context.getResources().getString(R.string.remove));
                optionsBinding.addButton.setIcon(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_baseline_remove_24,getTheme()));
                optionsBinding.addButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Dialog progressDialog = Utils.getDialogProgressBar(context);
                        progressDialog.show();
                        bottomSheetDialog.dismiss();
                        ApiInterface apiInterface=APIClient.getClient().create(ApiInterface.class);
                        Call<ResponseBody> call=apiInterface.deleteFav("Bearer "+Utils.getSharedPreference(context,"tkn"),id);
                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if(response.isSuccessful()){
                                    product.setIsfav(0);
                                } else {
                                    Log.e("Error",response.code()+"");
                                }
                                progressDialog.dismiss();
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                progressDialog.dismiss();
                                Log.e("Error",t.getMessage());
                            }
                        });
                    }
                });
            }

            optionsBinding.shareAsImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    share();
                }
            });

            optionsBinding.shareProductButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent2 = new Intent();
                    intent2.setAction(Intent.ACTION_SEND);
                    intent2.setType("text/plain");
                    intent2.putExtra(Intent.EXTRA_TEXT, "https://www.elishi.com.tm/"+id);
                    startActivity(Intent.createChooser(intent2, context.getResources().getString(R.string.share)));
                }
            });
        }
        optionsBinding.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.setContentView(optionsBinding.getRoot());
        bottomSheetDialog.show();

    }




    private void setFonts() {
        description.setTypeface(Utils.getRegularFont(context));
    }

    private void pullListener() {
        scrollView.setOnPullZoomListener(new PullToZoomBase.OnPullZoomListener() {
            @Override
            public void onPullZooming(int newScrollValue) {
                Log.e("Y",newScrollValue+" / "+oldY);
                if(oldY>newScrollValue && newScrollValue<=0){
                    if(back1.getRotation()<=90)
                        back1.setRotation(back1.getRotation()+2);
                    if(more1.getRotation()>=-90)
                        more1.setRotation(more1.getRotation()-2);
                }

                if(oldY<=newScrollValue && newScrollValue<=0){
                    if(back1.getRotation()>=0)
                        back1.setRotation(back1.getRotation()-2);
                    if(more1.getRotation()<=0)
                        more1.setRotation(more1.getRotation()+2);
                }
                oldY=newScrollValue;


            }

            @Override
            public void onPullZoomEnd() {
                back1.setRotation(0f);
                more1.setRotation(0f);
            }
        });
    }

    private void initComponents() {
        bannerSlider=findViewById(R.id.bannerSlider);
        indicator_view=findViewById(R.id.indicator_view);
        toolbar=findViewById(R.id.toolbar);
        profileImage=scrollView.findViewById(R.id.profileImage);
        back1=scrollView.findViewById(R.id.back1);
        more1=scrollView.findViewById(R.id.more1);
        description=scrollView.findViewById(R.id.description);
        back = findViewById(R.id.back);
        more = findViewById(R.id.more);
        loading = findViewById(R.id.loading);
        retry = findViewById(R.id.retry);
        noInternetContainer = findViewById(R.id.noInternetContainer);
        name = scrollView.findViewById(R.id.name);
        price = scrollView.findViewById(R.id.price);
        similarRec = scrollView.findViewById(R.id.similarRec);
        imagesRec = scrollView.findViewById(R.id.imagesRec);
        imagesContainer = scrollView.findViewById(R.id.imagesContainer);
        descContainer = scrollView.findViewById(R.id.descContainer);
        similarContainer = scrollView.findViewById(R.id.similarContainer);
    }

    private void loadViewForCode() {
        PullToZoomScrollViewEx scrollView = (PullToZoomScrollViewEx) findViewById(R.id.scroll_view);
        View headView = LayoutInflater.from(this).inflate(R.layout.profile_head_view, null, false);
        View zoomView = LayoutInflater.from(this).inflate(R.layout.profile_zoom_view, null, false);
        View contentView = LayoutInflater.from(this).inflate(R.layout.profile_content_view, null, false);

        scrollView.setHeaderView(headView);
        scrollView.setZoomView(zoomView);
        scrollView.setScrollContentView(contentView);
        scrollView.setZoomEnabled(true);

        scrollView.getPullRootView().getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                firstScrollState=(13.0F * (mScreenWidth / 16.0F))-scrollView.getPullRootView().getScrollY();
                if(firstScrollState<=150){
                    if(toolbar.getAlpha()<=1.0f)
                        toolbar.setAlpha(toolbar.getAlpha()+0.09f);
                } else{
                    if(toolbar.getAlpha()>=0.0f)
                        toolbar.setAlpha(toolbar.getAlpha()-0.09f);
                }
            }
        });
    }

    private void setBanner() {
        indicator_view.setNoOfPages(largeImages.size());
        bannerSlider.setAdapter(new ProductImage(largeImages, context));
        bannerSlider.setClipToPadding(false);
        bannerSlider.setClipChildren(false);
        bannerSlider.setOffscreenPageLimit(1);
        bannerSlider.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });
        bannerSlider.setPageTransformer(compositePageTransformer);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            bannerSlider.setNestedScrollingEnabled(false);
        }
        bannerSlider.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                indicator_view.onPageChange(position);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        });

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

    private void saveImage(Bitmap bitmap, @NonNull String name) throws IOException {
        OutputStream fos;
        Uri uri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ContentResolver resolver = getContentResolver();
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, name + ".jpg");
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg");
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);
            uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            fos = resolver.openOutputStream(Objects.requireNonNull(uri));
        } else {
            String imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
            File image = new File(imagesDir, name + ".jpg");
            fos = new FileOutputStream(image);
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        Objects.requireNonNull(fos).close();

    }

    public static Bitmap loadBitmapFromView(View v) {
        Bitmap b = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);
        return b;
    }

    private void save_toMediaStore() {
        Bitmap bitmap = loadBitmapFromView(findViewById(R.id.con));
        Random generator = new Random();
        int n = 100000;
        n = generator.nextInt(n);

        try {
            saveImage(bitmap, n + "");
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            String type = mime.getMimeTypeFromExtension("image/*");

            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), n + ".jpg");
            Uri uri= FileProvider7.getUriForFile(ProductView.this, file);
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setType("image/jpg");
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
            startActivity(Intent.createChooser(shareIntent, "Select"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 11 && grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            save_toMediaStore();
        }
    }

    public void share(){
        if (ActivityCompat.checkSelfPermission(ProductView.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ProductView.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    11);

        } else {
            save_toMediaStore();
        }
    }

}
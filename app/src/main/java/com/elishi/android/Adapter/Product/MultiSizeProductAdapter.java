package com.elishi.android.Adapter.Product;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.transition.Fade;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.elishi.android.Activity.MainActivity;
import com.elishi.android.Activity.Product.ProductPage;
import com.elishi.android.Activity.Product.ProductView;
import com.elishi.android.Api.APIClient;
import com.elishi.android.Api.ApiInterface;
import com.elishi.android.Common.Click;
import com.elishi.android.Common.Constant;
import com.elishi.android.Common.LayoutType;
import com.elishi.android.Common.ProductStatus;
import com.elishi.android.Common.Utils;
import com.elishi.android.Modal.Favorite.FavBody;
import com.elishi.android.Modal.Home.Ads;
import com.elishi.android.Modal.Product.Product;
import com.elishi.android.Modal.Response.GBody;
import com.elishi.android.R;
import com.elishi.android.View.CustomViews.AppTextView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.hrskrs.instadotlib.InstaDotView;
import com.zhpan.indicator.IndicatorView;
import com.zhpan.indicator.enums.IndicatorSlideMode;
import com.zhpan.indicator.enums.IndicatorStyle;
import com.zhpan.indicator.option.IndicatorOptions;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.function.Predicate;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MultiSizeProductAdapter extends RecyclerView.Adapter<MultiSizeProductAdapter.ViewHolder> {
    private ArrayList<Product> products = new ArrayList<>();
    private Context context;
    private boolean isHorizontal = false;
    private Integer pos = -1;
    private boolean isReadyAds = false;
    private ArrayList<Ads> ads = new ArrayList<>();
    private int adsPos = 0;
    private Integer productHeight;

    public MultiSizeProductAdapter(ArrayList<Product> products, Context context, boolean isHorizontal) {
        this.products = products;
        this.context = context;
        this.isHorizontal = isHorizontal;
    }

    public MultiSizeProductAdapter(ArrayList<Product> products, Context context, boolean isHorizontal,ArrayList<Ads> ads) {
        this.products = products;
        this.context = context;
        this.isHorizontal = isHorizontal;
        this.ads=ads;
    }

    public MultiSizeProductAdapter(ArrayList<Product> products, Context context,Integer productHeight,boolean isHorizontal) {
        this.products = products;
        this.context = context;
        this.productHeight = productHeight;
        this.isHorizontal=isHorizontal;
    }



    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Utils.loadLocal(context);
        final View itemView =
                LayoutInflater.from(context).inflate(R.layout.product_design, parent, false);
        return new MultiSizeProductAdapter.ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull @NotNull MultiSizeProductAdapter.ViewHolder holder, int position) {
        Product product = products.get(holder.getAbsoluteAdapterPosition());



        if (isHorizontal) {
            holder.indicatorView.setVisibility(View.GONE);
            RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) holder.container.getLayoutParams();
            lp.width = (int) context.getResources().getDimension(R.dimen.product_width);
            holder.container.setLayoutParams(lp);
            setProduct(holder,product,holder.getAbsoluteAdapterPosition());
            ArrayList<String> imgs = new ArrayList<>();
            imgs.add(Constant.IMAGE_URL + product.getImages().get(0).getSmall_image());
            setSlider(holder.slider, imgs, holder.indicatorView, product.getId() + "");
        } else {
            if (product.getLayout_type() != null) {
                if (product.getLayout_type().equals(LayoutType.ADS)) {
                    holder.adsImage.setVisibility(View.VISIBLE);
                    holder.slider.setVisibility(View.GONE);
                    holder.name.setVisibility(View.GONE);
                    holder.indicatorView.setVisibility(View.GONE);
                    holder.cost.setVisibility(View.GONE);
                    holder.statusContainer.setVisibility(View.GONE);
                    holder.productStatus.setVisibility(View.GONE);
                    holder.productStatusDesc.setVisibility(View.GONE);
                    holder.fav.setVisibility(View.GONE);
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Click.adsClick(product.getAds(),context);
                        }
                    });
                    Glide.with(context)
                            .load(Constant.IMAGE_URL + product.getAds().getAds_image())
                            .into(holder.adsImage);
                    try{
                        StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
                        layoutParams.setFullSpan(true);
                    } catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            } else {
               try{
                   ArrayList<String> smallImages = new ArrayList<>();
                   if(product.getImages()!=null){
                       for (Product.Images img : product.getImages()) {
                           smallImages.add(Constant.IMAGE_URL + img.getSmall_image());
                       }
                   }
                   setSlider(holder.slider, smallImages, holder.indicatorView, product.getId() + "");
                   setProduct(holder,product,holder.getAbsoluteAdapterPosition());
               } catch (Exception ex){
                   ex.printStackTrace();
               }
                try{
                    StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
                    layoutParams.setFullSpan(false);
                } catch (Exception ex){
                    ex.printStackTrace();
                }
                holder.adsImage.setVisibility(View.GONE);
                holder.slider.setVisibility(View.VISIBLE);
                holder.name.setVisibility(View.VISIBLE);
                holder.indicatorView.setVisibility(View.VISIBLE);
                holder.cost.setVisibility(View.VISIBLE);
//                holder.statusContainer.setVisibility(View.VISIBLE);
//                holder.productStatus.setVisibility(View.VISIBLE);
//                holder.productStatusDesc.setVisibility(View.VISIBLE);
                holder.fav.setVisibility(View.VISIBLE);
                holder.progressBar.setVisibility(View.GONE);
            }
        }

        if(productHeight!=null){
            ViewGroup.LayoutParams lm=holder.heightCon.getLayoutParams();
            lm.height = (int) context.getResources().getDimension(productHeight);
            holder.heightCon.setLayoutParams(lm);
            holder.statusContainer.setVisibility(View.GONE);
            holder.fav.setVisibility(View.GONE);
            holder.name.setVisibility(View.GONE);
            holder.cost.setVisibility(View.GONE);
        }




    }

    private void setProduct(ViewHolder holder, Product product, int absoluteAdapterPosition) {
        holder.name.setText(product.getProduct_name());
        holder.cost.setText(product.getPrice() + " TMT");
        holder.name.setTypeface(Utils.getRegularFont(context));
        holder.cost.setTypeface(Utils.getBoldFont(context));

        if(product.getIsfav()!=null){
            if (product.getIsfav() == 1) {
                holder.fav.setImageResource(R.drawable.ic_fav);
            } else {
                holder.fav.setImageResource(R.drawable.ic_dis_fav);
            }
        } else {
            holder.fav.setImageResource(R.drawable.ic_fav);
        }

        if (product.getStatus() != null) {
            if (product.getStatus().equals(ProductStatus.VIP)) {
                holder.productStatus.setText(context.getResources().getString(R.string.vip_product));
                holder.statusIcon.setImageResource(R.drawable.ic_vip_icon);
                holder.statusContainer.setBackgroundResource(R.drawable.vip_status_background);
                holder.productStatusDesc.setText(context.getResources().getString(R.string.vip_product_st));
                holder.statusContainer.setVisibility(View.VISIBLE);
                holder.statusDescBg.setVisibility(View.VISIBLE);
                holder.productStatus.setVisibility(View.VISIBLE);
            } else if (product.getStatus().equals(ProductStatus.MASTER)) {
                holder.productStatus.setText(context.getResources().getString(R.string.master_product));
                holder.statusIcon.setImageResource(R.drawable.ic_master_icon);
                holder.statusContainer.setBackgroundResource(R.drawable.master_status_background);
                holder.statusContainer.setVisibility(View.VISIBLE);
                holder.statusDescBg.setVisibility(View.VISIBLE);
                holder.productStatus.setVisibility(View.VISIBLE);
            }
            if(product.getStatus().equals(ProductStatus.ACTIVE)){
                holder.statusContainer.setVisibility(View.GONE);
                holder.statusDescBg.setVisibility(View.GONE);
                holder.productStatus.setVisibility(View.GONE);
            }
        } else {
            holder.productStatus.setVisibility(View.GONE);
            holder.statusContainer.setVisibility(View.GONE);
            holder.statusDescBg.setVisibility(View.GONE);
        }


        Collections.sort(product.getImages(), (abc1, abc2) -> Boolean.compare(abc2.getIs_first(), abc1.getIs_first()));

        holder.fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Utils.getSharedPreference(context,"tkn").isEmpty()){
                    MaterialAlertDialogBuilder alertDialogBuilder=new MaterialAlertDialogBuilder(context);
                    alertDialogBuilder.setTitle(R.string.must_login_title);
                    alertDialogBuilder.setMessage(R.string.must_login);
                    alertDialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alertDialogBuilder.setPositiveButton(R.string.create_account, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MainActivity.get().getBottomNavigationView().setSelectedItemId(R.id.profile);
                        }
                    });
                    alertDialogBuilder.show();
                    return;
                }

                if (product.getIsfav()==null || product.getIsfav() == 1) {
                    setPassiveFav(holder,holder.getAbsoluteAdapterPosition());
                    Dialog progressDialog = Utils.getDialogProgressBar(context);
                    progressDialog.show();
                    ApiInterface apiInterface=APIClient.getClient().create(ApiInterface.class);
                    Call<ResponseBody> call=apiInterface.deleteFav("Bearer "+Utils.getSharedPreference(context,"tkn"),product.getId()+"");
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if(response.isSuccessful()){
                            } else {
                                Log.e("Error",response.code()+"");
                                setActiveFav(holder,holder.getAbsoluteAdapterPosition());
                            }
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            progressDialog.dismiss();
                            setActiveFav(holder,holder.getAbsoluteAdapterPosition());
                            Log.e("Error",t.getMessage());
                        }
                    });
                } else {
                    setActiveFav(holder,holder.getAbsoluteAdapterPosition());
                    Dialog progressDialog = Utils.getDialogProgressBar(context);
                    progressDialog.show();
                    ApiInterface apiInterface=APIClient.getClient().create(ApiInterface.class);
                    Call<ResponseBody> call=apiInterface.addFav(new FavBody(product.getId()+""),"Bearer "+Utils.getSharedPreference(context,"tkn"));
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if(response.isSuccessful()){
                            } else {
                                setPassiveFav(holder,holder.getAbsoluteAdapterPosition());
                            }
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            progressDialog.dismiss();
                            setPassiveFav(holder,holder.getAbsoluteAdapterPosition());
                        }
                    });
                }

            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductView.class);
                intent.putExtra("id", product.getId()+"");
                intent.putExtra("image", Constant.IMAGE_URL + product.getImages().get(0).getSmall_image());
                context.startActivity(intent);
            }
        });
    }

    private void setPassiveFav(ViewHolder holder, int absoluteAdapterPosition) {
        products.get(absoluteAdapterPosition).setIsfav(0);
        holder.fav.setImageResource(R.drawable.ic_dis_fav);
    }

    private void setActiveFav(ViewHolder holder, int absoluteAdapterPosition) {
        products.get(absoluteAdapterPosition).setIsfav(1);
        holder.fav.setImageResource(R.drawable.ic_fav);
    }

    private void setSlider(ViewPager2 slider, ArrayList<String> smallImages, InstaDotView indicatorView, String productId) {
        if (smallImages.size() > 1)
            setIndicators(indicatorView, smallImages);
        slider.setAdapter(new ProductImageSlider(smallImages, context, productId, slider));
        slider.setClipToPadding(false);
        slider.setClipChildren(false);
        slider.setOffscreenPageLimit(1);
        slider.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        slider.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                try {
                    indicatorView.onPageChange(position);
                } catch (Exception ex){
                    ex.printStackTrace();
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        });
    }

    private void setIndicators(InstaDotView indicatorView, ArrayList<String> smallImages) {
        if (smallImages != null && smallImages.size() > 0)
            indicatorView.setNoOfPages(smallImages.size());
    }

    private void setImageHeight(int dimension, ImageView image) {
        RelativeLayout.LayoutParams lm = (RelativeLayout.LayoutParams) image.getLayoutParams();
        lm.height = dimension;
        image.setLayoutParams(lm);
    }


    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, cost, productStatusDesc;
        ImageView fav, statusIcon, adsImage;
        RelativeLayout container, container2;
        LinearLayout statusContainer, statusDescBg;
        ViewPager2 slider;
        InstaDotView indicatorView;
        AppTextView productStatus;
        ProgressBar progressBar;
        RelativeLayout heightCon;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            cost = itemView.findViewById(R.id.cost);
            fav = itemView.findViewById(R.id.fav);
            container = itemView.findViewById(R.id.container);
            slider = itemView.findViewById(R.id.bannerSlider);
            indicatorView = itemView.findViewById(R.id.indicator_view);
            productStatus = itemView.findViewById(R.id.productStatus);
            statusContainer = itemView.findViewById(R.id.statusContainer);
            statusIcon = itemView.findViewById(R.id.statusIcon);
            statusDescBg = itemView.findViewById(R.id.statusDescBg);
            productStatusDesc = itemView.findViewById(R.id.productStatusDesc);
            progressBar = itemView.findViewById(R.id.pagination);
            adsImage = itemView.findViewById(R.id.adsImg);
            container2 = itemView.findViewById(R.id.container2);
            heightCon = itemView.findViewById(R.id.heightCon);
        }
    }
}

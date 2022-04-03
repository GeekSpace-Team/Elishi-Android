package com.elishi.android.Adapter.Product;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.transition.Fade;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.elishi.android.Activity.Product.ProductPage;
import com.elishi.android.Common.Constant;
import com.elishi.android.Common.ProductStatus;
import com.elishi.android.Common.Utils;
import com.elishi.android.Modal.Product.Product;
import com.elishi.android.R;
import com.elishi.android.View.CustomViews.AppTextView;
import com.zhpan.indicator.IndicatorView;
import com.zhpan.indicator.enums.IndicatorSlideMode;
import com.zhpan.indicator.enums.IndicatorStyle;
import com.zhpan.indicator.option.IndicatorOptions;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.function.Predicate;

public class MultiSizeProductAdapter extends RecyclerView.Adapter<MultiSizeProductAdapter.ViewHolder> {
    private ArrayList<Product> products = new ArrayList<>();
    private Context context;
    private boolean isHorizontal = false;
    private Integer pos = -1;

    public MultiSizeProductAdapter(ArrayList<Product> products, Context context, boolean isHorizontal) {
        this.products = products;
        this.context = context;
        this.isHorizontal = isHorizontal;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        final View itemView =
                LayoutInflater.from(context).inflate(R.layout.product_design, parent, false);
        return new MultiSizeProductAdapter.ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull @NotNull MultiSizeProductAdapter.ViewHolder holder, int position) {
        Product product = products.get(holder.getAbsoluteAdapterPosition());

        holder.name.setText(product.getProduct_name());
        holder.cost.setText(product.getPrice() + " TMT");
        holder.name.setTypeface(Utils.getRegularFont(context));
        holder.cost.setTypeface(Utils.getBoldFont(context));

        if (product.getIsfav()==1) {
            holder.fav.setImageResource(R.drawable.ic_fav);
        } else {
            holder.fav.setImageResource(R.drawable.ic_dis_fav);
        }

        if(product.getStatus()!=null){
            if(product.getStatus().equals(ProductStatus.STATUSES[0])){
                holder.productStatus.setText(context.getResources().getString(R.string.vip_product));
                holder.statusIcon.setImageResource(R.drawable.ic_vip_icon);
                holder.statusContainer.setBackgroundResource(R.drawable.vip_status_background);
            } else if(product.getStatus().equals(ProductStatus.STATUSES[1])){
                holder.productStatus.setText(context.getResources().getString(R.string.master_product));
                holder.statusIcon.setImageResource(R.drawable.ic_master_icon);
                holder.statusContainer.setBackgroundResource(R.drawable.master_status_background);
            } else {
                holder.statusContainer.setVisibility(View.GONE);
                holder.statusDescBg.setVisibility(View.GONE);
            }
        } else{
            holder.productStatus.setVisibility(View.GONE);
        }

        if (isHorizontal) {
            RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) holder.container.getLayoutParams();
            lp.width = (int)context.getResources().getDimension(R.dimen.product_width);
            holder.container.setLayoutParams(lp);
            ArrayList<String> imgs=new ArrayList<>();
            imgs.add(Constant.IMAGE_URL+product.getImages().get(0).getSmall_image());
            setSlider(holder.slider,imgs,holder.indicatorView,product.getId()+"");
        } else{
            ArrayList<String> smallImages = new ArrayList<>();
            for(Product.Images img:product.getImages()){
                smallImages.add(Constant.IMAGE_URL+img.getSmall_image());
            }
            setSlider(holder.slider,smallImages,holder.indicatorView,product.getId()+"");
        }

        holder.fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (product.getIsfav()==1) {
                    products.get(holder.getAdapterPosition()).setIsfav(0);
                    holder.fav.setImageResource(R.drawable.ic_dis_fav);
                } else {
                    products.get(holder.getAdapterPosition()).setIsfav(1);
                    holder.fav.setImageResource(R.drawable.ic_fav);
                }

            }
        });



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductPage.class);
                intent.putExtra("id", product.getId());
                intent.putExtra("image", Constant.IMAGE_URL+product.getImages().get(0).getSmall_image());
                context.startActivity(intent);
                ((Activity)context).overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
            }
        });




    }

    private void setSlider(ViewPager2 slider, ArrayList<String> smallImages, IndicatorView indicatorView,String productId) {
        if(smallImages.size()>1)
            setIndicators(indicatorView,smallImages);
        slider.setAdapter(new ProductImageSlider(smallImages,context,productId,slider));
        slider.setClipToPadding(false);
        slider.setClipChildren(false);
        slider.setOffscreenPageLimit(1);
        slider.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        slider.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                indicatorView.onPageSelected(position);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                indicatorView.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        });
    }

    private void setIndicators(IndicatorView indicatorView, ArrayList<String> smallImages) {
        IndicatorOptions options=new IndicatorOptions();
        options.setSliderColor(context.getResources().getColor(R.color.third),context.getResources().getColor(R.color.second));
        options.setSliderHeight(context.getResources().getDimension(R.dimen.sliderCircle));
        options.setSliderWidth(context.getResources().getDimension(R.dimen.sliderCircle), context.getResources().getDimension(R.dimen.sliderRound));
        options.setSlideMode(IndicatorSlideMode.SCALE);
        options.setIndicatorStyle(IndicatorStyle.ROUND_RECT);
        options.setPageSize(smallImages.size());
        indicatorView.setIndicatorOptions(options);
        indicatorView.notifyDataChanged();
    }

    private void setImageHeight(int dimension, ImageView image) {
        RelativeLayout.LayoutParams lm = (RelativeLayout.LayoutParams) image.getLayoutParams();
        lm.height = dimension;
        image.setLayoutParams(lm);
    }

    private void setGradient(ImageView fav) {

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, cost,productStatusDesc;
        ImageView fav,statusIcon;
        RelativeLayout container;
        LinearLayout statusContainer,statusDescBg;
        ViewPager2 slider;
        IndicatorView indicatorView;
        AppTextView productStatus;


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
        }
    }
}

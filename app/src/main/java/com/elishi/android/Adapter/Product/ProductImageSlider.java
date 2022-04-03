package com.elishi.android.Adapter.Product;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.elishi.android.Activity.Product.ProductPage;
import com.elishi.android.Common.PlaceHolderColors;
import com.elishi.android.R;

import java.util.List;
import java.util.Random;

public class ProductImageSlider extends RecyclerView.Adapter<ProductImageSlider.SliderViewHolder> {
    private List<String> sliderItems;
    private Context context;
    private String productId;
    private ViewPager2 slider;
    public ProductImageSlider(List<String> sliderItems, Context context, String productId, ViewPager2 slider) {
        this.sliderItems = sliderItems;
        this.context = context;
        this.productId=productId;
        this.slider=slider;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderViewHolder(LayoutInflater.from(context).inflate(R.layout.product_image_slider, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        String bannerSlider=sliderItems.get(position);
        final int min = 0;
        final int max = PlaceHolderColors.PLACEHOLDERS.length-1;
        final int r = new Random().nextInt((max - min) + 1) + min;

        Glide.with(context)
                .load(bannerSlider)
                .placeholder(PlaceHolderColors.PLACEHOLDERS[r])
                .into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductPage.class);
                intent.putExtra("id", productId);
                intent.putExtra("image", bannerSlider);
                context.startActivity(intent);
                ((Activity)context).overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);

            }
        });
    }
    @Override
    public int getItemCount() {
        return sliderItems.size();
    }
    static class SliderViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
        }
    }




}
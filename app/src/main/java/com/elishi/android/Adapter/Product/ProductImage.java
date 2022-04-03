package com.elishi.android.Adapter.Product;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.elishi.android.Common.PlaceHolderColors;
import com.elishi.android.Modal.Home.BannerSlider;
import com.elishi.android.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;
import java.util.Random;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;

public class ProductImage extends RecyclerView.Adapter<ProductImage.SliderViewHolder> {
    private List<String> sliderItems;
    private Context context;

    public ProductImage(List<String> sliderItems, Context context) {
        this.sliderItems = sliderItems;
        this.context = context;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderViewHolder(LayoutInflater.from(context).inflate(R.layout.product_image, parent, false));
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
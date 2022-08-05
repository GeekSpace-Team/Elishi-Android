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
import com.bumptech.glide.request.RequestOptions;
import com.elishi.android.Common.Constant;
import com.elishi.android.Common.PlaceHolderColors;
import com.elishi.android.Modal.Home.BannerSlider;
import com.elishi.android.R;
import com.makeramen.roundedimageview.RoundedImageView;
import com.stfalcon.imageviewer.StfalconImageViewer;
import com.stfalcon.imageviewer.loader.ImageLoader;

import java.util.ArrayList;
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
                .load(Constant.IMAGE_URL+bannerSlider)
                .timeout(60000).placeholder(PlaceHolderColors.PLACEHOLDERS[r])
                .into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageLoader<String> imageLoader=new ImageLoader<String>() {
                    @Override
                    public void loadImage(ImageView imageView, String image) {
                        final int min = 0;
                        final int max = PlaceHolderColors.PLACEHOLDERS.length - 1;
                        final int r = new Random().nextInt((max - min) + 1) + min;
                        Glide.with(context)
                                .load(Constant.IMAGE_URL+image)
                                .timeout(60000).placeholder(PlaceHolderColors.PLACEHOLDERS[r])
                                .thumbnail(0.25f)
                                .into(imageView);
                    }
                };
                StfalconImageViewer.Builder<String> builder=new StfalconImageViewer.Builder<String>(context,sliderItems,imageLoader);
                builder.withBackgroundColorResource(R.color.white);
                builder.allowZooming(true);
                builder.allowSwipeToDismiss(true);
                builder.withHiddenStatusBar(false);
                builder.show().setCurrentPosition(holder.getAbsoluteAdapterPosition());
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
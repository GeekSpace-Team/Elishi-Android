package com.elishi.android.Adapter.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.elishi.android.Modal.Home.BannerSlider;
import com.elishi.android.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class BannerSliderAdapter extends RecyclerView.Adapter<BannerSliderAdapter.SliderViewHolder> {
    private List<BannerSlider> sliderItems;
    private ViewPager2 viewPager2;
    private Context context;

    public BannerSliderAdapter(List<BannerSlider> sliderItems, ViewPager2 viewPager2, Context context) {
        this.sliderItems = sliderItems;
        this.viewPager2 = viewPager2;
        this.context = context;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderViewHolder(LayoutInflater.from(context).inflate(R.layout.banner_item, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        BannerSlider bannerSlider=sliderItems.get(position);
        Glide.with(context)
                .load(bannerSlider.getImageUrl())
                .placeholder(R.drawable.placeholder)
                .into(holder.imageView);
        if (position == sliderItems.size()- 2){
            viewPager2.post(runnable);
        }
    }
    @Override
    public int getItemCount() {
        return sliderItems.size();
    }
    class SliderViewHolder extends RecyclerView.ViewHolder {
        private RoundedImageView imageView;
        SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            sliderItems.addAll(sliderItems);
            notifyDataSetChanged();
        }
    };


}
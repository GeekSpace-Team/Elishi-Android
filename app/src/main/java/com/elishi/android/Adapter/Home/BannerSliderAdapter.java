package com.elishi.android.Adapter.Home;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.elishi.android.Common.Click;
import com.elishi.android.Common.Constant;
import com.elishi.android.Common.PlaceHolderColors;
import com.elishi.android.Common.RoundedBackground;
import com.elishi.android.Common.Utils;
import com.elishi.android.Modal.Home.BannerSlider;
import com.elishi.android.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;
import java.util.Random;

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
        BannerSlider bannerSlider = sliderItems.get(position);
        final int min = 0;
        final int max = PlaceHolderColors.PLACEHOLDERS.length - 1;
        final int r = new Random().nextInt((max - min) + 1) + min;
        holder.imageView.setBackground(RoundedBackground.create(PlaceHolderColors.PLACEHOLDERS[r], R.dimen.cornerRadius, context));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            holder.imageView.setClipToOutline(true);
        }
        String imageUrl = bannerSlider.getBanner_image_tm();
        if (Utils.getLanguage(context).equals("ru")) {
            imageUrl = bannerSlider.getBanner_image_ru();
        } else if (Utils.getLanguage(context).equals("en")) {
            imageUrl = bannerSlider.getBanner_image_en();
        }


        String extension = "";
        if (imageUrl.contains(".")) {
            extension = imageUrl.substring(imageUrl.lastIndexOf("."));
        }
        if (extension.toLowerCase().contains("gif")) {
            Glide.with(context)
                    .asGif()
                    .load(Constant.IMAGE_URL+imageUrl)
                    .timeout(60000).placeholder(PlaceHolderColors.PLACEHOLDERS[r])
                    .thumbnail(0.25f)
                    .into(holder.imageView);
        } else {
            Glide.with(context)
                    .load(Constant.IMAGE_URL+imageUrl)
                    .timeout(60000).placeholder(PlaceHolderColors.PLACEHOLDERS[r])
                    .thumbnail(0.25f)
                    .into(holder.imageView);
        }

        if (position == sliderItems.size() - 2) {
            viewPager2.post(runnable);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bannerSlider.getSiteURL()!=null && !bannerSlider.getSiteURL().trim().isEmpty()){
                    Click.bannerClick(bannerSlider,context);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return sliderItems.size();
    }

    class SliderViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

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
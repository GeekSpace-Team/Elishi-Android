package com.elishi.android.Adapter.Product;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.elishi.android.Common.Constant;
import com.elishi.android.Common.PlaceHolderColors;
import com.elishi.android.Modal.Product.Product;
import com.elishi.android.R;
import com.makeramen.roundedimageview.RoundedImageView;
import com.stfalcon.imageviewer.StfalconImageViewer;
import com.stfalcon.imageviewer.loader.ImageLoader;

import java.util.ArrayList;
import java.util.Random;

public class SingleProductImageAdapter extends RecyclerView.Adapter<SingleProductImageAdapter.ViewHolder> {
    private ArrayList<Product.Images> images=new ArrayList<>();
    private Context context;

    public SingleProductImageAdapter(ArrayList<Product.Images> images, Context context) {
        this.images = images;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_image,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            Product.Images image=images.get(holder.getAbsoluteAdapterPosition());
            final int min = 0;
            final int max = PlaceHolderColors.PLACEHOLDERS.length - 1;
            final int r = new Random().nextInt((max - min) + 1) + min;
            String extension = "";
            if (image.getSmall_image().contains(".")) {
                extension = image.getSmall_image().substring(image.getSmall_image().lastIndexOf("."));
            }
            if (extension.toLowerCase().contains("gif")) {
                Glide.with(context)
                        .asGif()
                        .load(Constant.IMAGE_URL+image.getSmall_image())
                        .timeout(60000).placeholder(PlaceHolderColors.PLACEHOLDERS[r])
                        .centerCrop()
                        .fitCenter()
                        .thumbnail(0.25f)
                        .into(holder.images);
            } else {
                Glide.with(context)
                        .load(Constant.IMAGE_URL+image.getSmall_image())
                        .timeout(60000).placeholder(PlaceHolderColors.PLACEHOLDERS[r])
                        .centerCrop()
                        .fitCenter()
                        .thumbnail(0.25f)
                        .into(holder.images);
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageLoader<Product.Images> imageLoader=new ImageLoader<Product.Images>() {
                    @Override
                    public void loadImage(ImageView imageView, Product.Images image) {
                        final int min = 0;
                        final int max = PlaceHolderColors.PLACEHOLDERS.length - 1;
                        final int r = new Random().nextInt((max - min) + 1) + min;
                        Glide.with(context)
                                .load(Constant.IMAGE_URL+image.getLarge_image())
                                .timeout(60000).placeholder(PlaceHolderColors.PLACEHOLDERS[r])
                                .thumbnail(0.25f)
                                .into(imageView);
                    }
                };
                StfalconImageViewer.Builder<Product.Images> builder=new StfalconImageViewer.Builder<Product.Images>(context,images,imageLoader);
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
        return images.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RoundedImageView images;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            images=itemView.findViewById(R.id.images);
        }
    }
}

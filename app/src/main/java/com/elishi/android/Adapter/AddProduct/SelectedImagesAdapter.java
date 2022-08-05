package com.elishi.android.Adapter.AddProduct;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.elishi.android.Common.ImageStatus;
import com.elishi.android.Fragment.AddProductFragment;
import com.elishi.android.Modal.AddProduct.SelectedImage;
import com.elishi.android.R;

import java.util.ArrayList;

public class SelectedImagesAdapter extends RecyclerView.Adapter<SelectedImagesAdapter.ViewHolder> {
    private ArrayList<SelectedImage> images = new ArrayList<>();
    private Context context;
    private RelativeLayout in_progress;
    public SelectedImagesAdapter(ArrayList<SelectedImage> images, Context context) {
        this.images = images;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.image_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SelectedImage image=images.get(holder.getAdapterPosition());
        if (images.get(holder.getAdapterPosition()).getType() != null && images.get(holder.getAdapterPosition()).getType() == 2) {
            hideAllButtons(holder);
            holder.add_image.setVisibility(View.VISIBLE);

            holder.add_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AddProductFragment.INSTANCE.selectImage(777);
                }
            });

        } else {
            if(image.getStatus().equals(ImageStatus.DEFAULT)) {
                hideAllButtons(holder);
                holder.remove.setVisibility(View.VISIBLE);

                holder.remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        images.remove(holder.getAdapterPosition());
                        if (images.size() <= 0) {
                            AddProductFragment.INSTANCE.getImageRecycler().setVisibility(View.GONE);
                            AddProductFragment.INSTANCE.getImageCon().setVisibility(View.VISIBLE);
                        }
                        notifyItemRemoved(holder.getAdapterPosition());

                    }
                });
            } else if(image.getStatus().equals(ImageStatus.IN_PROGRESS)){
                hideAllButtons(holder);
                holder.in_progress.setVisibility(View.VISIBLE);
            } else if(image.getStatus().equals(ImageStatus.ERROR)){
                hideAllButtons(holder);
                holder.remove.setVisibility(View.VISIBLE);
                holder.error.setVisibility(View.VISIBLE);
            } else if(image.getStatus().equals(ImageStatus.SUCCESS)){
                hideAllButtons(holder);
                holder.remove.setVisibility(View.VISIBLE);
                holder.success.setVisibility(View.VISIBLE);
            }

            Glide.with(context)
                    .applyDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.placeholder))
                    .load(image.getUri())
                    .into(holder.img);
        }





    }

    private void hideAllButtons(ViewHolder holder) {
        holder.remove.setVisibility(View.GONE);
        holder.success.setVisibility(View.GONE);
        holder.error.setVisibility(View.GONE);
        holder.in_progress.setVisibility(View.GONE);
        holder.add_image.setVisibility(View.GONE);
    }


    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img, remove,add_image;
        private CardView error,success;
        private RelativeLayout in_progress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            remove = itemView.findViewById(R.id.remove);
            img = itemView.findViewById(R.id.img);
            add_image = itemView.findViewById(R.id.addImageButton);
            in_progress = itemView.findViewById(R.id.in_progress);
            error = itemView.findViewById(R.id.error);
            success = itemView.findViewById(R.id.success);
        }
    }
}

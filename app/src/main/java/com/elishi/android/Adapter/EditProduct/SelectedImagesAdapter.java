package com.elishi.android.Adapter.EditProduct;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.elishi.android.Api.APIClient;
import com.elishi.android.Api.ApiInterface;
import com.elishi.android.Common.Constant;
import com.elishi.android.Common.ImageStatus;
import com.elishi.android.Common.Utils;
import com.elishi.android.Fragment.AddProductFragment;
import com.elishi.android.Fragment.Product.EditProduct;
import com.elishi.android.Modal.AddProduct.SelectedImage;
import com.elishi.android.Modal.Product.Product;
import com.elishi.android.Modal.Response.GBody;
import com.elishi.android.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        SelectedImage image = images.get(holder.getAbsoluteAdapterPosition());
        if (images.get(holder.getAbsoluteAdapterPosition()).getType() != null && images.get(holder.getAbsoluteAdapterPosition()).getType() == 2) {
            hideAllButtons(holder);
            holder.add_image.setVisibility(View.VISIBLE);

            holder.add_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditProduct.INSTANCE.selectImage(777);
                }
            });

        } else {
            if (image.getStatus().equals(ImageStatus.DEFAULT)) {
                hideAllButtons(holder);
                holder.remove.setVisibility(View.VISIBLE);
                holder.remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (image.getUrl().isEmpty()) {
                            images.remove(holder.getAbsoluteAdapterPosition());
                            notifyItemRemoved(holder.getAbsoluteAdapterPosition());
                        } else {
                            try {
//                            if (images.get(holder.getAbsoluteAdapterPosition()).getType() == 3) {
                                Product.Images dlt = null;
                                int pos=holder.getAbsoluteAdapterPosition();
                                try {
                                    ArrayList<Product.Images> imgs = EditProduct.INSTANCE.imageResponses;
                                    for (Product.Images img : imgs) {
                                        if (image.getUrl().equals(img.getSmall_image())) {
                                            dlt = img;
                                        }
                                    }
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                                if (dlt != null) {
                                    Dialog dialog = Utils.getDialogProgressBar(context);
                                    dialog.show();
                                    ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
                                    Call<GBody<String>> call = apiInterface.deleteSingleImage(dlt, "Bearer " + Utils.getSharedPreference(context, "tkn"));
                                    call.enqueue(new Callback<GBody<String>>() {
                                        @Override
                                        public void onResponse(Call<GBody<String>> call, Response<GBody<String>> response) {
                                            if (response.isSuccessful()) {
                                                deleteOldImage(image);
                                                images.remove(pos);
                                                notifyItemRemoved(pos);
                                            }
                                            dialog.dismiss();
                                        }

                                        @Override
                                        public void onFailure(Call<GBody<String>> call, Throwable t) {
                                            dialog.dismiss();
                                        }
                                    });
                                }
//                            }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                });
            } else if (image.getStatus().equals(ImageStatus.IN_PROGRESS)) {
                hideAllButtons(holder);
                holder.in_progress.setVisibility(View.VISIBLE);
            } else if (image.getStatus().equals(ImageStatus.ERROR)) {
                hideAllButtons(holder);
                holder.remove.setVisibility(View.VISIBLE);
                holder.error.setVisibility(View.VISIBLE);
            } else if (image.getStatus().equals(ImageStatus.SUCCESS)) {
                hideAllButtons(holder);
                holder.remove.setVisibility(View.VISIBLE);
                holder.success.setVisibility(View.VISIBLE);
            }

            if (images.get(holder.getAbsoluteAdapterPosition()).getType() != null && images.get(holder.getAbsoluteAdapterPosition()).getType() == 1) {
                Glide.with(context)
                        .applyDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.placeholder))
                        .load(image.getUri())
                        .into(holder.img);
            }

            if (images.get(holder.getAbsoluteAdapterPosition()).getType() != null && images.get(holder.getAbsoluteAdapterPosition()).getType() == 3) {
                Glide.with(context)
                        .applyDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.placeholder))
                        .load(Constant.IMAGE_URL + image.getUrl())
                        .into(holder.img);
            }

        }


    }

    private void deleteOldImage(SelectedImage image) {
        try {
            ArrayList<Product.Images> imgs = EditProduct.INSTANCE.imageResponses;
            for (Product.Images img : imgs) {
                if (image.getUrl().equals(img.getSmall_image())) {
                    EditProduct.INSTANCE.imageResponses.remove(img);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
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
        private ImageView img, remove, add_image;
        private CardView error, success;
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

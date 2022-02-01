package com.elishi.android.Adapter.Product;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.elishi.android.Common.Utils;
import com.elishi.android.Modal.Product.Product;
import com.elishi.android.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MultiSizeProductAdapter extends RecyclerView.Adapter<MultiSizeProductAdapter.ViewHolder> {
    private ArrayList<Product> products = new ArrayList<>();
    private Context context;
    private boolean isHorizontal = false;

    public MultiSizeProductAdapter(ArrayList<Product> products, Context context, boolean isHorizontal) {
        this.products = products;
        this.context = context;
        this.isHorizontal = isHorizontal;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_design, parent, false);
        return new MultiSizeProductAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MultiSizeProductAdapter.ViewHolder holder, int position) {
        Product product = products.get(holder.getAdapterPosition());
        holder.name.setText(product.getName());
        holder.cost.setText(product.getCost() + " TMT");
        holder.name.setTypeface(Utils.getRegularFont(context));
        holder.cost.setTypeface(Utils.getBoldFont(context));
        Glide.with(context)
                .load(product.getImage())
                .placeholder(R.drawable.placeholder)
                .into(holder.image);
        if (product.isFavourite()) {
            holder.fav.setImageResource(R.drawable.ic_fav);
        } else {
            holder.fav.setImageResource(R.drawable.ic_dis_fav);
        }

        if (isHorizontal) {
            RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) holder.container.getLayoutParams();
            lp.width = 650;
            holder.container.setLayoutParams(lp);
        }
        if((position%6)==0){
            StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            layoutParams.setFullSpan(true);
            setImageHeight((int) context.getResources().getDimension(R.dimen.productHeightOrginal),holder.image);
        } else if((position%3)==0){
            setImageHeight((int) context.getResources().getDimension(R.dimen.productHeight),holder.image);
        }

        holder.fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (product.isFavourite()) {
                    products.get(holder.getAdapterPosition()).setFavourite(false);
                    holder.fav.setImageResource(R.drawable.ic_dis_fav);
                } else {
                    products.get(holder.getAdapterPosition()).setFavourite(true);
                    holder.fav.setImageResource(R.drawable.ic_fav);
                }

            }
        });


    }

    private void setImageHeight(int dimension, ImageView image) {
        RelativeLayout.LayoutParams lm= (RelativeLayout.LayoutParams) image.getLayoutParams();
        lm.height=dimension;
        image.setLayoutParams(lm);
    }

    private void setGradient(ImageView fav) {

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, cost;
        ImageView image, fav;
        RelativeLayout container;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            cost = itemView.findViewById(R.id.cost);
            image = itemView.findViewById(R.id.image);
            fav = itemView.findViewById(R.id.fav);
            container = itemView.findViewById(R.id.container);
        }
    }
}

package com.elishi.android.Adapter.Category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.elishi.android.Common.Utils;
import com.elishi.android.Modal.Category.Category;
import com.elishi.android.R;
import com.google.android.material.card.MaterialCardView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ShortCategoryAdapter extends RecyclerView.Adapter<ShortCategoryAdapter.ViewHolder> {
    private ArrayList<Category> categories=new ArrayList<>();
    private Context context;


    public ShortCategoryAdapter(ArrayList<Category> categories, Context context) {
        this.categories = categories;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.short_category, parent,false);
        return new ShortCategoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ShortCategoryAdapter.ViewHolder holder, int position) {
        Category category=categories.get(position);
        holder.CategoryText.setText(category.getName());
        holder.CategoryText.setTypeface(Utils.getBoldFont(context));
        Glide.with(context)
                .load(category.getImage())
                .placeholder(R.drawable.placeholder)
                .into(holder.CategoryImage);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout CategoryCon;
        ImageView CategoryImage;
        MaterialCardView CategoryCard;
        TextView CategoryText;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            CategoryText=itemView.findViewById(R.id.CategoryText);
            CategoryCon=itemView.findViewById(R.id.CategoryCon);
            CategoryImage=itemView.findViewById(R.id.CategoryImage);
            CategoryCard=itemView.findViewById(R.id.imgCard);
        }
    }
}

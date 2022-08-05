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
import com.elishi.android.Activity.MainActivity;
import com.elishi.android.Common.Constant;
import com.elishi.android.Common.PlaceHolderColors;
import com.elishi.android.Common.Utils;
import com.elishi.android.Fragment.Product.Products;
import com.elishi.android.Modal.Category.Category;
import com.elishi.android.R;
import com.google.android.material.card.MaterialCardView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Random;

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
        holder.CategoryText.setText(category.getCategory_name_tm());
        if(Utils.getLanguage(context).equals("ru")){
            holder.CategoryText.setText(category.getCategory_name_ru());
        } else if(Utils.getLanguage(context).equals("en")){
            holder.CategoryText.setText(category.getCategory_name_en());
        }
        holder.CategoryText.setTypeface(Utils.getBoldFont(context));
        final int min = 0;
        final int max = PlaceHolderColors.PLACEHOLDERS.length-1;
        final int r = new Random().nextInt((max - min) + 1) + min;
        Glide.with(context)
                .load(Constant.IMAGE_URL+category.getImage())
                .timeout(60000).placeholder(PlaceHolderColors.PLACEHOLDERS[r])
                .into(holder.CategoryImage);

        if(category.getStatus()!=3){
            holder.statusBg.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.get().getBottomNavigationView().setSelectedItemId(R.id.category);
                Products.title=holder.CategoryText.getText().toString();
                Products.category=category.getId();
                Products.sub_category.clear();
                Products.region.clear();
                Products.status.clear();
                Products.min=null;
                Products.max=null;
                Products.page=1;
                Products.sort=0;
                Products.userId=null;
                Utils.productFragment(new Products(),Products.class.getSimpleName(),MainActivity.get().getSupportFragmentManager(),R.id.content);
                MainActivity.secondFragment= new Products();
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout CategoryCon,statusBg;
        ImageView CategoryImage;
        MaterialCardView CategoryCard;
        TextView CategoryText;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            CategoryText=itemView.findViewById(R.id.CategoryText);
            CategoryCon=itemView.findViewById(R.id.CategoryCon);
            CategoryImage=itemView.findViewById(R.id.CategoryImage);
            CategoryCard=itemView.findViewById(R.id.imgCard);
            statusBg=itemView.findViewById(R.id.statusBg);
        }
    }
}

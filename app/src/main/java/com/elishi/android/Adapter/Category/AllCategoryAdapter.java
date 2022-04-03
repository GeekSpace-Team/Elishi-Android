package com.elishi.android.Adapter.Category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.elishi.android.Common.PlaceHolderColors;
import com.elishi.android.Common.Utils;
import com.elishi.android.Fragment.Product.Products;
import com.elishi.android.Modal.Category.AllCategory;
import com.elishi.android.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Random;

public class AllCategoryAdapter extends RecyclerView.Adapter<AllCategoryAdapter.ViewHolder> {
    private ArrayList<AllCategory> allCategories = new ArrayList<>();
    private Context context;
    private FragmentManager fragmentManager;
    private Boolean isSecond=true;

    public AllCategoryAdapter(ArrayList<AllCategory> allCategories, Context context, FragmentManager fragmentManager) {
        this.allCategories = allCategories;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.all_category_design, parent, false);
        return new AllCategoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AllCategoryAdapter.ViewHolder holder, int position) {
        AllCategory category = allCategories.get(position);
        holder.title.setTypeface(Utils.getBoldFont(context));
        holder.title.setText(category.getTitle_tm());
        String language = Utils.getLanguage(context, "language");
        if (language.equals("ru")) {
            holder.title.setText(category.getTitle_ru());
        } else if (language.equals("en")) {
            holder.title.setText(category.getTitle_en());
        }

        if (category.getImages() != null) {
            switch (category.getImages().size()) {
                case 1:
                    setTemplate1(holder.imgContainer,category);
                    break;
                case 2:
                    setTemplate2(holder.imgContainer,category);
                    break;
                case 3:
                    setTemplate3(holder.imgContainer,category);
                    break;

            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.hideAdd(new Products(),Products.class.getSimpleName(),fragmentManager,R.id.content);
            }
        });

    }





    private void setTemplate2(LinearLayout imgContainer, AllCategory category) {
        View view=LayoutInflater.from(context).inflate(R.layout.category_template_1, imgContainer,false);
        ImageView img1=view.findViewById(R.id.img1);
        ImageView img2=view.findViewById(R.id.img2);
        final int min = 0;
        final int max = PlaceHolderColors.PLACEHOLDERS.length-1;
        final int r = new Random().nextInt((max - min) + 1) + min;
        Glide.with(context)
                .load(category.getImages().get(0))
                .placeholder(PlaceHolderColors.PLACEHOLDERS[r])
                .into(img1);

        Glide.with(context)
                .load(category.getImages().get(1))
                .placeholder(PlaceHolderColors.PLACEHOLDERS[r])
                .into(img2);

        imgContainer.removeAllViews();
        imgContainer.addView(view);
    }

    private void setTemplate1(LinearLayout imgContainer, AllCategory category) {
        View view=LayoutInflater.from(context).inflate(R.layout.category_template_3, imgContainer,false);
        ImageView img1=view.findViewById(R.id.img1);
        final int min = 0;
        final int max = PlaceHolderColors.PLACEHOLDERS.length-1;
        final int r = new Random().nextInt((max - min) + 1) + min;
        Glide.with(context)
                .load(category.getImages().get(0))
                .placeholder(PlaceHolderColors.PLACEHOLDERS[r])
                .into(img1);


        imgContainer.removeAllViews();
        imgContainer.addView(view);
    }

    private void setTemplate3(LinearLayout imgContainer, AllCategory category) {
        if(isSecond){
            View view=LayoutInflater.from(context).inflate(R.layout.category_template_2, imgContainer,false);
            ImageView img1=view.findViewById(R.id.img1);
            ImageView img2=view.findViewById(R.id.img2);
            ImageView img3=view.findViewById(R.id.img3);

            final int min = 0;
            final int max = PlaceHolderColors.PLACEHOLDERS.length-1;
            final int r = new Random().nextInt((max - min) + 1) + min;

            Glide.with(context)
                    .load(category.getImages().get(0))
                    .placeholder(PlaceHolderColors.PLACEHOLDERS[r])
                    .into(img1);

            Glide.with(context)
                    .load(category.getImages().get(1))
                    .placeholder(PlaceHolderColors.PLACEHOLDERS[r])
                    .into(img2);

            Glide.with(context)
                    .load(category.getImages().get(2))
                    .placeholder(PlaceHolderColors.PLACEHOLDERS[r])
                    .into(img3);

            imgContainer.removeAllViews();
            imgContainer.addView(view);
            isSecond=false;
        } else{
            setTemplate4(imgContainer,category);
        }
    }

    private void setTemplate4(LinearLayout imgContainer, AllCategory category) {
        View view=LayoutInflater.from(context).inflate(R.layout.category_template_4, imgContainer,false);
        ImageView img1=view.findViewById(R.id.img1);
        ImageView img2=view.findViewById(R.id.img2);
        ImageView img3=view.findViewById(R.id.img3);

        final int min = 0;
        final int max = PlaceHolderColors.PLACEHOLDERS.length-1;
        final int r = new Random().nextInt((max - min) + 1) + min;

        Glide.with(context)
                .load(category.getImages().get(0))
                .placeholder(PlaceHolderColors.PLACEHOLDERS[r])
                .into(img1);

        Glide.with(context)
                .load(category.getImages().get(1))
                .placeholder(PlaceHolderColors.PLACEHOLDERS[r])
                .into(img2);

        Glide.with(context)
                .load(category.getImages().get(2))
                .placeholder(PlaceHolderColors.PLACEHOLDERS[r])
                .into(img3);

        imgContainer.removeAllViews();
        imgContainer.addView(view);
        isSecond=true;
    }

    @Override
    public int getItemCount() {
        return allCategories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout imgContainer;
        private TextView title;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imgContainer = itemView.findViewById(R.id.imgContainer);
            title = itemView.findViewById(R.id.title);
        }
    }
}

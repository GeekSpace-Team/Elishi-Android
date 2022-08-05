package com.elishi.android.Adapter.Category;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.elishi.android.Activity.MainActivity;
import com.elishi.android.Common.Constant;
import com.elishi.android.Common.PlaceHolderColors;
import com.elishi.android.Common.SubCategoryColors;
import com.elishi.android.Common.Utils;
import com.elishi.android.Fragment.Product.Products;
import com.elishi.android.Modal.Category.SubCategory;
import com.elishi.android.R;
import com.elishi.android.View.CustomViews.AppTextView;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.Random;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.ViewHolder> {
    private ArrayList<SubCategory> subCategories=new ArrayList<>();
    private Context context;
    private FragmentManager fragmentManager;
    private final int[] SUBCATEGORYCOLORS=new int[]{
            android.R.color.transparent,
    };

    public SubCategoryAdapter(ArrayList<SubCategory> subCategories, Context context, FragmentManager fragmentManager) {
        this.subCategories = subCategories;
        this.context = context;
        this.fragmentManager = fragmentManager;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.sub_category_item,parent,false);
        return new SubCategoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SubCategory subCategory=subCategories.get(position);
        holder.name.setText(subCategory.getSub_category_name_tm());
        String language = Utils.getLanguage(context);
        if (language.equals("ru")) {
            holder.name.setText(subCategory.getSub_category_name_ru());
        } else if (language.equals("en")) {
            holder.name.setText(subCategory.getSub_category_name_en());
        }
        int min1 = 0;
        int max1 = SUBCATEGORYCOLORS.length - 1;
        int r1 = new Random().nextInt((max1 - min1) + 1) + min1;
        holder.card.setCardBackgroundColor(SUBCATEGORYCOLORS[r1]);
        Log.e("R",SUBCATEGORYCOLORS[r1]+"");
        if(subCategory.getId()!=-1){
            int min = 0;
            int max = PlaceHolderColors.PLACEHOLDERS.length - 1;
            int r = new Random().nextInt((max - min) + 1) + min;
            Glide.with(context)
                    .load(Constant.IMAGE_URL+subCategory.getImage())
                    .thumbnail(0.25f)
                    .into(holder.image);


            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Products.title=holder.name.getText().toString();
                    Products.category=null;
                    Products.sub_category.clear();
                    Products.sub_category.add(subCategory.getId());
                    Products.region.clear();
                    Products.status.clear();
                    Products.min=null;
                    Products.max=null;
                    Products.page=1;
                    Products.sort=0;
                    Products.userId=null;
                    Utils.productFragment(new Products(),Products.class.getSimpleName(),MainActivity.get().getSupportFragmentManager(),R.id.content);
                    MainActivity.secondFragment=new Products();
                }
            });
        } else {
            holder.name.setText(context.getResources().getString(R.string.seeAll));
            holder.image.setImageResource(R.drawable.ic_baseline_read_more_24);
            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Products.title=subCategory.getSub_category_name_tm();
                    if(Utils.getLanguage(context).equals("ru"))
                        Products.title=subCategory.getSub_category_name_ru();
                    if(Utils.getLanguage(context).equals("en"))
                        Products.title=subCategory.getSub_category_name_en();
                    Products.category=subCategory.getCategory_id();
                    Products.sub_category.clear();
                    Products.region.clear();
                    Products.status.clear();
                    Products.min=null;
                    Products.max=null;
                    Products.page=1;
                    Products.sort=0;
                    Products.userId=null;
                    Utils.productFragment(new Products(),Products.class.getSimpleName(),MainActivity.get().getSupportFragmentManager(),R.id.content);
                    MainActivity.secondFragment=new Products();
                }
            });
        }
        holder.card2.setBackgroundColor(context.getResources().getColor(SUBCATEGORYCOLORS[r1]));




    }

    @Override
    public int getItemCount() {
        return subCategories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private MaterialCardView card;
        private ImageView image;
        private AppTextView name;
        private RelativeLayout card2;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            card=itemView.findViewById(R.id.card);
            image=itemView.findViewById(R.id.image);
            card2=itemView.findViewById(R.id.card2);
        }
    }
}

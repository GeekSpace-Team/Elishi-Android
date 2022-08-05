package com.elishi.android.Adapter.Category;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.elishi.android.Common.Constant;
import com.elishi.android.Common.PlaceHolderColors;
import com.elishi.android.Common.Utils;
import com.elishi.android.Fragment.Product.Products;
import com.elishi.android.Modal.Category.AllCategory;
import com.elishi.android.Modal.Category.SubCategory;
import com.elishi.android.R;
import com.elishi.android.View.CustomViews.AppTextView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Random;

public class AllCategoryAdapter extends RecyclerView.Adapter<AllCategoryAdapter.ViewHolder> {
    private ArrayList<AllCategory> allCategories = new ArrayList<>();
    private Context context;
    private FragmentManager fragmentManager;
    private Boolean isFirst=true;
    private ViewHolder oldHolder;
    private RecyclerView subRecyclerView;

    public AllCategoryAdapter(ArrayList<AllCategory> allCategories, Context context, FragmentManager fragmentManager, RecyclerView subRecyclerView) {
        this.allCategories = allCategories;
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.subRecyclerView = subRecyclerView;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_item, parent, false);
        return new AllCategoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AllCategoryAdapter.ViewHolder holder, int position) {
        AllCategory category = allCategories.get(position);
        holder.title.setTypeface(Utils.getBoldFont(context));
        holder.title.setText(category.getCategory_name_tm());
        String language = Utils.getLanguage(context);
        if (language.equals("ru")) {
            holder.title.setText(category.getCategory_name_ru());
        }
        if (language.equals("en")) {
            holder.title.setText(category.getCategory_name_en());
        }
        setPassive(holder);
        if(isFirst){
            setActive(holder);
            setSubCategory(category);
            isFirst=false;
            oldHolder=holder;
        }

        final int min = 0;
        final int max = PlaceHolderColors.PLACEHOLDERS.length - 1;
        final int r = new Random().nextInt((max - min) + 1) + min;
        Glide.with(context)
                .load(Constant.IMAGE_URL+category.getImage())
                .timeout(60000).placeholder(PlaceHolderColors.PLACEHOLDERS[r])
                .thumbnail(0.25f)
                .into(holder.image);


        holder.con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(oldHolder!=null){
                    setPassive(oldHolder);
                }
                setActive(holder);
                setSubCategory(category);
                oldHolder=holder;
            }
        });


    }

    private void setSubCategory(AllCategory category) {
        ArrayList<SubCategory> sub_category = null;
        if(category.getSub_category()==null){
            sub_category=new ArrayList<>();
        } else {
            sub_category=category.getSub_category();
            Log.e("Size",category.getSub_category().size()+"");
        }

        if(sub_category.size()>0){
            if(sub_category.get(sub_category.size()-1).getId()!=-1){
                sub_category.add(new SubCategory(-1,
                        category.getCategory_name_tm(),
                        category.getCategory_name_ru(),
                        category.getCategory_name_en(),
                        category.getId(),
                        1,
                        "",
                        "",
                        category.getImage()));
            }
        } else {
            sub_category.add(new SubCategory(-1,
                    category.getCategory_name_tm(),
                    category.getCategory_name_ru(),
                    category.getCategory_name_en(),
                    category.getId(),
                    1,
                    "",
                    "",
                    category.getImage()));
        }

        subRecyclerView.setAdapter(new SubCategoryAdapter(sub_category,context,fragmentManager));
        subRecyclerView.setLayoutManager(new GridLayoutManager(context,2));
    }

    private void setPassive(ViewHolder holder) {
        holder.stroke.setVisibility(View.GONE);
        holder.title.setTextColor(context.getResources().getColor(R.color.gray3));
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
        holder.image.setColorFilter(filter);
        holder.imgBg.setBackgroundResource(R.drawable.category_cyrcle_passive);
        holder.container.setBackgroundResource(android.R.color.transparent);
    }

    private void setActive(ViewHolder holder) {
        holder.stroke.setVisibility(View.VISIBLE);
        holder.title.setTextColor(context.getResources().getColor(R.color.second));
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(1);
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
        holder.image.setColorFilter(filter);
        holder.imgBg.setBackgroundResource(R.drawable.category_cyrcle_active);
        holder.container.setBackgroundResource(R.color.white);
    }


    @Override
    public int getItemCount() {
        return allCategories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout container,imgBg;
        private AppTextView title;
        private View stroke;
        private ImageView image;
        private LinearLayout con;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container);
            stroke = itemView.findViewById(R.id.stroke);
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.name);
            imgBg = itemView.findViewById(R.id.imgBg);
            con = itemView.findViewById(R.id.con);
        }
    }
}

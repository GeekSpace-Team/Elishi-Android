package com.elishi.android.Adapter.Filter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.elishi.android.Common.FilterType;
import com.elishi.android.Common.Utils;
import com.elishi.android.Fragment.Product.Products;
import com.elishi.android.Fragment.ProductFilter.FilterItems;
import com.elishi.android.Modal.Category.SubCategory;
import com.elishi.android.Modal.Filter.FilterItem;
import com.elishi.android.Modal.Filter.FilterList;
import com.elishi.android.Modal.Response.PublicAPI.SubLocations;
import com.elishi.android.R;
import com.elishi.android.View.CustomViews.AppTextView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FilterItemAdapter extends RecyclerView.Adapter<FilterItemAdapter.ViewHolder> {

    private FilterList filterList;
    private Context context;
    private ViewHolder oldHolder = null;
    private String query = "";

    public FilterItemAdapter(FilterList filterList, Context context, String query) {
        this.filterList = filterList;
        this.context = context;
        this.query = query;
        Utils.loadLocal(context);
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.filter_item_design, parent, false);
        return new FilterItemAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FilterItemAdapter.ViewHolder holder, int position) {

        holder.setIsRecyclable(false);
        if (filterList.getType().equals(FilterType.CATEGORY)) {
            SubCategory subCategory = filterList.getCategory().get(holder.getAbsoluteAdapterPosition());
            if (query.trim().isEmpty()) {
                holder.itemView.setVisibility(View.VISIBLE);
                holder.itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                setCategory(subCategory,holder);
            } else {
                if(subCategory.getSub_category_name_tm().toUpperCase().contains(query.toUpperCase())
                || subCategory.getSub_category_name_en().toUpperCase().contains(query.toUpperCase())
                || subCategory.getSub_category_name_ru().toUpperCase().contains(query.toUpperCase())){
                    setCategory(subCategory,holder);
                } else {
                    holder.itemView.setVisibility(View.GONE);
                    holder.itemView.setLayoutParams(new ViewGroup.LayoutParams(0, 0));
                }
            }
        }

        if (filterList.getType().equals(FilterType.LOCATION)) {
            SubLocations subLocations = filterList.getRegions().get(holder.getAbsoluteAdapterPosition());
            holder.title.setText(subLocations.getDistrict_name_tm());
            if(query.trim().isEmpty()){
                holder.itemView.setVisibility(View.VISIBLE);
                holder.itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                setLocation(subLocations,holder);
            } else {
                if(subLocations.getDistrict_name_tm().toUpperCase().contains(query.toUpperCase())
                        || subLocations.getDistrict_name_en().toUpperCase().contains(query.toUpperCase())
                        || subLocations.getDistrict_name_ru().toUpperCase().contains(query.toUpperCase())){
                    setLocation(subLocations,holder);
                } else {
                    holder.itemView.setVisibility(View.GONE);
                    holder.itemView.setLayoutParams(new ViewGroup.LayoutParams(0, 0));
                }
            }
        }

        if (filterList.getType().equals(FilterType.PRICE)) {
            FilterItem filterItem = filterList.getFilterItems().get(holder.getAbsoluteAdapterPosition());
            holder.title.setText(filterItem.getTitle_tm() + " TMT - " + filterItem.getTitle_ru() + " TMT");

            try {
                Double min = Double.parseDouble(filterItem.getTitle_tm());
                Double max = Double.parseDouble(filterItem.getTitle_ru());
                if (min.equals(Products.min) && max.equals(Products.max)) {
                    setActive(holder);
                    oldHolder = holder;
                    FilterItems.get().getMin().setText(filterItem.getTitle_tm());
                    FilterItems.get().getMax().setText(filterItem.getTitle_ru());
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (oldHolder != null) {
                        setPassive(oldHolder);
                    }
                    setActive(holder);
                    oldHolder = holder;
                    FilterItems.get().getMin().setText(filterItem.getTitle_tm());
                    FilterItems.get().getMax().setText(filterItem.getTitle_ru());
                    try {
                        Products.min = Double.parseDouble(filterItem.getTitle_tm());
                        Products.max = Double.parseDouble(filterItem.getTitle_ru());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        Products.min = null;
                        Products.max = null;
                    }
                }
            });
        }

        if (filterList.getType().equals(FilterType.STATUS)) {
            FilterItem filterItem = filterList.getFilterItems().get(holder.getAbsoluteAdapterPosition());
            holder.title.setText(filterItem.getTitle_tm());
            if (Utils.getLanguage(context).equals("ru")) {
                holder.title.setText(filterItem.getTitle_ru());
            }
            if (Utils.getLanguage(context).equals("en")) {
                holder.title.setText(filterItem.getTitle_en());
            }

            try {
                if (Products.status.contains(filterItem.getId())) {
                    setActive(holder);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        if (Products.status.contains(filterItem.getId())) {
                            Products.status.remove(filterItem.getId());
                            setPassive(holder);
                        } else {
                            Products.status.add(filterItem.getId());
                            setActive(holder);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
        }

    }

    private void setLocation(SubLocations subLocations, ViewHolder holder) {
        if (Utils.getLanguage(context).equals("ru")) {
            holder.title.setText(subLocations.getDistrict_name_ru());
        }
        if (Utils.getLanguage(context).equals("en")) {
            holder.title.setText(subLocations.getDistrict_name_en());
        }

        try {
            if (Products.region.contains(subLocations.getId())) {
                setActive(holder);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (Products.region.contains(subLocations.getId())) {
                        Products.region.remove(subLocations.getId());
                        setPassive(holder);
                    } else {
                        Products.region.add(subLocations.getId());
                        setActive(holder);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private void setCategory(SubCategory subCategory, ViewHolder holder) {
        holder.title.setText(subCategory.getSub_category_name_tm());
        if (Utils.getLanguage(context).equals("ru")) {
            holder.title.setText(subCategory.getSub_category_name_ru());
        }
        if (Utils.getLanguage(context).equals("en")) {
            holder.title.setText(subCategory.getSub_category_name_en());
        }

        try {
            if (Products.sub_category.contains(subCategory.getId())) {
                setActive(holder);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    if (Products.sub_category.contains(subCategory.getId())) {
                        Products.sub_category.remove(subCategory.getId());
                        setPassive(holder);
                    } else {
                        Products.sub_category.add(subCategory.getId());
                        setActive(holder);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private void setActive(ViewHolder holder) {
        holder.title.createView(3);
        holder.check.setVisibility(View.VISIBLE);
        holder.title.setTextColor(context.getResources().getColor(R.color.second));
    }

    private void setPassive(ViewHolder holder) {
        holder.title.createView(4);
        holder.check.setVisibility(View.GONE);
        holder.title.setTextColor(context.getResources().getColor(R.color.fourth));
    }

    @Override
    public int getItemCount() {
        if (filterList.getType().equals(FilterType.CATEGORY))
            return filterList.getCategory().size();
        else if (filterList.getType().equals(FilterType.LOCATION))
            return filterList.getRegions().size();
        else
            return filterList.getFilterItems().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private AppTextView title;
        private ImageView check;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            check = itemView.findViewById(R.id.check);
            title = itemView.findViewById(R.id.title);
        }
    }
}

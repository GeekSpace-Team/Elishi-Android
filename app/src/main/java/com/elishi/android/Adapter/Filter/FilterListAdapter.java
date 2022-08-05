package com.elishi.android.Adapter.Filter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.elishi.android.Common.FilterType;
import com.elishi.android.Common.Utils;
import com.elishi.android.Fragment.Product.Products;
import com.elishi.android.Fragment.ProductFilter.FilterItems;
import com.elishi.android.Modal.Filter.FilterList;
import com.elishi.android.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FilterListAdapter extends RecyclerView.Adapter<FilterListAdapter.ViewHolder> {
    private ArrayList<FilterList> arrayList = new ArrayList<>();
    private Context context;
    private FragmentManager fragmentManager;

    public FilterListAdapter(ArrayList<FilterList> arrayList, Context context, FragmentManager supportFragmentManager) {
        this.arrayList = arrayList;
        this.context = context;
        this.fragmentManager = supportFragmentManager;
        Utils.loadLocal(context);
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.filter_list_design, parent, false);
        return new FilterListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FilterListAdapter.ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        FilterList filterList=arrayList.get(position);
        holder.title.setText(filterList.getTitle_tm());
        if(Utils.getLanguage(context).equals("ru")){
            holder.title.setText(filterList.getTitle_ru());
        }

        if(Utils.getLanguage(context).equals("en")){
            holder.title.setText(filterList.getTitle_en());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction().replace(R.id.filterRoot,new FilterItems(filterList,holder.title.getText().toString(),arrayList),FilterItems.class.getSimpleName()).commitNow();

            }
        });

        if(filterList.getType().equals(FilterType.CATEGORY)){
            holder.count.setText("("+Products.sub_category.size()+")");
        }

        if(filterList.getType().equals(FilterType.PRICE)){
            if(Products.min!=null && Products.max!=null){
                holder.count.setText("("+Products.min+" TMT - "+Products.max+" TMT)");
            } else {
                holder.count.setText("(0)");
            }
        }

        if(filterList.getType().equals(FilterType.STATUS)){
            holder.count.setText("("+Products.status.size()+")");
        }

        if(filterList.getType().equals(FilterType.LOCATION)){
            holder.count.setText("("+Products.region.size()+")");
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, title2, count;
        ImageView arrow;
        View stroke;
        LinearLayout con1, con2;
        SwitchCompat switch1;
        RelativeLayout container;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            arrow = itemView.findViewById(R.id.arrow);
            title = itemView.findViewById(R.id.title);
            title2 = itemView.findViewById(R.id.title2);
            stroke = itemView.findViewById(R.id.stroke);
            con1 = itemView.findViewById(R.id.con1);
            con2 = itemView.findViewById(R.id.con2);
            switch1 = itemView.findViewById(R.id.switch1);
            container = itemView.findViewById(R.id.container);
            count = itemView.findViewById(R.id.count);
        }
    }




}

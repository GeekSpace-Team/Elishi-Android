package com.elishi.android.Adapter.Filter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.elishi.android.Modal.Filter.FilterItem;
import com.elishi.android.R;
import com.elishi.android.View.CustomViews.AppTextView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FilterItemAdapter extends RecyclerView.Adapter<FilterItemAdapter.ViewHolder> {

    private ArrayList<FilterItem> arrayList = new ArrayList<>();
    private Context context;

    public FilterItemAdapter(ArrayList<FilterItem> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
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
        FilterItem object = arrayList.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.title.createView(3);
                holder.check.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
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

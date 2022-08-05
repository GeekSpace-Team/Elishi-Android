package com.elishi.android.Adapter.Category;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.elishi.android.Adapter.Location.SubLocationAdapter;
import com.elishi.android.Common.Utils;
import com.elishi.android.Modal.Category.AllCategory;
import com.elishi.android.Modal.Response.PublicAPI.Locations;
import com.elishi.android.R;

import java.util.ArrayList;

public class SelectCategoryAdapter extends RecyclerView.Adapter<SelectCategoryAdapter.ViewHolder> {
    private ArrayList<AllCategory> regions = new ArrayList<>();
    private Context context;
    private Dialog dialog;
    RecyclerView old_rec = null;
    ImageView old_arrow = null;

    public SelectCategoryAdapter(ArrayList<AllCategory> regions, Context context, Dialog dialog) {
        this.regions = regions;
        this.context = context;
        this.dialog = dialog;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.location_design, parent, false);
        return new SelectCategoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        AllCategory category = regions.get(position);
        if(category.getSub_category()==null || category.getSub_category().size()<=0){
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
        }
        holder.region.setText(category.getCategory_name_tm());
        holder.region.setTypeface(Utils.getRegularFont(context));
        if (Utils.getLanguage(context).equals("ru")) {
            holder.region.setText(category.getCategory_name_ru());
        } else if (Utils.getLanguage(context).equals("en")) {
            holder.region.setText(category.getCategory_name_en());
        }
        if (category.getSub_category() != null && category.getSub_category().size() > 0) {
            SelectSubCategoryAdapter subLocationAdapter = new SelectSubCategoryAdapter(category.getSub_category(), context, dialog);
            holder.subLocationRec.setAdapter(subLocationAdapter);
            holder.subLocationRec.setLayoutManager(new LinearLayoutManager(context));
            holder.con.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.subLocationRec.getVisibility() == View.VISIBLE) {
                        collapse(holder.subLocationRec, holder.arrow);
                    } else {
                        expand(holder.subLocationRec, holder.arrow);
                    }
                }
            });
        }
    }

    private void collapse(RecyclerView recyclerView, ImageView arrow) {
        collapseAnimation(recyclerView);
        arrow.setRotation(0);

//        arrow.setImageResource(R.drawable.more);
    }

    private void expand(RecyclerView recyclerView, ImageView arrow) {
        expandAnimation(recyclerView);
        recyclerView.setVisibility(View.VISIBLE);

        if (old_rec != null && old_arrow.getRotation() != 0) {
            collapseAnimation(old_rec);
            old_arrow.setRotation(0);
        }
        arrow.setRotation(90);
        old_arrow = arrow;
        old_rec = recyclerView;
//        arrow.setImageResource(R.drawable.less);
    }

    public static void expandAnimation(final View v) {
        int matchParentMeasureSpec = View.MeasureSpec.makeMeasureSpec(((View) v.getParent()).getWidth(), View.MeasureSpec.EXACTLY);
        int wrapContentMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        v.measure(matchParentMeasureSpec, wrapContentMeasureSpec);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? LinearLayout.LayoutParams.WRAP_CONTENT
                        : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // Expansion speed of 1dp/ms
        a.setDuration(300);
        v.startAnimation(a);
    }

    public static void collapseAnimation(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // Collapse speed of 1dp/ms
        a.setDuration(300);
        v.startAnimation(a);
    }

    @Override
    public int getItemCount() {
        return regions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView region;
        RecyclerView subLocationRec;
        ImageView arrow;
        LinearLayout con;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subLocationRec = itemView.findViewById(R.id.subLocRec);
            region = itemView.findViewById(R.id.region);
            arrow = itemView.findViewById(R.id.arrow);
            con = itemView.findViewById(R.id.con);
        }
    }
}

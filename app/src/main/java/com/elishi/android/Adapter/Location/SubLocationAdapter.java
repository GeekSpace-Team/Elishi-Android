package com.elishi.android.Adapter.Location;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.elishi.android.Common.Utils;
import com.elishi.android.Modal.Location.Region;
import com.elishi.android.Modal.Location.SubLocation;
import com.elishi.android.R;

import java.util.ArrayList;

public class SubLocationAdapter extends RecyclerView.Adapter<SubLocationAdapter.ViewHolder> {
    private ArrayList<SubLocation> subLocations=new ArrayList<>();
    private Context context;
    private Dialog dialog;
    private ImageView oldImg=null;

    public SubLocationAdapter(ArrayList<SubLocation> subLocations, Context context, Dialog dialog) {
        this.subLocations = subLocations;
        this.context = context;
        this.dialog = dialog;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.sub_loc_design, parent, false);
        return new SubLocationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SubLocation subLocation=subLocations.get(position);
        holder.title.setText(subLocation.getTitle_tm());

        holder.title.setTypeface(Utils.getRegularFont(context));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(oldImg!=null){
                    oldImg.setImageResource(R.drawable.ic_baseline_radio_button_unchecked_24);
                }
                holder.image.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_cyrcle_bg));
                oldImg=holder.image;
                dialog.dismiss();
            }
        });

    }

    @Override
    public int getItemCount() {
        return subLocations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            image=itemView.findViewById(R.id.image);
        }
    }
}

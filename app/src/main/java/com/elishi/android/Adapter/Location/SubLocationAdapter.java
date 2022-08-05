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
import com.elishi.android.Fragment.Profile.CreateAccount;
import com.elishi.android.Fragment.Profile.EditProfile;
import com.elishi.android.Modal.Location.Region;
import com.elishi.android.Modal.Location.SubLocation;
import com.elishi.android.Modal.Response.PublicAPI.SubLocations;
import com.elishi.android.R;

import java.util.ArrayList;

public class SubLocationAdapter extends RecyclerView.Adapter<SubLocationAdapter.ViewHolder> {
    private ArrayList<SubLocations> subLocations = new ArrayList<>();
    private Context context;
    private Dialog dialog;
    private ImageView oldImg = null;

    public SubLocationAdapter(ArrayList<SubLocations> subLocations, Context context, Dialog dialog) {
        this.subLocations = subLocations;
        this.context = context;
        this.dialog = dialog;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sub_loc_design, parent, false);
        return new SubLocationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SubLocations subLocation = subLocations.get(position);
        holder.title.setText(subLocation.getDistrict_name_tm());
        if (Utils.getLanguage(context).equals("ru")) {
            holder.title.setText(subLocation.getDistrict_name_ru());
        } else if (Utils.getLanguage(context).equals("en")) {
            holder.title.setText(subLocation.getDistrict_name_en());
        }
       try {
           if(CreateAccount.districtId!=0 && CreateAccount.districtId.equals(subLocation.getId())){
               holder.image.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_cyrcle_bg));
               oldImg = holder.image;
           }
       } catch (Exception ex){
           ex.printStackTrace();
       }

        try {
            if(EditProfile.districtId!=0 && EditProfile.districtId.equals(subLocation.getId())){
                holder.image.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_cyrcle_bg));
                oldImg = holder.image;
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }

        holder.title.setTypeface(Utils.getRegularFont(context));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (oldImg != null) {
                    oldImg.setImageResource(R.drawable.ic_baseline_radio_button_unchecked_24);
                }
                holder.image.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_cyrcle_bg));
                oldImg = holder.image;
                CreateAccount.districtId=subLocation.getId();
                CreateAccount.districtName=holder.title.getText().toString();

                EditProfile.districtId=subLocation.getId();
                EditProfile.districtName=holder.title.getText().toString();
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
            title = itemView.findViewById(R.id.title);
            image = itemView.findViewById(R.id.image);
        }
    }
}

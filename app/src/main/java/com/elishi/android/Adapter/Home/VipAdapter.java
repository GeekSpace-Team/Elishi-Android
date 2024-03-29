package com.elishi.android.Adapter.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.elishi.android.Common.Constant;
import com.elishi.android.Common.PlaceHolderColors;
import com.elishi.android.Common.Utils;
import com.elishi.android.Modal.Profile.User;
import com.elishi.android.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Random;

public class VipAdapter extends RecyclerView.Adapter<VipAdapter.ViewHolder> {
    private ArrayList<User> users=new ArrayList<>();
    private Context context;

    public VipAdapter(ArrayList<User> users, Context context) {
        this.users = users;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.vip_user, parent,false);
        return new VipAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull VipAdapter.ViewHolder holder, int position) {
        User user=users.get(position);
        final int min = 0;
        final int max = PlaceHolderColors.PLACEHOLDERS.length-1;
        final int r = new Random().nextInt((max - min) + 1) + min;
        Glide.with(context)
                .load(Constant.IMAGE_URL+user.getProfile_image())
                .timeout(60000).placeholder(PlaceHolderColors.PLACEHOLDERS[r])
                .into(holder.image);
        holder.name.setText(user.getFullname());
        holder.name.setSingleLine();
        holder.name.setTypeface(Utils.getMediumFont(context));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.username);
            image=itemView.findViewById(R.id.imageSrc);
        }
    }
}

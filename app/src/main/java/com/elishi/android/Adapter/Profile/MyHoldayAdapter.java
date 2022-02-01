package com.elishi.android.Adapter.Profile;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.elishi.android.Activity.MainActivity;
import com.elishi.android.Common.Utils;
import com.elishi.android.Fragment.Holiday.HolidayView;
import com.elishi.android.Modal.Profile.MyHoliday;
import com.elishi.android.Modal.Profile.MyProduct;
import com.elishi.android.R;
import com.elishi.android.databinding.MyHolidayItemBinding;
import com.elishi.android.databinding.MyProductItemBinding;

import java.util.ArrayList;

public class MyHoldayAdapter extends RecyclerView.Adapter<MyHoldayAdapter.ViewHolder> {
    private ArrayList<MyHoliday> myHolidays=new ArrayList<>();
    private Context context;
    private MyHolidayItemBinding binding;
    private FragmentManager fragmentManager;
    public MyHoldayAdapter(ArrayList<MyHoliday> myHolidays, Context context,FragmentManager fragmentManager) {
        this.myHolidays = myHolidays;
        this.context = context;
        this.fragmentManager=fragmentManager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding=MyHolidayItemBinding.inflate(LayoutInflater.from(context));
        return new MyHoldayAdapter.ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyHoliday holiday=myHolidays.get(position);
        Glide.with(context)
                .load(holiday.getImage())
                .placeholder(R.drawable.ic_baseline_person_outline_24)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.image.setVisibility(View.GONE);
                        holder.realImage.setVisibility(View.VISIBLE);
                        return false;
                    }
                })
                .into(holder.realImage);
        binding.name.setText(holiday.getName());
        binding.date.setText(holiday.getDate());
        binding.desc.setText(holiday.getDesc());

        binding.name.setTypeface(Utils.getMediumFont(context));
        binding.date.setTypeface(Utils.getRegularFont(context));
        binding.desc.setTypeface(Utils.getRegularFont(context));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.hideAdd(new HolidayView(),HolidayView.class.getSimpleName(),fragmentManager,R.id.content);
                MainActivity.fifthFragment=new HolidayView();
            }
        });

        binding.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(android.content.Intent.EXTRA_SUBJECT, holiday.getName());
                intent.putExtra(android.content.Intent.EXTRA_TEXT, holiday.getDesc());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return myHolidays.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image,realImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.image);
            realImage=itemView.findViewById(R.id.realImage);
        }
    }
}

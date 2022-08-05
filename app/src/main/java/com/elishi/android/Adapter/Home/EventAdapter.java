package com.elishi.android.Adapter.Home;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.elishi.android.Adapter.Product.MultiSizeProductAdapter;
import com.elishi.android.Common.Click;
import com.elishi.android.Common.Constant;
import com.elishi.android.Common.EventType;
import com.elishi.android.Common.PlaceHolderColors;
import com.elishi.android.Common.Utils;
import com.elishi.android.Modal.Home.Events;
import com.elishi.android.R;


import java.util.Random;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {
    private ArrayList<Events> events=new ArrayList<>();
    private Context context;

    public EventAdapter(ArrayList<Events> events, Context context) {
        this.events = events;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.event_item,parent,false);
        return new EventAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Events event=events.get(position);
        String imageUrl="";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            holder.image.setClipToOutline(true);
        }
        if(event.getType().equals(EventType.EVENT)){
            holder.eventProducts.setVisibility(View.GONE);
            holder.image.setVisibility(View.VISIBLE);
            imageUrl=event.getEvent().getEvent_image();
        } else if(event.getType().equals(EventType.ADS)){
            holder.eventProducts.setVisibility(View.GONE);
            holder.image.setVisibility(View.VISIBLE);
            imageUrl=event.getAds().getAds_image();
        } else if(event.getType().equals(EventType.EVENT_PRODUCTS)){
            String title=event.getEventProducts().getEvent().getTitle_tm();
            if(Utils.getLanguage(context).equals("ru")){
                title=event.getEventProducts().getEvent().getTitle_ru();
            }
            if(Utils.getLanguage(context).equals("en")){
                title=event.getEventProducts().getEvent().getTitle_en();
            }
            holder.eventProducts.setVisibility(View.VISIBLE);
            holder.image.setVisibility(View.GONE);
            holder.eventTitle.setText(title);
            holder.eventRec.setLayoutManager(new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false));
            holder.eventRec.setAdapter(new MultiSizeProductAdapter(event.getEventProducts().getProducts(),context,true));
        }

        if(event.getType().equals(EventType.EVENT) || event.getType().equals(EventType.ADS)){
            final int min = 0;
            final int max = PlaceHolderColors.PLACEHOLDERS.length - 1;
            final int r = new Random().nextInt((max - min) + 1) + min;
            String extension = "";
            if (imageUrl.contains(".")) {
                extension = imageUrl.substring(imageUrl.lastIndexOf("."));
            }
            if (extension.toLowerCase().contains("gif")) {
                Glide.with(context)
                        .asGif()
                        .load(Constant.IMAGE_URL+imageUrl)
                        .timeout(60000).placeholder(PlaceHolderColors.PLACEHOLDERS[r])
                        .thumbnail(0.25f)
                        .into(holder.image);
            } else {
                Glide.with(context)
                        .load(Constant.IMAGE_URL+imageUrl)
                        .timeout(60000).placeholder(PlaceHolderColors.PLACEHOLDERS[r])
                        .thumbnail(0.25f)
                        .into(holder.image);
            }

            if(event.getType().equals(EventType.EVENT)){
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Click.eventClick(event.getEvent(),context);
                    }
                });
            }

            if(event.getType().equals(EventType.ADS)){
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Click.adsClick(event.getAds(),context);
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private LinearLayout eventProducts;
        private RecyclerView eventRec;
        private TextView eventTitle;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            eventTitle=itemView.findViewById(R.id.eventTitle);
            image=itemView.findViewById(R.id.image);
            eventProducts=itemView.findViewById(R.id.eventProducts);
            eventRec=itemView.findViewById(R.id.eventRec);
        }
    }
}

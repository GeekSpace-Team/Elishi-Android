package com.elishi.android.Fragment.Product;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.elishi.android.Adapter.Product.MultiSizeProductAdapter;
import com.elishi.android.Adapter.Product.SingleProductImageAdapter;
import com.elishi.android.Common.Click;
import com.elishi.android.Common.Constant;
import com.elishi.android.Common.PlaceHolderColors;
import com.elishi.android.Common.Utils;
import com.elishi.android.Modal.Home.Ads;
import com.elishi.android.Modal.Product.GetSingleProduct;
import com.elishi.android.Modal.Product.Product;
import com.elishi.android.R;
import com.elishi.android.View.CustomViews.AppTextView;
import com.elishi.android.databinding.FragmentDescriptionBinding;

import java.util.ArrayList;
import java.util.Random;


public class Description extends Fragment {

    private String type;
    private FragmentDescriptionBinding binding;
    public ArrayList<Product> similarProducts=new ArrayList<>();
    public Product product;
    public GetSingleProduct getSingleProduct;
    private Context context;
    public Description() {
    }

    public static Description newInstance(String type) {
        Description fragment = new Description();
        Bundle args = new Bundle();
        args.putString("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getString("type");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context=getContext();
        Utils.loadLocal(context);
        binding=FragmentDescriptionBinding.inflate(inflater,container,false);
        setDetails();
        return binding.getRoot();
    }





    private void setDetails() {
        binding.details.setVisibility(View.VISIBLE);
        checkEmpty(binding.size,binding.sizeCon,product.getSize());
        checkEmpty(binding.phoneNumber,binding.phoneNumberCon,product.getPhone_number());
        checkEmpty(binding.address,binding.addRessCon,product.getRegion_name_tm()+", "+product.getDistrict_name_tm());
        checkEmpty(binding.category,binding.categoryCon,product.getSub_category_name_tm());
        if(Utils.getLanguage(context).equals("ru")){
            checkEmpty(binding.address,binding.addRessCon,product.getRegion_name_ru()+", "+product.getDistrict_name_ru());
            checkEmpty(binding.category,binding.categoryCon,product.getSub_category_name_ru());
        }
        if(Utils.getLanguage(context).equals("en")){
            checkEmpty(binding.address,binding.addRessCon,product.getRegion_name_en()+", "+product.getDistrict_name_en());
            checkEmpty(binding.category,binding.categoryCon,product.getSub_category_name_en());
        }
        if(getSingleProduct!=null && getSingleProduct.getAds()!=null){
            setAds(getSingleProduct.getAds());
        } else {
            binding.adsCard.setVisibility(View.GONE);
        }
    }

    private void setAds(Ads ads) {
        final int min = 0;
        final int max = PlaceHolderColors.PLACEHOLDERS.length - 1;
        final int r = new Random().nextInt((max - min) + 1) + min;
        String extension = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            binding.adsImg.setClipToOutline(true);
        }
        if(ads.getAds_image()==null){
            binding.adsImg.setVisibility(View.GONE);
            return;
        }
        if (ads.getAds_image().contains(".")) {
            extension = ads.getAds_image().substring(ads.getAds_image().lastIndexOf("."));
        }
        if (extension.toLowerCase().contains("gif")) {
            Glide.with(context)
                    .asGif()
                    .load(Constant.IMAGE_URL+ads.getAds_image())
                    .timeout(60000).placeholder(PlaceHolderColors.PLACEHOLDERS[r])
                    .thumbnail(0.25f)
                    .into(binding.adsImg);
        } else {
            Glide.with(context)
                    .load(Constant.IMAGE_URL+ads.getAds_image())
                    .timeout(60000).placeholder(PlaceHolderColors.PLACEHOLDERS[r])
                    .thumbnail(0.25f)
                    .into(binding.adsImg);
        }

        binding.adsImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Click.adsClick(ads,context);
            }
        });
    }

    private void checkEmpty(AppTextView tv, AppTextView con, String string) {
        if(string == null || string.isEmpty() || string.trim().isEmpty()) {
            tv.setVisibility(View.GONE);
            con.setVisibility(View.GONE);
        } else {
            tv.setVisibility(View.VISIBLE);
            con.setVisibility(View.VISIBLE);
            tv.setText(string);
        }
    }


}
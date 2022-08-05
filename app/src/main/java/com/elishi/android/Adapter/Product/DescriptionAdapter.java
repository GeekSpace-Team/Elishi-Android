package com.elishi.android.Adapter.Product;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.elishi.android.Fragment.Product.Description;
import com.elishi.android.Modal.Product.GetSingleProduct;
import com.elishi.android.Modal.Product.Product;
import com.elishi.android.R;

import java.util.ArrayList;

public class DescriptionAdapter extends FragmentPagerAdapter {

    private Context context;
    private FragmentManager fm;
    private Product product;
    private ArrayList<Product> similarProducts=new ArrayList<>();
    private GetSingleProduct getSingleProduct;
    public DescriptionAdapter(@NonNull FragmentManager fm, Context context,Product product,ArrayList<Product> similarProducts,GetSingleProduct getSingleProduct) {
        super(fm);
        this.context = context;
        this.product=product;
        this.similarProducts=similarProducts;
        this.getSingleProduct=getSingleProduct;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0) {
            Description description=Description.newInstance("1");
            description.product=product;
            description.getSingleProduct=getSingleProduct;
            description.similarProducts=similarProducts;
            fragment = description;
        }
        else if (position == 1)
        {
            Description description=Description.newInstance("2");
            description.product=product;
            description.getSingleProduct=getSingleProduct;
            description.similarProducts=similarProducts;
            fragment = description;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        String title = null;
        if (position == 0)
            title = context.getResources().getString(R.string.details);
        else if (position == 1)
            title = context.getResources().getString(R.string.images);
        return title;
    }
}

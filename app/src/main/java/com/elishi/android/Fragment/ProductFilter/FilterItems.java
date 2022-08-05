package com.elishi.android.Fragment.ProductFilter;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elishi.android.Activity.MainActivity;
import com.elishi.android.Adapter.Filter.FilterItemAdapter;
import com.elishi.android.Common.FilterType;
import com.elishi.android.Common.Utils;
import com.elishi.android.Fragment.Product.Products;
import com.elishi.android.Modal.Filter.FilterItem;
import com.elishi.android.Modal.Filter.FilterList;
import com.elishi.android.R;
import com.elishi.android.View.CustomViews.AppEditText;
import com.elishi.android.databinding.FragmentFilterItemsBinding;

import java.util.ArrayList;


public class FilterItems extends Fragment {

    private FragmentFilterItemsBinding binding;
    private Context context;
    private FilterList filterList;
    private String title="";
    private static FilterItems INSTANCE;
    private ArrayList<com.elishi.android.Modal.Filter.FilterList> filters=new ArrayList<>();

    public FilterItems(FilterList filterList, String toString,ArrayList<com.elishi.android.Modal.Filter.FilterList> filters) {
        this.filterList=filterList;
        this.title=toString;
        this.filters=filters;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentFilterItemsBinding.inflate(inflater,container,false);
        context=getContext();
        INSTANCE=this;
        binding.title.setText(title);
        if(filterList.getType().equals(FilterType.PRICE)){
            binding.search.setVisibility(View.GONE);
            binding.min.setVisibility(View.VISIBLE);
            binding.line.setVisibility(View.VISIBLE);
            binding.max.setVisibility(View.VISIBLE);
        }
        Utils.loadLocal(context);
        setListener();
        setItems();
        return binding.getRoot();
    }

    private void setListener() {
        binding.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.filterRoot,new com.elishi.android.Fragment.ProductFilter.FilterList(filters,getFragmentManager()), com.elishi.android.Fragment.ProductFilter.FilterList.class.getSimpleName()).commitNow();

            }
        });

        binding.min.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    Products.min=Double.parseDouble(s.toString());
                } catch (Exception ex){
                    ex.printStackTrace();
                    Products.min=null;
                }
                try{
                    binding.rec.getAdapter().notifyDataSetChanged();
                } catch (Exception ex){
                    ex.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.max.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    Products.max=Double.parseDouble(s.toString());
                } catch (Exception ex){
                    ex.printStackTrace();
                    Products.max=null;
                }
                try{
                    binding.rec.getAdapter().notifyDataSetChanged();
                } catch (Exception ex){
                    ex.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Products.category=null;
                Products.userId=null;
                if(filterList.getType().equals(FilterType.CATEGORY)){
                    Products.sub_category.clear();
                    Products.page=1;
                    FilterDialog.get().dismiss();
                    Products.get().request(1);
                }
                if(filterList.getType().equals(FilterType.LOCATION)){
                    Products.region.clear();
                    Products.page=1;
                    FilterDialog.get().dismiss();
                    Products.get().request(1);
                }
                if(filterList.getType().equals(FilterType.PRICE)){
                    Products.min=null;
                    Products.max=null;
                    Products.page=1;
                    FilterDialog.get().dismiss();
                    Products.get().request(1);
                }
                if(filterList.getType().equals(FilterType.STATUS)){
                    Products.status.clear();
                    Products.page=1;
                    FilterDialog.get().dismiss();
                    Products.get().request(1);
                }
            }
        });

        binding.acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Products.category=null;
                Products.page=1;
                FilterDialog.get().dismiss();
                Products.get().request(1);
            }
        });

        binding.search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setItems();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        if(filterList.getType().equals(FilterType.STATUS)){
            binding.linear.setVisibility(View.GONE);
        }


    }

    public static FilterItems get(){
        return INSTANCE;
    }

    public AppEditText getMin(){
        return binding.min;
    }

    public AppEditText getMax(){
        return binding.max;
    }

    private void setItems() {
        binding.rec.setAdapter(new FilterItemAdapter(filterList,context,binding.search.getText().toString()));
        binding.rec.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            FilterDialog.get().setCancelable(false);
        }
    }
}
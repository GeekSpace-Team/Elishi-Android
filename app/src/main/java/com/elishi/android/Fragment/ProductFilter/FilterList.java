package com.elishi.android.Fragment.ProductFilter;

import android.content.Context;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.elishi.android.Adapter.Filter.FilterListAdapter;
import com.elishi.android.Common.Utils;
import com.elishi.android.Fragment.Product.Products;
import com.elishi.android.R;
import com.elishi.android.databinding.FragmentFilterListBinding;

import java.util.ArrayList;


public class FilterList extends Fragment {

    private FragmentFilterListBinding binding;
    private Context context;
    private ArrayList<com.elishi.android.Modal.Filter.FilterList> filters = new ArrayList<>();
    private FragmentManager fragmentManager;

    public FilterList(ArrayList<com.elishi.android.Modal.Filter.FilterList> filters, FragmentManager fragmentManager) {
        this.filters = filters;
        this.fragmentManager = fragmentManager;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFilterListBinding.inflate(inflater, container, false);
        context = getContext();
        setFilters();
        setListener();
        try{
            getActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
                @Override
                public void handleOnBackPressed() {
                    Toast.makeText(context, "OK", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception ex){
            ex.printStackTrace();
        }
        Utils.loadLocal(context);
        return binding.getRoot();
    }

    private void setListener() {
        binding.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Products.get().getDialog().dismiss();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        binding.clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Products.category=null;
                Products.sub_category.clear();
                Products.min=null;
                Products.max=null;
                Products.status.clear();
                Products.region.clear();
                Products.page=1;
                Products.userId=null;
                FilterDialog.get().dismiss();
                Products.get().request(1);
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
    }

    private void setFilters() {
        binding.rec.setAdapter(new FilterListAdapter(filters, context, fragmentManager));
        binding.rec.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }

    @Override
    public void onResume() {
        super.onResume();
        FilterDialog.isBack=true;
    }

    @Override
    public void onPause() {
        super.onPause();
        FilterDialog.isBack=true;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            FilterDialog.get().setCancelable(false);
        }
    }
}
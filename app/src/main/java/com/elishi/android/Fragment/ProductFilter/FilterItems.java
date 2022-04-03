package com.elishi.android.Fragment.ProductFilter;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elishi.android.Adapter.Filter.FilterItemAdapter;
import com.elishi.android.Modal.Filter.FilterItem;
import com.elishi.android.R;
import com.elishi.android.databinding.FragmentFilterItemsBinding;

import java.util.ArrayList;


public class FilterItems extends Fragment {

    private FragmentFilterItemsBinding binding;
    private Context context;
    private ArrayList<FilterItem> filterItems=new ArrayList<>();


    public FilterItems(ArrayList<FilterItem> filterItems) {
        this.filterItems=filterItems;
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
        setItems();
        return binding.getRoot();
    }

    private void setItems() {
        binding.rec.setAdapter(new FilterItemAdapter(filterItems,context));
        binding.rec.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }
}
package com.elishi.android.Fragment.ProductFilter;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.elishi.android.R;
import com.elishi.android.databinding.FilterDialogBinding;
import com.elishi.android.databinding.FragmentFilterDialogBinding;

import java.util.ArrayList;


public class FilterDialog extends DialogFragment {


    private ArrayList<com.elishi.android.Modal.Filter.FilterList> filters=new ArrayList<>();
    private FragmentFilterDialogBinding binding;
    public FilterDialog(ArrayList<com.elishi.android.Modal.Filter.FilterList> filters) {
        this.filters=filters;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFilterDialogBinding.inflate(inflater,container,false);
        try {
            getChildFragmentManager().beginTransaction().replace(R.id.filterRoot, new FilterList(filters,getChildFragmentManager()), FilterList.class.getSimpleName()).commit();
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return binding.getRoot();
    }

    @Override
    public int getTheme() {
        return R.style.FullScreenDialog;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null)
        {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            Window window = dialog.getWindow();
            window.setGravity(Gravity.CENTER);
            window.getAttributes().windowAnimations = R.style.DialogAnimation;
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }
}
package com.elishi.android.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.elishi.android.Class.SpinnerItem;
import com.elishi.android.Common.Utils;
import com.elishi.android.R;
import com.elishi.android.View.CustomViews.Custom_Spinner;
import com.elishi.android.databinding.FragmentAddProductBinding;

import java.util.ArrayList;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;


public class AddProductFragment extends Fragment {

    private Context context;
    private FragmentAddProductBinding binding;
    private ArrayList<SpinnerItem> categories=new ArrayList<>();


    public AddProductFragment() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentAddProductBinding.inflate(inflater,container,false);
        context=getContext();
        setItems();
        setFonts();
        setListener();
        return binding.getRoot();
    }

    private void setFonts() {
        binding.okButton.setTypeface(Utils.getBoldFont(context));
    }

    private void setItems() {
        categories.add(new SpinnerItem("1","Haly"));
        categories.add(new SpinnerItem("1","Monjuklar"));
        categories.add(new SpinnerItem("1","Haly"));
        categories.add(new SpinnerItem("1","Haly"));
        binding.category.setAdapter(new Custom_Spinner(context,categories));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }

    private void setListener() {
        OverScrollDecoratorHelper.setUpOverScroll(binding.scroll);
    }
}
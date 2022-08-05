package com.elishi.android.Fragment.ProductFilter;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.elishi.android.Common.Utils;
import com.elishi.android.Fragment.HomeFragment;
import com.elishi.android.R;
import com.elishi.android.databinding.FilterDialogBinding;
import com.elishi.android.databinding.FragmentFilterDialogBinding;

import java.util.ArrayList;


public class FilterDialog extends DialogFragment {


    private ArrayList<com.elishi.android.Modal.Filter.FilterList> filters=new ArrayList<>();
    private FragmentFilterDialogBinding binding;
    private static FilterDialog INSTANCE;
    public static boolean isBack=true;
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
            getChildFragmentManager().beginTransaction().replace(R.id.filterRoot,new FilterList(filters,getChildFragmentManager()),FilterList.class.getSimpleName()).commitNow();
        } catch (Exception ex){
            ex.printStackTrace();
        }
        Utils.loadLocal(getContext());
        INSTANCE=this;
        return binding.getRoot();
    }

    public static FilterDialog get(){
        return INSTANCE;
    }


    @Override
    public int getTheme() {
        return R.style.FullScreenDialog;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Dialog dialog1 = getDialog();
        if (dialog1 != null)
        {
            dialog1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            Window window = dialog1.getWindow();
            window.setGravity(Gravity.CENTER);
            window.getAttributes().windowAnimations = R.style.DialogAnimation;
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog1.getWindow().setLayout(width, height);
            dialog1.setCancelable(false);
            dialog1.setCanceledOnTouchOutside(false);
            dialog1.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        FilterList filterList = (FilterList) getChildFragmentManager().findFragmentByTag(FilterList.class.getSimpleName());
                        FilterItems filterItems = (FilterItems) getChildFragmentManager().findFragmentByTag(FilterItems.class.getSimpleName());
                        if(filterItems!=null && filterItems.isVisible()) {
                            getChildFragmentManager().beginTransaction().replace(R.id.filterRoot,new FilterList(filters,getChildFragmentManager()),FilterList.class.getSimpleName()).commitNow();
                            return true;
                        }
//                        if(filterList!=null && filterList.isVisible()){
//                            dialog.dismiss();
//                            return true;
//                        }
                    }
                    return false;
                }

            });
        }
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }
}
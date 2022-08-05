package com.elishi.android.Fragment.Holiday;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elishi.android.Common.Utils;
import com.elishi.android.R;
import com.elishi.android.databinding.FragmentHolidayViewBinding;


public class HolidayView extends Fragment {

    private FragmentHolidayViewBinding binding;
    private Context context;

    public HolidayView() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context=getContext();
        Utils.loadLocal(context);
        binding=FragmentHolidayViewBinding.inflate(inflater,container,false);
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }
}
package com.elishi.android.Fragment.Profile;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elishi.android.Adapter.Profile.MyHoldayAdapter;
import com.elishi.android.Modal.Profile.MyHoliday;
import com.elishi.android.R;
import com.elishi.android.databinding.FragmentMyHolidaysBinding;

import java.util.ArrayList;


public class MyHolidays extends Fragment {

    private FragmentMyHolidaysBinding binding;
    private ArrayList<MyHoliday> myHolidays=new ArrayList<>();
    private Context context;

    public MyHolidays() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentMyHolidaysBinding.inflate(inflater,container,false);
        context=getContext();
        setHolidays();
        return binding.getRoot();
    }

    private void setHolidays() {
        binding.rec.setAdapter(new MyHoldayAdapter(myHolidays,context,getActivity().getSupportFragmentManager()));
        binding.rec.setLayoutManager(new LinearLayoutManager(context));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }
}
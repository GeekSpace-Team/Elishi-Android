package com.elishi.android.View.CustomViews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.elishi.android.Class.SpinnerItem;
import com.elishi.android.Common.Utils;
import com.elishi.android.R;

import java.util.ArrayList;

public class Custom_Spinner extends ArrayAdapter {
    public Custom_Spinner(@NonNull Context context, ArrayList<SpinnerItem> customlist) {
        super(context, 0, customlist);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.custom_2,parent,false);

        }
        SpinnerItem item= (SpinnerItem) getItem(position);

        TextView spinnerTv=convertView.findViewById(R.id.tvSpinnerLayout);
        if (item!=null) {

            spinnerTv.setText(item.getITEM_NAME());
            spinnerTv.setTypeface(Utils.getRegularFont(getContext()));
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.custom_dropdown_layout,parent,false);

        }
        SpinnerItem item= (SpinnerItem) getItem(position);

        TextView dropDownTV=convertView.findViewById(R.id.tvDropDownLayout);
        if (item!=null) {

            dropDownTV.setText(item.getITEM_NAME());
            dropDownTV.setTypeface(Utils.getMediumFont(getContext()));
        }
        return convertView;
    }
}

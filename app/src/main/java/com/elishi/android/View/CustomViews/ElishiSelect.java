package com.elishi.android.View.CustomViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.elishi.android.Class.SpinnerItem;
import com.elishi.android.Common.Utils;
import com.elishi.android.R;

import java.util.HashMap;

public class ElishiSelect extends FrameLayout {
    private Context context;
    private View view;
    private TextView textView;
    private Spinner spinner;

    public ElishiSelect(@NonNull Context context) {
        super(context);
        this.context=context;
    }

    public ElishiSelect(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        init(attrs);
    }


    public ElishiSelect(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray a=context.obtainStyledAttributes(attrs, R.styleable.ElishiSelect);
        String label=a.getString(R.styleable.ElishiSelect_labelSelect);

        a.recycle();

        createView(label);
    }

    private void createView(String label) {
        view=inflate(context,R.layout.elishi_select,this);
        textView=view.findViewById(R.id.label);
        spinner=view.findViewById(R.id.select);
        textView.setTypeface(Utils.getMediumFont(context));
        textView.setText(label);
    }

    public void setItems(Custom_Spinner adapter){
        spinner.setAdapter(adapter);
    }

    public Spinner getSelect(){
        return spinner;
    }



}

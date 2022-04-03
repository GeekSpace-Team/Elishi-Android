package com.elishi.android.View.CustomViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.AppCompatEditText;

import com.elishi.android.Common.Utils;
import com.elishi.android.R;

public class AppEditText extends AppCompatEditText {
    private Context context;
    private AttributeSet attr;
    public AppEditText(@NonNull Context context) {
        super(context);
        this.context=context;
    }

    public AppEditText(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        this.attr=attrs;
        init(attrs);
    }

    public AppEditText(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        this.attr=attrs;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray array=context.obtainStyledAttributes(attr, R.styleable.AppEditText);
        int font=array.getInt(R.styleable.AppEditText_editTextFont,-1);
        createView(font);
    }

    private void createView(int font) {
        switch (font) {
            case 0:
                setTypeface(Utils.getBlackFont(context));
                break;
            case 1:
                setTypeface(Utils.getBoldFont(context));
                break;
            case 2:
                setTypeface(Utils.getLightFont(context));
                break;
            case 3:
                setTypeface(Utils.getMediumFont(context));
                break;
            case 4:
                setTypeface(Utils.getRegularFont(context));
                break;
            case 5:
                setTypeface(Utils.getThinFont(context));
                break;
            case 6:
                setTypeface(Utils.getUltraFont(context));
                break;
        }

    }


}

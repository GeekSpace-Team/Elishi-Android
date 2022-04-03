package com.elishi.android.View.CustomViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.elishi.android.Common.Utils;
import com.elishi.android.R;

public class ElishiEditText extends LinearLayout {
    private EditText editText;
    private View view;
    private TextView label;
    private Context context;
    public ElishiEditText(@NonNull Context context) {
        super(context);
        this.context=context;
    }
    public ElishiEditText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        init(attrs);
    }
    public ElishiEditText(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        init(attrs);
    }









    private void init(AttributeSet attrs) {
        TypedArray a=getContext().obtainStyledAttributes(attrs, R.styleable.ElishiEditText);
        String labelText=a.getString(R.styleable.ElishiEditText_label);
        String placeholderText=a.getString(R.styleable.ElishiEditText_placeholder);
        int type=a.getInt(R.styleable.ElishiEditText_android_inputType,0);
        int gravity=a.getInt(R.styleable.ElishiEditText_android_gravity,0);
        float customHeight=a.getDimension(R.styleable.ElishiEditText_customHeight,0.0f);
        a.recycle();
        requestLayout();
        createView(labelText,placeholderText,type,gravity,customHeight);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    private void createView(String labelText, String placeholderText, int type, int gravity, float customHeight) {
        view=inflate(context,R.layout.my_custom_edit_text,this);
        label=view.findViewById(R.id.label);
        editText=view.findViewById(R.id.editText);
        label.setTypeface(Utils.getMediumFont(getContext()));
        editText.setTypeface(Utils.getRegularFont(getContext()));
        label.setText(labelText);
        editText.setHint(placeholderText);
        editText.setInputType(type);
        if(gravity!=0)
            editText.setGravity(gravity);
        if(customHeight!=0.0f) {
            LinearLayout.LayoutParams lm = (LinearLayout.LayoutParams) editText.getLayoutParams();
            lm.height=(int) customHeight;
            setLayoutParams(lm);
        }
    }

    public String getValue(){
        return editText.getText().toString();
    }

    public EditText getEditText(){return editText;}





}

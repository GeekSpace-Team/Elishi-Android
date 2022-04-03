package com.elishi.android.Common;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;

public class RoundedBackground {
    public static GradientDrawable create(int color, int cornerRadius, Context context){
        GradientDrawable shape =  new GradientDrawable();
        shape.setCornerRadius(context.getResources().getDimension(cornerRadius));
        shape.setColor(context.getResources().getColor(color));
        return shape;
    }
}

package com.elishi.android.Common;

import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.elishi.android.databinding.AppSnackbarBinding;
import com.google.android.material.snackbar.Snackbar;

public class AppSnackBar {
    private Context context;
    private AppSnackbarBinding binding;
    private View v;
    private Snackbar snackbar;
    public interface SnackBarListener{
        void onButtonClicked();
    }

    public AppSnackBar(Context context,View view) {
        this.context = context;
        this.v=view;
        createView();
    }

    private void createView() {
        binding=AppSnackbarBinding.inflate(LayoutInflater.from(context));
        Shader textShader=new LinearGradient(0, 0, 0, 20,
                new int[]{Color.parseColor("#FF4D3C"),Color.parseColor("#FFAC5F")},
                new float[]{0, 1}, Shader.TileMode.CLAMP);
        binding.button.getPaint().setShader(textShader);
        createSnackBar();
    }

    private void createSnackBar() {
        snackbar = Snackbar.make(v, "", Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackbarLayout.setPadding(0, 0, 0, 0);
        FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)snackbarLayout.getLayoutParams();
        params.gravity = Gravity.TOP;
        snackbarLayout.setLayoutParams(params);
        snackbarLayout.addView(binding.getRoot(), 0);
    }

    public void setTitle(String text){
        binding.title.setText(text);
    }
    public void setTitle(int textId){
        binding.title.setText(context.getResources().getString(textId));
    }
    public void actionText(String text){
        binding.button.setText(text);
    }
    public void actionText(int textId){
        binding.button.setText(context.getResources().getString(textId));
    }
    public void setButtonClickListener(SnackBarListener listener){
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onButtonClicked();
                dismiss();
            }
        });
    }

    public void show(){
        snackbar.show();
    }

    public void dismiss(){
        snackbar.dismiss();
    }

}

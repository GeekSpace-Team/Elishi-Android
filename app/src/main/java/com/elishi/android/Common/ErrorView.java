package com.elishi.android.Common;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;

import com.bumptech.glide.Glide;
import com.elishi.android.databinding.ErrorViewBinding;

import java.util.Random;

public class ErrorView {
    private Context context;
    private ErrorViewBinding binding;
    public interface ErrorListener{
        public void onRetryListener();
    }

    public void ErrorView(Context context){
        this.context=context;
        createView();
    }

    private void createView() {
        binding=ErrorViewBinding.inflate(LayoutInflater.from(context));
    }
    public void setImage(int image){
        binding.image.setImageResource(image);
    }
    public void setImage(String imageUrl){
        final int min = 0;
        final int max = PlaceHolderColors.PLACEHOLDERS.length-1;
        final int r = new Random().nextInt((max - min) + 1) + min;
        Glide.with(context)
                .load(imageUrl)
                .placeholder(PlaceHolderColors.PLACEHOLDERS[r])
                .into(binding.image);
    }
    public void setImage(Drawable drawable){
        binding.image.setImageDrawable(drawable);
    }
    public void setTitle(int textId){
        binding.title.setText(context.getResources().getString(textId));
    }
    public void setTitle(String text){
        binding.title.setText(text);
    }
    public void setRetryListener(ErrorListener listener){
        binding.retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onRetryListener();
            }
        });
    }
    public void setButtonText(String text){
        binding.retryButton.setText(text);
    }
    public void setButtonText(int textId){
        binding.retryButton.setText(context.getResources().getString(textId));
    }

    public View getErrorView(){
        return binding.getRoot();
    }
}

package com.elishi.android.Fragment.Constant;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bumptech.glide.Glide;
import com.elishi.android.Api.APIClient;
import com.elishi.android.Api.ApiInterface;
import com.elishi.android.Common.PlaceHolderColors;
import com.elishi.android.Common.Utils;
import com.elishi.android.Modal.Constant.Constant;
import com.elishi.android.Modal.Response.GBody;
import com.elishi.android.R;
import com.elishi.android.databinding.FragmentConstantPageBinding;
import com.google.android.material.card.MaterialCardView;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ConstantPage extends Fragment {

    private FragmentConstantPageBinding binding;
    private Context context;
    public String type="";
    public String id="";
    public String image="";
    private String html="";
    private MotionLayout noInternetContainer;
    private MaterialCardView retry;
    public ConstantPage() {
    }

    public static ConstantPage newInstance(String type) {
        Bundle args = new Bundle();
        args.putString("type",type);
        ConstantPage fragment = new ConstantPage();
        fragment.setArguments(args);
        return fragment;
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
        binding=FragmentConstantPageBinding.inflate(inflater,container,false);
        try {
            this.type = getArguments().getString("type");
        } catch (Exception ex){
            ex.printStackTrace();
        }
        retry=binding.getRoot().findViewById(R.id.retry);
        noInternetContainer=binding.getRoot().findViewById(R.id.noInternetContainer);
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getConstant(type);
            }
        });



        getConstant(type);
        return binding.getRoot();
    }


    private void getConstant(String type) {
        binding.loading.setVisibility(View.VISIBLE);
        binding.webView.setVisibility(View.GONE);
        binding.titleContainer.setVisibility(View.GONE);
        noInternetContainer.setVisibility(View.GONE);
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<GBody<Constant>> call=null;
        if(!type.isEmpty()){
            call=apiInterface.getConstantPage(type);
        } else {
            call=apiInterface.getConstantById(id);
        }
        call.enqueue(new Callback<GBody<Constant>>() {
            @Override
            public void onResponse(Call<GBody<Constant>> call, Response<GBody<Constant>> response) {
                if(response.isSuccessful() && response.body().getBody()!=null && response.body().getBody().getId()!=null){
                    binding.webView.setVisibility(View.VISIBLE);
                    binding.titleContainer.setVisibility(View.VISIBLE);
                    html=response.body().getBody().getContent_light_tm();
                    if(Utils.getSharedPreference(context,"mode").equals("light")){
                        html=response.body().getBody().getContent_light_tm();
                        if(Utils.getLanguage(context).equals("ru")){
                            html=response.body().getBody().getContent_light_ru();
                        }
                        if(Utils.getLanguage(context).equals("en")){
                            html=response.body().getBody().getContent_light_en();
                        }
                    } else if(Utils.getSharedPreference(context,"mode").equals("night")){
                        html=response.body().getBody().getContent_dark_tm();
                        if(Utils.getLanguage(context).equals("ru")){
                            html=response.body().getBody().getContent_dark_ru();
                        }
                        if(Utils.getLanguage(context).equals("en")){
                            html=response.body().getBody().getContent_dark_en();
                        }
                    }
                    binding.title.setText(response.body().getBody().getTitleTM());
                    if(Utils.getLanguage(context).equals("ru")){
                        binding.title.setText(response.body().getBody().getTitleRU());
                    }
                    if(Utils.getLanguage(context).equals("en")){
                        binding.title.setText(response.body().getBody().getTitleEN());
                    }
                    setWebView(html);
                } else {
                    noInternetContainer.setVisibility(View.VISIBLE);
                }
                binding.loading.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<GBody<Constant>> call, Throwable t) {
                noInternetContainer.setVisibility(View.VISIBLE);
                binding.loading.setVisibility(View.GONE);
            }
        });
    }

    private void setWebView(String html) {
        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        binding.webView.getSettings().setBuiltInZoomControls(false);
        binding.webView.getSettings().setLoadWithOverviewMode(true);
        binding.webView.getSettings().setLoadsImagesAutomatically(true);
        binding.webView.setBackgroundColor(0);
        binding.webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if(url!=null) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
                return true;
            }
        });

        binding.webView.getSettings().setUseWideViewPort(true);
        binding.webView.setWebChromeClient(new WebChromeClient());
        if(!image.trim().isEmpty()){
            html="<img src='"+ com.elishi.android.Common.Constant.IMAGE_URL+image+"'/><br/>"+html;
        }
        html="<!DOCTYPE html>" +
                "<head>" +
                "<meta charset='utf-8'/>" +
                "<meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
                "<style>" +
                "img{" +
                "width:100%;" +
                "}" +
                "</style>" +
                "</head>" +
                "<body>"+html+"" +
                "</body>" +
                "<html>";
        binding.webView.loadDataWithBaseURL(null, html, null, "UTF-8", null);

    }
}
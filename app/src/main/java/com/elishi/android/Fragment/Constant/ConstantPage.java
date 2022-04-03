package com.elishi.android.Fragment.Constant;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.elishi.android.R;
import com.elishi.android.databinding.FragmentConstantPageBinding;


public class ConstantPage extends Fragment {

    private FragmentConstantPageBinding binding;
    private Context context;
    public String type="";
    private String html="<html><head>" +
            "</head><body><h2>Hello world</h2></body></html>";
    public ConstantPage() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentConstantPageBinding.inflate(inflater,container,false);
        context=getContext();
        setWebView(html);
        return binding.getRoot();
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
        binding.webView.loadDataWithBaseURL(null, html, null, "UTF-8", null);

    }
}
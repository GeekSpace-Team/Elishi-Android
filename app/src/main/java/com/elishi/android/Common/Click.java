package com.elishi.android.Common;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.elishi.android.Activity.ConstantActivity;
import com.elishi.android.Activity.MainActivity;
import com.elishi.android.Activity.Product.ProductView;
import com.elishi.android.Fragment.Constant.ConstantPage;
import com.elishi.android.Fragment.Product.Products;
import com.elishi.android.Modal.Home.Ads;
import com.elishi.android.Modal.Home.BannerSlider;
import com.elishi.android.Modal.Home.Event;
import com.elishi.android.R;
import com.google.firebase.analytics.FirebaseAnalytics;

public class Click {
    public static void eventClick(Event event, Context context){
        String title= event.getTitle_tm();
        if(Utils.getLanguage(context).equals("ru")){
            title=event.getTitle_ru();
        }
        if(Utils.getLanguage(context).equals("en")){
            title=event.getTitle_en();
        }
        if(event.getEvent_type().equals(EventStatus.CATEGORY)){
            gotoCategory(title,event.getGo_id());
        }

        if(event.getEvent_type().equals(EventStatus.SUB_CATEGORY)){
            gotoSubCategory(title,event.getGo_id());
        }

        if(event.getEvent_type().equals(EventStatus.USER)){
            gotoUser(title,event.getGo_id());
        }

        if(event.getEvent_type().equals(EventStatus.SINGLE_PRODUCT)){
            Intent intent = new Intent(context, ProductView.class);
            intent.putExtra("id", event.getGo_id()+"");
            intent.putExtra("image", "");
            context.startActivity(intent);
        }

        if(event.getEvent_type().equals(EventStatus.CONSTANT)){
            ConstantActivity.id=event.getGo_id()+"";
            ConstantActivity.imageUrl=event.getEvent_image();
            context.startActivity(new Intent(context,ConstantActivity.class));
        }

        if(event.getUrl()!=null && !event.getUrl().trim().isEmpty()){
            actionView(event.getUrl(),context);
        }


    }

    private static void gotoUser(String title, Integer go_id) {
        MainActivity.get().getBottomNavigationView().setSelectedItemId(R.id.category);
        Products.title=title;
        Products.category=null;
        Products.sub_category.clear();
        Products.region.clear();
        Products.status.clear();
        Products.min=null;
        Products.max=null;
        Products.page=1;
        Products.sort=0;
        Products.userId=go_id;
        Utils.productFragment(new Products(),Products.class.getSimpleName(),MainActivity.get().getSupportFragmentManager(),R.id.content);
        MainActivity.secondFragment= new Products();
    }

    private static void gotoSubCategory(String title, Integer go_id) {
        MainActivity.get().getBottomNavigationView().setSelectedItemId(R.id.category);
        Products.title=title;
        Products.category=null;
        Products.sub_category.clear();
        Products.sub_category.add(go_id);
        Products.region.clear();
        Products.status.clear();
        Products.min=null;
        Products.max=null;
        Products.page=1;
        Products.sort=0;
        Products.userId=null;
        Utils.productFragment(new Products(),Products.class.getSimpleName(),MainActivity.get().getSupportFragmentManager(),R.id.content);
        MainActivity.secondFragment= new Products();
    }

    private static void gotoCategory(String title, Integer go_id) {
        MainActivity.get().getBottomNavigationView().setSelectedItemId(R.id.category);
        Products.title=title;
        Products.category=go_id;
        Products.sub_category.clear();
        Products.region.clear();
        Products.status.clear();
        Products.min=null;
        Products.max=null;
        Products.page=1;
        Products.sort=0;
        Products.userId=null;
        Utils.productFragment(new Products(),Products.class.getSimpleName(),MainActivity.get().getSupportFragmentManager(),R.id.content);
        MainActivity.secondFragment= new Products();
    }


    public static void adsClick(Ads ads,Context context){
        if(ads!=null){

            Bundle bundle = new Bundle();
            bundle.putString("ads_id", ads.getId()+"");
            bundle.putString("ads_image", ads.getSite_url());
            FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(context);
            firebaseAnalytics.logEvent("ads_click", bundle);

            if(ads.getSite_url()!=null && !ads.getSite_url().trim().isEmpty()){
                actionView(ads.getSite_url(),context);
            } else {
                ConstantActivity.id=ads.getConstant_id()+"";
                ConstantActivity.imageUrl=ads.getAds_image();
                context.startActivity(new Intent(context,ConstantActivity.class));
            }
        }
    }

    public static void actionView(String site_url,Context context) {
        try{
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(site_url));
            context.startActivity(i);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static void bannerClick(BannerSlider bannerSlider, Context context) {
        Uri uri = Uri.parse(bannerSlider.getSiteURL());
        String category_id = uri.getQueryParameter("category_id");
        String sub_category_id = uri.getQueryParameter("sub_category_id");
        String product_id = uri.getQueryParameter("product_id");
        String user_id = uri.getQueryParameter("user_id");
        String constant_id = uri.getQueryParameter("constant_id");
        if(category_id!=null && !category_id.trim().isEmpty()){
            try{
                gotoCategory(context.getResources().getString(R.string.products),Integer.parseInt(category_id));
            } catch (Exception ex){
                ex.printStackTrace();
            }
            return;
        }

        if(sub_category_id!=null && !sub_category_id.trim().isEmpty()){
            try{
                gotoSubCategory(context.getResources().getString(R.string.products),Integer.parseInt(sub_category_id));
            } catch (Exception ex){
                ex.printStackTrace();
            }
            return;
        }

        if(user_id!=null && !user_id.trim().isEmpty()){
            try{
                gotoUser(context.getResources().getString(R.string.products),Integer.parseInt(user_id));
            } catch (Exception ex){
                ex.printStackTrace();
            }
            return;
        }

        if(product_id!=null && !product_id.trim().isEmpty()){
            Intent intent = new Intent(context, ProductView.class);
            intent.putExtra("id", product_id);
            intent.putExtra("image", "");
            context.startActivity(intent);
            return;
        }
        if(constant_id!=null && !constant_id.trim().isEmpty()){
            ConstantActivity.id=constant_id;
            ConstantActivity.imageUrl=bannerSlider.getBanner_image_tm();
            if(Utils.getLanguage(context).equals("ru")){
                ConstantActivity.imageUrl=bannerSlider.getBanner_image_ru();
            }
            if(Utils.getLanguage(context).equals("en")){
                ConstantActivity.imageUrl=bannerSlider.getBanner_image_en();
            }
            context.startActivity(new Intent(context,ConstantActivity.class));
            return;
        }

        actionView(bannerSlider.getSiteURL(),context);
    }
}

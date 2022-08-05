package com.elishi.android.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.elishi.android.Common.Utils;
import com.elishi.android.Fragment.Constant.ConstantPage;
import com.elishi.android.R;

public class ConstantActivity extends AppCompatActivity {
    public static String id="";
    public static String type="";
    public static String imageUrl="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.loadLocal(this);
        setContentView(R.layout.activity_constant);
        checkMode();
        ConstantPage constantPage;
        if(!id.isEmpty()){
            constantPage = ConstantPage.newInstance("");
            constantPage.id=id;
        } else {
            constantPage = ConstantPage.newInstance(type);
        }
        if(!imageUrl.trim().isEmpty()){
           constantPage.image=imageUrl;
        }
        Utils.hideAdd(constantPage, ConstantPage.class.getSimpleName(), getSupportFragmentManager(), R.id.content);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        id="";
        type="";
        imageUrl="";
    }

    private void checkMode() {
        int nightModeFlags =
                getResources().getConfiguration().uiMode &
                        Configuration.UI_MODE_NIGHT_MASK;
        View view = getWindow().getDecorView();
        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    view.setSystemUiVisibility(view.getSystemUiVisibility() & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                }
                break;

            case Configuration.UI_MODE_NIGHT_NO:
            case Configuration.UI_MODE_NIGHT_UNDEFINED:

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    view.setSystemUiVisibility(view.getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                }
                break;
        }
    }
}
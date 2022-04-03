package com.elishi.android.Common;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.elishi.android.Modal.Response.Message;
import com.elishi.android.R;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static android.content.Context.MODE_PRIVATE;

import java.util.Locale;

public class Utils {
    public static void hideAdd(Fragment fragment, String tagFragmentName, FragmentManager mFragmentManager, int frame) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        Fragment currentFragment = mFragmentManager.getPrimaryNavigationFragment();
        if (currentFragment != null) {
            fragmentTransaction.hide(currentFragment);
        }

        Fragment fragmentTemp = mFragmentManager.findFragmentByTag(tagFragmentName);
        if (fragmentTemp == null) {
            fragmentTemp = fragment;
            fragmentTransaction.add(frame, fragmentTemp, tagFragmentName);
        } else {
            fragmentTransaction.show(fragmentTemp);
        }
        fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        fragmentTransaction.setPrimaryNavigationFragment(fragmentTemp);
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.commitNowAllowingStateLoss();
    }



    public static void removeShow(Fragment fragment, String tagFragmentName, FragmentManager mFragmentManager, int frame) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        Fragment currentFragment = mFragmentManager.getPrimaryNavigationFragment();
        if (currentFragment != null) {
            fragmentTransaction.remove(currentFragment);
        }

        Fragment fragmentTemp = mFragmentManager.findFragmentByTag(tagFragmentName);
        if (fragmentTemp == null) {
            fragmentTemp = fragment;
            fragmentTransaction.add(frame, fragmentTemp, tagFragmentName);
        } else {
            fragmentTransaction.show(fragmentTemp);
        }
        fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        fragmentTransaction.setPrimaryNavigationFragment(fragmentTemp);
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.commitNowAllowingStateLoss();
    }

    public static Typeface getHandWritingFont(Context context) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/high-destiny-personal-use.otf");
        return font;
    }

    public static Typeface getBoldFont(Context context) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/Gordita Bold.otf");
        return font;
    }

    public static Typeface getLightFont(Context context) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/Gordita Light.otf");
        return font;
    }

    public static Typeface getMediumFont(Context context) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/Gordita Medium.otf");
        return font;
    }

    public static Typeface getRegularFont(Context context) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/Gordita Regular.otf");
        return font;
    }

    public static Typeface getThinFont(Context context) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/Gordita Thin.otf");
        return font;
    }

    public static Typeface getBlackFont(Context context) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/Gordita Black.otf");
        return font;
    }

    public static Typeface getUltraFont(Context context) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/Gordita Ultra.otf");
        return font;
    }
    public static Typeface getMRC(Context context) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/m_rc.ttf");
        return font;
    }


    public static Typeface getFontByName(Context context,String name) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/"+name);
    }


    public static void changeSize(final Context context, final BottomNavigationView bottomNavigationView) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
        for (int i = 0; i < menuView.getChildCount(); i++) {
            final View iconView = menuView.findViewById(R.id.add);
            final ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(100, 100);
            findIcon(context,iconView);
            Log.e("OK", "OK");
        }
    }

    public static void findIcon(final Context context, final View v) {
        try {
            if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    findIcon(context, child);
                }
            } else if (v instanceof ImageView) {
                final FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(150, 100);
                layoutParams.setMargins(0,0,0,0);
                ((ImageView) v).setLayoutParams(layoutParams);

            }
        } catch (Exception e) {
        }
    }


    public static Bitmap addGradient(Bitmap originalBitmap) {
        int width = originalBitmap.getWidth();
        int height = originalBitmap.getHeight();
        Bitmap updatedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(updatedBitmap);
        canvas.drawBitmap(originalBitmap, 0, 0, null);
        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0, 0, 0, height, 0xFFAC5F, 0xFF4D3C, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawRect(0, 0, width, height, paint);
        return updatedBitmap;
    }

    public static String getLanguage(Context context,String name){
        SharedPreferences prefs = context.getSharedPreferences(name, MODE_PRIVATE);
        return prefs.getString(name, "");
    }

    public static void setPreference(String name,String value,Context context){
        SharedPreferences.Editor editor = context.getSharedPreferences(name, MODE_PRIVATE).edit();
        editor.putString(name, value);
        editor.apply();
    }

    public static String getSharedPreference(Context context, String name) {
        SharedPreferences prefs = context.getSharedPreferences(name, MODE_PRIVATE);
        String value = prefs.getString(name, "");
        return value;
    }


    public static Bitmap blurRenderScript(Bitmap smallBitmap, int radius,Context mContext) {

        try {
            smallBitmap = RGB565toARGB888(smallBitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }


        Bitmap bitmap = Bitmap.createBitmap(
                smallBitmap.getWidth(), smallBitmap.getHeight(),
                Bitmap.Config.ARGB_8888);

        RenderScript renderScript = RenderScript.create(mContext);

        Allocation blurInput = Allocation.createFromBitmap(renderScript, smallBitmap);
        Allocation blurOutput = Allocation.createFromBitmap(renderScript, bitmap);

        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(renderScript,
                Element.U8_4(renderScript));
        blur.setInput(blurInput);
        blur.setRadius(radius); // radius must be 0 < r <= 25
        blur.forEach(blurOutput);

        blurOutput.copyTo(bitmap);
        renderScript.destroy();

        return bitmap;

    }

    public static Bitmap RGB565toARGB888(Bitmap img) throws Exception {
        int numPixels = img.getWidth() * img.getHeight();
        int[] pixels = new int[numPixels];

        //Get JPEG pixels.  Each int is the color values for one pixel.
        img.getPixels(pixels, 0, img.getWidth(), 0, 0, img.getWidth(), img.getHeight());

        //Create a Bitmap of the appropriate format.
        Bitmap result = Bitmap.createBitmap(img.getWidth(), img.getHeight(), Bitmap.Config.ARGB_8888);

        //Set RGB pixels.
        result.setPixels(pixels, 0, result.getWidth(), 0, 0, result.getWidth(), result.getHeight());
        return result;
    }

    public static void setLocale(String lang, Context context) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
        //saved data to shared preferences
        SharedPreferences.Editor editor = context.getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();
    }

    // load language saved in shared preferences
    public static void loadLocal(Context context) {
        SharedPreferences share = context.getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String languages = share.getString("My_Lang", "");
        Utils.setLocale(languages, context);
    }


    public static String getLanguage(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("Settings", MODE_PRIVATE);
        String value = prefs.getString("My_Lang", "tm");
        return value;
    }

    public static String checkMessage(Context context, Message message){
        if(message==null){
            return "";
        }
        String msg = message.getTm();
        if(Utils.getLanguage(context).equals("ru")){
            msg=message.getRu();
        }
        if(Utils.getLanguage(context).equals("en")){
            msg=message.getEn();
        }
        return msg;
    }


}

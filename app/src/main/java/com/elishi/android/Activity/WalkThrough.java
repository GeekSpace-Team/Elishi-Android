package com.elishi.android.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.elishi.android.Adapter.WalkThrough.MyAdapter;
import com.elishi.android.Common.Utils;
import com.elishi.android.R;
import com.uzairiqbal.circulartimerview.CircularTimerListener;
import com.uzairiqbal.circulartimerview.CircularTimerView;
import com.uzairiqbal.circulartimerview.TimeFormatEnum;

public class WalkThrough extends AppCompatActivity {
    private CircularTimerView progress_circular;
    private TextView title, desc;
    private Context context = this;
    private ViewPager2 viewPager;
    private MyAdapter adapter;
    private ImageView one, two, three;
    private Float progress = 0f;
    private RelativeLayout main;
    private RelativeLayout next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.loadLocal(context);
        setContentView(R.layout.activity_walk_through);
        setColors();
        initComponents();
        setTimerProgress();
        setFonts();
        setPager();
        setListener();
    }

    private void setListener() {
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextPage();
            }
        });
    }

    private void nextPage() {
        if (viewPager.getCurrentItem() == 2) {
            Utils.setPreference("firstTime","-1",context);
            finish();
            startActivity(new Intent(context, MainActivity.class));
        } else {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
        }
    }


    private void setPager() {
        adapter = new MyAdapter(this);
        viewPager.setAdapter(adapter);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setTimerProgress();
                if (position == 0) {
                    setAllUnselect();
                    setActive(one);
                    setAllText(context.getResources().getString(R.string.intro_title_1), context.getResources().getString(R.string.intro_message_1));
                }
                if (position == 1) {
                    setAllUnselect();
                    setActive(two);
                    setAllText(context.getResources().getString(R.string.intro_title_2), context.getResources().getString(R.string.intro_message_2));

                }
                if (position == 2) {
                    setAllUnselect();
                    setActive(three);
                    setAllText(context.getResources().getString(R.string.intro_title_3), context.getResources().getString(R.string.intro_message_3));
                }
            }
        });
    }

    private void setAllText(String s, String s1) {
        title.setText(s);
        desc.setText(s1);
        title.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in));
        desc.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in));
    }

    private void setActive(ImageView img) {
        img.setImageResource(R.drawable.indicator_active);
    }

    private void setAllUnselect() {
        one.setImageResource(R.drawable.indicator_inactive);
        two.setImageResource(R.drawable.indicator_inactive);
        three.setImageResource(R.drawable.indicator_inactive);
    }

    private void setFonts() {
    }

    private void setTimerProgress() {
        progress_circular.setProgress(0);
        progress_circular.setStrokeWidthDimension(20);
        progress_circular.setCircularTimerListener(new CircularTimerListener() {
            @Override
            public String updateDataOnTick(long remainingTimeInMs) {
                progress = Float.parseFloat(String.valueOf((int) Math.ceil((remainingTimeInMs / 1000.f))));
                return String.valueOf((int) Math.ceil((remainingTimeInMs / 1000.f)));
            }

            @Override
            public void onTimerFinished() {
                nextPage();
            }
        }, 3, TimeFormatEnum.SECONDS, 3);

        progress_circular.startTimer();
    }

    private void initComponents() {
        progress_circular = findViewById(R.id.progress_circular);
        title = findViewById(R.id.title);
        desc = findViewById(R.id.desc);
        viewPager = findViewById(R.id.viewPager);
        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        three = findViewById(R.id.three);
        main = findViewById(R.id.main);
        next = findViewById(R.id.next);
    }

    private void setColors() {
        Window window = getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.white));
        }
    }
}
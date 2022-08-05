package com.elishi.android.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.elishi.android.Common.Utils;
import com.elishi.android.Fragment.Settings.Settings;
import com.elishi.android.R;
import com.google.android.material.button.MaterialButton;

public class SettingsActivity extends AppCompatActivity {
    private MaterialButton next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.loadLocal(this);
        setContentView(R.layout.activity_settings);
        initComponents();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(SettingsActivity.this,WalkThrough.class));
            }
        });

        Settings st=new Settings();
        st.type="first";
        getSupportFragmentManager().beginTransaction().replace(R.id.content,st,Settings.class.getSimpleName()).commitNow();
    }

    private void initComponents() {
        next=findViewById(R.id.next);
    }
}
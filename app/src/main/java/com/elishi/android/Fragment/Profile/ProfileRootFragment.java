package com.elishi.android.Fragment.Profile;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.elishi.android.Activity.MainActivity;
import com.elishi.android.Common.Utils;
import com.elishi.android.R;


public class ProfileRootFragment extends Fragment {


    private View view;
    private Context context;
    public ProfileRootFragment() {
    }

    public static ProfileRootFragment newInstance(String param1, String param2) {
        ProfileRootFragment fragment = new ProfileRootFragment();
        Bundle args = new Bundle();
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
        Utils.transparentStatusBar(getActivity(),true,true);
        context=getContext();
        Utils.loadLocal(context);
        view=inflater.inflate(R.layout.fragment_profile_root, container, false);
        if(Utils.getSharedPreference(context,"tkn").isEmpty() || Utils.getSharedPreference(context,"userId").isEmpty()){
            getFragmentManager().beginTransaction().replace(R.id.layout,RegistRoot.newInstance(),RegistRoot.class.getSimpleName()).commit();
        } else if(!Utils.getSharedPreference(context,"tkn").isEmpty() && !Utils.getSharedPreference(context,"userId").isEmpty()){
            getFragmentManager().beginTransaction().replace(R.id.layout,new MyProfile(),MyProfile.class.getSimpleName()).commit();
        }

        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        try {
            if(hidden){
                Utils.transparentStatusBar(getActivity(),false,false);
                checkMode();
            } else {
                Utils.transparentStatusBar(getActivity(),true,true);
            }

            if(!hidden && !Utils.getSharedPreference(context,"tkn").isEmpty() && !Utils.getSharedPreference(context,"userId").isEmpty()){
                MainActivity.get().getSupportFragmentManager().beginTransaction().replace(R.id.layout,new MyProfile(),MyProfile.class.getSimpleName()).commit();
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }


    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private void checkMode() {
        int nightModeFlags =
                getResources().getConfiguration().uiMode &
                        Configuration.UI_MODE_NIGHT_MASK;
        View view = getActivity().getWindow().getDecorView();
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
package com.elishi.android.Fragment.Profile;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

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
        view=inflater.inflate(R.layout.fragment_profile_root, container, false);
        context=getContext();
        if(Utils.getSharedPreference(context,"tkn").isEmpty() || Utils.getSharedPreference(context,"userId").isEmpty()){
            getFragmentManager().beginTransaction().replace(R.id.layout,new RegistRoot(),RegistRoot.class.getSimpleName()).commit();
        } else if(!Utils.getSharedPreference(context,"tkn").isEmpty() || !Utils.getSharedPreference(context,"userId").isEmpty()){
            getFragmentManager().beginTransaction().replace(R.id.layout,new MyProfile(),MyProfile.class.getSimpleName()).commit();
        }

        return view;
    }


}
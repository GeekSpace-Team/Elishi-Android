package com.elishi.android.Fragment.Profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elishi.android.R;


public class Login extends Fragment {



    public Login() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getFragmentManager().beginTransaction().replace(R.id.loginRoot,new PhoneVerification("login"),PhoneVerification.class.getSimpleName()).commit();
        return inflater.inflate(R.layout.fragment_login, container, false);
    }


}
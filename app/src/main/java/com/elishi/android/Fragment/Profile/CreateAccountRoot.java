package com.elishi.android.Fragment.Profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elishi.android.R;


public class CreateAccountRoot extends Fragment {



    public CreateAccountRoot() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getFragmentManager().beginTransaction().replace(R.id.createAccountRoot,new PhoneVerification("createAccount"),PhoneVerification.class.getSimpleName()).commit();
        return inflater.inflate(R.layout.fragment_create_account_root, container, false);
    }
}
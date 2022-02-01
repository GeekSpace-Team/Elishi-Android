package com.elishi.android.Fragment.Profile;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elishi.android.Common.Utils;
import com.elishi.android.R;
import com.elishi.android.databinding.FragmentPhoneVerificationBinding;


public class PhoneVerification extends Fragment {

    private FragmentPhoneVerificationBinding binding;
    private String which="";
    private View view;
    private Context context;
    public PhoneVerification(String which) {
        this.which=which;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPhoneVerificationBinding.inflate(inflater, container, false);
        view = binding.getRoot();
        context=getContext();
        setViews();
        setListener();
        setFonts();
        return view;
    }

    private void setFonts() {
        binding.title.setTypeface(Utils.getMediumFont(context));
        binding.phoneTitle.setTypeface(Utils.getRegularFont(context));
        binding.phonePrefix.setTypeface(Utils.getMediumFont(context));
        binding.phone.setTypeface(Utils.getMediumFont(context));
    }

    private void setViews() {
        binding.progress.getIndeterminateDrawable()
                .setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN );
    }

    private void setListener() {
        binding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.arrow.setVisibility(View.INVISIBLE);
                binding.progress.setVisibility(View.VISIBLE);
                if(which.equals("login")){
                    getFragmentManager().beginTransaction().replace(R.id.loginRoot,new VerifyCode(binding.phone.getText().toString(),which),VerifyCode.class.getSimpleName()).commit();
                } else if(which.equals("createAccount")){
                    getFragmentManager().beginTransaction().replace(R.id.createAccountRoot,new VerifyCode(binding.phone.getText().toString(),which),VerifyCode.class.getSimpleName()).commit();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
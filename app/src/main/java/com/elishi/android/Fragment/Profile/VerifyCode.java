package com.elishi.android.Fragment.Profile;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.elishi.android.Common.Utils;
import com.elishi.android.R;
import com.elishi.android.databinding.FragmentVerifyCodeBinding;


public class VerifyCode extends Fragment {

    private View view;
    private Context context;
    private FragmentVerifyCodeBinding binding;
    private String phoneNumber,which;

    public VerifyCode(String phoneNumber,String which) {
        this.phoneNumber=phoneNumber;
        this.which=which;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentVerifyCodeBinding.inflate(inflater, container, false);
        view = binding.getRoot();
        context=getContext();
        setFonts();
        setListener();
        setViews();
        return view;
    }

    private void setListener() {
        binding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.arrow.setVisibility(View.INVISIBLE);
                binding.progress.setVisibility(View.VISIBLE);
                if(which.equals("login")){
                    // Open Profile Page here
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.layout,new MyProfile(),MyProfile.class.getSimpleName()).commit();
                } else if(which.equals("createAccount")){
                    showDialog();
                }
            }
        });
    }

    private void showDialog() {
        Dialog dialog = new Dialog(context);
        LayoutInflater localInflater = LayoutInflater.from(context);
        View view = localInflater.inflate(R.layout.dialog_no_account, null, false);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        window.getAttributes().windowAnimations = R.style.DialogAnimation;

        TextView titleDialog=dialog.findViewById(R.id.title);
        TextView messageDialog=dialog.findViewById(R.id.message);
        Button yesButton=dialog.findViewById(R.id.yesButton);
        Button noButton=dialog.findViewById(R.id.noButton);

        titleDialog.setTypeface(Utils.getMediumFont(context));
        messageDialog.setTypeface(Utils.getRegularFont(context));
        yesButton.setTypeface(Utils.getMediumFont(context));
        noButton.setTypeface(Utils.getMediumFont(context));

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                getFragmentManager().beginTransaction().replace(R.id.createAccountRoot,new CreateAccount(),CreateAccount.class.getSimpleName()).commit();
            }
        });


        dialog.setCancelable(true);
        window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        dialog.show();

    }

    private void setFonts() {
        binding.sendAgain.setTypeface(Utils.getRegularFont(context));
        binding.timer.setTypeface(Utils.getRegularFont(context));
        binding.title.setTypeface(Utils.getMediumFont(context));
        binding.minTitle.setTypeface(Utils.getRegularFont(context));
        binding.code.setTypeface(Utils.getRegularFont(context));
    }

    private void setViews() {
        binding.progress.getIndeterminateDrawable()
                .setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN );
    }
}
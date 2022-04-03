package com.elishi.android.Fragment.Profile;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.elishi.android.Api.APIClient;
import com.elishi.android.Api.ApiInterface;
import com.elishi.android.Common.AppSnackBar;
import com.elishi.android.Common.Utils;
import com.elishi.android.Modal.Request.Login.PhoneCode;
import com.elishi.android.Modal.Response.GBody;
import com.elishi.android.Modal.Response.Login.UserBody;
import com.elishi.android.R;
import com.elishi.android.databinding.FragmentPhoneVerificationBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
                if(binding.phone.getText().toString().length()<8 || !binding.phone.getText().toString().startsWith("6")){
                    AppSnackBar snackBar=new AppSnackBar(context,view);
                    snackBar.setTitle(R.string.enter_phone);
                    snackBar.actionText(R.string.cancel);
                    snackBar.setButtonClickListener(new AppSnackBar.SnackBarListener() {
                        @Override
                        public void onButtonClicked() {
                            snackBar.dismiss();
                        }
                    });
                    snackBar.show();
                    return;
                }
                showProgress();
                ApiInterface apiInterface= APIClient.getClient().create(ApiInterface.class);
                Call<GBody<UserBody>> call=apiInterface.phoneVerification(new PhoneCode("+993"+binding.phone.getText().toString(),""));
                call.enqueue(new Callback<GBody<UserBody>>() {
                    @Override
                    public void onResponse(Call<GBody<UserBody>> call, Response<GBody<UserBody>> response) {
                        if(response.isSuccessful()){
                            UserBody body=response.body().getBody();
                            String msg=Utils.checkMessage(context,response.body().getMessage());
                            if(!msg.isEmpty()){
                                AppSnackBar snackBar=new AppSnackBar(context,view);
                                snackBar.setTitle(msg);
                                snackBar.actionText(R.string.cancel);
                                snackBar.show();
                            }

                            if(which.equals("login")){
                                if(body.getExist().equals("exist")){
                                    getFragmentManager().beginTransaction().replace(R.id.loginRoot,new VerifyCode("+993"+binding.phone.getText().toString(),which),VerifyCode.class.getSimpleName()).commit();
                                } else {
                                    showDialog();
                                }
                            } else if(which.equals("createAccount")){
                                getFragmentManager().beginTransaction().replace(R.id.createAccountRoot,new VerifyCode("+993"+binding.phone.getText().toString(),which),VerifyCode.class.getSimpleName()).commit();
                            }

                        } else {
                            AppSnackBar snackBar=new AppSnackBar(context,view);
                            snackBar.setTitle(R.string.error_message);
                            snackBar.actionText(R.string.cancel);
                            snackBar.show();
                        }
                        hideProgress();
                    }

                    @Override
                    public void onFailure(Call<GBody<UserBody>> call, Throwable t) {
                        AppSnackBar snackBar=new AppSnackBar(context,view);
                        snackBar.setTitle(t.getMessage());
                        snackBar.actionText(R.string.cancel);
                        snackBar.show();
                        hideProgress();
                    }
                });

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
                RegistRoot.get().getViewPager().setCurrentItem(1);
            }
        });


        dialog.setCancelable(true);
        window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        dialog.show();

    }
    private void showProgress() {
        binding.arrow.setVisibility(View.INVISIBLE);
        binding.progress.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        binding.arrow.setVisibility(View.VISIBLE);
        binding.progress.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
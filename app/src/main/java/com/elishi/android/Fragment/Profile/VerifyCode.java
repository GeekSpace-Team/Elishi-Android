package com.elishi.android.Fragment.Profile;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.ContextThemeWrapper;
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
import com.elishi.android.databinding.FragmentVerifyCodeBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class VerifyCode extends Fragment {

    private View view;
    private Context context;
    private FragmentVerifyCodeBinding binding;
    private String phoneNumber, which;
    private CountDownTimer countDownTimer;
    public VerifyCode(String phoneNumber, String which) {
        this.phoneNumber = phoneNumber;
        this.which = which;
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
        context = getContext();
        setFonts();
        setListener();
        setViews();
        return view;
    }

    private void setListener() {
        binding.sendAgain.setVisibility(View.GONE);
        binding.progressSendCode.setVisibility(View.GONE);
        countDownTimer = new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                try {
                    String time = String.format("%02d:%02d", ((millisUntilFinished / 1000) % 3600) / 60, ((millisUntilFinished / 1000) % 60));
                    binding.timer.setText(time);
                } catch (Exception ex){
                    ex.printStackTrace();
                }
            }

            public void onFinish() {
                try {
                    binding.sendAgain.setVisibility(View.VISIBLE);
                    binding.timer.setText("00:00");
                    binding.sendAgain.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            binding.progressSendCode.setVisibility(View.VISIBLE);
                            binding.sendAgain.setVisibility(View.GONE);
                            getCode();
                        }


                    });
                } catch (Exception exception){
                    exception.printStackTrace();
                }
            }
        }.start();
        binding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.code.getText().toString().length() < 4) {
                    AppSnackBar snackBar = new AppSnackBar(context, view);
                    snackBar.setTitle(R.string.fill_out);
                    snackBar.actionText("OK");
                    snackBar.show();
                    return;
                }
                showProgress();
                // Open Profile Page here
                ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
                PhoneCode body = new PhoneCode(phoneNumber, binding.code.getText().toString(),which);
                Call<GBody<UserBody>> call = apiInterface.codeVerification(body);
                call.enqueue(new Callback<GBody<UserBody>>() {
                    @Override
                    public void onResponse(Call<GBody<UserBody>> call, Response<GBody<UserBody>> response) {
                        if (response.isSuccessful()) {
                            UserBody body = response.body().getBody();
                            String msg = Utils.checkMessage(context, response.body().getMessage());
                            if (!msg.isEmpty()) {
                                AppSnackBar snackBar = new AppSnackBar(context, view);
                                snackBar.setTitle(msg);
                                snackBar.actionText(R.string.cancel);
                                snackBar.show();
                            }

                            if (which.equals("login")) {
                                if (body.getExist().equals("exist")) {
                                    exist(body);
                                } else {
                                    showDialog();
                                }
                            } else if (which.equals("createAccount")) {
                                if (body.getExist().equals("exist")) {
                                    exist(body);
                                } else {
                                    getFragmentManager().beginTransaction().replace(R.id.createAccountRoot, CreateAccount.newInstance(phoneNumber), CreateAccount.class.getSimpleName()).commit();
                                }
                            }
                        } else {
                            AppSnackBar snackBar = new AppSnackBar(context, view);
                            snackBar.setTitle(R.string.error_message);
                            snackBar.actionText(R.string.cancel);
                            snackBar.show();
                        }
                        hideProgress();
                    }

                    @Override
                    public void onFailure(Call<GBody<UserBody>> call, Throwable t) {
                        AppSnackBar snackBar = new AppSnackBar(context, view);
                        snackBar.setTitle(t.getMessage());
                        snackBar.actionText(R.string.cancel);
                        snackBar.show();
                        hideProgress();
                    }
                });


            }
        });
    }

    private void getCode() {
        ApiInterface apiInterface= APIClient.getClient().create(ApiInterface.class);
        Call<GBody<UserBody>> call=apiInterface.phoneVerification(new PhoneCode(phoneNumber,"","createAccount"));
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
                    setListener();


                } else {
                    AppSnackBar snackBar=new AppSnackBar(context,view);
                    snackBar.setTitle(R.string.error_message);
                    snackBar.actionText(R.string.cancel);
                    snackBar.show();
                    binding.progressSendCode.setVisibility(View.GONE);
                    binding.sendAgain.setVisibility(View.VISIBLE);
                    binding.timer.setText("00:00");
                }
                hideProgress();

            }

            @Override
            public void onFailure(Call<GBody<UserBody>> call, Throwable t) {
                AppSnackBar snackBar=new AppSnackBar(context,view);
                snackBar.setTitle(R.string.error_message);
                snackBar.actionText(R.string.cancel);
                snackBar.show();
                hideProgress();
                binding.progressSendCode.setVisibility(View.GONE);
                binding.sendAgain.setVisibility(View.VISIBLE);
                binding.timer.setText("00:00");
            }
        });
    }

    private void exist(UserBody body) {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.layout, new MyProfile(), MyProfile.class.getSimpleName()).commit();
        Utils.setPreference("tkn", body.getUser().getToken(), context);
        Utils.setPreference("userId", body.getUser().getId() + "", context);
        Utils.setPreference("phoneNumber", body.getUser().getPhone_number(), context);
        Utils.setPreference("fullname", body.getUser().getFullname(), context);
        Utils.setPreference("district_name_tm", body.getUser().getDistrict_name_tm(), context);
        Utils.setPreference("district_name_ru", body.getUser().getDistrict_name_ru(), context);
        Utils.setPreference("district_name_en", body.getUser().getDistrict_name_en(), context);
        Utils.setPreference("region_name_tm", body.getUser().getRegion_name_tm(), context);
        Utils.setPreference("region_name_ru", body.getUser().getRegion_name_ru(), context);
        Utils.setPreference("region_name_en", body.getUser().getRegion_name_en(), context);
        Utils.setPreference("product_limit", body.getUser().getProduct_limit()+"", context);
        if (body.getUser().getProfile_image() != null)
            Utils.setPreference("profile_image", body.getUser().getProfile_image(), context);
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

        TextView titleDialog = dialog.findViewById(R.id.title);
        TextView messageDialog = dialog.findViewById(R.id.message);
        Button yesButton = dialog.findViewById(R.id.yesButton);
        Button noButton = dialog.findViewById(R.id.noButton);

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

    private void hideProgress() {
        binding.arrow.setVisibility(View.VISIBLE);
        binding.progress.setVisibility(View.INVISIBLE);
    }

    private void showProgress() {
        binding.arrow.setVisibility(View.INVISIBLE);
        binding.progress.setVisibility(View.VISIBLE);
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
                .setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        countDownTimer.cancel();
        binding = null;
    }
}
package com.elishi.android.Fragment.Profile;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.elishi.android.Adapter.Location.RegionAdapter;
import com.elishi.android.Api.APIClient;
import com.elishi.android.Api.ApiInterface;
import com.elishi.android.Common.AppSnackBar;
import com.elishi.android.Common.Utils;
import com.elishi.android.Modal.Location.Region;
import com.elishi.android.Modal.Location.SubLocation;
import com.elishi.android.Modal.Request.User.SignupBody;
import com.elishi.android.Modal.Response.GBody;
import com.elishi.android.Modal.Response.Login.UserBody;
import com.elishi.android.Modal.Response.PublicAPI.Locations;
import com.elishi.android.R;
import com.elishi.android.databinding.FragmentCreateAccountBinding;

import java.util.ArrayList;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CreateAccount extends Fragment {

    private View view;
    private Context context;
    private FragmentCreateAccountBinding binding;
    private String phoneNumber = "";
    private ApiInterface apiInterface;
    private ArrayList<Locations> locations = new ArrayList<>();
    public static Integer districtId = 0;
    public static String districtName = "";

    public CreateAccount() {
    }

    public static CreateAccount newInstance(String phoneNumber) {
        Bundle args = new Bundle();
        args.putString("phoneNumber", phoneNumber);
        CreateAccount fragment = new CreateAccount();
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
        binding = FragmentCreateAccountBinding.inflate(inflater, container, false);
        view = binding.getRoot();
        try {
            this.phoneNumber = getArguments().getString("phoneNumber");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        context = getContext();
        districtId = 0;
        districtName = "";
        setFonts();

        getLocations();
        return view;
    }

    private void getLocations() {
        apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<GBody<ArrayList<Locations>>> call = apiInterface.getLocations();
        call.enqueue(new Callback<GBody<ArrayList<Locations>>>() {
            @Override
            public void onResponse(Call<GBody<ArrayList<Locations>>> call, Response<GBody<ArrayList<Locations>>> response) {
                if (response.isSuccessful()) {
                    setListener();
                    if (response.body().getBody() != null && response.body().getBody().size() > 0) {
                        locations = response.body().getBody();
                    }
                }
            }

            @Override
            public void onFailure(Call<GBody<ArrayList<Locations>>> call, Throwable t) {

            }
        });
    }

    private void setListener() {
        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.fullName.getText().toString().length() < 2 || districtId == 0) {
                    AppSnackBar snackBar = new AppSnackBar(context, view);
                    snackBar.setTitle(R.string.fill_out);
                    snackBar.actionText("OK");
                    snackBar.show();
                    return;
                }
                showLoading();
                apiInterface = APIClient.getClient().create(ApiInterface.class);
                SignupBody body = new SignupBody(binding.fullName.getText().toString(),
                        Utils.getSharedPreference(context, "notif_token"),
                        phoneNumber,
                        districtId);
                Call<GBody<UserBody>> call = apiInterface.signUp(body);
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
                            exist(body);
                        } else {
                            AppSnackBar snackBar = new AppSnackBar(context, view);
                            snackBar.setTitle(R.string.error_message);
                            snackBar.actionText(R.string.cancel);
                            snackBar.show();
                        }
                        hideLoading();
                    }

                    @Override
                    public void onFailure(Call<GBody<UserBody>> call, Throwable t) {
                        AppSnackBar snackBar=new AppSnackBar(context,view);
                        snackBar.setTitle(R.string.error_message);
                        snackBar.actionText(R.string.cancel);
                        snackBar.show();
                        hideLoading();
                    }
                });
            }
        });

        binding.locationContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        binding.location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
    }

    private void exist(UserBody body) {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.layout, new MyProfile(), MyProfile.class.getSimpleName()).commit();
        Utils.setPreference("tkn", body.getUser().getToken(), context);
        Utils.setPreference("userId", body.getUser().getId() + "", context);
        Utils.setPreference("phoneNumber", body.getUser().getPhone_number(), context);
        Utils.setPreference("fullname", body.getUser().getFullname(), context);
        if (body.getUser().getProfile_image() != null)
            Utils.setPreference("profile_image", body.getUser().getProfile_image(), context);
    }

    private void showLoading() {
        binding.saveButton.setVisibility(View.GONE);
        binding.progress.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        binding.saveButton.setVisibility(View.VISIBLE);
        binding.progress.setVisibility(View.GONE);
    }

    private void setFonts() {
        binding.fullName.setTypeface(Utils.getRegularFont(context));
        binding.location.setTypeface(Utils.getRegularFont(context));
        binding.saveButton.setTypeface(Utils.getMediumFont(context));
    }

    private void showDialog() {
        Dialog dialog = new Dialog(context);
        LayoutInflater localInflater = LayoutInflater.from(context);
        View view = localInflater.inflate(R.layout.select_location_dialog, null, false);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        window.getAttributes().windowAnimations = R.style.DialogAnimation;

        TextView dialogTitle = dialog.findViewById(R.id.title);
        RecyclerView regionRec = dialog.findViewById(R.id.regionRec);
        ImageView close = dialog.findViewById(R.id.close);

        dialogTitle.setTypeface(Utils.getMediumFont(context));

        regionRec.setAdapter(new RegionAdapter(locations, context, dialog));
        regionRec.setLayoutManager(new LinearLayoutManager(context));

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if (districtId != 0) {
                    binding.location.setText(districtName);
                }
            }
        });

        dialog.setCancelable(true);
        window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        dialog.show();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        districtId = 0;
        districtName = "";
    }
}
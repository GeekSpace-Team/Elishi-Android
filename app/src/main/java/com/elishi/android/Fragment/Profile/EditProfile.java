package com.elishi.android.Fragment.Profile;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.elishi.android.Adapter.Location.RegionAdapter;
import com.elishi.android.Api.APIClient;
import com.elishi.android.Api.ApiInterface;
import com.elishi.android.Common.AppSnackBar;
import com.elishi.android.Common.Utils;
import com.elishi.android.Modal.EditProfile.EditProfileBody;
import com.elishi.android.Modal.Profile.GetUserById;
import com.elishi.android.Modal.Response.GBody;
import com.elishi.android.Modal.Response.Login.User;
import com.elishi.android.Modal.Response.Login.UserBody;
import com.elishi.android.Modal.Response.PublicAPI.Locations;
import com.elishi.android.R;
import com.elishi.android.databinding.FragmentEditProfileBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditProfile extends Fragment {

    private FragmentEditProfileBinding binding;
    private Context context;
    private ApiInterface apiInterface;
    private ArrayList<Locations> locations = new ArrayList<>();
    public static Integer districtId = 0;
    public static String districtName = "";
    private String oldEmail="";
    private Integer gender=1;

    public EditProfile() {
        // Required empty public constructor
    }


    public static EditProfile newInstance() {
        EditProfile fragment = new EditProfile();
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
        binding=FragmentEditProfileBinding.inflate(inflater,container,false);
        context=getContext();
        districtId = 0;
        districtName = "";
        setFonts();
        getProfile();
        return binding.getRoot();
    }

    private void setFonts() {
        binding.address.setTypeface(Utils.getRegularFont(context));
        binding.addressCon.setTypeface(Utils.getRegularFont(context));
        binding.email.setTypeface(Utils.getRegularFont(context));
        binding.emailCon.setTypeface(Utils.getRegularFont(context));
        binding.fullName.setTypeface(Utils.getRegularFont(context));
        binding.fullNameCon.setTypeface(Utils.getRegularFont(context));
        binding.region.setTypeface(Utils.getRegularFont(context));
        binding.regionCon.setTypeface(Utils.getRegularFont(context));
        binding.man.setTypeface(Utils.getMediumFont(context));
        binding.women.setTypeface(Utils.getMediumFont(context));
        binding.saveButton.setTypeface(Utils.getBoldFont(context));
        binding.region.setTag(binding.region.getKeyListener());
        binding.region.setKeyListener(null);
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
                    binding.region.setText(districtName);
                }
                binding.region.clearFocus();
            }
        });

        dialog.setCancelable(true);
        window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        dialog.show();

    }

    private void getLocations() {
        binding.con.setAlpha(0.4f);
        binding.progress.setVisibility(View.VISIBLE);
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
                    binding.saveButton.setEnabled(true);
                } else {
                    AppSnackBar appSnackBar=new AppSnackBar(context,binding.con);
                    appSnackBar.setTitle(R.string.error_message);
                    appSnackBar.show();
                }
                binding.con.setAlpha(1f);
                binding.progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<GBody<ArrayList<Locations>>> call, Throwable t) {
                binding.con.setAlpha(1f);
                binding.progress.setVisibility(View.GONE);
                AppSnackBar appSnackBar=new AppSnackBar(context,binding.con);
                appSnackBar.setTitle(R.string.error_message);
                appSnackBar.show();
            }
        });
    }

    private void setListener() {
        binding.region.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    showDialog();
                }
            }
        });


        binding.man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender=1;
            }
        });

        binding.women.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender=1;
            }
        });


        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName=binding.fullName.getText().toString();
                if(fullName.trim().isEmpty() || districtId==0){
                    AppSnackBar snackBar = new AppSnackBar(context, binding.getRoot());
                    snackBar.setTitle(R.string.fill_out);
                    snackBar.actionText("OK");
                    snackBar.show();
                    return;
                }

                Dialog progress=Utils.getDialogProgressBar(context);
                progress.show();
                ApiInterface apiInterface=APIClient.getClient().create(ApiInterface.class);
                Call<ResponseBody> call=apiInterface.editProfile("Bearer "+Utils.getSharedPreference(context,"tkn"),
                        new EditProfileBody(fullName,binding.address.getText().toString(),districtId+"",
                                "",gender+""));
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        MaterialAlertDialogBuilder alertDialogBuilder=new MaterialAlertDialogBuilder(context);
                        if(response.isSuccessful()){
                            alertDialogBuilder.setMessage(context.getResources().getString(R.string.successfully_updated));
                        } else {
                            alertDialogBuilder.setMessage(context.getResources().getString(R.string.error_message));
                        }
                        alertDialogBuilder.setTitle(R.string.pay_attention);
                        alertDialogBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alertDialogBuilder.show();
                        progress.dismiss();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        progress.dismiss();
                        MaterialAlertDialogBuilder alertDialogBuilder=new MaterialAlertDialogBuilder(context);
                        alertDialogBuilder.setMessage(context.getResources().getString(R.string.error_message));
                        alertDialogBuilder.setTitle(R.string.pay_attention);
                        alertDialogBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alertDialogBuilder.show();
                    }
                });


            }
        });
    }


    public void getProfile() {
        binding.con.setAlpha(0.4f);
        binding.progress.setVisibility(View.VISIBLE);
        apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<GBody<GetUserById>> call = apiInterface.getUserById("Bearer " + Utils.getSharedPreference(context, "tkn"));
        call.enqueue(new Callback<GBody<GetUserById>>() {
            @Override
            public void onResponse(Call<GBody<GetUserById>> call, Response<GBody<GetUserById>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getBody() != null) {
                    if (response.body().getBody().getProducts() != null) {
                        User user=response.body().getBody().getUser();
                        binding.fullName.setText(user.getFullname()+"");
                        binding.email.setText(user.getEmail()+"");
                        oldEmail= user.getEmail()+"";
                        districtId = user.getRegion_id();
                        binding.region.setText(user.getDistrict_name_tm()+"");
                        if(Utils.getLanguage(context).equals("ru")){
                            binding.region.setText(user.getDistrict_name_ru()+"");
                        }
                        if(Utils.getLanguage(context).equals("en")){
                            binding.region.setText(user.getDistrict_name_en()+"");
                        }
                        districtName=binding.region.getText().toString();
                        binding.address.setText(user.getAddress());
                        if(user.getGender()==null || user.getGender()==1){
                            binding.man.setChecked(true);
                            binding.women.setChecked(false);
                            gender=1;
                        } else if(user.getGender()==2){
                            binding.man.setChecked(false);
                            binding.women.setChecked(true);
                            gender=2;
                        }
                        getLocations();
                    }

                } else {
                    AppSnackBar appSnackBar=new AppSnackBar(context,binding.con);
                    appSnackBar.setTitle(R.string.error_message);
                    appSnackBar.show();
                }
                binding.con.setAlpha(1f);
                binding.progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<GBody<GetUserById>> call, Throwable t) {
                binding.con.setAlpha(1f);
                binding.progress.setVisibility(View.GONE);
                AppSnackBar appSnackBar=new AppSnackBar(context,binding.con);
                appSnackBar.setTitle(R.string.error_message);
                appSnackBar.show();
            }
        });
    }

}
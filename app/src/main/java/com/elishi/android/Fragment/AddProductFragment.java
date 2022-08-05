package com.elishi.android.Fragment;

import android.Manifest;
import android.app.ActionBar;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.elishi.android.Activity.ConstantActivity;
import com.elishi.android.Activity.MainActivity;
import com.elishi.android.Adapter.AddProduct.SelectedImagesAdapter;
import com.elishi.android.Adapter.Category.SelectCategoryAdapter;
import com.elishi.android.Api.APIClient;
import com.elishi.android.Api.ApiInterface;
import com.elishi.android.Class.SpinnerItem;
import com.elishi.android.Common.AppSnackBar;
import com.elishi.android.Common.ImageStatus;
import com.elishi.android.Common.RealPathUtil;
import com.elishi.android.Common.Utils;
import com.elishi.android.Modal.AddProduct.AddProductBody;
import com.elishi.android.Modal.AddProduct.SelectedImage;
import com.elishi.android.Modal.AddProduct.VerifyCode;
import com.elishi.android.Modal.Category.AllCategory;
import com.elishi.android.Modal.Product.Product;
import com.elishi.android.Modal.Profile.GetUserById;
import com.elishi.android.Modal.Request.Login.PhoneCode;
import com.elishi.android.Modal.Response.GBody;
import com.elishi.android.Modal.Response.Login.UserBody;
import com.elishi.android.R;
import com.elishi.android.databinding.FragmentAddProductBinding;
import com.flod.loadingbutton.LoadingButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;
import com.tapadoo.alerter.Alert;
import com.tapadoo.alerter.Alerter;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddProductFragment extends Fragment {

    private Context context;
    private FragmentAddProductBinding binding;
    private ArrayList<SpinnerItem> categories = new ArrayList<>();
    private ArrayList<AllCategory> allCategories = new ArrayList<>();
    public static Integer selectedSubCategory = 0;
    public static String selectedSubCategoryName = "";
    private int resultCode = 0;
    private Uri firstImage = null;
    private ArrayList<SelectedImage> images = new ArrayList<>();
    private Integer uploadImagePos = -1;
    private ArrayList<Product.Images> imageResponses = new ArrayList<>();
    public static AddProductFragment INSTANCE;
    private MotionLayout noInternetContainer;
    private MaterialCardView retry;
    private String checkedNumber="";
    private String verifyCode="----";
    public AddProductFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddProductBinding.inflate(inflater, container, false);
        context = getContext();
        INSTANCE = this;
        noInternetContainer = binding.getRoot().findViewById(R.id.noInternetContainer);
        retry = binding.getRoot().findViewById(R.id.retry);
        setFonts();
        setListener();
        checkProductLimit();
        return binding.getRoot();
    }

    private void checkProductLimit() {
        if(Utils.getSharedPreference(context,"tkn").isEmpty()){
            binding.noLogin.setVisibility(View.VISIBLE);
            binding.scroll.setVisibility(View.GONE);
            binding.limitContainer.setVisibility(View.GONE);
            binding.indicator.setVisibility(View.GONE);
            binding.loading.setVisibility(View.GONE);
            noInternetContainer.setVisibility(View.GONE);
            return;
        }
        binding.phone.setText(Utils.getSharedPreference(context, "phoneNumber"));
        binding.scroll.setVisibility(View.GONE);
        binding.limitContainer.setVisibility(View.GONE);
        binding.indicator.setVisibility(View.VISIBLE);
        binding.loading.setVisibility(View.VISIBLE);
        binding.noLogin.setVisibility(View.GONE);
        noInternetContainer.setVisibility(View.GONE);
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<GBody<GetUserById>> call = apiInterface.getUserById("Bearer " + Utils.getSharedPreference(context, "tkn"));
        call.enqueue(new Callback<GBody<GetUserById>>() {
            @Override
            public void onResponse(Call<GBody<GetUserById>> call, Response<GBody<GetUserById>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getBody() != null && response.body().getBody().getUser().getProduct_limit() != null) {
                    if (response.body().getBody().getUser().getProduct_limit() == -1) {
                        binding.scroll.setVisibility(View.VISIBLE);
                        noInternetContainer.setVisibility(View.GONE);
                        binding.limitContainer.setVisibility(View.GONE);
                    } else {
                        if (response.body().getBody().getUser().getCount_product() >= response.body().getBody().getUser().getProduct_limit()) {
                            binding.scroll.setVisibility(View.GONE);
                            noInternetContainer.setVisibility(View.GONE);
                            binding.limitContainer.setVisibility(View.VISIBLE);
                        } else {
                            binding.scroll.setVisibility(View.VISIBLE);
                            noInternetContainer.setVisibility(View.GONE);
                            binding.limitContainer.setVisibility(View.GONE);
                        }
                    }

                    getCategories();

                } else {
                    noInternetContainer.setVisibility(View.VISIBLE);
                    binding.scroll.setVisibility(View.GONE);
                    binding.limitContainer.setVisibility(View.GONE);
                }
                binding.loading.setVisibility(View.GONE);
                binding.indicator.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<GBody<GetUserById>> call, Throwable t) {
                binding.loading.setVisibility(View.GONE);
                noInternetContainer.setVisibility(View.VISIBLE);
                binding.indicator.setVisibility(View.GONE);
            }
        });
    }

    private void setFonts() {
        binding.okButton.setTypeface(Utils.getBoldFont(context));
    }


    private void getCategories() {
        binding.indicator.setVisibility(View.VISIBLE);
        binding.container.setAlpha(0.4f);
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<GBody<ArrayList<AllCategory>>> call = apiInterface.getCategories();
        call.enqueue(new Callback<GBody<ArrayList<AllCategory>>>() {
            @Override
            public void onResponse(Call<GBody<ArrayList<AllCategory>>> call, Response<GBody<ArrayList<AllCategory>>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getBody() != null && !response.body().getError()) {
                    allCategories = response.body().getBody();
                }
                binding.indicator.setVisibility(View.GONE);
                binding.container.setAlpha(1f);
            }

            @Override
            public void onFailure(Call<GBody<ArrayList<AllCategory>>> call, Throwable t) {
                binding.indicator.setVisibility(View.GONE);
                binding.container.setAlpha(1f);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void setListener() {

        binding.adminNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+99362737222"));
                startActivity(intent);
            }
        });
        binding.noLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.get().getBottomNavigationView().setSelectedItemId(R.id.profile);
            }
        });
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkProductLimit();
            }
        });
        binding.subCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        binding.firstImageCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(111);
            }
        });

        binding.imgCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(1);
            }
        });


        binding.phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String phoneStr = s.toString();
                if (phoneStr.length()>=12) {
                    if (!phoneStr.equals(Utils.getSharedPreference(context, "phoneNumber"))) {
                        // verify code
                        binding.getCode.setVisibility(View.VISIBLE);
                        binding.verifyCode.setVisibility(View.VISIBLE);
                    } else {
                        binding.getCode.setVisibility(View.GONE);
                        binding.verifyCode.setVisibility(View.GONE);
                    }
                } else {
                    binding.getCode.setVisibility(View.GONE);
                    binding.verifyCode.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.getCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkedNumber=binding.phone.getText().toString();
                binding.getCode.setVisibility(View.GONE);
                binding.codeProgress.setVisibility(View.VISIBLE);
                ApiInterface apiInterface=APIClient.getClient().create(ApiInterface.class);
                Call<GBody<VerifyCode>> call= apiInterface.getVerifyCode("Bearer "+Utils.getSharedPreference(context,"tkn"),new PhoneCode(binding.phone.getText().toString(),"",""));
                call.enqueue(new Callback<GBody<VerifyCode>>() {
                    @Override
                    public void onResponse(Call<GBody<VerifyCode>> call, Response<GBody<VerifyCode>> response) {
                        if(response.isSuccessful() && response.body()!=null && response.body().getBody()!=null){
                            String msg=Utils.checkMessage(context,response.body().getMessage());
                            if(!msg.isEmpty()){
                                AppSnackBar snackBar=new AppSnackBar(context,binding.getRoot());
                                snackBar.setTitle(msg);
                                snackBar.actionText(R.string.cancel);
                                snackBar.show();
                            }
                            verifyCode=response.body().getBody().getCode();
                        } else {
                            AppSnackBar snackBar=new AppSnackBar(context,binding.getRoot());
                            snackBar.setTitle(R.string.error_message);
                            snackBar.actionText(R.string.cancel);
                            snackBar.show();
                        }
                        binding.getCode.setVisibility(View.VISIBLE);
                        binding.codeProgress.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<GBody<VerifyCode>> call, Throwable t) {
                        AppSnackBar snackBar=new AppSnackBar(context,binding.getRoot());
                        snackBar.setTitle(R.string.error_message);
                        snackBar.actionText(R.string.cancel);
                        snackBar.show();
                        binding.getCode.setVisibility(View.VISIBLE);
                        binding.codeProgress.setVisibility(View.GONE);
                    }
                });
            }
        });

        binding.acceptPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstantActivity.type="product_policy";
                context.startActivity(new Intent(context,ConstantActivity.class));
            }
        });


        binding.okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameStr = binding.name.getText().toString();
                String priceStr = binding.price.getText().toString();
                String sizeStr = binding.size.getText().toString();
                String phoneStr = binding.phone.getText().toString();
                String descStr = binding.desc.getText().toString();

                if(!binding.acceptPolicy.isChecked()){
                    AppSnackBar appSnackBar = new AppSnackBar(context, binding.firstImageCon);
                    appSnackBar.setTitle(R.string.must_accept_policy);
                    appSnackBar.actionText(R.string.cancel);
                    appSnackBar.show();
                    return;
                }

                if (nameStr.trim().isEmpty()) {
                    AppSnackBar appSnackBar = new AppSnackBar(context, binding.firstImageCon);
                    appSnackBar.setTitle(R.string.must_name);
                    appSnackBar.actionText(R.string.cancel);
                    appSnackBar.show();
                    return;
                }

                if (selectedSubCategory == 0) {
                    AppSnackBar appSnackBar = new AppSnackBar(context, binding.firstImageCon);
                    appSnackBar.setTitle(R.string.must_category);
                    appSnackBar.actionText(R.string.cancel);
                    appSnackBar.show();
                    return;
                }

                if (!phoneStr.trim().isEmpty()) {
                    if (!phoneStr.equals(Utils.getSharedPreference(context, "phoneNumber"))) {
                        if(verifyCode.equals("----")){
                            AppSnackBar appSnackBar = new AppSnackBar(context, binding.firstImageCon);
                            appSnackBar.setTitle(R.string.must_verify_code);
                            appSnackBar.actionText(R.string.cancel);
                            appSnackBar.show();
                            return;
                        }
                        if(binding.phone.getText().toString().equals(checkedNumber)){
                            if(!verifyCode.equals(binding.verifyCode.getText().toString())){
                                AppSnackBar appSnackBar = new AppSnackBar(context, binding.firstImageCon);
                                appSnackBar.setTitle(R.string.verify_code_wrong);
                                appSnackBar.actionText(R.string.cancel);
                                appSnackBar.show();
                                return;
                            }
                        } else {
                            AppSnackBar appSnackBar = new AppSnackBar(context, binding.firstImageCon);
                            appSnackBar.setTitle(R.string.must_verify_code);
                            appSnackBar.actionText(R.string.cancel);
                            appSnackBar.show();
                            return;
                        }
                    }
                } else if (phoneStr.isEmpty()) {
                    phoneStr = Utils.getSharedPreference(context, "phoneNumber");
                }
                if (firstImage == null) {
                    AppSnackBar appSnackBar = new AppSnackBar(context, binding.firstImageCon);
                    appSnackBar.setTitle(R.string.must_first_image);
                    appSnackBar.actionText(R.string.cancel);
                    appSnackBar.show();
                    return;
                }
                binding.okButton.setEnabled(false);
                uploadImage(firstImage, true);
            }
        });

        binding.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.uploadContainer.setVisibility(View.VISIBLE);
                binding.mainImage.setVisibility(View.GONE);
                binding.remove.setVisibility(View.GONE);
                binding.success.setVisibility(View.GONE);
                binding.error.setVisibility(View.GONE);
                binding.inProgress.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && permissions.length > 0) {
            startGallery(requestCode);
        } else {
            Snackbar.make(binding.firstImageCon, R.string.set_permission, Snackbar.LENGTH_LONG)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);
                        }
                    })
                    .show();
        }
    }

    public void selectImage(int code) {
        resultCode = code;
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 12);

            return;

        }
        startGallery(code);
    }

    public void startGallery(int code) {
        Intent intent = new Intent();
        intent.setType("image/*");
        if (code == 111)
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
        else
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);

        someActivityResultLauncher.launch(intent);
    }

    public RecyclerView getImageRecycler() {
        return binding.imgRec;
    }

    public MaterialCardView getImageCon() {
        return binding.imgCon;
    }

    private void uploadImage(Uri uri, boolean isMain) {
        binding.indicator.setVisibility(View.VISIBLE);
//        binding.con2.setAlpha(0.2f);
        if (!isMain) {
            setImageStatus(uploadImagePos, ImageStatus.IN_PROGRESS);
        } else {
            binding.error.setVisibility(View.GONE);
            binding.success.setVisibility(View.GONE);
            binding.inProgress.setVisibility(View.VISIBLE);
            binding.remove.setVisibility(View.GONE);
        }
        Call<GBody<Product.Images>> call = null;
        MultipartBody.Part filePart = null;
        File file = new File(RealPathUtil.getRealPath(context, uri));


        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        if (isMain) {
            filePart = MultipartBody.Part.createFormData("first_image_large", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
            call = apiInterface.addProductMainImage(filePart, "Bearer " + Utils.getSharedPreference(context, "tkn"));
        } else {
            filePart = MultipartBody.Part.createFormData("sliders_large", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
            call = apiInterface.addProductSmallImage(filePart, "Bearer " + Utils.getSharedPreference(context, "tkn"));
        }
        call.enqueue(new Callback<GBody<Product.Images>>() {
            @Override
            public void onResponse(Call<GBody<Product.Images>> call, Response<GBody<Product.Images>> response) {
                if (response.isSuccessful()) {
                    if (!isMain) {
                        setImageStatus(uploadImagePos, ImageStatus.SUCCESS);
                    } else {
                        binding.error.setVisibility(View.GONE);
                        binding.success.setVisibility(View.VISIBLE);
                        binding.inProgress.setVisibility(View.GONE);
                        binding.remove.setVisibility(View.VISIBLE);
                    }
                    imageResponses.add(response.body().getBody());
                } else {
                    if (!isMain) {
                        setImageStatus(uploadImagePos, ImageStatus.ERROR);
                    } else {
                        binding.error.setVisibility(View.VISIBLE);
                        binding.success.setVisibility(View.GONE);
                        binding.inProgress.setVisibility(View.GONE);
                        binding.remove.setVisibility(View.VISIBLE);
                    }
                }

                uploadImagePos++;
                if (uploadImagePos < images.size() && images.get(uploadImagePos).getType() != 2) {
                    uploadImage(images.get(uploadImagePos).getUri(), false);
                } else {
                    if (response.code() == 234) {
                        try {
                            if (response.body() != null && response.body().getMessage() != null) {
                                String msg = Utils.checkMessage(context, response.body().getMessage());
                                if (!msg.isEmpty()) {
                                    Alerter.create(getActivity())
                                            .setText(msg)
                                            .setIcon(R.drawable.ic_baseline_error_outline_24)
                                            .setBackgroundColorRes(R.color.red)
                                            .setDuration(4000)
                                            .show();
                                }
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    addProduct();
                }
                binding.indicator.setVisibility(View.GONE);


            }

            @Override
            public void onFailure(Call<GBody<Product.Images>> call, Throwable t) {
                Log.e("Error", t.getMessage());
                Toast.makeText(context, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                binding.indicator.setVisibility(View.GONE);
                if (!isMain) {
                    setImageStatus(uploadImagePos, ImageStatus.ERROR);
                } else {
                    binding.error.setVisibility(View.VISIBLE);
                    binding.success.setVisibility(View.GONE);
                    binding.inProgress.setVisibility(View.GONE);
                    binding.remove.setVisibility(View.VISIBLE);
                }
                if (uploadImagePos <= images.size()) {
                    uploadImagePos++;
                    uploadImage(images.get(uploadImagePos).getUri(), false);
                } else {
                    addProduct();

                }
            }
        });
    }

    private void setImageStatus(int i, String status) {
        try {
            images.get(i).setStatus(status);
            binding.imgRec.getAdapter().notifyDataSetChanged();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void addProduct() {
        String nameStr = binding.name.getText().toString();
        String priceStr = binding.price.getText().toString();
        String sizeStr = binding.size.getText().toString();
        String phoneStr = binding.phone.getText().toString();
        String descStr = binding.desc.getText().toString();

        Alert alert = Alerter.create(getActivity())
                .setTitle(context.getResources().getString(R.string.product_adding))
                .setText(context.getResources().getString(R.string.please_wait))
                .enableProgress(true)
                .disableOutsideTouch()
                .setDismissable(false)
                .enableInfiniteDuration(true)
                .setProgressColorRes(R.color.realWhite)
                .setBackgroundColorRes(R.color.second)
                .show();
        binding.container.setAlpha(0.4f);


        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        AddProductBody body = new AddProductBody(nameStr, priceStr, sizeStr, selectedSubCategory + "", phoneStr, descStr, imageResponses);
        Call<GBody<Product>> call = apiInterface.addProduct(body, "Bearer " + Utils.getSharedPreference(context, "tkn"));

        call.enqueue(new Callback<GBody<Product>>() {
            @Override
            public void onResponse(Call<GBody<Product>> call, Response<GBody<Product>> response) {

                if (response.isSuccessful()) {
                    binding.okButton.setEnabled(true);
                    clearFields();
                    Alerter.create(getActivity())
                            .setText(context.getResources().getString(R.string.successfully_added))
                            .setIcon(R.drawable.ic_baseline_check_circle_outline_24)
                            .setBackgroundColorRes(R.color.green2_status)
                            .setDuration(4000)
                            .show();
                    Log.e("Result", "success");
                } else {
                    binding.okButton.setEnabled(true);
                    Alerter.create(getActivity())
                            .setText(context.getResources().getString(R.string.error_message))
                            .setIcon(R.drawable.ic_baseline_error_outline_24)
                            .setBackgroundColorRes(R.color.red)
                            .setDuration(4000)
                            .show();
                    Log.e("Result", response.code() + "");
                }
                binding.container.setAlpha(1.0f);
                uploadImagePos = -1;
                checkProductLimit();

            }

            @Override
            public void onFailure(Call<GBody<Product>> call, Throwable t) {
                AppSnackBar appSnackBar = new AppSnackBar(context, binding.firstImageCon);
                appSnackBar.setTitle(t.getMessage());
                appSnackBar.actionText(R.string.cancel);
                appSnackBar.show();
                binding.okButton.setEnabled(true);
                Alerter.create(getActivity())
                        .setText(context.getResources().getString(R.string.error_message))
                        .setIcon(R.drawable.ic_baseline_error_outline_24)
                        .setBackgroundColorRes(R.color.red)
                        .setDuration(4000)
                        .show();
                Log.e("Error", t.getMessage());
                uploadImagePos = -1;
                checkProductLimit();
            }
        });

    }

    private void clearFields() {
        images.clear();
        imageResponses.clear();
        binding.imgCon.setVisibility(View.VISIBLE);
        binding.imgRec.setVisibility(View.GONE);
        binding.imgRec.setAdapter(new SelectedImagesAdapter(images, context));
        binding.imgRec.setLayoutManager(new GridLayoutManager(context, 3));

        binding.uploadContainer.setVisibility(View.VISIBLE);
        binding.mainImage.setVisibility(View.GONE);
        binding.remove.setVisibility(View.GONE);
        binding.success.setVisibility(View.GONE);
        binding.error.setVisibility(View.GONE);
        binding.inProgress.setVisibility(View.GONE);

        binding.name.setText("");
        binding.price.setText("0");
        binding.size.setText("");
        binding.desc.setText("");
        selectedSubCategory = 0;
        selectedSubCategoryName = "";
        binding.subCategory.setText("");
        firstImage=null;
    }

    public ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Intent data = result.getData();
                    if (data == null) {
                        return;
                    }
                    if (data.getClipData() != null) {
                        if (images.size() > 0 && images.get(images.size() - 1).getType() == 2) {
                            images.remove(images.size() - 1);
                        }
                        int count = data.getClipData().getItemCount();
                        for (int i = 0; i < count; i++) {
                            Uri imageUri = data.getClipData().getItemAt(i).getUri();
                            images.add(new SelectedImage(imageUri, 1, ImageStatus.DEFAULT,""));
                            Log.e("URI", imageUri.toString());
                        }
                        if (count > 0) {
                            binding.imgCon.setVisibility(View.GONE);
                            binding.imgRec.setVisibility(View.VISIBLE);
                            images.add(new SelectedImage(null, 2, ImageStatus.DEFAULT,""));
                            binding.imgRec.setAdapter(new SelectedImagesAdapter(images, context));
                            binding.imgRec.setLayoutManager(new GridLayoutManager(context, 3));
                        }
                    } else {
                        if (resultCode == 111) {
                            Uri uri = data.getData();
                            binding.uploadContainer.setVisibility(View.GONE);
                            binding.mainImage.setVisibility(View.VISIBLE);
                            binding.remove.setVisibility(View.VISIBLE);
                            Glide.with(context)
                                    .load(uri)
                                    .into(binding.mainImage);
                            firstImage = uri;

                        } else {
                            if (images.size() > 0 && images.get(images.size() - 1).getType() == 2) {
                                images.remove(images.size() - 1);
                            }
                            Uri imageUri = data.getData();
                            images.add(new SelectedImage(imageUri, 1, ImageStatus.DEFAULT,""));
                            Log.e("URI", imageUri.toString());
                            binding.imgCon.setVisibility(View.GONE);
                            binding.imgRec.setVisibility(View.VISIBLE);
                            images.add(new SelectedImage(null, 2, ImageStatus.DEFAULT,""));
                            binding.imgRec.setAdapter(new SelectedImagesAdapter(images, context));
                            binding.imgRec.setLayoutManager(new GridLayoutManager(context, 3));
                        }
                    }
                }
            });


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
        dialogTitle.setText(context.getResources().getString(R.string.select_category));
        regionRec.setAdapter(new SelectCategoryAdapter(allCategories, context, dialog));
        regionRec.setLayoutManager(new LinearLayoutManager(context));
        close.setOnClickListener(view1 -> dialog.dismiss());
        dialog.setOnDismissListener(dialogInterface -> {
            if (selectedSubCategory != 0) {
                binding.subCategory.setText(selectedSubCategoryName);
            }
        });

        dialog.setCancelable(true);
        window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        dialog.show();

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            checkProductLimit();
        }
    }
}
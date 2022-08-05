package com.elishi.android.Fragment.Product;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.elishi.android.Activity.MainActivity;
import com.elishi.android.Adapter.EditProduct.SelectedImagesAdapter;
import com.elishi.android.Adapter.Category.SelectCategoryAdapter;
import com.elishi.android.Api.APIClient;
import com.elishi.android.Api.ApiInterface;
import com.elishi.android.Class.SpinnerItem;
import com.elishi.android.Common.AppSnackBar;
import com.elishi.android.Common.Constant;
import com.elishi.android.Common.ImageStatus;
import com.elishi.android.Common.RealPathUtil;
import com.elishi.android.Common.Utils;
import com.elishi.android.Fragment.AddProductFragment;
import com.elishi.android.Fragment.Profile.MyProfile;
import com.elishi.android.Modal.AddProduct.AddProductBody;
import com.elishi.android.Modal.AddProduct.SelectedImage;
import com.elishi.android.Modal.AddProduct.VerifyCode;
import com.elishi.android.Modal.Category.AllCategory;
import com.elishi.android.Modal.EditProduct.EditProductBody;
import com.elishi.android.Modal.Product.GetSingleProduct;
import com.elishi.android.Modal.Product.Product;
import com.elishi.android.Modal.Profile.GetUserById;
import com.elishi.android.Modal.Request.Login.PhoneCode;
import com.elishi.android.Modal.Response.GBody;
import com.elishi.android.R;
import com.elishi.android.View.CustomViews.AppEditText;
import com.elishi.android.databinding.FragmentAddProductBinding;
import com.elishi.android.databinding.FragmentEditProductBinding;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.tapadoo.alerter.Alert;
import com.tapadoo.alerter.Alerter;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditProduct extends DialogFragment {

    private Context context;
    private FragmentEditProductBinding binding;
    private ArrayList<SpinnerItem> categories = new ArrayList<>();
    private ArrayList<AllCategory> allCategories = new ArrayList<>();
    public static Integer selectedSubCategory = 0;
    public static String selectedSubCategoryName = "";
    private int resultCode = 0;
    private Uri firstImage = null;
    public ArrayList<SelectedImage> images = new ArrayList<>();
    private ArrayList<SelectedImage> images2 = new ArrayList<>();
    private Integer uploadImagePos = -1;
    public ArrayList<Product.Images> imageResponses = new ArrayList<>();
    public ArrayList<Product.Images> imageResponses2 = new ArrayList<>();
    public static EditProduct INSTANCE;
    private MotionLayout noInternetContainer;
    private MaterialCardView retry;
    private String id;
    private Boolean isDangerous = false;
    private ProgressDialog dialog;
    private Product oldData;

    public EditProduct() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static EditProduct newInstance(String id) {
        Bundle args = new Bundle();
        args.putString("id", id);
        EditProduct fragment = new EditProduct();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context=getContext();
        Utils.loadLocal(context);
        binding = FragmentEditProductBinding.inflate(inflater, container, false);
        INSTANCE = this;
        try {
            if (getArguments() != null) {
                id = getArguments().getString("id");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        noInternetContainer = binding.getRoot().findViewById(R.id.noInternetContainer);
        retry = binding.getRoot().findViewById(R.id.retry);
        setFonts();
        setListener();
        getProduct();
        return binding.getRoot();
    }

    private void getProduct() {
        binding.scroll.setVisibility(View.GONE);
        binding.limitContainer.setVisibility(View.GONE);
        binding.indicator.setVisibility(View.VISIBLE);
        binding.loading.setVisibility(View.VISIBLE);
        binding.noLogin.setVisibility(View.GONE);
        noInternetContainer.setVisibility(View.GONE);
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<GBody<GetSingleProduct>> call = apiInterface.getSingleProduct(id, "Bearer " + Utils.getSharedPreference(context, "tkn"));
        call.enqueue(new Callback<GBody<GetSingleProduct>>() {
            @Override
            public void onResponse(Call<GBody<GetSingleProduct>> call, Response<GBody<GetSingleProduct>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getBody() != null) {
                    binding.scroll.setVisibility(View.VISIBLE);
                    noInternetContainer.setVisibility(View.GONE);
                    binding.limitContainer.setVisibility(View.GONE);
                    setOldData(response.body().getBody().getProduct());
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
            public void onFailure(Call<GBody<GetSingleProduct>> call, Throwable t) {
                binding.loading.setVisibility(View.GONE);
                noInternetContainer.setVisibility(View.VISIBLE);
                binding.indicator.setVisibility(View.GONE);
            }
        });
    }

    private void setOldData(Product product) {
        if (product != null) {
            oldData=product;
            setText(binding.name, product.getProduct_name());
            setText(binding.price, product.getPrice() + "");
            setText(binding.size, product.getSize());
            setText(binding.desc, product.getDescription());
            setText(binding.phone, product.getPhone_number());
            selectedSubCategory = product.getSub_category_id();
            selectedSubCategoryName = product.getSub_category_name_tm();
            if (Utils.getLanguage(context).equals("ru")) {
                selectedSubCategoryName = product.getSub_category_name_ru();
            }
            if (Utils.getLanguage(context).equals("en")) {
                selectedSubCategoryName = product.getSub_category_name_en();
            }

            binding.subCategory.setText(selectedSubCategoryName);

            imageResponses.clear();
            imageResponses2.clear();

            imageResponses.addAll(product.getImages());
            Collections.sort(imageResponses, (abc1, abc2) -> Boolean.compare(abc2.getIs_first(), abc1.getIs_first()));
            Glide.with(context)
                    .load(Constant.IMAGE_URL + imageResponses.get(0).getSmall_image())
                    .into(binding.mainImage);
            binding.uploadContainer.setVisibility(View.GONE);
            binding.mainImage.setVisibility(View.VISIBLE);
            binding.remove.setVisibility(View.VISIBLE);
            images.clear();
            for (Product.Images img : imageResponses) {
                if (!img.getIs_first()) {
                    images.add(new SelectedImage(null, 3, ImageStatus.DEFAULT, img.getSmall_image()));
                }
            }
            images.add(new SelectedImage(null, 2, ImageStatus.DEFAULT, ""));
            binding.imgCon.setVisibility(View.GONE);
            binding.imgRec.setVisibility(View.VISIBLE);
            binding.imgRec.setAdapter(new SelectedImagesAdapter(images, context));
            binding.imgRec.setLayoutManager(new GridLayoutManager(context, 3));
        }
    }

    private void setText(AppEditText tv, String text) {
        if (text != null && !text.trim().isEmpty() && text.length() > 0) {
            tv.setText(text);
        } else {
            tv.setText("");
        }
    }

    @Override
    public int getTheme() {
        return R.style.FullScreenDialog;
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
        selectedSubCategory = 0;
        selectedSubCategoryName = "";
        AddProductFragment.selectedSubCategory = 0;
        AddProductFragment.selectedSubCategoryName = "";
    }

    private void setListener() {

        binding.adminNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
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
                getProduct();
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


        binding.okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameStr = binding.name.getText().toString();
                String sizeStr = binding.size.getText().toString();
                String phoneStr = binding.phone.getText().toString();
                String descStr = binding.desc.getText().toString();
                checkIsDangerouse();

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

//                if (firstImage == null && !imageResponses.get(0).getIs_first()) {
//                    AppSnackBar appSnackBar = new AppSnackBar(context, binding.firstImageCon);
//                    appSnackBar.setTitle(R.string.must_first_image);
//                    appSnackBar.actionText(R.string.cancel);
//                    appSnackBar.show();
//                    return;
//                }
                dialog=new ProgressDialog(context);
                dialog.setCancelable(false);
                dialog.setTitle(context.getResources().getString(R.string.product_updating));
                dialog.setMessage(context.getResources().getString(R.string.please_wait));
                dialog.show();
                images2.clear();
                for (SelectedImage image : images) {
                    if (image.getType() == 1) {
                        images2.add(new SelectedImage(image.getUri(), image.getType(), image.getStatus(), ""));
                    }
                }
                binding.okButton.setEnabled(false);
                if (firstImage != null) {
                    imageResponses.remove(0);
                    uploadImage(firstImage, true);
                } else {

                    if (images2.size() > 0) {
                        uploadImagePos++;
                        uploadImage(images2.get(uploadImagePos).getUri(), false);
                    } else {
                        addProduct();
                    }
                }

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

    private void checkIsDangerouse() {
        if(firstImage!=null){
            isDangerous=true;
            return;
        }
        if(imageResponses2.size()>0){
            isDangerous=true;
            return;
        }

        if(!oldData.getProduct_name().equals(binding.name.getText().toString())){
            isDangerous=true;
            return;
        }

        if(!oldData.getSize().equals(binding.size.getText().toString())){
            isDangerous=true;
            return;
        }

        if(!oldData.getDescription().equals(binding.desc.getText().toString())){
            isDangerous=true;
            return;
        }

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
//            setImageStatus(uploadImagePos, ImageStatus.IN_PROGRESS);
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
//                        setImageStatus(uploadImagePos, ImageStatus.SUCCESS);
                    } else {
                        binding.error.setVisibility(View.GONE);
                        binding.success.setVisibility(View.VISIBLE);
                        binding.inProgress.setVisibility(View.GONE);
                        binding.remove.setVisibility(View.VISIBLE);
                    }
                    imageResponses2.add(response.body().getBody());
                    Log.e("OK",imageResponses2.size()+" / "+response.body().getBody().getSmall_image());
                } else {
                    if (!isMain) {
//                        setImageStatus(uploadImagePos, ImageStatus.ERROR);
                    } else {
                        binding.error.setVisibility(View.VISIBLE);
                        binding.success.setVisibility(View.GONE);
                        binding.inProgress.setVisibility(View.GONE);
                        binding.remove.setVisibility(View.VISIBLE);
                    }
                    Log.e("Error",imageResponses2.size()+" / "+response.code());
                }

                uploadImagePos++;
                if (uploadImagePos < images2.size() && images2.get(uploadImagePos).getType() != 2 && images2.get(uploadImagePos).getType()!=3) {
                    uploadImage(images2.get(uploadImagePos).getUri(), false);
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
//                    setImageStatus(uploadImagePos, ImageStatus.ERROR);
                } else {
                    binding.error.setVisibility(View.VISIBLE);
                    binding.success.setVisibility(View.GONE);
                    binding.inProgress.setVisibility(View.GONE);
                    binding.remove.setVisibility(View.VISIBLE);
                }
                if (uploadImagePos <= images2.size() && images2.get(uploadImagePos).getType() != 2 && images2.get(uploadImagePos).getType()!=3) {
                    uploadImagePos++;
                    uploadImage(images2.get(uploadImagePos).getUri(), false);
                } else {
                    addProduct();

                }
                Log.e("Error",imageResponses.size()+" / "+t.getMessage());

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


        binding.container.setAlpha(0.4f);


        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        EditProductBody body = new EditProductBody(id, nameStr, priceStr, sizeStr, selectedSubCategory + "", phoneStr, descStr, imageResponses2, isDangerous);
        Call<GBody<Product>> call = apiInterface.updateProduct(body, "Bearer " + Utils.getSharedPreference(context, "tkn"));

        call.enqueue(new Callback<GBody<Product>>() {
            @Override
            public void onResponse(Call<GBody<Product>> call, Response<GBody<Product>> response) {
                MaterialAlertDialogBuilder alertDialogBuilder=new MaterialAlertDialogBuilder(context);

                dialog.dismiss();
                if (response.isSuccessful()) {
                    binding.okButton.setEnabled(true);
                    clearFields();

                    alertDialogBuilder.setMessage(context.getResources().getString(R.string.successfully_updated));
                    MainActivity.get().getSupportFragmentManager().beginTransaction().replace(R.id.layout,new MyProfile(),MyProfile.class.getSimpleName()).commit();

                    Log.e("Result", "success");
                } else {
                    binding.okButton.setEnabled(true);
                    Log.e("Result", response.code() + "");
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
                binding.container.setAlpha(1.0f);
                uploadImagePos = -1;
                getProduct();

            }

            @Override
            public void onFailure(Call<GBody<Product>> call, Throwable t) {
                dialog.dismiss();
                binding.okButton.setEnabled(true);
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
                Log.e("Error", t.getMessage());
                uploadImagePos = -1;
                getProduct();
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
                            images.add(new SelectedImage(imageUri, 1, ImageStatus.DEFAULT, ""));
                            Log.e("URI", imageUri.toString());
                        }
                        if (count > 0) {
                            binding.imgCon.setVisibility(View.GONE);
                            binding.imgRec.setVisibility(View.VISIBLE);
                            images.add(new SelectedImage(null, 2, ImageStatus.DEFAULT, ""));
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
                            images.add(new SelectedImage(imageUri, 1, ImageStatus.DEFAULT, ""));
                            Log.e("URI", imageUri.toString());
                            binding.imgCon.setVisibility(View.GONE);
                            binding.imgRec.setVisibility(View.VISIBLE);
                            images.add(new SelectedImage(null, 2, ImageStatus.DEFAULT, ""));
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
}
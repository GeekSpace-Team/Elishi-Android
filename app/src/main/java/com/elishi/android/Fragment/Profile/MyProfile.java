package com.elishi.android.Fragment.Profile;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.elishi.android.Activity.MainActivity;
import com.elishi.android.Adapter.Profile.TabAdapter;
import com.elishi.android.Api.APIClient;
import com.elishi.android.Api.ApiInterface;
import com.elishi.android.Common.AppAlertDialog;
import com.elishi.android.Common.AppSnackBar;
import com.elishi.android.Common.Constant;
import com.elishi.android.Common.RealPathUtil;
import com.elishi.android.Common.Utils;
import com.elishi.android.Fragment.CategoryFragment;
import com.elishi.android.Fragment.Constant.ConstantPage;
import com.elishi.android.Fragment.HomeFragment;
import com.elishi.android.Fragment.Settings.Settings;
import com.elishi.android.Listener.AppBarStateChangeListener;
import com.elishi.android.Modal.Product.Product;
import com.elishi.android.Modal.Profile.GetUserById;
import com.elishi.android.Modal.Profile.MyProduct;
import com.elishi.android.Modal.Profile.User;
import com.elishi.android.Modal.Response.GBody;
import com.elishi.android.Modal.Response.Login.UserBody;
import com.elishi.android.R;
import com.elishi.android.databinding.FragmentMyProfileBinding;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyProfile extends Fragment {

    private View view;
    private Context context;
    private FragmentMyProfileBinding binding;
    private boolean isImageLoaded = false;
    private String profileImageUrl = "";
    private FragmentManager fragmentManager;
    private ApiInterface apiInterface;
    private static MyProfile INSTANCE;
    private MotionLayout noInternetContainer;
    private MaterialCardView retry;
    private TabAdapter adapter=null;
    public MyProfile() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMyProfileBinding.inflate(inflater, container, false);
        view = binding.getRoot();
        context = getContext();
        initComponents();
        INSTANCE = this;
        setListener();
        getProfile();
        profileImageUrl = Constant.IMAGE_URL + Utils.getSharedPreference(context, "profile_image");
        setImage(profileImageUrl);
        setColors();
        setFonts();
        return view;
    }

    private void initComponents() {
        noInternetContainer=view.findViewById(R.id.noInternetContainer);
        retry=view.findViewById(R.id.retry);
    }

    private void setFonts() {
        binding.username.setTypeface(Utils.getRegularFont(context));
        binding.location.setTypeface(Utils.getRegularFont(context));
        binding.phoneNumber.setTypeface(Utils.getRegularFont(context));
        binding.title.setTypeface(Utils.getBoldFont(context));
    }

    private void setColors() {
        if (!profileImageUrl.trim().isEmpty()) {
            if (isImageLoaded)
                setColorToolbar(R.color.realWhite);
            else
                setColorToolbar(R.color.fourth);
        } else {
            setColorToolbar(R.color.fourth);
        }
    }

    private void setImage(String profileImageUrl) {
        binding.darkTransParent.setVisibility(View.GONE);
        if (!Utils.getSharedPreference(context, "profile_image").trim().isEmpty()) {
            binding.editImage.setVisibility(View.VISIBLE);
            binding.profileImage.setOnClickListener(null);
            Glide.with(context)
                    .load(profileImageUrl)
                    .placeholder(R.drawable.image_round_bg)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            if (!profileImageUrl.trim().isEmpty()) {
                                BitmapDrawable drawable = (BitmapDrawable) resource;
                                Bitmap bitmap = drawable.getBitmap();
                                Bitmap blurred = Utils.blurRenderScript(bitmap, 10, context);//second parametre is radius
                                binding.blurImage.setImageBitmap(blurred);
                                setColorToolbar(R.color.realWhite);
                                binding.darkTransParent.setVisibility(View.VISIBLE);
                            } else {
                                setColorToolbar(R.color.fourth);
                            }
                            isImageLoaded = true;
                            return true;
                        }
                    }).into(binding.blurImage);
        } else {
            binding.editImage.setVisibility(View.GONE);
            binding.profileImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 12);
                        return;

                    }
                    startGallery();
                }
            });
        }

        Glide.with(context)
                .load(profileImageUrl)
                .placeholder(R.drawable.ic_profile_image_placeholder)
                .into(binding.profileImage);

        binding.username.setText(Utils.getSharedPreference(context, "fullname"));
        String region = Utils.getSharedPreference(context, "region_name_tm");
        String subLocation = Utils.getSharedPreference(context, "district_name_tm");
        if (Utils.getLanguage(context).equals("ru")) {
            region = Utils.getSharedPreference(context, "region_name_ru");
            subLocation = Utils.getSharedPreference(context, "district_name_ru");
        } else if (Utils.getLanguage(context).equals("en")) {
            region = Utils.getSharedPreference(context, "region_name_en");
            subLocation = Utils.getSharedPreference(context, "district_name_en");
        }

        binding.location.setText(region + " / " + subLocation);
        binding.phoneNumber.setText(Utils.getSharedPreference(context, "phoneNumber"));
    }

    private void setTabs(ArrayList<Product> products) {
        binding.viewPager.setOffscreenPageLimit(2);
        binding.tabLayout2.setupWithViewPager(binding.viewPager, true);
        adapter = new TabAdapter(get().getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        MyProducts myProducts = MyProducts.newInstance();
        myProducts.myProducts = products;
        adapter.addFragment(myProducts, "");
        adapter.addFragment(new MyHolidays(), "");
        binding.viewPager.setAdapter(adapter);


        int colorInt = context.getResources().getColor(R.color.second);
        ColorStateList csl = ColorStateList.valueOf(colorInt);
        Drawable drawable = tintDrawable(context, R.drawable.ic_my_products, csl);

        binding.tabLayout2.getTabAt(0).setIcon(drawable);
        binding.tabLayout2.getTabAt(1).setIcon(R.drawable.ic_my_holidays);

        setTabsColor(0, 1);
    }

    private void setListener() {
        CoordinatorLayout.LayoutParams lm = (CoordinatorLayout.LayoutParams) binding.con1.getLayoutParams();
        binding.appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.COLLAPSED) {
                    setColorToolbar(R.color.fourth);
                    binding.tabLayout2.setBackgroundColor(context.getResources().getColor(R.color.white));
                    lm.topMargin = 0;
                    binding.con1.setLayoutParams(lm);
                } else if (state == State.EXPANDED) {
                    setColors();
                    binding.tabLayout2.setBackgroundResource(R.drawable.tab_round_bg);
                    lm.topMargin = (int) context.getResources().getDimension(R.dimen.marginTop);
                    binding.con1.setLayoutParams(lm);
                }
            }
        });

        binding.menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheetDialog();
            }
        });


        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int unSelected = 1;
                if (position == 1) {
                    unSelected = 0;
                }
                setTabsColor(position, unSelected);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        binding.editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 12);
                    return;

                }
                startGallery();
            }
        });

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getProfile();
            }
        });
    }

    public void startGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        someActivityResultLauncher.launch(intent);
    }

    public ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        Uri uri = data.getData();
                        if(uri!=null){
                            uploadImage(uri);
                        }
                    }
                }
            });


    private void uploadImage(Uri uri){

        File file = new File(RealPathUtil.getRealPath(context,uri));
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("image", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
        MultipartBody.Part filePart2 = MultipartBody.Part.createFormData("oldImage", Utils.getSharedPreference(context, "profile_image"));
        binding.progressImage.setVisibility(View.VISIBLE);
        apiInterface=APIClient.getClient().create(ApiInterface.class);
        Call<GBody<User>> call= apiInterface.addProductImage(filePart2,filePart,"Bearer "+Utils.getSharedPreference(context,"tkn"));
        call.enqueue(new Callback<GBody<User>>() {
            @Override
            public void onResponse(Call<GBody<User>> call, Response<GBody<User>> response) {
                if(response.isSuccessful()){
                    profileImageUrl=Constant.IMAGE_URL+response.body().getBody().getProfile_image();
                    Utils.setPreference("profile_image",response.body().getBody().getProfile_image(),context);
                    setImage(profileImageUrl);
                } else {

                }
                binding.progressImage.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<GBody<User>> call, Throwable t) {
                Log.e("Error",t.getMessage());
                binding.progressImage.setVisibility(View.GONE);
            }
        });
    }



    private void showBottomSheetDialog() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(R.layout.menu_dialog);
        TextView settingsTV = bottomSheetDialog.findViewById(R.id.settingsTV);
        TextView aboutTV = bottomSheetDialog.findViewById(R.id.aboutTV);
        TextView helpTV = bottomSheetDialog.findViewById(R.id.helpTV);
        TextView editTV = bottomSheetDialog.findViewById(R.id.editTV);
        TextView logoutTV = bottomSheetDialog.findViewById(R.id.logoutTV);
        LinearLayout settingsContainer = bottomSheetDialog.findViewById(R.id.settingsContainer);
        LinearLayout aboutContainer = bottomSheetDialog.findViewById(R.id.aboutContainer);
        LinearLayout helpContainer = bottomSheetDialog.findViewById(R.id.helpContainer);
        LinearLayout editProfile = bottomSheetDialog.findViewById(R.id.editProfile);
        LinearLayout logout = bottomSheetDialog.findViewById(R.id.logout);
        LinearLayout feedbackContainer = bottomSheetDialog.findViewById(R.id.feedbackContainer);
        settingsTV.setTypeface(Utils.getRegularFont(context));
        aboutTV.setTypeface(Utils.getRegularFont(context));
        helpTV.setTypeface(Utils.getRegularFont(context));
        editTV.setTypeface(Utils.getRegularFont(context));
        logoutTV.setTypeface(Utils.getRegularFont(context));

        feedbackContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String appPackageName = context.getPackageName(); // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        });

        settingsContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
                Utils.hideAdd(new Settings(), Settings.class.getSimpleName(), getFragmentManager(), R.id.content);
                MainActivity.fifthFragment = new Settings();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
                AppAlertDialog dialog = new AppAlertDialog(context);
                dialog.setTitle(context.getResources().getString(R.string.is_logout));
                dialog.setAlertListener(new AppAlertDialog.AlertListener() {
                    @Override
                    public void onCancelClickListener() {
                        dialog.dismiss();
                    }

                    @Override
                    public void onOkClickListener() {
                        Utils.setPreference("tkn", "", context);
                        restart();
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        helpContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
                ConstantPage constantPage = ConstantPage.newInstance("help");
                constantPage.type = "help";
                Utils.hideAdd(constantPage, ConstantPage.class.getSimpleName(), getFragmentManager(), R.id.content);
                MainActivity.fifthFragment = constantPage;
            }
        });

        aboutContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
                ConstantPage constantPage = ConstantPage.newInstance("about");
                constantPage.type = "about";
                Utils.hideAdd(constantPage, ConstantPage.class.getSimpleName(), getFragmentManager(), R.id.content);
                MainActivity.fifthFragment = constantPage;
            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                EditProfile editProfile1=EditProfile.newInstance();
                Utils.hideAdd(editProfile1, EditProfile.class.getSimpleName(), getFragmentManager(), R.id.content);
                MainActivity.fifthFragment = editProfile1;
            }
        });


        bottomSheetDialog.show();
    }

    private void restart() {
        getActivity().recreate();
        getActivity().getWindow().setWindowAnimations(R.style.WindowAnimationFadeInOut);
        MainActivity.isChangeTheme = false;
    }

    private void setTabsColor(int position, int unSelected) {
        int tabIconColor = ContextCompat.getColor(context, R.color.second);
        tabIconColor = ContextCompat.getColor(context, R.color.second);
        binding.tabLayout2.getTabAt(position).getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);

        tabIconColor = ContextCompat.getColor(context, R.color.text_color);
        binding.tabLayout2.getTabAt(unSelected).getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
    }

    private void setColorToolbar(int color) {
        binding.title.setTextColor(context.getResources().getColor(color));
        binding.username.setTextColor(context.getResources().getColor(color));
        binding.location.setTextColor(context.getResources().getColor(color));
        binding.phoneNumber.setTextColor(context.getResources().getColor(color));
        binding.menuIcon.setColorFilter(ContextCompat.getColor(context, color), android.graphics.PorterDuff.Mode.SRC_IN);
    }

    private Drawable tintDrawable(Context context, @DrawableRes int resId, ColorStateList stateList) {
        Drawable drawable = DrawableCompat.wrap(ContextCompat.getDrawable(context, resId)).mutate();
        DrawableCompat.setTintList(drawable, stateList);
        DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_IN);
        return drawable;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public static MyProfile get() {
        return INSTANCE;
    }

    public void getProfile() {
        binding.loading.setVisibility(View.VISIBLE);
        noInternetContainer.setVisibility(View.GONE);
        binding.containerFull.setVisibility(View.GONE);
        apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<GBody<GetUserById>> call = apiInterface.getUserById("Bearer " + Utils.getSharedPreference(context, "tkn"));
        call.enqueue(new Callback<GBody<GetUserById>>() {
            @Override
            public void onResponse(Call<GBody<GetUserById>> call, Response<GBody<GetUserById>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getBody() != null) {
                    if (response.body().getBody().getProducts() != null) {
                        setTabs(response.body().getBody().getProducts());
                    }
                    exist(new UserBody("exist",response.body().getBody().getUser()));
                    setImage(profileImageUrl);
                    binding.containerFull.setVisibility(View.VISIBLE);
                } else {
                    noInternetContainer.setVisibility(View.VISIBLE);
                }
                binding.loading.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<GBody<GetUserById>> call, Throwable t) {
                binding.loading.setVisibility(View.GONE);
                noInternetContainer.setVisibility(View.VISIBLE);
            }
        });
    }

    private void exist(UserBody body) {
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

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();

    }



}
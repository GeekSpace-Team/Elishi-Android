package com.elishi.android.Fragment.Product;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.GridLayoutManager.DefaultSpanSizeLookup;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.elishi.android.Activity.MainActivity;
import com.elishi.android.Activity.Search.SearchPage;
import com.elishi.android.Adapter.Product.MultiSizeProductAdapter;
import com.elishi.android.Api.APIClient;
import com.elishi.android.Api.ApiInterface;
import com.elishi.android.Common.FilterType;
import com.elishi.android.Common.LayoutType;
import com.elishi.android.Common.Utils;
import com.elishi.android.Fragment.ProductFilter.FilterDialog;
import com.elishi.android.Modal.Filter.FilterItem;
import com.elishi.android.Modal.Filter.FilterList;
import com.elishi.android.Modal.Home.Ads;
import com.elishi.android.Modal.Product.Product;
import com.elishi.android.Modal.Product.ProductBody;
import com.elishi.android.Modal.Response.GBody;
import com.elishi.android.R;
import com.elishi.android.View.SpacesItemDecoration;
import com.elishi.android.databinding.FilterDialogBinding;
import com.elishi.android.databinding.FragmentProductsBinding;
import com.elishi.android.databinding.SortDialogBinding;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.radiobutton.MaterialRadioButton;

import java.util.ArrayList;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Products extends Fragment {
    private FragmentProductsBinding binding;
    private Context context;
    private SkeletonScreen skeletonScreen;
    private ArrayList<Product> products = new ArrayList<>();
    private StaggeredGridLayoutManager sGridLayoutManager;
    private MultiSizeProductAdapter productAdapter;
    private BottomSheetDialog bottomSheetDialog;
    private FilterDialog dialog;
    private static Products INSTANCE;
    // FOR FILTERS
    public static ArrayList<Integer> sub_category = new ArrayList<>();
    public static Integer page = 1;
    public static Integer limit = 20;
    public static Integer category = null;
    public static Double min = null;
    public static Double max = null;
    public static ArrayList<Integer> region = new ArrayList<>();
    public static ArrayList<Integer> status = new ArrayList<>();
    public static Integer sort = 0;
    public static Integer userId=null;
    // FILTER ITEMS
    private ArrayList<com.elishi.android.Modal.Filter.FilterList> filters = new ArrayList<>();
    private FilterList filter_result = null;
    private MotionLayout noInternetContainer;
    private MaterialCardView retry;
    private TextView appTextView;
    private View view;
    private ImageView imageNoInternet;
    public static String title = "";
    private ArrayList<Ads> ads = new ArrayList<>();
    private boolean isLoading = false;
    private boolean isMore = true;
    private int adsPos=0;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    public Products() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context=getContext();
        Utils.loadLocal(context);
        binding = FragmentProductsBinding.inflate(inflater, container, false);
        INSTANCE = this;
        view = binding.getRoot();
        initComponents();
        showSortDialog();

        setListener();
        binding.title.setText(title);
        request(page);
        return binding.getRoot();
    }

    private void initComponents() {
        noInternetContainer = view.findViewById(R.id.noInternetContainer);
        retry = view.findViewById(R.id.retry);
        appTextView = view.findViewById(R.id.appTextView);
        imageNoInternet = view.findViewById(R.id.imageNoInternet);
    }

    private void getFilters() {
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<GBody<FilterList>> call = apiInterface.getFilter();
        call.enqueue(new Callback<GBody<FilterList>>() {
            @Override
            public void onResponse(Call<GBody<FilterList>> call, Response<GBody<FilterList>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getBody() != null) {
                    filter_result = response.body().getBody();
                    createFilters();
                    showFilterDialog();
                }
            }

            @Override
            public void onFailure(Call<GBody<FilterList>> call, Throwable t) {

            }
        });
    }

    private void createFilters() {
        filters.clear();
        ArrayList<FilterItem> price_items = new ArrayList<>();
        ArrayList<FilterItem> status_items = new ArrayList<>();
        for (int i = 0; i < 10000; i += 100) {
            int tmp = i + 100;
            price_items.add(new FilterItem(i, i + "", tmp + "", i + " TMT"));
        }

        status_items.add(new FilterItem(2, context.getResources().getString(R.string.master_product),
                context.getResources().getString(R.string.master_product),
                context.getResources().getString(R.string.master_product)));

        status_items.add(new FilterItem(3, context.getResources().getString(R.string.vip_product),
                context.getResources().getString(R.string.vip_product),
                context.getResources().getString(R.string.vip_product)));

        filters.add(new com.elishi.android.Modal.Filter.FilterList(1,
                "Kategoriýa",
                "Категория",
                "Category",
                null,
                FilterType.CATEGORY,
                filter_result.getCategory(),
                null
        ));
        filters.add(new com.elishi.android.Modal.Filter.FilterList(1,
                "Ýerleşýän ýeri",
                "Место",
                "Location",
                null,
                FilterType.LOCATION,
                null,
                filter_result.getRegions()
        ));

        filters.add(new com.elishi.android.Modal.Filter.FilterList(1,
                "Baha",
                "Цена",
                "Price",
                price_items,
                FilterType.PRICE,
                null,
                null
        ));

        filters.add(new com.elishi.android.Modal.Filter.FilterList(1,
                "Status",
                "Статус",
                "Status",
                status_items,
                FilterType.STATUS,
                null,
                null
        ));

    }

    public static Products get() {
        return INSTANCE;
    }

    public FilterDialog getDialog() {
        return dialog;
    }

    private void setListener() {
        binding.sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.show();
            }
        });

        binding.list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!isLoading && isMore && !binding.list.canScrollVertically(1)) {
                    MainActivity.get().getPagination().setVisibility(View.VISIBLE);
                    page++;
                    request(page);
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                staggeredGridLayoutManager.invalidateSpanAssignments();
            }
        });

        binding.filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    dialog.show(getFragmentManager(), FilterDialog.class.getSimpleName());
                } catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = 1;
                request(page);
            }
        });

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        binding.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, SearchPage.class));
            }
        });
    }


    private void showFilterDialog() {
        dialog = new FilterDialog(filters);
    }

    private void showSortDialog() {
        bottomSheetDialog = new BottomSheetDialog(context);
        SortDialogBinding sortDialogBinding = SortDialogBinding.inflate(LayoutInflater.from(context));
        sortDialogBinding.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
            }
        });

        sortDialogBinding.firstButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortDialogBinding.first.setChecked(true);
            }
        });
        sortDialogBinding.secondButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortDialogBinding.second.setChecked(true);
            }
        });
        sortDialogBinding.thirdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortDialogBinding.third.setChecked(true);
            }
        });
        sortDialogBinding.fourthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortDialogBinding.fourth.setChecked(true);
            }
        });
        sortDialogBinding.fifthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortDialogBinding.fifth.setChecked(true);
            }
        });

        sortDialogBinding.first.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sortTo(0, sortDialogBinding, sortDialogBinding.first);
                }
            }
        });

        sortDialogBinding.second.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sortTo(2, sortDialogBinding, sortDialogBinding.second);
                }
            }
        });

        sortDialogBinding.third.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sortTo(1, sortDialogBinding, sortDialogBinding.third);
                }
            }
        });

        sortDialogBinding.fourth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sortTo(4, sortDialogBinding, sortDialogBinding.fourth);
                }
            }
        });

        sortDialogBinding.fifth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sortTo(3, sortDialogBinding, sortDialogBinding.fifth);
                }
            }
        });


        bottomSheetDialog.setContentView(sortDialogBinding.getRoot());


    }

    private void sortTo(int i, SortDialogBinding sortDialogBinding, MaterialRadioButton first) {
        setRadioButtonsOff(sortDialogBinding);
        first.setChecked(true);
        page = 1;
        sort = i;
        request(page);
    }

    private void setRadioButtonsOff(SortDialogBinding bnd) {
        bnd.first.setChecked(false);
        bnd.second.setChecked(false);
        bnd.third.setChecked(false);
        bnd.fourth.setChecked(false);
        bnd.fifth.setChecked(false);
    }


    private void setProducts2(ArrayList<Ads> ads) {
        try {
            binding.list.setVisibility(View.VISIBLE);
            binding.extraRec.setVisibility(View.GONE);
//            binding.list.setHasFixedSize(true);
            Log.e("Ads", ads.size() + "");
            productAdapter = new MultiSizeProductAdapter(products, context, false, ads);
            staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//            staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
            binding.list.setLayoutManager(staggeredGridLayoutManager);
            binding.list.setAdapter(productAdapter);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void getAds() {
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<GBody<ArrayList<Ads>>> call = apiInterface.getAdsProducts();
        call.enqueue(new Callback<GBody<ArrayList<Ads>>>() {
            @Override
            public void onResponse(Call<GBody<ArrayList<Ads>>> call, Response<GBody<ArrayList<Ads>>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getBody() != null && response.body().getBody().size() > 0) {
                    ads = response.body().getBody();
                }
                Log.e("Size", ads.size() + "");
                setProducts2(ads);
            }

            @Override
            public void onFailure(Call<GBody<ArrayList<Ads>>> call, Throwable t) {
                setProducts2(null);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        sub_category.clear();
        page = 1;
        limit=20;
//        category = null;
//        min = null;
//        max = null;
//        region.clear();
//        status.clear();
//        sort = 0;
//        title = "";
        isLoading = false;
        isMore = true;
//        userId=null;
    }

    public void request(int i) {
        isLoading = true;

        if (page == 1) {
            adsPos=0;
            isMore=true;
            products.clear();
            binding.extraRec.setVisibility(View.VISIBLE);
            productAdapter = new MultiSizeProductAdapter(products, context, false, null);
            staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//            staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
            binding.extraRec.setLayoutManager(staggeredGridLayoutManager);
            skeletonScreen = Skeleton.bind(binding.extraRec)
                    .adapter(productAdapter)
                    .color(R.color.skeletonColor)
                    .load(R.layout.skeleton_product)
                    .duration(1200)
                    .count(5)
                    .show();
        } else {
            if(limit>=40){
                limit=20;
            }
            limit+=20;
        }
        noInternetContainer.setVisibility(View.GONE);
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        ProductBody body = new ProductBody(i, limit, sub_category, category, min, max, region, status, sort,userId);
        Call<GBody<ArrayList<Product>>> call = apiInterface.getProducts("Bearer " + Utils.getSharedPreference(context, "tkn"), body);
        call.enqueue(new Callback<GBody<ArrayList<Product>>>() {
            @Override
            public void onResponse(Call<GBody<ArrayList<Product>>> call, Response<GBody<ArrayList<Product>>> response) {
                if (response.isSuccessful()) {
                    Log.e("Size",response.body().getBody().size()+"");
                    if ((response.body().getBody() == null || response.body().getBody().size() <= 0)) {
                        isMore = false;
                    }
                    if (i == 1 && (response.body().getBody() == null || response.body().getBody().size() <= 0)) {
                        noInternetContainer.setVisibility(View.VISIBLE);
                        binding.list.setVisibility(View.GONE);
                        binding.extraRec.setVisibility(View.GONE);
                        imageNoInternet.setImageResource(R.drawable.empty_icon);
                        appTextView.setText(context.getResources().getString(R.string.no_data));
                    } else {

                        products.addAll(response.body().getBody());

                        if (page == 1) {
                            getAds();
                        } else {
                            if(isMore){
                                setAds();
                            }
                            try {
                                productAdapter.notifyDataSetChanged();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }

                    }


                } else {
                    if (products.size() <= 0) {
                        noInternetContainer.setVisibility(View.VISIBLE);
                    }
                    Log.e("code", response.code() + "");
                }
                skeletonScreen.hide();
                if (page == 1) {
                    getFilters();
                }


                isLoading = false;
                MainActivity.get().getPagination().setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<GBody<ArrayList<Product>>> call, Throwable t) {
                if (products.size() <= 0) {
                    noInternetContainer.setVisibility(View.VISIBLE);
                }
                MainActivity.get().getPagination().setVisibility(View.GONE);
                Log.e("Error", t.getMessage());
                skeletonScreen.hide();
                isLoading = false;
            }
        });
    }

    private void setAds(){
        if(ads!=null && ads.size()>0 && adsPos<ads.size()){
            products.add(products.size()-2,new Product(ads.get(adsPos),LayoutType.ADS));
            adsPos++;
        }
    }

}
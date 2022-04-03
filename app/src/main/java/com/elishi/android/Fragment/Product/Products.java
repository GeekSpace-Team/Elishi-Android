package com.elishi.android.Fragment.Product;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.view.ContextThemeWrapper;
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
import android.widget.LinearLayout;

import com.elishi.android.Adapter.Product.MultiSizeProductAdapter;
import com.elishi.android.Common.FilterType;
import com.elishi.android.Fragment.ProductFilter.FilterDialog;
import com.elishi.android.Fragment.ProductFilter.FilterList;
import com.elishi.android.Modal.Filter.FilterItem;
import com.elishi.android.Modal.Product.Product;
import com.elishi.android.R;
import com.elishi.android.databinding.FilterDialogBinding;
import com.elishi.android.databinding.FragmentProductsBinding;
import com.elishi.android.databinding.SortDialogBinding;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;


public class Products extends Fragment {
    private FragmentProductsBinding binding;
    private Context context;
    private SkeletonScreen skeletonScreen;
    private ArrayList<Product> products=new ArrayList<>();
    private StaggeredGridLayoutManager sGridLayoutManager;
    private MultiSizeProductAdapter productAdapter;
    private BottomSheetDialog bottomSheetDialog;
    private FilterDialog dialog;
    private static Products INSTANCE;
    private ArrayList<com.elishi.android.Modal.Filter.FilterList> filters=new ArrayList<>();
    public Products() {
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentProductsBinding.inflate(inflater,container,false);
        context=getContext();
        INSTANCE=this;
        createFilters();
        showSortDialog();
        showFilterDialog();
        setListener();
        setProducts();
        return binding.getRoot();
    }

    private void createFilters() {
        filters.clear();
        ArrayList<FilterItem> items=new ArrayList<>();
        for(int i=0;i<=100;i++){
            items.add(new FilterItem(i,"Tm"+i,"Ru"+i,"En"+i));
        }
        filters.add(new com.elishi.android.Modal.Filter.FilterList(1,
                "Kategoriyalar",
                "Категория",
                "Category",
                items,
                FilterType.CATEGORY
                ));
        filters.add(new com.elishi.android.Modal.Filter.FilterList(1,
                "Yeri",
                "Место",
                "Location",
                items,
                FilterType.LOCATION
        ));

        filters.add(new com.elishi.android.Modal.Filter.FilterList(1,
                "Baha",
                "Цена",
                "Price",
                items,
                FilterType.PRICE
        ));

        filters.add(new com.elishi.android.Modal.Filter.FilterList(1,
                "Status",
                "Статус",
                "Status",
                items,
                FilterType.STATUS
        ));

    }

    public static Products get(){
        return INSTANCE;
    }

    public FilterDialog getDialog(){
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
                if(!binding.list.canScrollVertically(1)){
                    Log.e("Bottom","Ok");
                }
            }
        });

        binding.filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show(getFragmentManager(),FilterDialog.class.getSimpleName());
            }
        });
    }



    private void showFilterDialog() {
        dialog=new FilterDialog(filters);


    }

    private void showSortDialog() {
        bottomSheetDialog = new BottomSheetDialog(context);
        SortDialogBinding sortDialogBinding=SortDialogBinding.inflate(LayoutInflater.from(context));
        sortDialogBinding.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
            }
        });

        sortDialogBinding.firstButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRadioButtonsOff(sortDialogBinding);
                sortDialogBinding.first.setChecked(true);
            }
        });
        sortDialogBinding.secondButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRadioButtonsOff(sortDialogBinding);
                sortDialogBinding.second.setChecked(true);
            }
        });
        sortDialogBinding.thirdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRadioButtonsOff(sortDialogBinding);
                sortDialogBinding.third.setChecked(true);
            }
        });
        sortDialogBinding.fourthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRadioButtonsOff(sortDialogBinding);
                sortDialogBinding.fourth.setChecked(true);
            }
        });
        sortDialogBinding.fifthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRadioButtonsOff(sortDialogBinding);
                sortDialogBinding.fifth.setChecked(true);
            }
        });


        bottomSheetDialog.setContentView(sortDialogBinding.getRoot());


    }

    private void setRadioButtonsOff(SortDialogBinding bnd){
        bnd.first.setChecked(false);
        bnd.second.setChecked(false);
        bnd.third.setChecked(false);
        bnd.fourth.setChecked(false);
        bnd.fifth.setChecked(false);
    }

    private void setProducts() {
        binding.extraRec.setVisibility(View.VISIBLE);
        productAdapter=new MultiSizeProductAdapter(products,context,false);
        binding.extraRec.setLayoutManager(getLayoutManager());
        skeletonScreen = Skeleton.bind(binding.extraRec)
                .adapter(productAdapter)
                .color(R.color.skeletonColor)
                .load(R.layout.skeleton_product)
                .duration(1200)
                .count(6)
                .show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    binding.list.setVisibility(View.VISIBLE);
                    binding.extraRec.setVisibility(View.GONE);

                    skeletonScreen.hide();
                    MultiSizeProductAdapter productAdapter = new MultiSizeProductAdapter(products, context, false);
                    GridLayoutManager gridLayoutManager = getLayoutManager();
                    binding.list.setLayoutManager(gridLayoutManager);
                    binding.list.setAdapter(productAdapter);
                    OverScrollDecoratorHelper.setUpOverScroll(binding.list, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);
                } catch (Exception ex){
                    ex.printStackTrace();
                }

            }
        },6000);
    }

    private GridLayoutManager getLayoutManager() {
        GridLayoutManager glm=new GridLayoutManager(context, 2);
        glm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup(){
            @Override
            public int getSpanSize(int position) {
                if((position+1)%5==0){
                    return 2;
                }
                return 1;
            }
        });
        return glm;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }
}
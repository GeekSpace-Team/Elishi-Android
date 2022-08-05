package com.elishi.android.Fragment.Profile;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.elishi.android.Activity.MainActivity;
import com.elishi.android.Adapter.Profile.MyProductsAdapter;
import com.elishi.android.Common.Utils;
import com.elishi.android.Modal.Product.Product;
import com.elishi.android.Modal.Profile.MyProduct;
import com.elishi.android.R;
import com.elishi.android.databinding.FragmentMyProductsBinding;
import com.elishi.android.databinding.FragmentMyProfileBinding;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;


public class MyProducts extends Fragment {

    private FragmentMyProductsBinding binding;
    private View view;
    private Context context;
    public ArrayList<Product> myProducts=new ArrayList<>();
    private LinearLayout empty;
    private MaterialButton empty_retry;
    public MyProducts() {
    }

    public static MyProducts newInstance() {
        Bundle args = new Bundle();
        MyProducts fragment = new MyProducts();
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
        binding= FragmentMyProductsBinding.inflate(inflater,container,false);
        view=binding.getRoot();
        empty=view.findViewById(R.id.empty);
        empty_retry=view.findViewById(R.id.empty_retry);
        context=getContext();
        empty_retry.setText(context.getResources().getString(R.string.add_product));
        empty_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.get().getBottomNavigationView().setSelectedItemId(R.id.add);
            }
        });

        if(myProducts==null || myProducts.size()<=0){
            empty.setVisibility(View.VISIBLE);
        } else {
            empty.setVisibility(View.GONE);
            setProducts();
        }
        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.swipeRefresh.setRefreshing(false);
                MainActivity.get().getSupportFragmentManager().beginTransaction().replace(R.id.layout,new MyProfile(),MyProfile.class.getSimpleName()).commit();
            }
        });
        return view;
    }

    private void setProducts() {
        binding.myProductsRec.setAdapter(new MyProductsAdapter(myProducts,context));
        binding.myProductsRec.setLayoutManager(new LinearLayoutManager(context));
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }
}
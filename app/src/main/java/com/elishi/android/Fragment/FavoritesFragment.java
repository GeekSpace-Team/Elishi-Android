package com.elishi.android.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elishi.android.Activity.MainActivity;
import com.elishi.android.Adapter.Product.MultiSizeProductAdapter;
import com.elishi.android.Api.APIClient;
import com.elishi.android.Api.ApiInterface;
import com.elishi.android.Common.Utils;
import com.elishi.android.Modal.Product.Product;
import com.elishi.android.Modal.Response.GBody;
import com.elishi.android.R;
import com.elishi.android.databinding.FragmentFavoritesBinding;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FavoritesFragment extends Fragment {

    private View view;
    private Context context;
    private TextView title;
    private RecyclerView favRec;
    private ArrayList<Product> products=new ArrayList<>();
    private int limit=20,page=1;
    private MotionLayout noInternetContainer;
    private MaterialCardView retry;
    private FragmentFavoritesBinding binding;
    private boolean isLoading=false,isMore=true;
    public FavoritesFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentFavoritesBinding.inflate(inflater,container,false);
        view=binding.getRoot();
        context=getContext();
        initComponents();
        setListener();
        setFonts();
        request(page);
        return binding.getRoot();
    }

    private void setListener() {
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page=1;
                request(page);
            }
        });

        binding.favRec.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(isMore && !isLoading && !binding.favRec.canScrollVertically(1)){
                    page++;
                    request(page);
                }
            }
        });

        binding.noLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.get().getBottomNavigationView().setSelectedItemId(R.id.profile);
            }
        });
    }

    private void request(int i) {
        if(Utils.getSharedPreference(context,"tkn").isEmpty()){
            page=1;
            binding.noLogin.setVisibility(View.VISIBLE);
            binding.favRec.setVisibility(View.GONE);
            binding.nofavs.setVisibility(View.GONE);
            binding.loading.setVisibility(View.GONE);
            noInternetContainer.setVisibility(View.GONE);
            return;
        }
        binding.noLogin.setVisibility(View.GONE);
        isLoading=true;
        if(i==1){
            products.clear();
            binding.loading.setVisibility(View.VISIBLE);
            noInternetContainer.setVisibility(View.GONE);
            binding.favRec.setVisibility(View.GONE);
            binding.nofavs.setVisibility(View.GONE);
        } else {
            MainActivity.get().getPagination().setVisibility(View.VISIBLE);
        }

        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<GBody<ArrayList<Product>>> call=apiInterface.getFavorite("Bearer "+Utils.getSharedPreference(context,"tkn"),i,limit);
        call.enqueue(new Callback<GBody<ArrayList<Product>>>() {
            @Override
            public void onResponse(Call<GBody<ArrayList<Product>>> call, Response<GBody<ArrayList<Product>>> response) {
                if(response.isSuccessful() && response.body()!=null && response.body().getBody()!=null){
                    binding.favRec.setVisibility(View.VISIBLE);
                    products.addAll(response.body().getBody());
                    if(i==1){
                        setAllCategories();
                    } else {
                        try{
                            binding.favRec.getAdapter().notifyDataSetChanged();
                        } catch (Exception ex){
                            ex.printStackTrace();
                        }
                    }
                    if(response.body().getBody().size()<=0 || response.body().getBody().size()<limit){
                        isMore=false;
                    }
                }
                if(products.size()<=0){
                    binding.nofavs.setVisibility(View.VISIBLE);
                }
                binding.loading.setVisibility(View.GONE);
                MainActivity.get().getPagination().setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<GBody<ArrayList<Product>>> call, Throwable t) {
                binding.loading.setVisibility(View.GONE);
                MainActivity.get().getPagination().setVisibility(View.GONE);
                if(products.size()<=0){
                    noInternetContainer.setVisibility(View.VISIBLE);
                }
            }
        });
    }


    private void setAllCategories() {
        MultiSizeProductAdapter productAdapter=new MultiSizeProductAdapter(products,context,false);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(context,2);
        favRec.setLayoutManager(gridLayoutManager);
        favRec.setAdapter(productAdapter);
    }

    private void setFonts() {
        title.setTypeface(Utils.getBoldFont(context));
    }

    private void initComponents() {
        title=view.findViewById(R.id.title);
        favRec=view.findViewById(R.id.favRec);
        noInternetContainer=view.findViewById(R.id.noInternetContainer);
        retry=view.findViewById(R.id.retry);
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            page=1;
            request(page);
        }
    }
}
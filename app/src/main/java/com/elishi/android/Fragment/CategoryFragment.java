package com.elishi.android.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.elishi.android.Adapter.Category.AllCategoryAdapter;
import com.elishi.android.Api.APIClient;
import com.elishi.android.Api.ApiInterface;
import com.elishi.android.Common.AppSnackBar;
import com.elishi.android.Common.Utils;
import com.elishi.android.Modal.Category.AllCategory;
import com.elishi.android.Modal.Response.GBody;
import com.elishi.android.R;
import com.elishi.android.databinding.FragmentCategoryBinding;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CategoryFragment extends Fragment {

    private View view;
    private Context context;
    private TextView title;
    private ArrayList<AllCategory> allCategories=new ArrayList<>();
    private RecyclerView categoryRec;
    private ScrollView scroll;
    private FragmentCategoryBinding binding;
    private MaterialCardView retry;
    public CategoryFragment() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentCategoryBinding.inflate(inflater,container,false);
        context=getContext();
        view=binding.getRoot();
        initComponents();
        setFonts();
        getCategories();
        return binding.getRoot();
    }

    private void getCategories() {
        hideError();
        binding.loading.setVisibility(View.VISIBLE);
        view.findViewById(R.id.noInternetContainer).setVisibility(View.GONE);
        binding.recContainer.setVisibility(View.GONE);
        ApiInterface apiInterface= APIClient.getClient().create(ApiInterface.class);
        Call<GBody<ArrayList<AllCategory>>> call=apiInterface.getCategories();
        call.enqueue(new Callback<GBody<ArrayList<AllCategory>>>() {
            @Override
            public void onResponse(Call<GBody<ArrayList<AllCategory>>> call, Response<GBody<ArrayList<AllCategory>>> response) {
                if(response.isSuccessful() && response.body()!=null && response.body().getBody()!=null && !response.body().getError()){
                    hideError();
                    allCategories=response.body().getBody();
                    setRecycler();
                } else {
                    String msg = Utils.checkMessage(context, response.body().getMessage());
                    if(!msg.isEmpty())
                        showSnackbar(msg);
                    showError();
                }
            }

            @Override
            public void onFailure(Call<GBody<ArrayList<AllCategory>>> call, Throwable t) {
                showError();
            }
        });
    }

    private void showSnackbar(String msg) {
        if (!msg.isEmpty()) {
            AppSnackBar snackBar = new AppSnackBar(context, view);
            snackBar.setTitle(msg);
            snackBar.actionText(R.string.cancel);
            snackBar.show();
        }
    }

    private void showError() {
        view.findViewById(R.id.noInternetContainer).setVisibility(View.VISIBLE);
        binding.recContainer.setVisibility(View.GONE);
        binding.loading.setVisibility(View.GONE);
    }

    private void hideError() {
        view.findViewById(R.id.noInternetContainer).setVisibility(View.GONE);
        binding.recContainer.setVisibility(View.VISIBLE);
        binding.loading.setVisibility(View.GONE);
    }

    private void setRecycler() {
        categoryRec.setAdapter(new AllCategoryAdapter(allCategories,context,getFragmentManager(),binding.subCategory));
        categoryRec.setLayoutManager(new LinearLayoutManager(context));
        categoryRec.setNestedScrollingEnabled(true);
//        OverScrollDecoratorHelper.setUpOverScroll(scroll);
    }



    private void setFonts() {
        title.setTypeface(Utils.getBoldFont(context));
    }

    private void initComponents() {
        title=view.findViewById(R.id.title);
        categoryRec=view.findViewById(R.id.categoryRec);
        scroll=view.findViewById(R.id.scroll);
        retry=view.findViewById(R.id.retry);
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCategories();
            }
        });
    }


}
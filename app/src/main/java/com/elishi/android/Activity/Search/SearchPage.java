package com.elishi.android.Activity.Search;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Fade;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.elishi.android.Adapter.Product.MultiSizeProductAdapter;
import com.elishi.android.Adapter.Search.SearchHistoryAdapter;
import com.elishi.android.Adapter.Search.SearchKeywordAdapter;
import com.elishi.android.Api.APIClient;
import com.elishi.android.Api.ApiInterface;
import com.elishi.android.Common.Utils;
import com.elishi.android.DateBase.SearchHistoryDB;
import com.elishi.android.Modal.Product.Product;
import com.elishi.android.Modal.Response.GBody;
import com.elishi.android.Modal.Search.SearchHistory;
import com.elishi.android.Modal.Search.SearchKeyword;
import com.elishi.android.R;
import com.elishi.android.databinding.ActivitySearchPageBinding;

import java.util.ArrayList;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchPage extends AppCompatActivity {
    private ActivitySearchPageBinding binding;
    private Context context=this;
    private String query="";
    private SearchHistoryDB db;
    private ArrayList<Product> products=new ArrayList<>();
    private Integer limit=20;
    private Integer page=1;
    private MultiSizeProductAdapter productAdapter;
    private boolean isMore=true;
    private boolean isLoading=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.loadLocal(context);
        binding=ActivitySearchPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db=new SearchHistoryDB(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            binding.searchCard.setTransitionName("search");
        }
        // here we are initializing
        // fade animation.
        Fade fade = new Fade();
        View decor = getWindow().getDecorView();

        // here also we are excluding status bar,
        // action bar and navigation bar from animation.
        fade.excludeTarget(decor.findViewById(R.id.action_bar_container), true);
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);

        // below methods are used for adding
        // enter and exit transition.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setEnterTransition(fade);
            getWindow().setExitTransition(fade);
        }

        showKeyboard();
        setListener();
        setSearchResult();
        checkMode();
    }

    private void showKeyboard() {
        binding.search.requestFocus();
        InputMethodManager imm = (InputMethodManager)   getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    private void hideKeyboard(){
        binding.search.clearFocus();
        InputMethodManager imm = (InputMethodManager)getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(binding.search.getWindowToken(), 0);
    }

    private void setSearchResult() {
        productAdapter=new MultiSizeProductAdapter(products,context,false);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        binding.searchResultRec.setLayoutManager(staggeredGridLayoutManager);
        binding.searchResultRec.setAdapter(productAdapter);
    }

    private void setListener() {
        binding.searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                page=1;
                search(page);
            }
        });

        binding.search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                query=binding.search.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    page=1;
                    search(page);
                    return true;
                }
                return false;
            }
        });

        binding.searchResultRec.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(!recyclerView.canScrollVertically(1) && !isLoading && isMore){
                    page++;
                    search(page);
                    binding.loadMoreProgress.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void checkMode() {
        int nightModeFlags =
                getResources().getConfiguration().uiMode &
                        Configuration.UI_MODE_NIGHT_MASK;
        View view = getWindow().getDecorView();
        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    view.setSystemUiVisibility(view.getSystemUiVisibility() & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                }
                break;

            case Configuration.UI_MODE_NIGHT_NO:
            case Configuration.UI_MODE_NIGHT_UNDEFINED:

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    view.setSystemUiVisibility(view.getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                }
                break;
        }
    }

    private void search(int p) {
        query=binding.search.getText().toString();
        if(query.trim().isEmpty()){
            return;
        }
        Dialog progressDialog=Utils.getDialogProgressBar(context);
        if(p==1){
            products.clear();
            progressDialog.show();
        }
        isLoading=true;
        hideKeyboard();
        ApiInterface apiInterface= APIClient.getClient().create(ApiInterface.class);
        Call<GBody<ArrayList<Product>>> call=apiInterface.search(p,query,limit,"Bearer "+ Utils.getSharedPreference(context,"tkn"));
        call.enqueue(new Callback<GBody<ArrayList<Product>>>() {
            @Override
            public void onResponse(Call<GBody<ArrayList<Product>>> call, Response<GBody<ArrayList<Product>>> response) {
                if(response.isSuccessful() && response.body()!=null && response.body().getBody()!=null){
                    products.addAll(response.body().getBody());
                    if(p==1){
                        setSearchResult();
                    } else {
                        try {
                            productAdapter.notifyDataSetChanged();
                        } catch (Exception ex){
                            ex.printStackTrace();
                        }
                    }
                    if(response.body().getBody().size()<limit){
                        isMore=false;
                    }
                } else {
                    isMore=false;
                }
                if(products.size()<=0){
                    binding.empty.setVisibility(View.VISIBLE);
                    binding.searchResultContainer.setVisibility(View.GONE);
                } else {
                    binding.empty.setVisibility(View.GONE);
                    binding.searchResultContainer.setVisibility(View.VISIBLE);
                }
                binding.loadMoreProgress.setVisibility(View.GONE);
                isLoading=false;
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GBody<ArrayList<Product>>> call, Throwable t) {
                if(products.size()<=0){
                    binding.empty.setVisibility(View.VISIBLE);
                    binding.searchResultContainer.setVisibility(View.GONE);
                }
                isLoading=false;
                progressDialog.dismiss();
            }
        });
    }


}
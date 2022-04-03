package com.elishi.android.Activity.Search;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.elishi.android.Adapter.Product.MultiSizeProductAdapter;
import com.elishi.android.Adapter.Search.SearchHistoryAdapter;
import com.elishi.android.Adapter.Search.SearchKeywordAdapter;
import com.elishi.android.DateBase.SearchHistoryDB;
import com.elishi.android.Modal.Product.Product;
import com.elishi.android.Modal.Search.SearchHistory;
import com.elishi.android.Modal.Search.SearchKeyword;
import com.elishi.android.R;
import com.elishi.android.databinding.ActivitySearchPageBinding;

import java.util.ArrayList;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

public class SearchPage extends AppCompatActivity {
    private ActivitySearchPageBinding binding;
    private ArrayList<SearchKeyword> keywords=new ArrayList<>();
    private ArrayList<SearchHistory> histories=new ArrayList<>();
    private Context context=this;
    private String query="";
    private SearchHistoryDB db;
    private ArrayList<Product> products=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySearchPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db=new SearchHistoryDB(context);
        setSearchKeywords();
        setSearchHistory();
        setListener();
        setSearchResult();
    }

    private void setSearchResult() {
        MultiSizeProductAdapter productAdapter=new MultiSizeProductAdapter(products,context,false);
        GridLayoutManager gridLayoutManager=getLayoutManager();
        binding.searchResultRec.setLayoutManager(gridLayoutManager);
        binding.searchResultRec.setAdapter(productAdapter);
        OverScrollDecoratorHelper.setUpOverScroll(binding.searchResultRec,OverScrollDecoratorHelper.ORIENTATION_VERTICAL);


    }

    private void setListener() {
        binding.searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
            }
        });

        binding.search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                query=binding.search.getText().toString();
                if(query.isEmpty()){
                    binding.searchResultContainer.setVisibility(View.GONE);
                    binding.searchDetails.setVisibility(View.VISIBLE);
                }
                setSearchHistory();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search();
                    return true;
                }
                return false;
            }
        });

        binding.searchResultRec.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(!recyclerView.canScrollVertically(1)){
                    binding.loadMoreProgress.setVisibility(View.VISIBLE);
                } else{
                    binding.loadMoreProgress.setVisibility(View.GONE);
                }
            }
        });
    }

    private void search() {

        query=binding.search.getText().toString();
        binding.search.clearFocus();
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(binding.search.getWindowToken(), 0);
        if(query.trim().isEmpty())
            return;
        Cursor cursor1=db.getSelect(query);
        if(cursor1.getCount()==0){
            db.insert(query);
            setSearchHistory();
        }
    }

    private void setSearchHistory() {
        binding.searchResultContainer.setVisibility(View.GONE);
        binding.searchDetails.setVisibility(View.VISIBLE);
        histories.clear();
        Cursor cursor=db.getAll();
        if(cursor.getCount()==0){
            binding.searchHistoryRec.setVisibility(View.GONE);
            binding.historyTv.setVisibility(View.GONE);
        } else{
            binding.searchHistoryRec.setVisibility(View.VISIBLE);
            binding.historyTv.setVisibility(View.VISIBLE);
            while (cursor.moveToNext()){
                histories.add(new SearchHistory(cursor.getInt(0),cursor.getString(1)));
            }
            binding.searchHistoryRec.setAdapter(new SearchHistoryAdapter(histories,context,binding.search));
            binding.searchHistoryRec.setLayoutManager(new LinearLayoutManager(context));
        }
    }

    private void setSearchKeywords() {
        keywords.clear();
        keywords.add(new SearchKeyword(1,"Haly"));
        keywords.add(new SearchKeyword(1,"Haly"));
        keywords.add(new SearchKeyword(1,"Haly"));
        keywords.add(new SearchKeyword(1,"Haly"));
        keywords.add(new SearchKeyword(1,"Haly"));
        keywords.add(new SearchKeyword(1,"Haly"));
        keywords.add(new SearchKeyword(1,"Haly"));
        binding.topSearchRec.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        binding.topSearchRec.setAdapter(new SearchKeywordAdapter(keywords,context,binding.search));
        OverScrollDecoratorHelper.setUpOverScroll(binding.topSearchRec,OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL);
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
}
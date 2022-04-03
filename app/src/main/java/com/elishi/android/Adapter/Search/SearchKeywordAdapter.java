package com.elishi.android.Adapter.Search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.elishi.android.Modal.Search.SearchKeyword;
import com.elishi.android.R;
import com.elishi.android.View.CustomViews.AppTextView;

import java.util.ArrayList;

public class SearchKeywordAdapter extends RecyclerView.Adapter<SearchKeywordAdapter.ViewHolder> {
    private ArrayList<SearchKeyword> keywords=new ArrayList<>();
    private Context context;
    private EditText editText;
    public SearchKeywordAdapter(ArrayList<SearchKeyword> keywords, Context context,EditText editText) {
        this.keywords = keywords;
        this.context = context;
        this.editText=editText;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.search_keyword_item,parent,false);
        return new SearchKeywordAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SearchKeyword keyword=keywords.get(position);
        holder.keyword.setText("#"+keyword.getText());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText(keyword.getText());
            }
        });
    }

    @Override
    public int getItemCount() {
        return keywords.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout itemBg;
        private AppTextView keyword;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            keyword=itemView.findViewById(R.id.keyword);
            itemBg=itemView.findViewById(R.id.itemBg);
        }
    }
}

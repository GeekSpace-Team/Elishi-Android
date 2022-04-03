package com.elishi.android.Adapter.Search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.elishi.android.DateBase.SearchHistoryDB;
import com.elishi.android.Modal.Search.SearchHistory;
import com.elishi.android.R;
import com.elishi.android.View.CustomViews.AppEditText;
import com.elishi.android.View.CustomViews.AppTextView;

import java.util.ArrayList;

public class SearchHistoryAdapter extends RecyclerView.Adapter<SearchHistoryAdapter.ViewHolder> {
    private ArrayList<SearchHistory> histories=new ArrayList<>();
    private Context context;
    private SearchHistoryDB db;
    private AppEditText search;
    public SearchHistoryAdapter(ArrayList<SearchHistory> histories, Context context, AppEditText search) {
        this.histories = histories;
        this.context = context;
        this.search=search;
        db=new SearchHistoryDB(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.search_history_item,parent,false);
        return new SearchHistoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SearchHistory history=histories.get(holder.getAbsoluteAdapterPosition());
        holder.text.setText(history.getText());
        holder.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteData(history.getText());
                histories.remove(holder.getAbsoluteAdapterPosition());
                notifyItemRemoved(holder.getAbsoluteAdapterPosition());
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search.setText(history.getText());
            }
        });
    }

    @Override
    public int getItemCount() {
        return histories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private AppTextView text;
        private ImageView close;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            close=itemView.findViewById(R.id.close);
            text=itemView.findViewById(R.id.text);
        }
    }
}

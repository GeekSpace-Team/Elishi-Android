package com.elishi.android.Adapter.Profile;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.elishi.android.Common.PlaceHolderColors;
import com.elishi.android.Common.Utils;
import com.elishi.android.Modal.Profile.MyProduct;
import com.elishi.android.R;
import com.elishi.android.databinding.MyProductItemBinding;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Random;

public class MyProductsAdapter extends RecyclerView.Adapter<MyProductsAdapter.ViewHolder> implements PopupMenu.OnMenuItemClickListener {
    private ArrayList<MyProduct> myProducts=new ArrayList<>();
    private Context context;
    private MyProductItemBinding binding;
    private Integer pos=0;
    public MyProductsAdapter(ArrayList<MyProduct> myProducts, Context context) {
        this.myProducts = myProducts;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding=MyProductItemBinding.inflate(LayoutInflater.from(context));
        return new MyProductsAdapter.ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyProduct product=myProducts.get(position);
        final int min = 0;
        final int max = PlaceHolderColors.PLACEHOLDERS.length-1;
        final int r = new Random().nextInt((max - min) + 1) + min;
        Glide.with(context)
                .load(product.getImage())
                .placeholder(PlaceHolderColors.PLACEHOLDERS[r])
                .into(binding.image);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                pos=holder.getAdapterPosition();
                showMenu(holder.itemView);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return myProducts.size();
    }

    public void showMenu(View v) {
        PopupMenu popup = new PopupMenu(context, v);
        try {
            Method method = popup.getMenu().getClass().getDeclaredMethod("setOptionalIconsVisible", boolean.class);
            method.setAccessible(true);
            method.invoke(popup.getMenu(), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // This activity implements OnMenuItemClickListener
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.actions);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:
                Toast.makeText(context, ""+pos, Toast.LENGTH_SHORT).show();
                return true;
            case R.id.delete:
                Toast.makeText(context, "Delete"+pos, Toast.LENGTH_SHORT).show();
                return true;
            case R.id.bringToFront:
                Toast.makeText(context, "Bring to front"+pos, Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

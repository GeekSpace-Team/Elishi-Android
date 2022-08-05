package com.elishi.android.Adapter.Profile;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.elishi.android.Activity.MainActivity;
import com.elishi.android.Activity.Product.ProductView;
import com.elishi.android.Api.APIClient;
import com.elishi.android.Api.ApiInterface;
import com.elishi.android.Common.Constant;
import com.elishi.android.Common.PlaceHolderColors;
import com.elishi.android.Common.ProductStatus;
import com.elishi.android.Common.Utils;
import com.elishi.android.Fragment.Product.EditProduct;
import com.elishi.android.Fragment.Profile.MyProfile;
import com.elishi.android.Fragment.Profile.ProfileRootFragment;
import com.elishi.android.Modal.Product.Product;
import com.elishi.android.Modal.Profile.MyProduct;
import com.elishi.android.R;
import com.elishi.android.databinding.MyProductBinding;
import com.elishi.android.databinding.MyProductItemBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyProductsAdapter extends RecyclerView.Adapter<MyProductsAdapter.ViewHolder> implements PopupMenu.OnMenuItemClickListener {
    private ArrayList<Product> myProducts=new ArrayList<>();
    private Context context;
    private MyProductBinding binding;
    private Integer pos=0;
    public MyProductsAdapter(ArrayList<Product> myProducts, Context context) {
        this.myProducts = myProducts;
        this.context = context;
        Utils.loadLocal(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding=MyProductBinding.inflate(LayoutInflater.from(context));
        return new MyProductsAdapter.ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product=myProducts.get(holder.getAbsoluteAdapterPosition());
        holder.setIsRecyclable(false);
        final int min = 0;
        final int max = PlaceHolderColors.PLACEHOLDERS.length-1;
        final int r = new Random().nextInt((max - min) + 1) + min;
        if(product.getImages()!=null) {
            Collections.sort(product.getImages(), (abc1, abc2) -> Boolean.compare(abc2.getIs_first(), abc1.getIs_first()));
            Glide.with(context)
                    .load(Constant.IMAGE_URL+product.getImages().get(0).getSmall_image())
                    .timeout(60000).placeholder(PlaceHolderColors.PLACEHOLDERS[r])
                    .into(binding.image);
        }

        binding.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pos=holder.getAbsoluteAdapterPosition();
                showMenu(view);
            }
        });

        binding.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductView.class);
                intent.putExtra("id", product.getId()+"");
                intent.putExtra("image", "");
                context.startActivity(intent);
            }
        });

        binding.card.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                pos=holder.getAbsoluteAdapterPosition();
                showMenu(v);
                return false;
            }
        });

        binding.name.setText(product.getProduct_name());
        binding.price.setText(product.getPrice()+" TMT");
        binding.cancelReason.setVisibility(View.GONE);
        binding.getRoot().setAlpha(1f);
        if(product.getStatus().equals(ProductStatus.PASSIVE)) {
            binding.status.setText(context.getResources().getString(R.string.status) + " " + context.getResources().getString(R.string.pending_product));
            setStatusColor(R.color.pending_status);
            binding.getRoot().setAlpha(0.5f);
        } else if(product.getStatus().equals(ProductStatus.ACTIVE)) {
            binding.status.setText(context.getResources().getString(R.string.status) + " " + context.getResources().getString(R.string.active_product));
            setStatusColor(R.color.green1_status);
        } else if(product.getStatus().equals(ProductStatus.MASTER)) {
            binding.status.setText(context.getResources().getString(R.string.status) + " " + context.getResources().getString(R.string.master_product_st));
            setStatusColor(R.color.second);
        } else if(product.getStatus().equals(ProductStatus.VIP)) {
            binding.status.setText(context.getResources().getString(R.string.status) + " " + context.getResources().getString(R.string.vip_product_st));
            setStatusColor(R.color.second);
        } else if(product.getStatus().equals(ProductStatus.CANCELED)) {
            setStatusColor(R.color.red);
            binding.status.setText(context.getResources().getString(R.string.status) + " " + context.getResources().getString(R.string.canceled_product));
            if(product.getCancel_reason()!=null && !product.getCancel_reason().trim().isEmpty()){
                binding.cancelReason.setVisibility(View.VISIBLE);
                binding.cancelReason.setText(context.getResources().getString(R.string.cancel_reason)+" "+product.getCancel_reason());
            }
        }
    }

    private void setStatusColor(int color) {
        binding.statusColor.setImageResource(color);
        binding.status.setTextColor(context.getResources().getColor(color));
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
                EditProduct dialogFragment=EditProduct.newInstance(myProducts.get(pos).getId()+"");
                dialogFragment.show(MainActivity.get().getSupportFragmentManager(),EditProduct.class.getSimpleName());
                return true;
            case R.id.delete:
                MaterialAlertDialogBuilder askDelete=new MaterialAlertDialogBuilder(context);
                askDelete.setTitle(R.string.pay_attention);
                askDelete.setMessage(R.string.do_you_want_delete);
                askDelete.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        deleteProduct(myProducts.get(pos).getId()+"");
                    }
                });
                askDelete.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                askDelete.show();
                return true;
            case R.id.bringToFront:
                bringToFront(myProducts.get(pos).getId()+"");
                return true;
            default:
                return false;
        }
    }

    private void deleteProduct(String s) {
        Dialog progressDialog=Utils.getDialogProgressBar(context);
        progressDialog.show();
        ApiInterface apiInterface= APIClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> call=apiInterface.deleteProduct("Bearer "+Utils.getSharedPreference(context,"tkn"),s);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try{
                        MainActivity.get().getSupportFragmentManager().beginTransaction().replace(R.id.layout,new MyProfile(),MyProfile.class.getSimpleName()).commit();
                    } catch (Exception ex){
                        ex.printStackTrace();
                    }
                } else{
                    MaterialAlertDialogBuilder alertDialogBuilder=new MaterialAlertDialogBuilder(context);
                    alertDialogBuilder.setMessage(context.getResources().getString(R.string.error_message));
                    alertDialogBuilder.setTitle(R.string.pay_attention);
                    alertDialogBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alertDialogBuilder.show();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                MaterialAlertDialogBuilder alertDialogBuilder=new MaterialAlertDialogBuilder(context);
                alertDialogBuilder.setMessage(context.getResources().getString(R.string.error_message));
                alertDialogBuilder.setTitle(R.string.pay_attention);
                alertDialogBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialogBuilder.show();
            }
        });
    }


    private void bringToFront(String s) {
        Dialog progressDialog=Utils.getDialogProgressBar(context);
        progressDialog.show();
        ApiInterface apiInterface= APIClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> call=apiInterface.bringToFront("Bearer "+Utils.getSharedPreference(context,"tkn"),s);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    MainActivity.get().getSupportFragmentManager().beginTransaction().replace(R.id.layout,new MyProfile(),MyProfile.class.getSimpleName()).commit();
                } else{
                    MaterialAlertDialogBuilder alertDialogBuilder=new MaterialAlertDialogBuilder(context);
                    alertDialogBuilder.setMessage(context.getResources().getString(R.string.error_message));
                    alertDialogBuilder.setTitle(R.string.pay_attention);
                    alertDialogBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alertDialogBuilder.show();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                MaterialAlertDialogBuilder alertDialogBuilder=new MaterialAlertDialogBuilder(context);
                alertDialogBuilder.setMessage(context.getResources().getString(R.string.error_message));
                alertDialogBuilder.setTitle(R.string.pay_attention);
                alertDialogBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialogBuilder.show();
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

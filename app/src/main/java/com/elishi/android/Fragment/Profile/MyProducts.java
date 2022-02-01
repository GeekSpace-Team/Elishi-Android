package com.elishi.android.Fragment.Profile;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elishi.android.Adapter.Profile.MyProductsAdapter;
import com.elishi.android.Modal.Profile.MyProduct;
import com.elishi.android.R;
import com.elishi.android.databinding.FragmentMyProductsBinding;
import com.elishi.android.databinding.FragmentMyProfileBinding;

import java.util.ArrayList;


public class MyProducts extends Fragment {

    private FragmentMyProductsBinding binding;
    private View view;
    private Context context;
    private ArrayList<MyProduct> myProducts=new ArrayList<>();
    public MyProducts() {
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
        context=getContext();
        setProducts();
        return view;
    }

    private void setProducts() {
        myProducts.clear();
        myProducts.add(new MyProduct(1,"https://i.pinimg.com/originals/92/65/d5/9265d507a842f9792f202ec0ba658ab0.jpg","Name"));
        myProducts.add(new MyProduct(1,"https://i.pinimg.com/originals/e5/02/5d/e5025d708a599332a3ef3a31053c173f.jpg","Name"));
        myProducts.add(new MyProduct(1,"https://i.pinimg.com/originals/e1/30/f0/e130f01e25e00ea06901bdb382abe93b.jpg","Name"));
        myProducts.add(new MyProduct(1,"https://i.pinimg.com/originals/e5/02/5d/e5025d708a599332a3ef3a31053c173f.jpg","Name"));
        myProducts.add(new MyProduct(1,"https://static01.nyt.com/images/2021/01/10/fashion/00NA-BEADWORK-03/00NA-BEADWORK-03-superJumbo.jpg","Name"));
        myProducts.add(new MyProduct(1,"https://static01.nyt.com/images/2021/01/10/fashion/00NA-BEADWORK-04/00NA-BEADWORK-04-articleLarge.jpg?quality=75&auto=webp&disable=upscale","Name"));
        myProducts.add(new MyProduct(1,"https://picklebarreltradingpost.com/wp-content/uploads/2018/07/Native-American-San-Carlos-Apache-beadwork-1024x683.jpg","Name"));
        myProducts.add(new MyProduct(1,"https://i.pinimg.com/originals/d7/24/af/d724af4aadeb35a4f266e2030ec2badf.jpg","Name"));
        myProducts.add(new MyProduct(1,"https://i.pinimg.com/originals/61/c4/75/61c47545e1568679e6322df7350fb96c.jpg","Name"));
        myProducts.add(new MyProduct(1,"https://www.karliisfikirleri.com/wp-content/uploads/2018/10/evde-el-i%C5%9Fleri-777x400.jpg","Name"));
        binding.myProductsRec.setAdapter(new MyProductsAdapter(myProducts,context));
        binding.myProductsRec.setLayoutManager(new GridLayoutManager(context,3));
    }
}
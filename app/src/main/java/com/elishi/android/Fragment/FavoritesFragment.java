package com.elishi.android.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.elishi.android.Adapter.Product.MultiSizeProductAdapter;
import com.elishi.android.Common.Utils;
import com.elishi.android.Modal.Product.Product;
import com.elishi.android.R;

import java.util.ArrayList;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;


public class FavoritesFragment extends Fragment {

    private View view;
    private Context context;
    private TextView title;
    private RecyclerView favRec;
    private ArrayList<Product> products=new ArrayList<>();
    public FavoritesFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_favorites, container, false);
        context=getContext();
        initComponents();
        setFonts();
        setAllCategories();
        return view;
    }



    private void setAllCategories() {
        MultiSizeProductAdapter productAdapter=new MultiSizeProductAdapter(products,context,false);
        GridLayoutManager gridLayoutManager=getLayoutManager();
        favRec.setLayoutManager(gridLayoutManager);
        favRec.setAdapter(productAdapter);
        OverScrollDecoratorHelper.setUpOverScroll(favRec,OverScrollDecoratorHelper.ORIENTATION_VERTICAL);
    }

    private void setFonts() {
        title.setTypeface(Utils.getBoldFont(context));
    }

    private void initComponents() {
        title=view.findViewById(R.id.title);
        favRec=view.findViewById(R.id.favRec);
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
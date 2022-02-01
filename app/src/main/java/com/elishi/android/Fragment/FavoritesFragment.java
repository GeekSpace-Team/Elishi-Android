package com.elishi.android.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.elishi.android.Adapter.Category.AllCategoryAdapter;
import com.elishi.android.Adapter.Product.FavAdapter;
import com.elishi.android.Adapter.Product.MultiSizeProductAdapter;
import com.elishi.android.Common.Utils;
import com.elishi.android.Modal.Category.AllCategory;
import com.elishi.android.Modal.Favorite.Favorites;
import com.elishi.android.Modal.Product.Product;
import com.elishi.android.R;

import java.util.ArrayList;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;


public class FavoritesFragment extends Fragment {

    private View view;
    private Context context;
    private TextView title;
    private ArrayList<Favorites> favorites=new ArrayList<>();
    private RecyclerView favRec;
    private ScrollView scroll;

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
        favorites.clear();
        favorites.add(new Favorites(1,"Nagşy maýda küýze",134.0,"https://p4.wallpaperbetter.com/wallpaper/465/666/514/photography-depth-of-field-handicraft-wallpaper-preview.jpg",true));
        favorites.add(new Favorites(1,"Nagşy maýda küýze",134.0,"https://images.pexels.com/photos/1117272/pexels-photo-1117272.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",true));
        favorites.add(new Favorites(1,"Nagşy maýda küýze",134.0,"https://www.trtavaz.com.tr/uploads/photos/2019/02/15/bb47aa3470d340fbb12120fff1451a50.gif?w=640",false));
        favorites.add(new Favorites(1,"Nagşy maýda küýze",134.0,"https://i.pinimg.com/originals/99/1e/a7/991ea7a64d1e70cb148bbc50f73a529f.jpg",false));
        favorites.add(new Favorites(1,"Nagşy maýda küýze",134.0,"https://i2.wp.com/www.atavatan-turkmenistan.com/wp-content/uploads/2020/09/2-21.jpg?fit=591%2C392&ssl=1",true));
        favorites.add(new Favorites(1,"Nagşy maýda küýze",134.0,"https://www.ylymly.com/wp-content/uploads/2019/02/turkmen-shay-saypaeleri.jpg",false));
        favorites.add(new Favorites(1,"Nagşy maýda küýze",134.0,"https://www.turkmenmetbugat.gov.tm/storage/articles/1845/uUQ9bO954oxkG2c4AKcwjGFORv10xcBryjfpQDsJ9vAJYhNdaNuWI9lr2LsJ.jpg",true));
        favorites.add(new Favorites(1,"Nagşy maýda küýze",134.0,"https://ashgabat.in/wp-content/uploads/2021/06/6c4a03801ce7c36bcc4bbe76f539783b-450x3002-3.jpg",false));
        favorites.add(new Favorites(1,"Nagşy maýda küýze",134.0,"https://i.mycdn.me/i?r=AzEOxUXG5QgodWC3x6hM10CkvXQVcFiK4Dn3ujHQVNkWc_apV-FlrPFK6NMUXEEsv2I&fn=sqr_288",true));
        favorites.add(new Favorites(1,"Nagşy maýda küýze",134.0,"https://p4.wallpaperbetter.com/wallpaper/465/666/514/photography-depth-of-field-handicraft-wallpaper-preview.jpg",true));
        favorites.add(new Favorites(1,"Nagşy maýda küýze",134.0,"https://images.pexels.com/photos/1117272/pexels-photo-1117272.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",true));
        favorites.add(new Favorites(1,"Nagşy maýda küýze",134.0,"https://www.trtavaz.com.tr/uploads/photos/2019/02/15/bb47aa3470d340fbb12120fff1451a50.gif?w=640",false));
        favorites.add(new Favorites(1,"Nagşy maýda küýze",134.0,"https://i.pinimg.com/originals/99/1e/a7/991ea7a64d1e70cb148bbc50f73a529f.jpg",false));
        favorites.add(new Favorites(1,"Nagşy maýda küýze",134.0,"https://i2.wp.com/www.atavatan-turkmenistan.com/wp-content/uploads/2020/09/2-21.jpg?fit=591%2C392&ssl=1",true));
        favorites.add(new Favorites(1,"Nagşy maýda küýze",134.0,"https://www.ylymly.com/wp-content/uploads/2019/02/turkmen-shay-saypaeleri.jpg",false));
        favorites.add(new Favorites(1,"Nagşy maýda küýze",134.0,"https://www.turkmenmetbugat.gov.tm/storage/articles/1845/uUQ9bO954oxkG2c4AKcwjGFORv10xcBryjfpQDsJ9vAJYhNdaNuWI9lr2LsJ.jpg",true));
        favorites.add(new Favorites(1,"Nagşy maýda küýze",134.0,"https://ashgabat.in/wp-content/uploads/2021/06/6c4a03801ce7c36bcc4bbe76f539783b-450x3002-3.jpg",false));
        favorites.add(new Favorites(1,"Nagşy maýda küýze",134.0,"https://i.mycdn.me/i?r=AzEOxUXG5QgodWC3x6hM10CkvXQVcFiK4Dn3ujHQVNkWc_apV-FlrPFK6NMUXEEsv2I&fn=sqr_288",true));

//        GridLayoutManager gridLayoutManager=new GridLayoutManager(context, 2);
        StaggeredGridLayoutManager sGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        FavAdapter productAdapter=new FavAdapter(favorites,context,false);
        favRec.setLayoutManager(sGridLayoutManager);
        favRec.setAdapter(productAdapter);
        favRec.setNestedScrollingEnabled(false);
        OverScrollDecoratorHelper.setUpOverScroll(scroll);
    }

    private void setFonts() {
        title.setTypeface(Utils.getBoldFont(context));
    }

    private void initComponents() {
        title=view.findViewById(R.id.title);
        favRec=view.findViewById(R.id.favRec);
        scroll=view.findViewById(R.id.scroll);
    }
}
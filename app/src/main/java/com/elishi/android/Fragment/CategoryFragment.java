package com.elishi.android.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.elishi.android.Adapter.Category.AllCategoryAdapter;
import com.elishi.android.Common.Utils;
import com.elishi.android.Modal.Category.AllCategory;
import com.elishi.android.R;

import java.util.ArrayList;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;


public class CategoryFragment extends Fragment {

    private View view;
    private Context context;
    private TextView title;
    private ArrayList<AllCategory> allCategories=new ArrayList<>();
    private RecyclerView categoryRec;
    private ScrollView scroll;
    public CategoryFragment() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_category, container, false);
        context=getContext();
        initComponents();
        setFonts();
        setAllCategories();
        setRecycler();
        return view;
    }

    private void setRecycler() {
        categoryRec.setAdapter(new AllCategoryAdapter(allCategories,context,getFragmentManager()));
        categoryRec.setLayoutManager(new LinearLayoutManager(context));
        categoryRec.setNestedScrollingEnabled(false);
        OverScrollDecoratorHelper.setUpOverScroll(scroll);
    }

    private void setAllCategories() {
        allCategories.clear();
        ArrayList<String> img1 = new ArrayList<>();
        img1.add("https://okcredit-blog-images-prod.storage.googleapis.com/2021/03/Handicraft-Business1--1-.jpg");
        ArrayList<String> img2 = new ArrayList<>();
        img2.add("https://media.mehrnews.com/d/2018/07/23/4/2841436.jpg");
        img2.add("https://images.pexels.com/photos/1117272/pexels-photo-1117272.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");
        ArrayList<String> img3 = new ArrayList<>();
        img3.add("https://cdn.s3waas.gov.in/s3577ef1154f3240ad5b9b413aa7346a1e/uploads/bfi_thumb/2019121752-1-olw8qulb5f5and2erqsskrfk2l65nbof2neab3mj7q.jpg");
        img3.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRECJQpDpAtv7qraOG-VMHFBj_i8K_mnh2kyIavipRlzjY7GY0eIWs714WnSldsXn3q_i8&usqp=CAU");
        img3.add("https://media.mehrnews.com/d/2018/07/23/4/2841436.jpg");
        allCategories.add(new AllCategory(1,img2,"Title tm","Title ru","Title en"));
        allCategories.add(new AllCategory(1,img3,"Title tm","Title ru","Title en"));
        allCategories.add(new AllCategory(1,img1,"Title tm","Title ru","Title en"));
        allCategories.add(new AllCategory(1,img2,"Title tm","Title ru","Title en"));
        allCategories.add(new AllCategory(1,img1,"Title tm","Title ru","Title en"));
        allCategories.add(new AllCategory(1,img3,"Title tm","Title ru","Title en"));
        allCategories.add(new AllCategory(1,img2,"Title tm","Title ru","Title en"));
        allCategories.add(new AllCategory(1,img3,"Title tm","Title ru","Title en"));
        allCategories.add(new AllCategory(1,img1,"Title tm","Title ru","Title en"));
        allCategories.add(new AllCategory(1,img2,"Title tm","Title ru","Title en"));
        allCategories.add(new AllCategory(1,img1,"Title tm","Title ru","Title en"));
        allCategories.add(new AllCategory(1,img3,"Title tm","Title ru","Title en"));
    }

    private void setFonts() {
        title.setTypeface(Utils.getBoldFont(context));
    }

    private void initComponents() {
        title=view.findViewById(R.id.title);
        categoryRec=view.findViewById(R.id.categoryRec);
        scroll=view.findViewById(R.id.scroll);
    }
}
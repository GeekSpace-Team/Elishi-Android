package com.elishi.android.Fragment.Profile;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.elishi.android.Adapter.Location.RegionAdapter;
import com.elishi.android.Common.Utils;
import com.elishi.android.Modal.Location.Region;
import com.elishi.android.Modal.Location.SubLocation;
import com.elishi.android.R;
import com.elishi.android.databinding.FragmentCreateAccountBinding;

import java.util.ArrayList;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;


public class CreateAccount extends Fragment {

    private View view;
    private Context context;
    private FragmentCreateAccountBinding binding;
    public CreateAccount() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentCreateAccountBinding.inflate(inflater,container,false);
        view=binding.getRoot();
        context=getContext();
        setFonts();
        setListener();
        return view;
    }

    private void setListener() {
        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.saveButton.setVisibility(View.GONE);
                binding.progress.setVisibility(View.VISIBLE);
            }
        });

        binding.locationContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        binding.location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
    }

    private void setFonts() {
        binding.fullName.setTypeface(Utils.getRegularFont(context));
        binding.location.setTypeface(Utils.getRegularFont(context));
        binding.saveButton.setTypeface(Utils.getMediumFont(context));
    }

    private void showDialog() {
        Dialog dialog = new Dialog(context);
        LayoutInflater localInflater = LayoutInflater.from(context);
        View view = localInflater.inflate(R.layout.select_location_dialog, null, false);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        window.getAttributes().windowAnimations = R.style.DialogAnimation;

        TextView dialogTitle=dialog.findViewById(R.id.title);
        RecyclerView regionRec=dialog.findViewById(R.id.regionRec);
        ImageView close=dialog.findViewById(R.id.close);

        dialogTitle.setTypeface(Utils.getMediumFont(context));
        ArrayList<Region> regions=new ArrayList<>();
        ArrayList<SubLocation> subLocations=new ArrayList<>();
        subLocations.add(new SubLocation("1","Ашгабат","Ashgabat"));
        subLocations.add(new SubLocation("1","Анев","Anew"));
        subLocations.add(new SubLocation("1","Кака","Kaka"));

        regions.add(new Region("1","Ахал","Ahal",subLocations));
        regions.add(new Region("1","Балкан","Balkan",subLocations));
        regions.add(new Region("1","Мары","Mary",subLocations));
        regions.add(new Region("1","Лебап","Lebap",subLocations));
        regions.add(new Region("1","Дашогуз","Dashoguz",subLocations));

        regionRec.setAdapter(new RegionAdapter(regions,context,dialog));
        regionRec.setLayoutManager(new LinearLayoutManager(context));

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });







        dialog.setCancelable(true);
        window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        dialog.show();

    }
}
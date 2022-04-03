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
import com.elishi.android.Modal.Response.PublicAPI.Locations;
import com.elishi.android.R;
import com.elishi.android.databinding.FragmentCreateAccountBinding;

import java.util.ArrayList;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;


public class CreateAccount extends Fragment {

    private View view;
    private Context context;
    private FragmentCreateAccountBinding binding;
    private String phoneNumber="";
    public CreateAccount() {
    }

    public static CreateAccount newInstance(String phoneNumber) {
        Bundle args = new Bundle();
        args.putString("phoneNumber",phoneNumber);
        CreateAccount fragment = new CreateAccount();
        fragment.setArguments(args);
        return fragment;
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
        try {
            this.phoneNumber = getArguments().getString("phoneNumber");
        } catch (Exception ex){
            ex.printStackTrace();
        }
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
        ArrayList<Locations> locations=new ArrayList<>();
        regionRec.setAdapter(new RegionAdapter(locations,context,dialog));
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }
}
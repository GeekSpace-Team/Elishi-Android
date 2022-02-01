package com.elishi.android.Fragment.Profile;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elishi.android.Adapter.Profile.MyHoldayAdapter;
import com.elishi.android.Modal.Profile.MyHoliday;
import com.elishi.android.R;
import com.elishi.android.databinding.FragmentMyHolidaysBinding;

import java.util.ArrayList;


public class MyHolidays extends Fragment {

    private FragmentMyHolidaysBinding binding;
    private ArrayList<MyHoliday> myHolidays=new ArrayList<>();
    private Context context;

    public MyHolidays() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentMyHolidaysBinding.inflate(inflater,container,false);
        context=getContext();
        setHolidays();
        return binding.getRoot();
    }

    private void setHolidays() {
        myHolidays.add(new MyHoliday(1,
                "https://fiverr-res.cloudinary.com/images/t_main1,q_auto,f_auto,q_auto,f_auto/gigs/128009228/original/8e8ad34b012b46ebd403bd4157f8fef6bb2c076b/design-minimalist-flat-cartoon-caricature-avatar-in-6-hours.jpg",
                "Elishi team",
                "10.01.2022",
                context.getResources().getString(R.string.content)));
        myHolidays.add(new MyHoliday(1,
                "https://4.bp.blogspot.com/-txKoWDBmvzY/XHAcBmIiZxI/AAAAAAAAC5o/wOkD9xoHn28Dl0EEslKhuI-OzP8_xvTUwCLcBGAs/s1600/2.jpg",
                "Elishi team",
                "10.01.2022",
                context.getResources().getString(R.string.content)));
        myHolidays.add(new MyHoliday(1,
                "https://fiverr-res.cloudinary.com/images/t_main1,q_auto,f_auto,q_auto,f_auto/gigs/104113705/original/6076831db34315e45bd2a31a9d79bb7b91d48e04/design-flat-style-minimalist-avatar-of-you.jpg",
                "Elishi team",
                "10.01.2022",
                context.getResources().getString(R.string.content)));
        myHolidays.add(new MyHoliday(1,
                "https://bestprofilepictures.com/wp-content/uploads/2021/06/Wolf-Profile-Picture.jpg",
                "Elishi team",
                "10.01.2022",
                context.getResources().getString(R.string.content)));
        myHolidays.add(new MyHoliday(1,
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTU7u14Oism0_PtVuxXlTG5K7IwLTnT6KTPf6zWduxFOZUcb-n1-Ahsw5echkeJ017_zkQ&usqp=CAU",
                "Elishi team",
                "10.01.2022",
                context.getResources().getString(R.string.content)));
        myHolidays.add(new MyHoliday(1,
                "",
                "Elishi team",
                "10.01.2022",
                context.getResources().getString(R.string.content)));
        myHolidays.add(new MyHoliday(1,
                "https://avatarfiles.alphacoders.com/145/145926.jpg",
                "Elishi team",
                "10.01.2022",
                context.getResources().getString(R.string.content)));
        myHolidays.add(new MyHoliday(1,
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQuCplPn3Mq26bkNeD71qseA9m-aNavXIG2yw&usqp=CAU",
                "Elishi team",
                "10.01.2022",
                context.getResources().getString(R.string.content)));


        binding.rec.setAdapter(new MyHoldayAdapter(myHolidays,context,getActivity().getSupportFragmentManager()));
        binding.rec.setLayoutManager(new LinearLayoutManager(context));

    }
}
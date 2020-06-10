package com.example.riddhi.m1etropolishtutelage;

/**
 * Created by RIDDHI on 26-02-17.
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;


public class announcements extends Fragment {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    FloatingActionButton update;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View v = inflater.inflate(R.layout.announcement, container, false);
        // getActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Globalvariable globalVariable;
        globalVariable = (Globalvariable) getApplicationContext();
        globalVariable.setName("home");
        viewPager = (ViewPager) v.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        update = (FloatingActionButton) v.findViewById(R.id.fab_annoncement);


        SharedPreferences pref = getActivity().getSharedPreferences("login", 0);
        String email = pref.getString("email", null);
        String password = pref.getString("password", null);
        Log.d("isssss",email+">>>>"+password);
        if(!email.equals("riddhi18121995@gmail.com") && !password.equals("riddhi2385")){
            update.setVisibility(View.GONE);
        }
        else {
            update.setVisibility(View.VISIBLE);
        }

        tabLayout = (TabLayout) v.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        return v;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Announcement");

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent in = new Intent(getActiZCDFfdvity(), upload_announcement.class);
//                startActivity(in);
                upload_announcement AA = new upload_announcement();
                setFragment(AA);

            }
        });


    }

    public void setFragment(Fragment frag) {

        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.content_frame, frag).commit();
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());
        adapter.addFragment(new sports_details(), "Sports");
        adapter.addFragment(new general_announcement(), "General");
        adapter.addFragment(new gov_initiative_details(), "Initiatives");
        adapter.addFragment(new scholership_details(), "Scholership");
        //adapter.addFragment(new T, "THREE");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position)

        {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
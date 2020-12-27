package com.Nick.myapplication.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public List<Fragment> fragmentList = new ArrayList<>();

    public ViewPagerAdapter( @NonNull FragmentManager fm ){
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem( int position ){
        return fragmentList.get(position);
    }

    @Override
    public int getCount(){
        return fragmentList.size();
    }
    public void addFragment (Fragment frament){
        fragmentList.add(frament);
    }

}

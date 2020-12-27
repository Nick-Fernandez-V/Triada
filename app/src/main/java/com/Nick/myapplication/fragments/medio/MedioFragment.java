package com.Nick.myapplication.fragments.medio;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.Nick.myapplication.R;
import com.Nick.myapplication.adapters.ViewPagerAdapter;
import com.Nick.myapplication.fragments.tabs.AmigosFragment;
import com.Nick.myapplication.fragments.tabs.ChatFragment;
import com.Nick.myapplication.fragments.tabs.ConocerFragment;
import com.Nick.myapplication.fragments.tabs.GustarFragment;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class MedioFragment extends Fragment {

    View root;

    TabLayout tableLayout;
    TabItem conocer,gustar,chatear,amigos;

    final int[] tabIcons = {R.drawable.ic_tab_conocer,R.drawable.ic_tab_gustar,R.drawable.ic_tab_chat,
            R.drawable.ic_tab_amigo};


    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_medio, container, false);

        tableLayout = root.findViewById(R.id.tabLayouts);
        conocer= root.findViewById(R.id.tabConocer);
        gustar= root.findViewById(R.id.tabGustas);
        chatear= root.findViewById(R.id.tabChat);
        amigos= root.findViewById(R.id.tabAmigos);

        ViewPager viewPager = root.findViewById(R.id.vP4);

        LoadPager(viewPager);
        tableLayout.setupWithViewPager(viewPager);
        TabIcon();
        iconColor(tableLayout.getTabAt(tableLayout.getSelectedTabPosition()),"#6745c2");

        tableLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected( TabLayout.Tab tab ){
                iconColor(tab,"#6745c2");
            }

            @Override
            public void onTabUnselected( TabLayout.Tab tab ){
                iconColor(tab,"#E0E0E0");
            }

            @Override
            public void onTabReselected( TabLayout.Tab tab ){

            }
        });

        return root;
    }


    private void iconColor(TabLayout.Tab tab, String color){
        tab.getIcon().setColorFilter(Color.parseColor(color),PorterDuff.Mode.SRC_IN);
    }


    private void LoadPager(ViewPager viewPager){
        //getFragmentManager()
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        viewPagerAdapter.addFragment(new ConocerFragment());
        viewPagerAdapter.addFragment(new GustarFragment());
        viewPagerAdapter.addFragment(new ChatFragment());
        viewPagerAdapter.addFragment(new AmigosFragment());
        viewPager.setAdapter(viewPagerAdapter);
    }


    public void TabIcon(){
        for (int i=0;i<4;i++)
        {
            tableLayout.getTabAt(i).setIcon(tabIcons[i]);
            iconColor( tableLayout.getTabAt(i),"#E0E0E0");
        }
    }

}
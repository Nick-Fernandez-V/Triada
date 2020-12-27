package com.Nick.myapplication.fragments.tabs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.Nick.myapplication.R;

public class GustarFragment extends Fragment {
    View root;
    TextView txTitle;


    @Override
    public View onCreateView( LayoutInflater inflater ,ViewGroup container ,
                              Bundle savedInstanceState )
    { root = inflater.inflate(R.layout.fragment_gustar ,container ,false);

        txTitle = root.findViewById(R.id.gustartv);
        txTitle.setText("Gustar");

        return root;
    }
}
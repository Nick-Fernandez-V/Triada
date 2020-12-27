package com.Nick.myapplication.fragments.inicio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.Nick.myapplication.R;

public class InicioFragment extends Fragment {

    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_inicio, container, false);


        return root;
    }

}
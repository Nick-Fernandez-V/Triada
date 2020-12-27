package com.Nick.myapplication.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.Nick.myapplication.R;

public class GaleriaImagenAdapter extends BaseAdapter {

    private Context context;

    public int[] imagenesArray = { R.drawable.imagen_avatar};

    public GaleriaImagenAdapter( Context context ){
        this.context = context;
    }

    @Override
    public int getCount(){
        return imagenesArray.length;
    }

    @Override
    public Object getItem( int position ){
        return imagenesArray[position];
    }

    @Override
    public long getItemId( int position ){
        return 0;
    }

    @Override
    public View getView( int position ,View convertView ,ViewGroup parent ){


        ImageView imageView = new ImageView(context);
        imageView.setImageResource(imagenesArray[position]);

        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(
                new ViewGroup.LayoutParams(350,350));

        return imageView;
    }

}

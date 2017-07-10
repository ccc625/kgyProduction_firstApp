package org.androidtown.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by lee on 2017-06-28.
 */

public class ViewerFragment extends Fragment{

    ImageView imageView;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_viewer, container , false);

        imageView = (ImageView)rootView.findViewById(R.id.imageView);

        return rootView;
    }

    public void setImage(int resId){
        imageView.setImageResource(resId);
    }
}

package org.androidtown.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by lee on 2017-06-28.
 */

public class MenuFragment extends Fragment{



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle saveInstanceState){
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_menu,container,false);

        return rootView;
    }
}

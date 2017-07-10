package org.androidtown.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by lee on 2017-06-28.
 */

public class ListFragment extends Fragment {
    String[] values = {"첫번째 이미지", "두번째 이미지", "세번째 이미지"};

    public static interface ImageSelectionCallback{
        public void onImageSelected(int position);

    }

    public ImageSelectionCallback callback;


    public void onAttach(Context context){
        super.onAttach(context);

        if(context instanceof ImageSelectionCallback){
        callback = (ImageSelectionCallback)context;
    }
}

public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_list,container,false);

    ListView listView = (ListView)rootView.findViewById(R.id.listView);
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, values);
    listView.setAdapter(adapter);

    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if(callback!=null){
                callback.onImageSelected(position);
            }

        }
    });
    return rootView;



}
}

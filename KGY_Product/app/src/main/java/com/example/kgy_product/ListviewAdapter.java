package com.example.kgy_product;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lee on 2017-05-13.
 */

public class ListviewAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<Listviewitem> data;
    private int layout;

    public ListviewAdapter(Context context, int resLayout, //context 상태를 나타내는것
                           ArrayList<Listviewitem> data){
        this.inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data=data;
        layout = resLayout;

}


public int getCount(){return data.size();}


public String getItem(int position){ return data.get(position).getName();}


public long getItemId(int position){return position;}

public View getView(int position , View convertView , ViewGroup parent) {
    if (convertView == null) {
        convertView = inflater.inflate(layout, parent, false);
    }
    Listviewitem listviewitem = data.get(position);

    ImageView icon = (ImageView) convertView.findViewById(R.id.imageView);
    icon.setImageResource(listviewitem.getIcon());

    TextView name = (TextView) convertView.findViewById(R.id.textview);
    name.setText(listviewitem.getName());

    return convertView;

}
}

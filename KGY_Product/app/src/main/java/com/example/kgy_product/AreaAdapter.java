package com.example.kgy_product;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lee on 2018-01-06.
 */

public class AreaAdapter extends BaseAdapter {


    private Context mContext = null;
    private int layout = 0;
    private ArrayList<Areadata> data =null;
    private LayoutInflater inflate = null;
    private HashMap<View,Areadata> views;

    public AreaAdapter( Context mContext, int layout,ArrayList<Areadata> data) {

        this.data = data;
        this.mContext = mContext;
        this.layout = layout;
        this.inflate = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(this.views != null){
            this.views.clear();
        }
        this.views = new HashMap<>();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position).getCode();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if(convertView ==null){
            convertView = inflate.inflate(this.layout, parent,false);
        }

        Areadata areadata = data.get(position);
        TextView text = (TextView)convertView.findViewById(R.id.text);
        text.setText(areadata.getName());

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,TeamMakeActivity.class);
                intent.putExtra("location",views.get(view).getCode());
                mContext.startActivity(intent);
            }
        };

        convertView.setOnClickListener(onClickListener);

        views.put(convertView,areadata);

        return convertView;
    }

    public void clear(){
        if( views != null){
            for (Map.Entry<View,Areadata> entry : views.entrySet()){
                entry.getKey().setOnClickListener(null);
                entry.getKey().destroyDrawingCache();
            }

            views.clear();
        }

        if (data != null){
            data.clear();
        }

        data = null;
        mContext = null;
        inflate = null;
    }
}

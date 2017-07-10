package com.example.kgy_product;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lee on 2017-07-10.
 */


public class ListViewAdapter extends BaseAdapter {

    private ArrayList<ListViewItem> listViewItemArrayList = new ArrayList<ListViewItem>();
    ImageView iconImageView;
    TextView descTextView;

    public ListViewAdapter(MainListViewActivity mainActivity, int simple_list_item_single_choice, ArrayList<String> items) {

    }

    public int getCount() {
        return listViewItemArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewItemArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addItem(Drawable icon, String desc)
    {
        ListViewItem item = new ListViewItem();

        item.setIcon(icon);
        item.setDesc(desc);

        listViewItemArrayList.add(item);
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        final int pos = position;
        final Context context = parent.getContext();

        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
        }

        iconImageView = (ImageView)convertView.findViewById(R.id.image);
        descTextView = (TextView)convertView.findViewById(R.id.TextView1);

        ListViewItem listViewItem = listViewItemArrayList.get(pos);

        iconImageView.setImageDrawable(listViewItem.getIcon());
        descTextView.setText(listViewItem.getDesc());

        return convertView;


    }
}

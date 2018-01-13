package com.example.kgy_product.board;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by ccc62 on 2018-01-13.
 */

public class CommentListViewAdaptor extends BaseAdapter
{
    private Context mContext;

    private LayoutInflater inflater;
    private ArrayList<CommentData> data;
    private int itemLayout;

    public CommentListViewAdaptor(Context context, int itemLayout, ArrayList<CommentData> data)
    {
        mContext = context;

        this.inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
        this.itemLayout = itemLayout;
    }

    @Override
    public int getCount()
    {
        return 0;
    }

    @Override
    public Object getItem(int position)
    {
        return null;
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        return null;
    }
}

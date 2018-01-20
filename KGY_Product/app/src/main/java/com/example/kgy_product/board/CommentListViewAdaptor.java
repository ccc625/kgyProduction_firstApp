package com.example.kgy_product.board;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kgy_product.R;

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
        return data.size();
    }

    @Override
    public Object getItem(int position)
    {
        return data.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if( convertView == null )
        {
            convertView = inflater.inflate(itemLayout, parent, false);
        }

        CommentData commentData = data.get(position);

        TextView txtCommentName = (TextView) convertView.findViewById(R.id.txtCommentName);
        txtCommentName.setText(commentData.getTeamName());

        TextView txtComment = (TextView) convertView.findViewById(R.id.txtComment);
        txtComment.setText(commentData.getComment());

        return convertView;
    }

    public void clear()
    {

    }
}

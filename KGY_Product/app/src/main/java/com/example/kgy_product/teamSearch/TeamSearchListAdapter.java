package com.example.kgy_product.teamSearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kgy_product.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ccc62 on 2017-06-06.
 */

public class TeamSearchListAdapter extends BaseAdapter
{
    private Context mContext;
    private LayoutInflater inflater;
    private ArrayList<TeamSearchListItem> data;
    private HashMap<View, TeamSearchListItem> views;
    private int itemLayout;

    public TeamSearchListAdapter(Context context, int itemLayout, ArrayList<TeamSearchListItem> data)
    {
        mContext = context;

        this.inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
        this.itemLayout = itemLayout;

        if( this.views != null)
            this.views.clear();

        this.views = new HashMap<>();
    }

    public void setData( ArrayList<TeamSearchListItem> inData )
    {
        this.data = inData;
    }

    public int getCount(){return data.size();}

    public String getItem(int position){ return data.get(position).getName();}

    public long getItemId(int position){return position;}

    public View getView(int position , View convertView , ViewGroup parent)
    {
        if( convertView == null )
        {
            convertView = inflater.inflate(itemLayout, parent, false);
        }

        TeamSearchListItem listviewitem = data.get(position);

        ImageView icon = (ImageView) convertView.findViewById(R.id.imgTeamSearchView);
        icon.setImageBitmap(listviewitem.getIcon());

        TextView name = (TextView) convertView.findViewById(R.id.txtTeamSearchView);
        name.setText(listviewitem.getName());

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                System.out.println( views.get(v).getName() );
            }
        };

        convertView.setOnClickListener(onClickListener);

        views.put(convertView, listviewitem);

        return convertView;
    }

    public void clear()
    {
        if( views != null )
        {
            for(Map.Entry<View, TeamSearchListItem> entry : views.entrySet())
            {
                entry.getKey().setOnClickListener(null);
                entry.getValue().setName("");
                entry.getValue().setIcon(null);
            }

            views.clear();
        }

        if( data != null )
            data.clear();

        data = null;
        views = null;
        inflater = null;
        mContext = null;
    }
}

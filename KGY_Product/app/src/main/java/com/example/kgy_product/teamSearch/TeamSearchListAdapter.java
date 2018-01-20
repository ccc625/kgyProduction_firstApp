package com.example.kgy_product.teamSearch;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kgy_product.util.BitmapUtil;
import com.example.kgy_product.BoardActivity;
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
    private ArrayList<TeamData> data;
    private HashMap<View, TeamData> views;
    private int itemLayout;

    public TeamSearchListAdapter(Context context, int itemLayout, ArrayList<TeamData> data)
    {
        mContext = context;

        this.inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
        this.itemLayout = itemLayout;

        if( this.views != null)
            this.views.clear();

        this.views = new HashMap<>();
    }

    public void setData( ArrayList<TeamData> inData )
    {
        this.data = inData;
    }

    public int getCount(){return data.size();}

    public String getItem(int position){ return data.get(position).getTeamNm();}

    public long getItemId(int position){return position;}

    public View getView(int position , View convertView , ViewGroup parent)
    {
        if( convertView == null )
        {
            convertView = inflater.inflate(itemLayout, parent, false);
        }

        TeamData teamData = data.get(position);

        ImageView icon = (ImageView) convertView.findViewById(R.id.imgTeamSearchView);
        icon.setImageBitmap(BitmapUtil.getBitmapToString(teamData.getImgFile()));

        TextView name = (TextView) convertView.findViewById(R.id.txtTeamSearchView);
        name.setText(teamData.getTeamNm());

        View.OnClickListener onClickListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                System.out.println( views.get(v).getTeamNo() );
                Intent intent = new Intent(mContext, BoardActivity.class);
                intent.putExtra("id",views.get(v).getTeamNo() );
                mContext.startActivity(intent);
            }
        };

        convertView.setOnClickListener(onClickListener);

        views.put(convertView, teamData);

        return convertView;
    }

    public void clear()
    {
        if( views != null )
        {
            for(Map.Entry<View, TeamData> entry : views.entrySet())
            {
                entry.getKey().setOnClickListener(null);
                entry.getKey().destroyDrawingCache();
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

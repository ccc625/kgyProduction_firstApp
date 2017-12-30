package com.example.kgy_product.teamMake;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.kgy_product.R;
import com.example.kgy_product.networkTask.NetworkdAdaptor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ccc62 on 2017-05-13.
 */

public class TeamInfoLayout extends LinearLayout
{
    private Context mContext;

    private LinearLayout rootLayout;
    private Spinner drunkTypeSpinner;
    private Spinner drunkQuantitySpinner;
    private EditText txtComment;
    private EditText txtWish;

    private ArrayAdapter spinnerAdapter;
    private HashMap<String, String> alcohol;

    public TeamInfoLayout( Context context )
    {
        super( context );

        mContext = context;
        init();
    }

    public void dispose()
    {
        if( drunkTypeSpinner != null )
        {
            drunkTypeSpinner.destroyDrawingCache();
            drunkTypeSpinner.setAdapter(null);
            drunkTypeSpinner = null;
        }

        if( drunkQuantitySpinner != null )
        {
            drunkQuantitySpinner.destroyDrawingCache();
            drunkQuantitySpinner = null;
        }

        if( txtComment != null )
        {
            txtComment.destroyDrawingCache();
            txtComment = null;
        }

        if( txtWish != null )
        {
            txtWish.destroyDrawingCache();
            txtWish = null;
        }

        if( rootLayout != null )
        {
            rootLayout.destroyDrawingCache();
            rootLayout = null;
        }

        if( spinnerAdapter != null )
        {
            spinnerAdapter.clear();
            spinnerAdapter = null;
        }

        if( alcohol != null )
        {
            alcohol.clear();
            alcohol = null;
        }

        mContext = null;
    }

    private void init()
    {
        rootLayout = (LinearLayout) inflate( mContext, R.layout.team_info_layout, null );
        addView( rootLayout, new LayoutParams( LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT ) );

        drunkTypeSpinner = (Spinner) rootLayout.findViewById( R.id.drunkTypeSpinner );
        drunkQuantitySpinner = (Spinner) rootLayout.findViewById( R.id.drunkQuantitySpinner );
        txtComment = (EditText) rootLayout.findViewById( R.id.txtComment );
        txtWish = (EditText) rootLayout.findViewById( R.id.txtWish );
    }

    private void setDrunkSpinner()
    {
        ArrayList<String> arrAlcohol = new ArrayList<>();

        for(Map.Entry<String, String> entry : alcohol.entrySet() )
        {
            arrAlcohol.add(entry.getKey());
        }

        spinnerAdapter = new ArrayAdapter(mContext, R.layout.support_simple_spinner_dropdown_item, arrAlcohol);
        drunkTypeSpinner.setAdapter(spinnerAdapter);
    }

    public void setData(JSONArray arrAlcohol)
    {
        alcohol = new HashMap<>();

        ///TODO @jimin arrAlcohol정렬 필요

        try
        {
            JSONObject objAlcohol;
            for( int i = 0; i < arrAlcohol.length(); i++ )
            {
                objAlcohol = (JSONObject) arrAlcohol.get(i);

                alcohol.put((String) objAlcohol.get("common_nm"), (String) objAlcohol.get("common_cd"));
            }
        }
        catch( JSONException exception )
        {
            exception.printStackTrace();
            return;
        }

        setDrunkSpinner();
    }

    public HashMap<String, String> getData()
    {
        HashMap<String, String> result = new HashMap<>();

        result.put("alcohol", alcohol.get(drunkTypeSpinner.getSelectedItem().toString()));

        String alcoholNum = drunkQuantitySpinner.getSelectedItem().toString();

        alcoholNum = alcoholNum.replace("병", "");

        result.put("al_num", alcoholNum);
        result.put("team_comment", txtComment.getText().toString());
        result.put("team_you_comment", txtWish.getText().toString());

        return result;
    }
}

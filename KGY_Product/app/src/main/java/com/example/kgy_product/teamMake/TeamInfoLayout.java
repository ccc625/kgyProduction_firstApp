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
            arrAlcohol.add(entry.getValue());
        }

        spinnerAdapter = new ArrayAdapter(mContext, R.layout.support_simple_spinner_dropdown_item, arrAlcohol);
        drunkTypeSpinner.setAdapter(spinnerAdapter);
    }

    public void setData(JSONArray arrAlcohol)
    {
        alcohol = new HashMap<>();

        try
        {
            JSONObject objAlcohol;
            for( int i = 0; i < arrAlcohol.length(); i++ )
            {
                objAlcohol = (JSONObject) arrAlcohol.get(i);

                alcohol.put((String) objAlcohol.get("common_cd"), (String) objAlcohol.get("common_nm"));
            }
        }
        catch( JSONException exception )
        {
            exception.printStackTrace();
            return;
        }

        setDrunkSpinner();
    }
}

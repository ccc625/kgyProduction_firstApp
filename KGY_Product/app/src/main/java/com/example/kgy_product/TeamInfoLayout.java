package com.example.kgy_product;

import android.content.Context;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

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
}

package com.example.kgy_product.teamMake;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.kgy_product.R;

/**
 * Created by ccc62 on 2017-05-13.
 */

public class MakeTeamLayout extends LinearLayout
{
    private Context mContext;

    private LinearLayout rootLayout;

    private RadioGroup selectGender;
    private RadioButton btnMan;
    private RadioButton btnWoman;

    private RadioGroup.OnCheckedChangeListener checkedChangeListener;

    public MakeTeamLayout( Context context )
    {
        super( context );

        mContext = context;



    }



    @Override
    protected void onAttachedToWindow()
    {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
    }

    public void dispose()
    {
        if( selectGender != null )
        {
            selectGender.destroyDrawingCache();
            selectGender.setOnCheckedChangeListener( null );
            selectGender = null;
        }

        if( btnMan != null )
        {
            btnMan.destroyDrawingCache();
            btnMan = null;
        }

        if( btnWoman != null )
        {
            btnWoman.destroyDrawingCache();
            btnWoman = null;
        }

        if( rootLayout != null)
        {
            rootLayout.destroyDrawingCache();
            rootLayout = null;
        }

        checkedChangeListener = null;
        mContext = null;
    }

    private void init()
    {
        initDisplayObject();
        initListener();
    }

    private void initDisplayObject()
    {
        rootLayout = (LinearLayout) inflate( mContext, R.layout.make_team_layout, null );
        addView( rootLayout, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT) );

        selectGender = ( RadioGroup ) rootLayout.findViewById( R.id.selectGender );

        btnMan = (RadioButton) rootLayout.findViewById(R.id.btnMan);
        btnWoman = (RadioButton) rootLayout.findViewById(R.id.btnWoman);
    }

    private void initListener()
    {
        checkedChangeListener = new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                if( checkedId == btnMan.getId() )
                {
                    System.out.println("btnMan touched");
                }
                else if( checkedId == btnWoman.getId() )
                {
                    System.out.println("btnWoman touched");
                }
            }
        };

        selectGender.setOnCheckedChangeListener( checkedChangeListener );
    }


}

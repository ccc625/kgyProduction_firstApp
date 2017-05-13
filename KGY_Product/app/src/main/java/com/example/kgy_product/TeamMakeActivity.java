package com.example.kgy_product;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.RadioButton;

/**
 * Created by ccc62 on 2017-03-25.
 */

public class TeamMakeActivity extends AppCompatActivity
{
    private static final int MODE_MIN = 101;
    private static final int MODE_MAX = 102;
    private static final int MODE_MAKE_TEAM = 101;
    private static final int MODE_INFO_TEAM = 102;

    private static final int CONTENT_INDEX = 1;
    private static final int BOTTOM_INDEX = 2;

    private LinearLayout lMakeTeam;

    private EditText txtTeamName;
    private Spinner memberCountSpinner;

    private BottomLayout bottomLayout;
    private MakeTeamLayout makeTeamLayout;
    private TeamInfoLayout teamInfoLayout;

    private int currentMode;

    private BottomLayout.ButtonCallback bottomButtonListener;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_team);

        init();
    }

    @Override
    protected void onDestroy()
    {
        txtTeamName = null;
        memberCountSpinner = null;

        bottomLayout = null;

        super.onDestroy();
    }

    private void init()
    {
        currentMode = 101;

        initDisplayObject();
        initListener();
    }

    private void initDisplayObject()
    {
        lMakeTeam = ( LinearLayout ) findViewById( R.id.lMakeTeam );

        initBottomLayout();

        setMode( currentMode );
    }

    private void initBottomLayout()
    {
        if( bottomLayout == null )
            bottomLayout = new BottomLayout(this);
        lMakeTeam.addView( bottomLayout );
    }

    private void setMode( int mode )
    {
        if( lMakeTeam.getChildCount() >= 3 )
            lMakeTeam.removeViewAt( CONTENT_INDEX );

        switch( mode )
        {
            case MODE_MAKE_TEAM :
                if( makeTeamLayout == null )
                    makeTeamLayout = new MakeTeamLayout(this);
                lMakeTeam.addView( makeTeamLayout, CONTENT_INDEX );
                break;
            case MODE_INFO_TEAM :
                if( teamInfoLayout == null )
                    teamInfoLayout = new TeamInfoLayout(this);
                lMakeTeam.addView( teamInfoLayout, CONTENT_INDEX );
                break;
        }
    }

    private void initListener()
    {
        bottomButtonListener = new BottomLayout.ButtonCallback() {
            @Override
            public void onClickPrevButton()
            {
                System.out.println("btnPrev");
                if( currentMode > MODE_MIN )
                    currentMode -= 1;

                setMode( currentMode );
            }

            @Override
            public void onClickNextButton()
            {
                System.out.println("btnNext");
                if( currentMode < MODE_MAX )
                    currentMode += 1;

                setMode( currentMode );
            }
        };

        bottomLayout.setButtonCallback( bottomButtonListener );
    }
}

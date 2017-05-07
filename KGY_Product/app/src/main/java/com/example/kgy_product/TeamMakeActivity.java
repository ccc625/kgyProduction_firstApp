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
    private LinearLayout lMakeTeam;

    private EditText txtTeamName;
    private Spinner memberCountSpinner;

    private LinearLayout bottomLayout;
    private LinearLayout makeTeamLayout;

    private RadioGroup.OnCheckedChangeListener checkedChangeListener;

    private RadioGroup selectGender;
    private RadioButton btnMan;
    private RadioButton btnWoman;
    private Button btnPrev;
    private Button btnNext;
    String[] items={"1명","2명","3명","4명","5명 ","6명"};

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_team);

        init();
    }

    @Override
    protected void onDestroy()
    {
        btnMan = null;
        btnWoman = null;

        txtTeamName = null;
        memberCountSpinner = null;

        bottomLayout = null;

        super.onDestroy();
    }

    private void init()
    {
        initDisplayObject();
        initListener();
    }

    private void initDisplayObject()
    {
//
//        txtTeamName = (EditText) findViewById(R.id.txtTeamName);
//        memberCountSpinner = (Spinner) findViewById(R.id.memberCountSpinner);

        lMakeTeam = ( LinearLayout ) findViewById( R.id.lMakeTeam );

        makeTeamLayout = ( LinearLayout ) getLayoutInflater().inflate( R.layout.make_team_layout, null );
        lMakeTeam.addView( makeTeamLayout );

        selectGender = ( RadioGroup ) makeTeamLayout.findViewById( R.id.selectGender );

        btnMan = (RadioButton) makeTeamLayout.findViewById(R.id.btnMan);
        btnWoman = (RadioButton) makeTeamLayout.findViewById(R.id.btnWoman);

//        addContentView( makeTeamLayout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT) );

        bottomLayout = ( LinearLayout ) getLayoutInflater().inflate( R.layout.bottom_layout, null );
        lMakeTeam.addView( bottomLayout );
//        addContentView( bottomLayout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT) );
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

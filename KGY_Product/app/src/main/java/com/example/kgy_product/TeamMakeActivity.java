package com.example.kgy_product;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.RadioButton;

/**
 * Created by ccc62 on 2017-03-25.
 */

public class TeamMakeActivity extends AppCompatActivity
{
    private EditText txtTeamName;
    private Spinner memberCountSpinner;

    private BottomLayout bottomLayout;

    private View.OnClickListener buttonClickListener;

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
        btnMan = (RadioButton) findViewById(R.id.btnMan);
        btnWoman = (RadioButton) findViewById(R.id.btnWoman);

        txtTeamName = (EditText) findViewById(R.id.txtTeamName);
        memberCountSpinner = (Spinner) findViewById(R.id.memberCountSpinner);

        bottomLayout = new BottomLayout(this);
        addContentView( bottomLayout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT) );
    }

    private void initListener()
    {
        buttonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == btnMan.getId()) {

                } else if (view.getId() == btnWoman.getId()) {

                }
            }
        };
    }
}

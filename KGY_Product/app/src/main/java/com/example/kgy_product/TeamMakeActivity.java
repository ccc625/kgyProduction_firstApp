package com.example.kgy_product;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.RadioButton;

/**
 * Created by ccc62 on 2017-03-25.
 */

public class TeamMakeActivity extends AppCompatActivity
{
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

    private void init()
    {
        initDisplayObject();

    }
    private void initDisplayObject()
    {
        btnPrev = (Button) findViewById(R.id.btnPrev);
        btnNext = (Button) findViewById(R.id.btnNext);
    }
}

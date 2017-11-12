package com.example.kgy_product;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{
    private Button btnSelectPlace0;
    private Button btnSelectPlace1;
    private Button btnSelectPlace2;




    private View.OnClickListener buttonClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init()
    {
        initDisplayObject();
        initListener();
    }

    private void initDisplayObject()
    {
        btnSelectPlace0 = (Button) findViewById(R.id.btnSelectPlace0);
        btnSelectPlace1 = (Button) findViewById(R.id.btnSelectPlace1);
        btnSelectPlace2 = (Button) findViewById(R.id.btnSelectPlace2);
    }

    private void initListener()
    {
        buttonClickListener = new View.OnClickListener() {
            @Override
            public void onClick( View view )
            {
                ///TODO 각 버튼별로 동작 지정
                if( view.getId() == btnSelectPlace0.getId() )
                {


                    Intent intent = new Intent(getApplicationContext(),TeamMakeActivity.class);
                    intent.putExtra("Seoul","서울");
                    startActivity(intent);



                }
                else if( view.getId() == btnSelectPlace1.getId() )
                {
                    Intent intent = new Intent(getApplicationContext(),TeamMakeActivity.class);
                    intent.putExtra("Gangnam","강남");
                    startActivity(intent);

                }
                else if( view.getId() == btnSelectPlace2.getId() )
                {
                    Intent intent = new Intent(getApplicationContext(),TeamMakeActivity.class);
                    intent.putExtra("Gangbook","강북");
                    startActivity(intent);

                }
            }
        };

        btnSelectPlace0.setOnClickListener( buttonClickListener );
        btnSelectPlace1.setOnClickListener( buttonClickListener );
        btnSelectPlace2.setOnClickListener( buttonClickListener );
    }
}

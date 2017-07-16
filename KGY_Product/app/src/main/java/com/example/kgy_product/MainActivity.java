package com.example.kgy_product;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.kgy_product.networkTask.NetworkTask;

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

        String url = "http://172.30.1.41:8080/kgy";

        NetworkTask networkTask = new NetworkTask(url, null);
        networkTask.execute();
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

                }
                else if( view.getId() == btnSelectPlace1.getId() )
                {

                }
                else if( view.getId() == btnSelectPlace2.getId() )
                {

                }
            }
        };

        btnSelectPlace0.setOnClickListener( buttonClickListener );
        btnSelectPlace1.setOnClickListener( buttonClickListener );
        btnSelectPlace2.setOnClickListener( buttonClickListener );
    }
}

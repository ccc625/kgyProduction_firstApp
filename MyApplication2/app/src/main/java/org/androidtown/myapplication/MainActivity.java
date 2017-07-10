package org.androidtown.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }
    public void onButton1Clicked(    View v){
        inflateLayout();
    }

    private void inflateLayout(){
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.contentsLayout);

        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.button, linearLayout,true);

        Button selectButton = (Button)findViewById(R.id.selectButton);
        final CheckBox allDay = (CheckBox)findViewById(R.id.allDay);

        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(allDay.isChecked()){
                    allDay.setChecked(false);
                }else {
                    allDay.setChecked();
                }
            }
        });
    }



}




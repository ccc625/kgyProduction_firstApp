package org.androidtown.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        String fileName = "items.list";
        ListView listView;
        final ListViewAdapter adapter;
        final ArrayList<String> items = new ArrayList<String>();
        Button but;




        but = (Button) findViewById(R.id.button1);
        adapter = new ListViewAdapter(this, android.R.layout.simple_list_item_single_choice, items);
        listView = (ListView) findViewById(R.id.listView1);
        listView.setAdapter(adapter);


        but.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText edit1 = (EditText) findViewById(R.id.edit);
                String strNew = (String) edit1.getText().toString();

                if (strNew.length() > 0) {
                    items.add(strNew);

                    edit1.setText("");

                    adapter.notifyDataSetChanged();


                }
            }
        });


    }
}














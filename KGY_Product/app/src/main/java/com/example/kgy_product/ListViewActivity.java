package com.example.kgy_product;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by lee on 2017-07-16.
 */

public class ListViewActivity extends AppCompatActivity {


    private ListView listview;
    private ArrayAdapter adapter;
    private ArrayList<String> items = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board);


        listview = (ListView) findViewById(R.id.listView1);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, items);

        listview.setAdapter(adapter);

        Button buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonAdd.setEnabled(true);
        buttonAdd.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText editTextNew = (EditText) findViewById(R.id.editTextNew);
                String strNew = (String) editTextNew.getText().toString();
                if (strNew.length() > 0) {
                    items.add(strNew);
                    editTextNew.setText("");
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
}

package com.example.kgy_product;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

import static com.example.kgy_product.R.id.listview;

/**
 * Created by lee on 2017-05-13.
 */

public class ListviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity6_main);

        ListView listView = (ListView) findViewById(listview);

        ArrayList<Listviewitem> data = new ArrayList<>();
        Listviewitem boy = new Listviewitem(R.drawable.boy, "남자그룹");
        Listviewitem tiger = new Listviewitem(R.drawable.woman, "여자그룹");

        data.add( boy );
        data.add( tiger );

        ListviewAdapter adapter = new ListviewAdapter(this, R.layout.item, data);
       listView.setAdapter(adapter);

    }
}
package org.androidtown.myapplication;

import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    BitmapDrawable bitmap;
    ListView listView1;
    IconTextAdapter adapter;
    Button Button1;
    EditText Edit1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView1 = (ListView) findViewById(R.id.listview1);
        Button1 = (Button) findViewById(R.id.Button1);
        Edit1 = (EditText) findViewById(R.id.Edit1);

        adapter = new IconTextAdapter(this);



Button1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Resources res = getResources();
        String str = Edit1.getText().toString();
        adapter.addItem(new IconTextItem(res.getDrawable(R.drawable.icon01), str));
    }
});




        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IconTextItem curItem = (IconTextItem) adapter.getItem(position);
                String[] curData = curItem.getData();

                Toast.makeText(getApplicationContext(), "Selected : " + curData[2], Toast.LENGTH_LONG).show();
            }
        });
    }
}


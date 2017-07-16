package org.androidtown.listview;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ListView listView1;
    IconTextListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView1 = (ListView)findViewById(R.id.listView1);

        adapter = new IconTextListAdapter(this);

        Resources res = getResources();
        adapter.addItem(new IconTextItem(res.getDrawable(R.drawable.icon05),
                "추억의 테트리스", "3000 다운로드", "900 원"));
        adapter.addItem(new IconTextItem(res.getDrawable(R.drawable.icon06),
                "추억의 테트리스", "2000 다운로드", "100 원"));


        listView1.setAdapter(adapter);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                IconTextItem curItem = (IconTextItem)adapter.getItem(position);
                String[] curData = curItem.getData();
                Toast.makeText(getApplicationContext(), "Select :" + curData[0] , Toast.LENGTH_LONG).show();
            }
        });
    }
}

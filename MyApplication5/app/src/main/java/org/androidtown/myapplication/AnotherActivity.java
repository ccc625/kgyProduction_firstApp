package org.androidtown.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by lee on 2017-06-27.
 */



public class AnotherActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.another);

        Button backBtn = (Button) findViewById(R.id.backBtn);


        backBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent resultIntent = new Intent();
                resultIntent.putExtra("name", "mike");

                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        Toast.makeText(this, "0", Toast.LENGTH_LONG).show();

    }


    @Override
    protected void onDestroy() {
        Toast.makeText(this, "1", Toast.LENGTH_LONG).show();

        super.onDestroy();
    }


    @Override
    protected void onPause() {
        Toast.makeText(this, "2", Toast.LENGTH_LONG).show();

        super.onPause();
    }


    @Override
    protected void onRestart() {
        Toast.makeText(this, "?3", Toast.LENGTH_LONG).show();

        super.onRestart();
    }


    @Override
    protected void onResume() {
        Toast.makeText(this, "4", Toast.LENGTH_LONG).show();

        super.onResume();
    }


    @Override
    protected void onStart() {
        Toast.makeText(this, "5", Toast.LENGTH_LONG).show();

        super.onStart();
    }


    @Override
    protected void onStop() {
        Toast.makeText(this, "?6", Toast.LENGTH_LONG).show();

        super.onStop();
    }

}

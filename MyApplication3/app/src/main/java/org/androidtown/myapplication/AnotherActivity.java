package org.androidtown.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by lee on 2017-06-25.
 */

public class AnotherActivity extends Activity{

    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.another);


        Button backButton = (Button)findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                Intent resultIntent =  new Intent();
                resultIntent.putExt  ra("name","mike");

                setResult(RESULT_OK , resultIntent);
                finish();
            }
        });
    }
}

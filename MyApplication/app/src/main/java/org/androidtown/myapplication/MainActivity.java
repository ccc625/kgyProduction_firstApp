package org.androidtown.myapplication;

import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;

public class MainActivity extends AppCompatActivity {

    ScrollView scrollView01;
    ImageView imageView01;
    BitmapDrawable bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scrollView01 = (ScrollView)findViewById(R.id.scrollView01);
        imageView01 = (ImageView)findViewById(R.id.imageView01);
        Button button01 = (Button)findViewById(R.id.button01);


       scrollView01.setHorizontalScrollBarEnabled(true);


        Resources res = getResources();

        BitmapDrawable bitmap = (BitmapDrawable)res.getDrawable(R.drawable.icon01);
        int bitmapWidth = bitmap.getIntrinsicWidth();
        int bitmapHight = bitmap.getIntrinsicHeight();

        imageView01.setImageDrawable(bitmap);
        imageView01.getLayoutParams().width=bitmapWidth;
        imageView01.getLayoutParams().height=bitmapHight;


        button01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Resources res = getResources();
                BitmapDrawable bitmap = (BitmapDrawable)res.getDrawable(R.drawable.icon02);
                int bitmapWidth = bitmap.getIntrinsicWidth();
                int bitmapHeight = bitmap.getIntrinsicHeight();


                imageView01.setImageDrawable(bitmap);
                imageView01.getLayoutParams().width=bitmapWidth;
                imageView01.getLayoutParams().height=bitmapHeight;
            }
        });


    }
}

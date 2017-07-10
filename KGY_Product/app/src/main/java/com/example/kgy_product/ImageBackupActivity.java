package com.example.kgy_product;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by lee on 2017-06-17.
 */

public class ImageBackupActivity extends Activity {

    ImageView imView;
    String imgUrl = "";
    Bitmap bmImg;
    back task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity6_main);
        task = new back();


        imView = (ImageView) findViewById(R.id.WomemGroup);

        task.execute(imgUrl + "img");

    }


    private class back extends AsyncTask<String, Integer, Bitmap> {


        protected Bitmap doInBackground(String... urls) {

            try {
                URL myFileUrl = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
                conn.connect();

                InputStream is = conn.getInputStream();

                bmImg = BitmapFactory.decodeStream(is);

            } catch (IOException e) {
                e.printStackTrace();

            }
            return bmImg;
        }

        protected void onPostExecute(Bitmap img) {
            imView.setImageBitmap(bmImg);
        }


    }
}

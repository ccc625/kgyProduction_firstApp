package com.example.kgy_product.networkTask;

import android.content.ContentValues;
import android.os.AsyncTask;

/**
 * Created by ccc62 on 2017-07-02.
 */

public class NetworkTask extends AsyncTask<Void, Void, String>
{
    private String url;
    private ContentValues values;

    public NetworkTask(String url, ContentValues values)
    {
        this.url = url;
        this.values = values;
    }

    @Override
    protected String doInBackground(Void... params)
    {
        String result;
        RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
        result = requestHttpURLConnection.request( url, values );

        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        System.out.println(s);
    }
}

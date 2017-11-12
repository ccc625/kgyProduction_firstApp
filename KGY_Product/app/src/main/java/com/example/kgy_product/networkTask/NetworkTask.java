package com.example.kgy_product.networkTask;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.util.JsonReader;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.HashMap;

/**
 * Created by ccc62 on 2017-07-02.
 */

public class NetworkTask extends AsyncTask<Void, Void, String>
{
    private String url;
    private String values;
    private NetworkCallback callback;

    public NetworkTask(String url, String values, NetworkCallback callback)
    {
        this.url = url;
        this.values = values;
        this.callback = callback;
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

        if( s == null )
        {
            this.callback.onResponse( null );
            return;
        }

        JSONObject data = null;
        try
        {
            data = new JSONObject(s);
        }
        catch( JSONException exeption )
        {
            exeption.printStackTrace();
        }


        this.callback.onResponse( data );
    }

    public interface NetworkCallback
    {
        void onResponse( JSONObject data );
    }
}

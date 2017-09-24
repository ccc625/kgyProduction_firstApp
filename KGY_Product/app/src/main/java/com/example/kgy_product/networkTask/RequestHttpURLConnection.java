package com.example.kgy_product.networkTask;

import android.content.ContentValues;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * Created by ccc62 on 2017-07-02.
 */

public class RequestHttpURLConnection
{
    public String request(String inUrl, String params)
    {
        HttpURLConnection urlConn = null;

        try
        {
            String strUrl = inUrl;

            strUrl += "?requestData=" + params;
//            strUrl = inUrl + "?requestData=[tokenToken]";

            strUrl = strUrl.replace("\"", "");

            URL url = new URL( strUrl );

            urlConn = (HttpURLConnection) url.openConnection();

            urlConn.setReadTimeout(10000);
            urlConn.setConnectTimeout(15000);
            urlConn.setRequestMethod("POST");
            urlConn.setDoInput(true);
            OutputStream outputStream = urlConn.getOutputStream();
//            urlConn.

            urlConn.connect();

            if (urlConn.getResponseCode() != HttpURLConnection.HTTP_OK)
                return null;

            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), "UTF-8"));

            String line;
            String page = "";

            while ((line = reader.readLine()) != null)
            {
                page += line;
            }

            return page;
        }
        catch( MalformedURLException e )
        {
            e.printStackTrace();
        }
        catch( IOException e )
        {
            e.printStackTrace();
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }
        finally
        {
            if( urlConn != null )
                urlConn.disconnect();
        }

        return null;
    }
}

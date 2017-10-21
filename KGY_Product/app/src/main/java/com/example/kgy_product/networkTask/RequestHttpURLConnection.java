package com.example.kgy_product.networkTask;

import android.content.ContentValues;
import android.util.JsonReader;
import android.util.JsonWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
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

//            params = params.replace("\"", "\\\"");

            URL url = new URL( strUrl );

            urlConn = (HttpURLConnection) url.openConnection();

            urlConn.setReadTimeout(10000);
            urlConn.setConnectTimeout(15000);
            urlConn.setRequestMethod("POST");
            urlConn.setDoInput(true);
            urlConn.setDoOutput(true);
            OutputStream outputStream = urlConn.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

            bufferedWriter.write(getPostString(params));
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

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

    private String getPostString(String jsonString)
    {
        StringBuilder stringBuilder = new StringBuilder();
        JSONObject jsonObject = null;
        try
        {
            jsonObject = new JSONObject(jsonString);
            Iterator<String> keys = jsonObject.keys();

            String key;
            boolean first = true;
            while( keys.hasNext() )
            {
                key = keys.next();

                if( first )
                {
                    first = false;
                }
                else
                {
                    stringBuilder.append("&");
                }

                try
                { // UTF-8로 주소에 키와 값을 붙임
                    stringBuilder.append(URLEncoder.encode(key, "UTF-8"));
                    stringBuilder.append("=");
                    stringBuilder.append(URLEncoder.encode(jsonObject.getString(key), "UTF-8"));
                }
                catch (UnsupportedEncodingException ue)
                {
                    ue.printStackTrace();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        catch( JSONException e )
        {
            e.printStackTrace();
        }

//        JSONArray jsonArray = new JSONArray( jsonString )

        return stringBuilder.toString();
    }

    private String getPostString(HashMap<String, String> map)
    {
        StringBuilder result = new StringBuilder();
        boolean first = true; // 첫 번째 매개변수 여부

        for (Map.Entry<String, String> entry : map.entrySet())
        {
            if (first)
                first = false;
            else // 첫 번째 매개변수가 아닌 경우엔 앞에 &를 붙임
                result.append("&");

            try
            { // UTF-8로 주소에 키와 값을 붙임
                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }
            catch (UnsupportedEncodingException ue)
            {
                ue.printStackTrace();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        return result.toString();
    }
}

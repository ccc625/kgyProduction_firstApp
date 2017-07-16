package com.example.kgy_product.networkTask;

import android.content.ContentValues;

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
    public String request(String inUrl, ContentValues params)
    {
        HttpURLConnection urlConn = null;

        StringBuffer sbParams = new StringBuffer();

        if( params == null )
        {
            sbParams.append("");
        }
        else
        {
            boolean isAnd = false;

            String key;
            String value;

            for( Map.Entry<String, Object> parameter : params.valueSet() )
            {
                key = parameter.getKey();
                value = parameter.getValue().toString();

                if( isAnd )
                    sbParams.append("&");

                sbParams.append(key).append("=").append(value);

                if( !isAnd )
                {
                    if( params.size() >= 2 )
                        isAnd = true;
                }
            }
        }

        try
        {
            URL url = new URL( inUrl );

            urlConn = (HttpURLConnection) url.openConnection();

            urlConn.setRequestMethod("POST");
            urlConn.setRequestProperty("Accept-Charset", "UTF-8");
            urlConn.setRequestProperty("Context_Type", "application/x-www-form-urlencoded;cahrset=UTF-8");

            String strParams = sbParams.toString();
            OutputStream os = urlConn.getOutputStream();
            os.write( strParams.getBytes("UTF-8"));
            os.flush();
            os.close();

            if (urlConn.getResponseCode() != HttpURLConnection.HTTP_OK)
                return null;

            // [2-4]. 읽어온 결과물 리턴.
            // 요청한 URL의 출력물을 BufferedReader로 받는다.
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), "UTF-8"));

            // 출력물의 라인과 그 합에 대한 변수.
            String line;
            String page = "";

            // 라인을 받아와 합친다.
            while ((line = reader.readLine()) != null){
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
        finally
        {
            if( urlConn != null )
                urlConn.disconnect();
        }

        return null;
    }
}

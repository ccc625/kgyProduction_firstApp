package com.example.kgy_product.networkTask;

import android.content.ContentValues;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ccc62 on 2017-08-26.
 */

public class AmfAdaptor
{
    private static AmfAdaptor _manager;

    public static AmfAdaptor instance()
    {
        if( _manager == null )
        {
            _manager = new AmfAdaptor( new Singleton() );
        }

        return _manager;
    }

    private HashMap<String, String[]> _serverMethods;

    private Thread _taksThread;

    public AmfAdaptor(Singleton singleton)
    {
        init();
    }

    private void init()
    {
        _serverMethods = new HashMap<String, String[]>();

        ServerConfig.init();
        setService();
    }

    private void setService()
    {
        HashMap<String, String[]> serviceList = ServerConfig.ServiceList;

        for( Map.Entry<String, String[]> entry : serviceList.entrySet() )
        {
            String serviceName = entry.getKey();
            String[] serverMethods = entry.getValue();

            _serverMethods.put( serviceName, serverMethods );
        }
    }

    private String getServiceName( String method )
    {
        String serviceName = null;

        for( Map.Entry<String, String[]> entry : _serverMethods.entrySet() )
        {
            String service = entry.getKey();
            String[] methods = entry.getValue();

            for( int i = 0; i < methods.length; i++ )
            {
                if( method.equals( methods[i] ) )
                {
                    serviceName = service;
                    break;
                }
            }
        }

        return serviceName;
    }

    public void initUser(final AmfCallback callback, String token )
    {
        ServerCallback serverCallback  = new ServerCallback() {
            @Override
            public void onResponse(JSONObject data)
            {
                callback.onResponse( data );
            }
        };

        requestMethod(ServerMethod.init, serverCallback, token);
    }

    private void requestMethod( String serverMethod, final ServerCallback callback, String... params)
    {
        String dest = "";
        String serviceName = getServiceName( serverMethod );

        dest = ServerConfig.DEST + "/" + serviceName + "/" + serverMethod + ".do";

        JSONArray jsonParams = new JSONArray();
        for( int i= 0; i < params.length; i++ )
        {
            try
            {
                jsonParams.put(i, params[i]);
            }
            catch( JSONException exeption )
            {
                exeption.printStackTrace();
            }
        }

        String strParam = jsonParams.toString();

        NetworkTask.NetworkCallback networkCallback = new NetworkTask.NetworkCallback() {
            @Override
            public void onResponse(JSONObject data)
            {
                try
                {
                    if( data != null && data.has("code") && data.get("code").toString().equals(ServerCode.OK) )
                        callback.onResponse( data );
                }
                catch( JSONException exeption )
                {
                    exeption.printStackTrace();
                }
            }
        };

        NetworkTask networkTask = new NetworkTask(dest, strParam, networkCallback);
        networkTask.execute();
    }

    public interface AmfCallback
    {
        void onResponse(JSONObject data);
    }

    private interface ServerCallback
    {
        void onResponse(JSONObject data);
    }
}
class Singleton{};

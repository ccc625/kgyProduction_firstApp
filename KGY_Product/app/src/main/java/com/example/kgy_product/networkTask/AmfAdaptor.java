package com.example.kgy_product.networkTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
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

        JSONObject jsonObject = new JSONObject();
        try
        {
            jsonObject.put("token", token);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }


//        HashMap<String, String> map = new HashMap<String, String>();
//        map.put("token", token);

        requestMethod(ServerMethod.initUser, serverCallback, jsonObject.toString());
    }

    public void registerUser(final AmfCallback callback, String name, String age, String empId)
    {
        ServerCallback serverCallback = new ServerCallback() {
            @Override
            public void onResponse(JSONObject data)
            {
                callback.onResponse( data );
            }
        };

        JSONObject jsonObject = new JSONObject();
        try
        {
            jsonObject.put("name", name);
            jsonObject.put("age", age);
            jsonObject.put("emp_id", empId);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

//        HashMap<String, String> map = new HashMap<String, String>();
//
//        map.put("name", name);
//        map.put("age", age);
//        map.put("emp_id", empId);

        requestMethod(ServerMethod.registerUser, serverCallback, jsonObject.toString());
    }

    public void openSampleList4(final AmfCallback callback, String name, String age, String empId)
    {
        ServerCallback serverCallback = new ServerCallback() {
            @Override
            public void onResponse(JSONObject data)
            {
                callback.onResponse( data );
            }
        };

        JSONObject jsonObject = new JSONObject();
        try
        {
            jsonObject.put("name", name);
            jsonObject.put("age", age);
            jsonObject.put("emp_id", empId);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        requestMethod(ServerMethod.openSampleList4, serverCallback, jsonObject.toString());
    }

    private void requestMethod( String serverMethod, final ServerCallback callback, String data)
    {
        String dest = "";
        String serviceName = getServiceName( serverMethod );

        dest = ServerConfig.LOCAL + "/" + serviceName + "/" + serverMethod + ".do";

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

        NetworkTask networkTask = new NetworkTask(dest, data, networkCallback);
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

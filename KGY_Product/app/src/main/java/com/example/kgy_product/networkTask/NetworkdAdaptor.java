package com.example.kgy_product.networkTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ccc62 on 2017-08-26.
 */

public class NetworkdAdaptor
{
    private static NetworkdAdaptor _manager;

    public static NetworkdAdaptor instance()
    {
        if( _manager == null )
        {
            _manager = new NetworkdAdaptor( new Singleton() );
        }

        return _manager;
    }

    private HashMap<String, String[]> _serverMethods;

    private Thread _taksThread;

    public NetworkdAdaptor(Singleton singleton)
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

    public void initUser(final NetworkCallback callback, String token )
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

        requestMethod(ServerMethod.initUser, serverCallback, jsonObject.toString());
    }

    public void registerUser(final NetworkCallback callback, String name, String age, String empId)
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

        requestMethod(ServerMethod.registerUser, serverCallback, jsonObject.toString());
    }

    public void openSampleList4(final NetworkCallback callback, String name, String age, String empId)
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

    public void getCommonList(final NetworkCallback callback, String upperKey)
    {
        ServerCallback serverCallback = new ServerCallback()
        {
            @Override
            public void onResponse(JSONObject data)
            {
                callback.onResponse(data);
            }
        };

        JSONObject jsonObject = new JSONObject();
        try
        {
            jsonObject.put("upperKey", upperKey);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        requestMethod(ServerMethod.getCommonList, serverCallback, jsonObject.toString());
    }

    public void setMakeRegister(final NetworkCallback callback , HashMap map)
    {
        ServerCallback serverCallback = new ServerCallback() {
            @Override
            public void onResponse(JSONObject data) {
                callback.onResponse(data);
            }
        };

        JSONObject jsonObject = new JSONObject();

        try
        {
            jsonObject.put("team_nm", map.get("team_nm"));
            jsonObject.put("team_gender", map.get("team_gender"));
            jsonObject.put("area", map.get("area"));
            jsonObject.put("alcohol", map.get("alcohol"));
            jsonObject.put("al_num", map.get("al_num"));
            jsonObject.put("team_comment", map.get("team_comment"));
            jsonObject.put("team_you_comment", map.get("team_you_comment"));
            jsonObject.put("team_img_cd", map.get("team_img_cd"));
            jsonObject.put("team_reg_time", map.get("team_reg_time"));
            jsonObject.put("team_phone", map.get("team_phone"));
            jsonObject.put("img_file", map.get("img_file"));
            jsonObject.put("team_number", map.get("team_number"));
        }
        catch(JSONException e)
        {

            e.printStackTrace();
        }
        requestMethod(ServerMethod.setMakeRegister,serverCallback , jsonObject.toString());
    }

    public void getTeamList(final NetworkCallback callback , HashMap map){
        ServerCallback serverCallback = new ServerCallback() {
            @Override
            public void onResponse(JSONObject data) {
                callback.onResponse(data);
            }
        };

        JSONObject jsonObject = new JSONObject();

        try
        {
            jsonObject.put("data", map);
        }
        catch(JSONException e)
        {

            e.printStackTrace();
        }
        requestMethod(ServerMethod.getTeamList,serverCallback , jsonObject.toString());
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
                    if( data != null && data.has("success") && (Boolean)data.get("success") )
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

    public interface NetworkCallback
    {
        void onResponse(JSONObject data);
    }

    private interface ServerCallback
    {
        void onResponse(JSONObject data);
    }
}
class Singleton{};

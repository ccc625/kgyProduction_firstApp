package com.example.kgy_product.networkTask;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;

/**
 * Created by ccc62 on 2017-08-26.
 */

public class ServerConfig
{
    public static final String DEST = "http://52.78.124.40:8080/kgy/sample";

    public static HashMap<String, String[]> ServiceList;

    public static String[] UserService =
    {
            "initUser"
    };

    public static void init()
    {
        ServiceList = new HashMap<>();

        ServiceList.put("UserService", ServerConfig.UserService);
    }


}

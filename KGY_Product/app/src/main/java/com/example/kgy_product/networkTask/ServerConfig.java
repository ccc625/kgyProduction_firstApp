package com.example.kgy_product.networkTask;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;

/**
 * Created by ccc62 on 2017-08-26.
 */

public class ServerConfig
{
    public static final String DEST = "http://52.78.124.40:8080/kgy";
    public static final String LOCAL = "http://172.30.1.3:8080/kgy";

    public static HashMap<String, String[]> ServiceList;

    public static String[] UserService =
    {
            "initUser",
            "registerUser"
    };

    public static String[] sample =
    {
            "openSampleList4"
    };

    public static void init()
    {
        ServiceList = new HashMap<>();

        ServiceList.put("UserService", ServerConfig.UserService);
        ServiceList.put("sample", ServerConfig.sample);
    }


}

package com.example.kgy_product.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ccc62 on 2018-01-13.
 */

public class TimeUtil
{
    //시간 차이
    public static String termTime(String regTime){
        long minute = 0;
        try {
            Date dt = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMddHHmm");

            dt = sdf.parse(sdf.format(dt));
            long curDateTime = dt.getTime();

            Date regDt = sdf.parse(regTime);
            long regDateTime = regDt.getTime();

            minute = (curDateTime - regDateTime) / 60000;

        } catch (Exception e){

        }

        return String.valueOf(minute);
    }

    public static String nowDate()
    {
        long now = System.currentTimeMillis();
        Date date = new Date(now);

        SimpleDateFormat sdf =  new SimpleDateFormat("yyyy/MM/dd/HH/mm");
        String nowDate = sdf.format(date);

        String result = "";

        if(Integer.parseInt(nowDate.split("/")[3]) <= 6){
            for(int i = 0; i < nowDate.split("/").length; i++){
                if(i == 1){
                    int mm = Integer.parseInt(nowDate.split("/")[i]) - 1;
                    result += String.valueOf(mm);
                } else if(i > 2){
                    break;
                }
                else {
                    result += nowDate.split("/")[i];
                }
            }
        } else {
            for(int i = 0; i < nowDate.split("/").length; i++){
                if(i > 2){
                    break;
                } else {
                    result += nowDate.split("/")[i];
                }
            }
        }

        return result;
    }
}

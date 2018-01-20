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
}

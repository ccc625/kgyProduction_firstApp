package org.androidtown.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by lee on 2017-06-27.
 */

public class MyBroadcastReceiver extends BroadcastReceiver {

    public void onReceive(Context context , Intent intent){

        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            Log.d("MySMSBroadcastReceiver", "SMS 메시지가 수신되었습니다");

            abortBroadcast();

            Intent myIntent = new Intent(context, MainActivity.class);
            myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(myIntent);
        }
    }=]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]


}

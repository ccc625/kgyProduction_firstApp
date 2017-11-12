package com.example.kgy_product;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.kgy_product.networkTask.NetworkdAdaptor;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity
{
    private Button btnSelectPlace0;
    private Button btnSelectPlace1;
    private Button btnSelectPlace2;

    private View.OnClickListener buttonClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        NetworkdAdaptor.NetworkCallback callback = new NetworkdAdaptor.NetworkCallback() {
//            @Override
//            public void onResponse(JSONObject data)
//            {
//                System.out.println(data);
//            }
//        };
//
//        NetworkdAdaptor.instance().getCommonList(callback, "ALCOHOL");

        init();
    }

    private void init()
    {
        initDisplayObject();
        initListener();

        //TODO 테스트용 삭제 예정
        saveLogin();

        if(isLoginCheck()){
            //TODO 디비 접속 로그인
        } else {
            initDisplayObject();
            initListener();

            NetworkdAdaptor.NetworkCallback callback = new NetworkdAdaptor.NetworkCallback() {
                @Override
                public void onResponse(JSONObject data)
                {
                    System.out.println( data );
                }
            };

//        NetworkdAdaptor.instance().initUser( callback, "tokenToKen");
//        NetworkdAdaptor.instance().openSampleList4( callback, "yjm", "20", "1");
        }
    }

    private void initDisplayObject()
    {
        btnSelectPlace0 = (Button) findViewById(R.id.btnSelectPlace0);
        btnSelectPlace1 = (Button) findViewById(R.id.btnSelectPlace1);
        btnSelectPlace2 = (Button) findViewById(R.id.btnSelectPlace2);
    }

    private void initListener()
    {
        buttonClickListener = new View.OnClickListener() {
            @Override
            public void onClick( View view )
            {
                ///TODO 각 버튼별로 동작 지정
                if( view.getId() == btnSelectPlace0.getId() )
                {
                    startMakeTeamActivity("Seoul");
                }
                else if( view.getId() == btnSelectPlace1.getId() )
                {
                    startMakeTeamActivity("Gangbuk");
                }
                else if( view.getId() == btnSelectPlace2.getId() )
                {
                    startMakeTeamActivity("Gangnam");
                }
            }
        };

        btnSelectPlace0.setOnClickListener( buttonClickListener );
        btnSelectPlace1.setOnClickListener( buttonClickListener );
        btnSelectPlace2.setOnClickListener( buttonClickListener );
    }

    private void startMakeTeamActivity(String location)
    {
        Intent intent = new Intent(getApplicationContext(),TeamMakeActivity.class);
        intent.putExtra("location",location);
        startActivity(intent);
    }

    private boolean isLoginCheck(){
        SharedPreferences setting = getSharedPreferences("setting",MODE_PRIVATE);
        String loginDate = setting.getString("date","");

        String now = nowDate();

        if(!(loginDate != null && !"".equals(loginDate)) ){
            return false;
        }

        if(Integer.parseInt(loginDate) == Integer.parseInt(now)){
            return true;
        } else {
            SharedPreferences.Editor editor = setting.edit();
            editor.remove("date");
            editor.remove("id");
            editor.commit();
            return false;
        }
    }

    private String nowDate(){
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

    //TODO 테스트용 삭제 예정
    private void saveLogin(){
        SharedPreferences setting = getSharedPreferences("setting",MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();

        Date date = new Date();

        SimpleDateFormat sdf =  new SimpleDateFormat("yyyyMMdd");
        String nowDate = sdf.format(date);

        editor.putString("date",nowDate);
        editor.putString("id","sss");
        editor.commit();
    }
}

package com.example.kgy_product;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.kgy_product.networkTask.NetworkdAdaptor;
import com.example.kgy_product.scheduler.ScheduleNode;
import com.example.kgy_product.scheduler.Scheduler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity
{

    ListView DataAccept;

    AreaAdapter adapter;




    private static final int PERMISSIONS_REQUEST_CODE = 1001;

    private Button btnSelectPlace0;

    private Scheduler scheduler;

    private View.OnClickListener buttonClickListener;

    //ArrayList<Areadata> areaList = new ArrayList<Areadata>();

   // RelativeLayout relative = (RelativeLayout)findViewById(R.id.relative);
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

//        btnSelectPlace0.setOnClickListener( buttonClickListener );



    }



    private void init()
    {
        Scheduler.OnCompleteSchedulerListener onCompleteSchedulerListener = new Scheduler.OnCompleteSchedulerListener()
        {
            public void onComplete()
            {
                System.out.println("[MainActivity] onCompleteScheduler");
            }
        };

        scheduler = new Scheduler(onCompleteSchedulerListener);
        ScheduleNode node;

        ScheduleNode.ScheduleAction checkPermissionsAction = new ScheduleNode.ScheduleAction() {
            @Override
            public void excute(Callback callback)
            {
                ///NOTE @jimin 퍼미션체크의 경우 흐름을 따라갈 수 없어서 별도로 Scheduler에 key값으로 complete를 쏴줘야함
                checkPermissions();
            }
        };

        node = new ScheduleNode("checkPermissionsAction", checkPermissionsAction);
        scheduler.add(node);

        ScheduleNode.ScheduleAction getLocationAction = new ScheduleNode.ScheduleAction()
        {
            @Override
        public void excute(final Callback callback) {

        NetworkdAdaptor.NetworkCallback networkCallback = new NetworkdAdaptor.NetworkCallback() {
            @Override
            public void onResponse(final JSONObject data) {
                try {
                    System.out.println(data.toString());

                    JSONArray arr = new JSONArray();
                    arr = data.getJSONArray("result");

                    final ArrayList<Areadata> areaList = new ArrayList<>(); //서버 정보를 담을 배열


                    for (int i = 0; i <arr.length(); i++){
                        Areadata areaData = new Areadata();
                        JSONObject obj = arr.getJSONObject(i);

                        areaData.setCode(obj.getString("common_cd"));
                        areaData.setName(obj.getString("common_nm"));

                        areaList.add(areaData);

                        System.out.println(areaList.get(i).getName());
                    }

                    DataAccept = (ListView)findViewById(R.id.dataAccept);
                    adapter = new AreaAdapter(MainActivity.this,R.layout.customview,areaList);
                    DataAccept.setAdapter(adapter);

                   /* DataAccept.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {


                            Intent intent = new Intent(getApplicationContext(),TeamMakeActivity.class);
                           // intent.putExtra("common_cd",);
                            startActivity(intent);
                        }
                    });*/

                    callback.excute();
                } catch (Exception e){

                }


            }
        };

        NetworkdAdaptor.instance().getCommonList(networkCallback, "LOCATION");
    }
    };

        node = new ScheduleNode("getLocationAction", getLocationAction);
        scheduler.add(node);

        ScheduleNode.ScheduleAction initDisplayAction = new ScheduleNode.ScheduleAction() {
            @Override
            public void excute(Callback callback)
            {
                initDisplayObject();
                callback.excute();
            }
        };

        node = new ScheduleNode("initDisplayAction", initDisplayAction);
        scheduler.add(node);

        ScheduleNode.ScheduleAction initListenerAction = new ScheduleNode.ScheduleAction() {
            @Override
            public void excute(Callback callback)
            {
                initListener();
                callback.excute();
            }
        };

        node = new ScheduleNode("initListenerAction", initListenerAction);
        scheduler.add(node);

        scheduler.start();

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
        }
    }

    private void initDisplayObject()
    {
        btnSelectPlace0 = (Button) findViewById(R.id.btnSelectPlace0);

    }

    private void initListener()
    {
        buttonClickListener = new View.OnClickListener() {
            @Override
            public void onClick( View view )
            {
                switch (view.getId()){
                    case R.id.btnSelectPlace0:
                        try{



                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                }
            }


        };



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

    private void checkPermissions()
    {
        int cameraPermissionCheckResult = ContextCompat.checkSelfPermission( this, Manifest.permission.CAMERA );
        int phoneStatePermissionCheckResult = ContextCompat.checkSelfPermission( this, Manifest.permission.READ_PHONE_STATE );

        if (cameraPermissionCheckResult != PackageManager.PERMISSION_GRANTED || phoneStatePermissionCheckResult != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions( this, new String[]{ Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE }, PERMISSIONS_REQUEST_CODE );
        }
        else
        {
            scheduler.completeSchedule("checkPermissionsAction");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK)
            return;

        switch (requestCode)
        {
            case PERMISSIONS_REQUEST_CODE :
                scheduler.completeSchedule("checkPermissionsAction");
                break;

            default :
                break;
        }
    }


}

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

import com.example.kgy_product.areaSelect.AreaAdapter;
import com.example.kgy_product.areaSelect.Areadata;
import com.example.kgy_product.networkTask.NetworkAdaptor;
import com.example.kgy_product.scheduler.ScheduleNode;
import com.example.kgy_product.scheduler.Scheduler;
import com.example.kgy_product.user.User;
import com.example.kgy_product.util.TimeUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity
{
    private static final int PERMISSIONS_REQUEST_CODE = 1001;

    private Button btnSelectPlace0;

    private Scheduler scheduler;

    private View.OnClickListener buttonClickListener;

    private ListView DataAccept;

    private AreaAdapter adapter;

    //ArrayList<Areadata> areaList = new ArrayList<Areadata>();

   // RelativeLayout relative = (RelativeLayout)findViewById(R.id.relative);
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init()
    {
        Scheduler.OnCompleteSchedulerListener onCompleteSchedulerListener = new Scheduler.OnCompleteSchedulerListener()
        {
            public void onComplete()
            {
                System.out.println("[MainActivity] onCompleteScheduler");

                if(isLoginCheck())
                {
                    if(User.instance().getId() != null && !"".equals(User.instance().getId()))
                        startTeamSearchActivity();
                }
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
            public void excute(final Callback callback)
            {
                NetworkAdaptor.NetworkCallback networkCallback = new NetworkAdaptor.NetworkCallback()
                {
                    @Override
                    public void onResponse(final JSONObject data)
                    {
                        try
                        {
                            System.out.println(data.toString());

                            JSONArray arr = data.getJSONArray("result");

                            final ArrayList<Areadata> areaList = new ArrayList<Areadata>(); //서버 정보를 담을 배열


                            for (int i = 0; i <arr.length(); i++)
                            {
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

                            callback.excute();
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                };

                NetworkAdaptor.instance().getCommonList(networkCallback, "LOCATION");
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
    }

    private void initDisplayObject()
    {

    }

    private void initListener()
    {

    }

    public void startTeamSearchActivity()
    {
        Intent intent = new Intent(getApplicationContext(),TeamSearchActivity.class);
        startActivity(intent);
    }

    private boolean isLoginCheck()
    {
        SharedPreferences setting = getSharedPreferences("setting",  MODE_PRIVATE);
        String loginDate = setting.getString("date","");

        String id = setting.getString("id", "");
        String name = setting.getString("name", "");

        String now = TimeUtil.nowDate();

        if(!(loginDate != null && !"".equals(loginDate)) )
            return false;

        if( !id.equals("") && !name.equals("") && Integer.parseInt(loginDate) == Integer.parseInt(now) )
        {
            User.instance().setUser(id, name, getApplicationContext());
            return true;
        }
        else
        {
            SharedPreferences.Editor editor = setting.edit();
            editor.remove("date");
            editor.remove("id");
            editor.remove("name");
            editor.commit();
            return false;
        }
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

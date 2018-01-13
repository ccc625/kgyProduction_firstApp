package com.example.kgy_product;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.kgy_product.networkTask.NetworkdAdaptor;
import com.example.kgy_product.scheduler.ScheduleNode;
import com.example.kgy_product.scheduler.Scheduler;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by deokhwan on 2018-01-06.
 */

public class BoardActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board);

        init();
    }

    @Override
    protected void onDestroy() {
        dispose();
        super.onDestroy();
    }

    private void dispose()
    {

    }

    //TODO intent에서 값 id로 넘어온 데이터 디비랑 연결해서 뿌리기 스케쥴이 이해가 안되서 못함 지민아 하렴
    private void init(){
        Scheduler.OnCompleteSchedulerListener onCompleteScheduler = new Scheduler.OnCompleteSchedulerListener(){
            public void onComplete()
            {
                System.out.println("onCompleteScheduler");
            }
        };

        Scheduler scheduler = new Scheduler(onCompleteScheduler);
        ScheduleNode node;

        ScheduleNode.ScheduleAction infoTeamInfoAction = new ScheduleNode.ScheduleAction() {
            @Override
            public void excute(Callback callback) {
                NetworkdAdaptor.NetworkCallback networkCallback = new NetworkdAdaptor.NetworkCallback() {
                    @Override
                    public void onResponse(JSONObject data) {
                        try {
                            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                            System.out.println(data.toString());
                        } catch (Exception e){

                        }
                    }
                };

                Intent intent = getIntent();
                String id = intent.getStringExtra("id");
                //팀상세정보
                NetworkdAdaptor.instance().getTeamInfo(networkCallback,id);
            }
        };
    }
}

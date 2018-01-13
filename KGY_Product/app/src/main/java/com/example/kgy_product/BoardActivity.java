package com.example.kgy_product;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kgy_product.networkTask.NetworkdAdaptor;
import com.example.kgy_product.scheduler.ScheduleNode;
import com.example.kgy_product.scheduler.Scheduler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by deokhwan on 2018-01-06.
 */

public class BoardActivity extends AppCompatActivity {

    ImageView boardImgView;
    TextView teamNameView;
    TextView teamContext;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board);

        boardImgView = (ImageView)findViewById(R.id.WomemGroup);
        teamNameView = (TextView)findViewById(R.id.TeamName);
        teamContext = (TextView)findViewById(R.id.teamContext);

        init();
    }

    @Override
    protected void onDestroy() {
        dispose();

        boardImgView = null;
        teamNameView = null;
        teamContext = null;

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
                            JSONArray list = new JSONArray();
                            list = data.getJSONArray("result");
                            JSONObject obj = new JSONObject();
                            obj = list.getJSONObject(0);
                            boardImgView.setImageBitmap(BitmapUtil.getBitmapToString(obj.getString("imgFile")));
                            teamNameView.setText(obj.getString("teamNm"));
                            String context = obj.getString("teamComment").toString()+"이 "+obj.getString("teamNumber").toString()+"명이서"
                                            +termTime(obj.getString("regDate").toString())+"분 전부터 기다리는중..."
                                            +"\n우리는 "+obj.getString("teamYouComment").toString()+"을 원해요~!";

                            teamContext.setText(context);
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

        node = new ScheduleNode("infoTeamInfoAction",infoTeamInfoAction);
        scheduler.add(node);

        scheduler.start();
    }

    //시간 차이
    private String termTime(String regTime){
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

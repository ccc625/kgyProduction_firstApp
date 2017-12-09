package com.example.kgy_product;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.kgy_product.networkTask.NetworkdAdaptor;
import com.example.kgy_product.scheduler.ScheduleNode;
import com.example.kgy_product.scheduler.Scheduler;
import com.example.kgy_product.teamSearch.TeamSearchListAdapter;
import com.example.kgy_product.teamSearch.TeamSearchListItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TeamSearchActivity extends AppCompatActivity
{
    private ImageButton btnSearch;
    private EditText txtSearch;
    private Button btnRefresh;

    private GridView teamSearchList;

    private TeamSearchListAdapter teamSearchListAdapter;
    private ArrayList<TeamSearchListItem> listData;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_search);

        init();
    }

    private void init()
    {
        Scheduler.OnCompleteSchedulerListener onCompleteSchedulerListener = new Scheduler.OnCompleteSchedulerListener()
        {
            public void onComplete()
            {
                System.out.println("onCompleteScheduler");
            }
        };

        Scheduler scheduler = new Scheduler(onCompleteSchedulerListener);
        ScheduleNode node;

        ScheduleNode.ScheduleAction loadTeamListAction = new ScheduleNode.ScheduleAction() {
            @Override
            public void excute(final Callback callback)
            {
                NetworkdAdaptor.NetworkCallback networkCallback = new NetworkdAdaptor.NetworkCallback() {
                    @Override
                    public void onResponse(JSONObject data)
                    {
                        System.out.println(data);

                        ///TODO 로드된 팀 정보 저장
                    }
                };

                ///TODO 팀정보 로드 콜 완성시에 callback위치 옮겨야함
                callback.excute();

                ///TODO 팀정보 로드
//                NetworkdAdaptor.instance().getCommonList(networkCallback, "ALCOHOL");
            }
        };

        node = new ScheduleNode("loadTeamListAction", loadTeamListAction);
        scheduler.add(node);

        ScheduleNode.ScheduleAction initDisplayObjectAction = new ScheduleNode.ScheduleAction() {
            @Override
            public void excute(final Callback callback)
            {
                initDisplayObject();
                callback.excute();
            }
        };

        node = new ScheduleNode("initDisplayObjectAction", initDisplayObjectAction);
        scheduler.add(node);

        ScheduleNode.ScheduleAction initListenerAction = new ScheduleNode.ScheduleAction() {
            @Override
            public void excute(final Callback callback)
            {
                initListener();
                callback.excute();
            }
        };

        node = new ScheduleNode("initListenerAction", initListenerAction);
        scheduler.add(node);

        ScheduleNode.ScheduleAction setListAction = new ScheduleNode.ScheduleAction() {
            @Override
            public void excute(final Callback callback)
            {
                setList();
                callback.excute();
            }
        };

        node = new ScheduleNode("setListAction", setListAction);
        scheduler.add(node);

        scheduler.start();
    }

    private void initDisplayObject()
    {
        btnSearch = (ImageButton) findViewById( R.id.btnSearch );
        txtSearch = (EditText) findViewById( R.id.txtSearch );
        btnRefresh = (Button) findViewById( R.id.btnRefresh );

        teamSearchList = (GridView) findViewById( R.id.teamSearchList );
    }

    private void initListener()
    {

    }

    private void setList()
    {
        teamSearchListAdapter = new TeamSearchListAdapter( this, R.layout.team_search_view_layout, null );

        setListData();

        teamSearchList.setAdapter( teamSearchListAdapter );
    }

    private void setListData()
    {
        if( listData == null )
        {
            listData = new ArrayList<TeamSearchListItem>();
        }
        ///* 테스트 데이터
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.man);

        TeamSearchListItem teamSearchListItem = new TeamSearchListItem();
        teamSearchListItem.setIcon( bitmap );
        teamSearchListItem.setName( "test" );

        listData.add( teamSearchListItem );

        bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.woman);

        teamSearchListItem = new TeamSearchListItem();
        teamSearchListItem.setIcon( bitmap );
        teamSearchListItem.setName( "test2" );

        listData.add( teamSearchListItem );

        //*/

        teamSearchListAdapter.setData( (ArrayList<TeamSearchListItem>) listData.clone() );
    }
}

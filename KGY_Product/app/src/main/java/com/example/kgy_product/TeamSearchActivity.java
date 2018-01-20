package com.example.kgy_product;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;

import com.example.kgy_product.networkTask.NetworkAdaptor;
import com.example.kgy_product.scheduler.ScheduleNode;
import com.example.kgy_product.scheduler.Scheduler;
import com.example.kgy_product.teamSearch.TeamData;
import com.example.kgy_product.teamSearch.TeamSearchListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class TeamSearchActivity extends AppCompatActivity
{
    private ImageButton btnSearch;
    private EditText txtSearch;
    private Button btnRefresh;

    private GridView teamSearchList;

    private TeamSearchListAdapter teamSearchListAdapter;
    private ArrayList<TeamData> teamDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_search);

        init();
    }

    @Override
    protected void onDestroy()
    {
        dispose();

        super.onDestroy();
    }

    private void dispose()
    {
        teamSearchListAdapter.clear();
        teamSearchListAdapter = null;
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
                NetworkAdaptor.NetworkCallback networkCallback = new NetworkAdaptor.NetworkCallback() {
                    @Override
                    public void onResponse(JSONObject data)
                    {
                        System.out.println(data.toString());

                        if( teamDatas == null )
                            teamDatas = new ArrayList<>();

                        teamDatas.clear();

                        try
                        {
                            JSONArray result = data.getJSONArray("result");

                            JSONObject team;
                            for( int i = 0; i < result.length(); i++ )
                            {
                                team = result.getJSONObject(i);

                                teamDatas.add(new TeamData(team.get("teamNo").toString(), team.get("teamNm").toString(), team.get("gender").toString(), team.get("imgFile").toString(), team.get("areaNm").toString()));
                            }
                        }
                        catch (JSONException exception)
                        {
                            exception.printStackTrace();
                        }

                        callback.excute();
                    }
                };

                Intent intent = getIntent();

                HashMap<String, Object> map = new HashMap<>();
                map.put("id", intent.getStringExtra("id"));
                map.put("search", "");

                NetworkAdaptor.instance().getTeamList(networkCallback, map);
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
        //btnRefresh = (Button) findViewById( R.id.btnRefresh );

        teamSearchList = (GridView) findViewById( R.id.teamSearchList );
    }

    private void initListener()
    {

    }

    private void setList()
    {
        teamSearchListAdapter = new TeamSearchListAdapter( this, R.layout.team_search_view_layout, null );

        teamSearchListAdapter.setData( (ArrayList<TeamData>) teamDatas.clone() );

        teamSearchList.setAdapter( teamSearchListAdapter );
    }


}

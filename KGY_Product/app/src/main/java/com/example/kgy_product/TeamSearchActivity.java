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

import com.example.kgy_product.teamSearch.TeamSearchListAdapter;
import com.example.kgy_product.teamSearch.TeamSearchListItem;

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
        teamSearchListAdapter = new TeamSearchListAdapter( this, R.layout.team_search_view_layout, null );

        initDisplayObject();
        initListener();

        setList();
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

//        listData.add( arrayList );
        //*/

        teamSearchListAdapter.setData( (ArrayList<TeamSearchListItem>) listData.clone() );
    }
}

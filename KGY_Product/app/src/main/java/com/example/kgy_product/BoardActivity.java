package com.example.kgy_product;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.kgy_product.board.BoardCommentLayout;
import com.example.kgy_product.board.CommentData;
import com.example.kgy_product.networkTask.NetworkAdaptor;
import com.example.kgy_product.scheduler.ScheduleNode;
import com.example.kgy_product.scheduler.Scheduler;
import com.example.kgy_product.user.User;
import com.example.kgy_product.util.BitmapUtil;
import com.example.kgy_product.util.TimeUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by deokhwan on 2018-01-06.
 */

public class BoardActivity extends AppCompatActivity {

    private ScrollView boardCommentScroll;

    private ImageView boardImgView;
    private TextView teamNameView;
    private TextView teamContext;

    private TextView txtUserName;
    private EditText txtUserComment;
    private Button btnCommentSend;

    private BoardCommentLayout boardCommentLayout;

    private String id;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

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

        ScheduleNode.ScheduleAction initDisplayObjectAction = new ScheduleNode.ScheduleAction()
        {
            @Override
            public void excute(Callback callback)
            {
                initDisplayObject();

                callback.excute();
            }
        };
        node = new ScheduleNode("initDisplayObjectAction", initDisplayObjectAction);
        scheduler.add(node);

        ScheduleNode.ScheduleAction initListenerAction = new ScheduleNode.ScheduleAction() {
            @Override
            public void excute(Callback callback) {
                initListener();

                callback.excute();
            }
        };
        node = new ScheduleNode("initListenerAction", initListenerAction);
        scheduler.add(node);

        ScheduleNode.ScheduleAction infoTeamInfoAction = new ScheduleNode.ScheduleAction()
        {
            @Override
            public void excute(final Callback callback)
            {
                NetworkAdaptor.NetworkCallback networkCallback = new NetworkAdaptor.NetworkCallback()
                {
                    @Override
                    public void onResponse(JSONObject data)
                    {
                        try
                        {
                            JSONArray list = data.getJSONArray("result");
                            JSONObject obj = list.getJSONObject(0);

                            String reg = "";
                            if( Integer.parseInt(TimeUtil.termTime(obj.getString("regDate").toString())) > 60){
                                int rd = Integer.parseInt(TimeUtil.termTime(obj.getString("regDate").toString()))/60;
                                reg = rd+"시간";
                            } else {
                                reg = Integer.parseInt(TimeUtil.termTime(obj.getString("regDate").toString()))+"분";
                            }

                            String context = obj.getString("teamComment").toString()+"이 "+obj.getString("teamNumber").toString()+"명이서"
                                            + reg+" 전부터 기다리는중..."
                                            +"\n우리는 "+obj.getString("teamYouComment").toString()+"을 원해요~!";

                            setTeamInfo(BitmapUtil.getBitmapToString(obj.getString("imgFile")), obj.getString("teamNm"), context);

                            callback.excute();
                        }
                        catch (JSONException e)
                        {
                            System.out.println("aa");
                        }
                    }
                };

                //팀상세정보
                NetworkAdaptor.instance().getTeamInfo(networkCallback,id);
            }
        };
        node = new ScheduleNode("infoTeamInfoAction",infoTeamInfoAction);
        scheduler.add(node);

        ScheduleNode.ScheduleAction getBoardListAction = new ScheduleNode.ScheduleAction()
        {
            @Override
            public void excute(final Callback callback)
            {
                refreshBoardList(callback);
            }
        };
        node = new ScheduleNode("getBoardListAction", getBoardListAction);
        scheduler.add(node);

        scheduler.start();
    }

    private void initListener()
    {
        View.OnClickListener commentSendListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                NetworkAdaptor.NetworkCallback networkCallback = new NetworkAdaptor.NetworkCallback() {
                    @Override
                    public void onResponse(JSONObject data)
                    {
                        System.out.println(data.toString());

                        txtUserComment.setText("");

                        refreshBoardList(null);
                    }
                };

                HashMap<String, String> hashMap = new HashMap<>();

                hashMap.put("teamNo", User.instance().getId());
                hashMap.put("boardUpper", id);
                hashMap.put("boardComment", txtUserComment.getText().toString());

                NetworkAdaptor.instance().setBoardInfo(networkCallback, hashMap);
            }
        };

        btnCommentSend.setOnClickListener(commentSendListener);
    }

    private void initDisplayObject()
    {
        boardCommentScroll = (ScrollView) findViewById(R.id.boardCommentScroll);

        boardImgView = (ImageView)findViewById(R.id.WomemGroup);
        teamNameView = (TextView)findViewById(R.id.TeamName);
        teamContext = (TextView)findViewById(R.id.teamContext);

        txtUserName = (TextView)  findViewById(R.id.txtUserName);
        txtUserComment = (EditText) findViewById(R.id.txtUserComment);
        btnCommentSend = (Button) findViewById(R.id.btnCommentSend);

        boardCommentLayout = new BoardCommentLayout(this);
        boardCommentScroll.addView(boardCommentLayout);

        txtUserName.setText(User.instance().getName());
    }

    private void setTeamInfo(Bitmap boardImage, String strTeamName, String strTeamContext)
    {
        boardImgView.setImageBitmap(boardImage);
        teamNameView.setText(strTeamName);
        teamContext.setText(strTeamContext);
    }

    private void refreshBoardList(final ScheduleNode.ScheduleAction.Callback callback)
    {
        NetworkAdaptor.NetworkCallback networkCallback = new NetworkAdaptor.NetworkCallback()
        {
            @Override
            public void onResponse(JSONObject data)
            {
                System.out.println(data.toString());

                try
                {
                    JSONArray result = data.getJSONArray("result");

                    CommentData commentData;
                    JSONObject obj;
                    ArrayList<CommentData> commentDatas = new ArrayList<CommentData>();
                    for(int i = 0; i < result.length(); i++)
                    {
                        obj = result.getJSONObject(i);
                        commentData = new CommentData(obj.getString("teamNm"), obj.getString("boardComment"));
                        commentDatas.add(commentData);
                    }

                    boardCommentLayout.setData(commentDatas);
                }
                catch(JSONException exception)
                {
                    exception.printStackTrace();
                }

                if( callback != null)
                    callback.excute();
            }
        };

        NetworkAdaptor.instance().getBoardList(networkCallback, id);
    }
}

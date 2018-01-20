package com.example.kgy_product.board;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kgy_product.R;

/**
 * Created by ccc62 on 2018-01-13.
 */

public class BoardCommentLayout extends LinearLayout
{
    private Context mContext;

    private LinearLayout rootLayout;

    private TextView txtUserName;
    private EditText txtUserComment;
    private Button btnCommentSend;

    private ListView commentListView;

    public BoardCommentLayout( Context context)
    {
        super(context);

        mContext = context;

        init();
    }

    private void init()
    {
        initDisplayObject();
        initListener();
    }

    private void initDisplayObject()
    {
        rootLayout = (LinearLayout) inflate( mContext, R.layout.board_comment_layout, null );
        addView( rootLayout, new LayoutParams( LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT ) );

        txtUserName = (TextView) rootLayout.findViewById(R.id.txtUserName);
        txtUserComment = (EditText) rootLayout.findViewById(R.id.txtUserComment);
        btnCommentSend = (Button) rootLayout.findViewById(R.id.btnCommentSend);

        commentListView = (ListView) rootLayout.findViewById(R.id.commentListView);
    }

    private void initListener()
    {
        OnClickListener commentSendListener = new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                sendComment();
            }
        };

        btnCommentSend.setOnClickListener(commentSendListener);
    }

    private void sendComment()
    {

    }

    public void dispose()
    {

    }
}

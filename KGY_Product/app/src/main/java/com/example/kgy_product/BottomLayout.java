package com.example.kgy_product;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by ccc62 on 2017-04-01.
 */

public class BottomLayout extends LinearLayout
{
    private Context mContext;

    private LinearLayout rootLayout;
    private Button btnPrev;
    private Button btnNext;

    private OnClickListener onClickListener;

    private ButtonCallback buttonCallback;

    public BottomLayout(Context context)
    {
        super(context);

        mContext = context;
        init();
    }

    @Override
    protected void onAttachedToWindow()
    {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
    }

    public void dispose()
    {
        ///TODO @jimin 객체 비워
    }

    public void setButtonCallback( ButtonCallback callback )
    {
        buttonCallback = callback;
    }

    private void init()
    {
        initDisplayObject();
        initListener();
    }

    private void initDisplayObject()
    {
        rootLayout = (LinearLayout) inflate(mContext, R.layout.bottom_layout, null);
        addView( rootLayout, new LayoutParams( LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT ) );

        btnPrev = (Button) rootLayout.findViewById(R.id.btnPrev);
        btnNext = (Button) rootLayout.findViewById(R.id.btnNext);
    }

    private void initListener()
    {
        onClickListener = new OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                if( buttonCallback == null )
                    return;

                if( view.getId() == btnPrev.getId() )
                {
                    buttonCallback.onClickPrevButton();
                }
                else if( view.getId() == btnNext.getId() )
                {
                    buttonCallback.onClickNextButton();
                }
            }
        };

        btnPrev.setOnClickListener( onClickListener );
        btnNext.setOnClickListener( onClickListener );
    }

    public interface ButtonCallback
    {
        void onClickPrevButton();
        void onClickNextButton();
    }
}

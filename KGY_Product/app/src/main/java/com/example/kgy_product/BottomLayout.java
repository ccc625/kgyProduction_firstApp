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
    private Button btnPrev;
    private Button btnNext;

    private OnClickListener onClickListener;

    private ButtonCallback buttonCallback;

    public BottomLayout(Context context)
    {
        super(context);

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
        buttonCallback = null;

        super.onDetachedFromWindow();
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
        btnPrev = (Button) findViewById(R.id.btnPrev);
        btnNext = (Button) findViewById(R.id.btnNext);
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

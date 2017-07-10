package org.androidtown.myapplication;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by lee on 2017-07-08.
 */

public class IconTextView extends LinearLayout {

    private ImageView mIcon;

    private TextView mText01;

    private TextView mText02;

    private TextView mText03;

    public IconTextView(Context context, IconTextItem aItem) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.itemlist, this, true);

        mIcon = (ImageView) findViewById(R.id.iconItem);
        mIcon.setImageDrawable(aItem.getIcon());

        // Set Text 03
        mText03 = (TextView) findViewById(R.id.dataItem03);
        mText03.setText(aItem.getData(2));

    }

    public void setText(int index, String data) {

        if (index == 2) {
            mText03.setText(data);
        } else {
            throw new IllegalArgumentException();
        }
    }


    public void setIcon(Drawable icon) {
        mIcon.setImageDrawable(icon);
    }

}

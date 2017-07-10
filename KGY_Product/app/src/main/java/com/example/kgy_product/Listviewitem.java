package com.example.kgy_product;

import android.graphics.drawable.Drawable;

/**
 * Created by lee on 2017-07-10.
 */


public class ListViewItem {

    private Drawable iconDrawble;
    private String descStr;


    public void setIcon(Drawable icon)
    {
        iconDrawble = icon;
    }

    public void setDesc(String Desc) {
        descStr = Desc;
    }

    public Drawable getIcon()
    {
        return this.iconDrawble;
    }

    public String getDesc()
    {
        return this.descStr;
    }
}

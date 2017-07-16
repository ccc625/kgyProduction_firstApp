package org.androidtown.myapplication;

import android.graphics.drawable.Drawable;

/**
 * Created by lee on 2017-07-08.
 */


public class IconTextItem {

    private Drawable mIcon;

    private String[] mData;

    private boolean mSelectable = true;


    // 파라미터값이 배열로 된 생성자

    public IconTextItem(Drawable mIcon, String[] mData) {

        this.mIcon = mIcon;

        this.mData = mData;

    }


    // 파라미터값이 String으로 된 생성자

    public IconTextItem(Drawable mIcon, String obj03) {

        this.mIcon = mIcon;


        mData = new String[3];



        mData[2] = obj03;

    }


    public boolean isSelectable(){

        return mSelectable;

    }


    public void setmSelectable(boolean mSelectable) {

        this.mSelectable = mSelectable;

    }


    // 배열 반환

    public String[] getData(){

        return mData;

    }


    // String 반환 파리미터 인덱스 값

    public String getData(int index){

        if (mData == null || index >= mData.length){

            return null;

        }

        return mData[index];

    }


    public void setData(String[] obj){

        mData = obj;

    }


    public void setIcon(Drawable Icon){

        mIcon = Icon;

    }


    public Drawable getIcon(){

        return mIcon;

    }



}

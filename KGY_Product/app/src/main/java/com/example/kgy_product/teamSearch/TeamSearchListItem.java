package com.example.kgy_product.teamSearch;

import android.graphics.Bitmap;

/**
 * Created by ccc62 on 2017-06-06.
 */

public class TeamSearchListItem
{
    private Bitmap icon;
    private String name;

    public TeamSearchListItem()
    {
    }

    public TeamSearchListItem( String name, Bitmap icon )
    {
        this.name = name;
        this.icon = icon;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }

    public void setIcon( Bitmap icon )
    {
        this.icon = icon;
    }

    public Bitmap getIcon()
    {
        return this.icon;
    }
}

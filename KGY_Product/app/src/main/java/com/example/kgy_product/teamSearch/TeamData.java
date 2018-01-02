package com.example.kgy_product.teamSearch;

import android.graphics.Bitmap;

/**
 * Created by ccc62 on 2017-12-09.
 */

public class TeamData
{
    private String teamNo;
    private String teamNm;
    private String gender;
    private String imgFile;
    private String areaNm;

    public void setTeamNo(String value)
    {
        teamNo = value;
    }

    public String getTeamNo()
    {
        return teamNo;
    }

    public void setTeamNm(String value)
    {
        teamNm = value;
    }

    public String getTeamNm()
    {
        return teamNm;
    }

    public void setGender(String value)
    {
        gender = value;
    }

    public String getGender()
    {
        return gender;
    }

    public void setImgFile(String value)
    {
        imgFile = value;
    }

    public String getImgFile()
    {
        return imgFile;
    }

    public void setAreaNm(String value)
    {
        areaNm = value;
    }

    public String getAreaNm()
    {
        return areaNm;
    }

    public TeamData()
    {

    }

    public TeamData(String teamNo, String teamNm, String gender, String imgFile, String areaNm)
    {
        setData(teamNo, teamNm, gender, imgFile, areaNm);
    }

    public void setData(String teamNo, String teamNm, String gender, String imgFile, String areaNm)
    {
        this.teamNo = teamNo;
        this.teamNm = teamNm;
        this.gender = gender;
        this.imgFile = imgFile;
        this.areaNm = areaNm;
    }
}

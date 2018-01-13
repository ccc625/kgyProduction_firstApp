package com.example.kgy_product.board;

/**
 * Created by ccc62 on 2018-01-13.
 */

public class CommentData
{
    private String teamName;
    private String comment;

    public CommentData()
    {

    }

    public CommentData(String teamName, String comment)
    {
        this.teamName = teamName;
        this.comment = comment;
    }

    public String getTeamName()
    {
        return teamName;
    }

    public void setTeamName(String teamName)
    {
        this.teamName = teamName;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }
}

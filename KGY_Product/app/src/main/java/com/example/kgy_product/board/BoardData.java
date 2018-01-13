package com.example.kgy_product.board;

/**
 * Created by ccc62 on 2018-01-13.
 */

public class BoardData
{
    private String teamNo;
    private String teamNm;
    private String teamGender;
    private String teamNumber;
    private String teamArea;
    private String teamAlcohol;
    private String teamAlNum;
    private String teamComment;
    private String teamYouComment;
    private String imgFile;

    public BoardData()
    {

    }

    public BoardData(String teamNo, String teamNm, String teamGender, String teamNumber, String teamArea, String teamAlcohol, String teamAlNum, String teamComment, String teamYouComment, String imgFile)
    {
        this.teamNo = teamNo;
        this.teamNm = teamNm;
        this.teamGender = teamGender;
        this.teamNumber = teamNumber;
        this.teamArea = teamArea;
        this.teamAlcohol = teamAlcohol;
        this.teamAlNum = teamAlNum;
        this.teamComment = teamComment;
        this.teamYouComment = teamYouComment;
        this.imgFile = imgFile;
    }

    public String getTeamNo() {
        return teamNo;
    }

    public void setTeamNo(String teamNo) {
        this.teamNo = teamNo;
    }

    public String getTeamNm() {
        return teamNm;
    }

    public void setTeamNm(String teamNm) {
        this.teamNm = teamNm;
    }

    public String getTeamGender() {
        return teamGender;
    }

    public void setTeamGender(String teamGender) {
        this.teamGender = teamGender;
    }

    public String getTeamNumber() {
        return teamNumber;
    }

    public void setTeamNumber(String teamNumber) {
        this.teamNumber = teamNumber;
    }

    public String getTeamArea() {
        return teamArea;
    }

    public void setTeamArea(String teamArea) {
        this.teamArea = teamArea;
    }

    public String getTeamAlcohol() {
        return teamAlcohol;
    }

    public void setTeamAlcohol(String teamAlcohol) {
        this.teamAlcohol = teamAlcohol;
    }

    public String getTeamAlNum() {
        return teamAlNum;
    }

    public void setTeamAlNum(String teamAlNum) {
        this.teamAlNum = teamAlNum;
    }

    public String getTeamComment() {
        return teamComment;
    }

    public void setTeamComment(String teamComment) {
        this.teamComment = teamComment;
    }

    public String getTeamYouComment() {
        return teamYouComment;
    }

    public void setTeamYouComment(String teamYouComment) {
        this.teamYouComment = teamYouComment;
    }

    public String getImgFile() {
        return imgFile;
    }

    public void setImgFile(String imgFile) {
        this.imgFile = imgFile;
    }
}

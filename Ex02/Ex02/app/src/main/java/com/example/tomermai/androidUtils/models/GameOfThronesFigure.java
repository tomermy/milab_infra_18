package com.example.tomermai.androidUtils.models;

public class GameOfThronesFigure {
    private String mFigureName;
    private int mProfileImageID;

    public GameOfThronesFigure(String mFigureName, int imageId) {

        this.mFigureName = mFigureName;
        this.mProfileImageID = imageId;
    }

    public String getmFigureName() {
        return mFigureName;
    }

    public void setmFigureName(String figureName) {
        this.mFigureName = figureName;
    }

    public int getmProfileImageID() {
        return mProfileImageID;
    }

    public void setmProfileImageID(int mProfileImageID) {
        this.mProfileImageID = mProfileImageID;
    }
}

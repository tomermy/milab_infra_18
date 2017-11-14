package com.example.tomermai.ex02;

class GameOfThronesFigure {
    private String mFigureName;
    private int mProfileImageID;

    GameOfThronesFigure(String mFigureName, int imageId) {

        this.mFigureName = mFigureName;
        this.mProfileImageID = imageId;
    }

    String getmFigureName() {
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

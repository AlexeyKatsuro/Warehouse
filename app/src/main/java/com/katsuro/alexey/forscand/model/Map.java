package com.katsuro.alexey.forscand.model;

import android.graphics.Canvas;

import com.google.gson.annotations.SerializedName;
import com.katsuro.alexey.forscand.buildModes.BuildMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexey on 4/20/18.
 */

public class Map implements Drawable {

    @SerializedName("wallList")
    private List<Wall> mWallList = new ArrayList<>();
    @SerializedName("boxList")
    private List<Box> mBoxList = new ArrayList<>();

    @SerializedName("gateList")
    private List<Gate> mGateList = new ArrayList<>();

    private float mScale = 33.33332f;

    public List<Wall> getWallList() {
        return mWallList;
    }


    public void setWallList(List<Wall> wallList) {
        mWallList = wallList;
    }

    public List<Box> getBoxList() {
        return mBoxList;
    }

    public void setBoxList(List<Box> boxList) {
        mBoxList = boxList;
    }

    public List<Gate> getGateList() {
        return mGateList;
    }

    public void setGateList(List<Gate> gateList) {
        mGateList = gateList;
    }

    @Override
    public void draw(Canvas canvas) {
        for (Box box : mBoxList){
            box.draw(canvas);
        }

        for(Wall wall: mWallList){
            wall.draw(canvas);
        }

        for (Gate gate: mGateList){
            gate.draw(canvas);
        }


    }

    public float getDpScale() {
        return mScale;
    }

    public float getPxScale() {
        return BuildMode.convertDpToPixel(mScale);
    }

}

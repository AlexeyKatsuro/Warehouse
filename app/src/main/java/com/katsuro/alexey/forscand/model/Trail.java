package com.katsuro.alexey.forscand.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * Created by alexey on 4/26/18.
 */

public class Trail implements Drawable {

    List<PointF> mPointList;

    public Trail(PointF startPoint) {
        mPointList = new LinkedList<>();
        mPointList.add(startPoint);
    }

    public Trail(float startX, float startY) {
        mPointList = new LinkedList<>();
        mPointList.add(new PointF(startX,startY));
    }

    public void lineTo(PointF point){
        mPointList.add(point);
    }

    public void lineTo(float nextX, float nextY){
        mPointList.add(new PointF(nextX,nextY));
    }

    public void remove(int i){
        mPointList.remove(i);
    }


    public void removeLast(){
        mPointList.remove(mPointList.size()-1);
    }

    public int length() {
        return mPointList.size();
    }

    public PointF get(int i) {
        return mPointList.get(i);
    }
    public PointF getLast() {
        return mPointList.get(mPointList.size()-1);
    }

    public PointF getLastButOne() {
        return mPointList.get(mPointList.size()-2);
    }


    public List<PointF> getPointList() {
        return mPointList;
    }

    public void setPointList(List<PointF> pointList) {
        mPointList = pointList;
    }

    @Override
    public void draw(Canvas canvas) {
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setStrokeWidth(3);
        p.setStyle(Paint.Style.STROKE);
        p.setColor(Color.BLUE);

        for(int i = 0; i<mPointList.size()-1;i++){
            PointF startPoint = mPointList.get(i);
            PointF endPoint = mPointList.get(i+1);

            canvas.drawLine(startPoint.x,startPoint.y,
                    endPoint.x,endPoint.y,p);
        }

    }
}

package com.katsuro.alexey.forscand.model;

import android.graphics.PointF;

/**
 * Created by alexey on 4/19/18.
 */

public class Wall {
    PointF start;
    PointF stop;

    public Wall() {
    }

    public Wall(PointF start, PointF stop) {
        this.start = start;
        this.stop = stop;
    }

    public Wall(float startX, float startY, float stopX, float stopY){
        start = new PointF(startX,startY);
        stop = new PointF(stopX,stopY);

    }

    public PointF getStart() {
        return start;
    }

    public float getStartX() {
        return start.x;
    }

    public float getStartY() {
        return start.y;
    }
    public void setStart(PointF start) {
        this.start = start;
    }

    public void setStart(float startX, float startY) {
        start = new PointF(startX,startY);
    }

    public PointF getStop() {
        return stop;
    }

    public float getStopX() {
        return stop.x;
    }
    public float getStopY() {
        return stop.y;
    }

    public void setStop(PointF stop) {
        this.stop = stop;
    }

    public void setStop(float stopX, float stopY) {
        stop = new PointF(stopX,stopY);
    }

}

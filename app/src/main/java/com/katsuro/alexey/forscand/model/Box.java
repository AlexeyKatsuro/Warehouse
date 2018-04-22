package com.katsuro.alexey.forscand.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;

/**
 * Created by alexey on 4/20/18.
 */

public class Box implements Drawable{

    private PointF mPosition = new PointF();
    private float width;
    private float height;
    private Paint mPaint;

    public Box() {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
    }

    public Box(PointF position, int width, int height) {
        this();
        mPosition = position;
        this.width = width;
        this.height = height;
    }

    public Box(float positionX, float positionY, int width, int height) {
        this();
        mPosition.x = positionX;
        mPosition.y = positionY;
        this.width = width;
        this.height = height;
    }

    public Box(Rect rect){
        this();
        mPosition.x = rect.left;
        mPosition.y = rect.top;
        width = rect.width();
        height = rect.height();
    }

    public PointF getPosition() {
        return mPosition;
    }
    public float getPositionX() {
        return mPosition.x;
    }
    public float getPositionY() {
        return mPosition.y;
    }

    public void setPosition(PointF position) {
        mPosition = position;
    }

    public void setPosition(float x, float y){
        mPosition = new PointF(x,y);
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Rect getRect(){
        return new Rect(
                (int) mPosition.x,
                (int) mPosition.y,
                (int) (mPosition.x+width),
                (int) (mPosition.y+height));
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(getRect(),mPaint);
    }
}

package com.katsuro.alexey.forscand.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;

import com.katsuro.alexey.forscand.buildModes.BuildMode;

/**
 * Created by alexey on 4/22/18.
 */

public class Gate implements Drawable{


    private PointF mPosition = new PointF();
    private float width;

    private transient Paint mPaintLine;

    public Gate() {
        mPaintLine = new Paint();

        mPaintLine.setStyle(Paint.Style.STROKE);
        mPaintLine.setColor(Color.BLUE);
        mPaintLine.setStrokeWidth(5);
    }

    public Gate(PointF position, float width) {
        this();
        mPosition = position;
        this.width =width;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawLine(
                BuildMode.convertDpToPixel(mPosition.x-width/2),
                BuildMode.convertDpToPixel(mPosition.y-width/2),
                BuildMode.convertDpToPixel(mPosition.x+width/2),
                BuildMode.convertDpToPixel(mPosition.y-width/2),
                mPaintLine);

        canvas.drawLine(
                BuildMode.convertDpToPixel(mPosition.x-width/2),
                BuildMode.convertDpToPixel(mPosition.y+width/2),
                BuildMode.convertDpToPixel(mPosition.x+width/2),
                BuildMode.convertDpToPixel(mPosition.y+width/2),
                mPaintLine);
    }

    public PointF getPosition() {
        return mPosition;
    }

    public void setPosition(PointF position) {
        mPosition = position;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }


    public Paint getPaintLine() {
        return mPaintLine;
    }

    public void setPaintLine(Paint paintLine) {
        mPaintLine = paintLine;
    }
}

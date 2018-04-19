package com.katsuro.alexey.forscand;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.katsuro.alexey.forscand.model.Wall;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexey on 4/19/18.
 */

public class WarehouseView extends View {

    private static final String TAG = WarehouseView.class.getSimpleName();
    private List<Wall> mWallList;
    private Paint mWallPaint;
    private int scale;

    public WarehouseView(Context context) {
        super(context);
        init();
    }

    public WarehouseView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init(){
        mWallList = new ArrayList<>();
        mWallPaint = new Paint();
        mWallPaint.setStyle(Paint.Style.STROKE);
        mWallPaint.setStrokeWidth(5);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(scale>=5) {
            drawGrid(canvas, scale);
        }



        for(Wall wall: mWallList){

            canvas.drawLine(
                    wall.getStartX(),
                    wall.getStartY(),
                    wall.getStopX(),
                    wall.getStopY(),
                    mWallPaint);
        }
    }

    private void drawGrid(Canvas canvas, int scale) {
        int w = canvas.getWidth();
        int h = canvas.getHeight();
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        paint.setColor(Color.GRAY);

        Log.i(TAG,String.format("w: %d, h: %d",w,h));
        for(int x = 0; x<w;x+=scale){
            canvas.drawLine(x,0,x,h,paint);
        }

        for(int y = 0; y<h; y+=scale){
            canvas.drawLine(0,y,w,y,paint);
        }

    }

    public List<Wall> getWallList() {
        return mWallList;
    }

    public void setWallList(List<Wall> wallList) {
        mWallList = wallList;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }
}

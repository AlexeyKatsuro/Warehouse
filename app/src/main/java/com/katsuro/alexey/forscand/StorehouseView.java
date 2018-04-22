package com.katsuro.alexey.forscand;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.katsuro.alexey.forscand.model.Box;
import com.katsuro.alexey.forscand.model.Map;
import com.katsuro.alexey.forscand.model.Wall;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexey on 4/19/18.
 */

public class StorehouseView extends View {

    private static final String TAG = StorehouseView.class.getSimpleName();
    private Map mMap = new Map();

    private int scale;

    public StorehouseView(Context context) {
        super(context);
        init();
    }

    public StorehouseView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init(){

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

        mMap.draw(canvas);
    }

    private void drawGrid(Canvas canvas, int scale) {
        int w = canvas.getWidth();
        int h = canvas.getHeight();
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        paint.setColor(Color.GRAY);

        for(int x = 0; x<w;x+=scale){
            canvas.drawLine(x,0,x,h,paint);
        }

        for(int y = 0; y<h; y+=scale){
            canvas.drawLine(0,y,w,y,paint);
        }

    }

    public Map getMap() {
        return mMap;
    }

    public void setMap(Map map) {
        mMap = map;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

}

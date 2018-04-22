package com.katsuro.alexey.forscand.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;

/**
 * Created by alexey on 4/22/18.
 */

public class Gate extends Wall implements Drawable{

    public enum Type{input,output}

    private Type mType;

    public Gate(Type type) {
        super();
        init(type);
    }

    private void init(Type type) {
        mType = type;
        if(mType== Type.input) {
            getPaint().setColor(Color.MAGENTA);
        }

        if (mType== Type.output){
            getPaint().setColor(Color.BLUE);
        }
        getPaint().setStrokeWidth(getPaint().getStrokeWidth()*2);
    }

    public Gate(PointF start, PointF stop, Type type) {
        super(start, stop);
        init(type);

    }

    public Gate(float startX, float startY, float stopX, float stopY,Type type) {
        super(startX, startY, stopX, stopY);
        init(type);
    }

    public Type getType() {
        return mType;
    }

    public void setType(Type type) {
        mType = type;
    }
}

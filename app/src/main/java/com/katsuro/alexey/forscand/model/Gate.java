package com.katsuro.alexey.forscand.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Handler;
import android.util.Log;

import com.katsuro.alexey.forscand.CommandHandler;
import com.katsuro.alexey.forscand.buildModes.BuildMode;
import com.katsuro.alexey.forscand.commands.Command;

import java.util.List;

/**
 * Created by alexey on 4/22/18.
 */

public class Gate extends Box{

    public Gate(){
        super();
        init();
    }
    public Gate(PointF position,Map  map) {
        super(position, map.getDpScale(), map.getDpScale());
        init();

    }

    @Override
    protected void init() {
        super.init();
        setPaint(new Paint());
        getPaint().setStyle(Paint.Style.STROKE);
        getPaint().setStrokeWidth(3);
        getPaint().setColor(Color.BLUE);
        setMargin(0);
        //setMargin(3);
    }

}

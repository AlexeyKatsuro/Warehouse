package com.katsuro.alexey.forscand.commands;

import android.graphics.Path;
import android.graphics.PointF;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.katsuro.alexey.forscand.StorehouseView;
import com.katsuro.alexey.forscand.UpdateUIListener;
import com.katsuro.alexey.forscand.model.Robot;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by alexey on 4/26/18.
 */

public class MoveCommand extends RobotCommand {

    protected static final String TAG = MoveCommand.class.getSimpleName();

    protected PointF mEnd;
    protected PointF mStart;
    private List<PointF> mBetweenPoints;
    private float speed = 2; // width/sec
    private float stepsInWidth = 20;


    public MoveCommand(Robot robot, PointF position, UpdateUIListener listener) {
        super(robot,listener);
        mEnd =position;

    }


    @Override
    public void execute() {
        mStart = mRobot.getPosition();
        float xlength =Math.abs(mEnd.x-mStart.x);
        float ylength =Math.abs(mEnd.y-mStart.y);
        int lenght = (int) Math.round(Math.sqrt(xlength*xlength+ylength*ylength)/mRobot.getWidth());
        mBetweenPoints = getBetweenPoints((int) (lenght*stepsInWidth));
        Path path = new Path();
        for(PointF point :mBetweenPoints){
            mRobot.move(point);
            mUpdateUIListener.onUpdateUI(mRobot.getRect());
            try {
                TimeUnit.MILLISECONDS.sleep((long) (1/stepsInWidth/(speed/1000)));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


    private List<PointF> getBetweenPoints( int count) {
        List<PointF> points = new LinkedList<>();
        float xlength =(mEnd.x-mStart.x);
        float ylength =(mEnd.y-mStart.y);
        if(xlength!=0) {
            float step = xlength / count;
            for (float x = mStart.x, c = 0;c<count; c++, x += step) {
                points.add(new PointF(x, getY(x)));
            }
            points.add(mEnd);
        } else {
            float step = ylength / count;
            for (float y = mStart.y,c = 0;c<count; c++,  y += step) {
                points.add(new PointF(getX(y),y));
            }
            points.add(mEnd);
        }

        return points;
    }

    private float getX(float y) {
        float A=mStart.y-mEnd.y,
                B=mEnd.x-mStart.x,
                C=mStart.x*mEnd.y-mEnd.x*mStart.y;
        float x = -(C+B*y)/A;
        return x;
    }

    private float getY(float x){
        float A=mStart.y-mEnd.y,
                B=mEnd.x-mStart.x,
                C=mStart.x*mEnd.y-mEnd.x*mStart.y;
        float y = -(C+A*x)/B;
        return y;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}

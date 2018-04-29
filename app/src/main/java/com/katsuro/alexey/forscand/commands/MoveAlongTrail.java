package com.katsuro.alexey.forscand.commands;


import android.graphics.PointF;
import android.os.Handler;
import android.view.View;

import com.katsuro.alexey.forscand.model.Robot;
import com.katsuro.alexey.forscand.model.Trail;


/**
 * Created by alexey on 4/26/18.
 */

public class MoveAlongTrail extends Command{
    private Robot mRobot;
    private Trail mTrail;
    private View mView;
    private Handler mHandler;



    public MoveAlongTrail(Robot robot, Trail trail, View view, Handler handler) {
        mRobot = robot;
        mTrail= trail;
        mView = view;
        mHandler = handler;
    }

    @Override
    public void execute() {
        for(PointF point: mTrail.getPointList()) {
            mRobot.addCommand(new MoveCommand(mRobot, point, mView,mHandler));
        }

    }
}

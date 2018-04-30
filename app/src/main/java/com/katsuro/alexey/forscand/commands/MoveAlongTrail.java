package com.katsuro.alexey.forscand.commands;


import android.graphics.PointF;
import android.os.Handler;
import android.view.View;

import com.katsuro.alexey.forscand.UpdateUIListener;
import com.katsuro.alexey.forscand.model.Robot;
import com.katsuro.alexey.forscand.model.Trail;


/**
 * Created by alexey on 4/26/18.
 */

public class MoveAlongTrail extends RobotCommand{

    private Trail mTrail;

    public MoveAlongTrail(Robot robot, Trail trail, UpdateUIListener listener) {
        super(robot,listener);
        mTrail= trail;
    }

    @Override
    public void execute() {
        for(PointF point: mTrail.getPointList()) {
            mRobot.addCommand(new MoveCommand(mRobot, point,mUpdateUIListener));
        }

    }
}

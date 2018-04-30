package com.katsuro.alexey.forscand.commands;

import android.os.Handler;
import android.view.View;

import com.katsuro.alexey.forscand.UpdateUIListener;
import com.katsuro.alexey.forscand.model.Box;
import com.katsuro.alexey.forscand.model.Robot;

/**
 * Created by alexey on 4/29/18.
 */

public class TakeBoxCommand extends RobotCommand {

    private Box mTakenBox;

    public TakeBoxCommand(Robot robot, Box box) {
        super(robot, null);
        mTakenBox = box;
    }

    @Override
    public void execute() {
        mRobot.takeBox(mTakenBox);
    }
}

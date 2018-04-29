package com.katsuro.alexey.forscand.commands;

import android.os.Handler;
import android.view.View;

import com.katsuro.alexey.forscand.UpdateUIListener;
import com.katsuro.alexey.forscand.model.Robot;

/**
 * Created by alexey on 4/29/18.
 */

public abstract class RobotCommand extends Command {
    protected Robot mRobot;
    protected UpdateUIListener mUpdateUIListener;

    public RobotCommand(Robot robot, UpdateUIListener listener) {
        mRobot = robot;
        mUpdateUIListener = listener;
    }
}

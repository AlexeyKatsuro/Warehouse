package com.katsuro.alexey.forscand.commands;

import com.katsuro.alexey.forscand.UpdateUIListener;
import com.katsuro.alexey.forscand.model.Robot;

/**
 * Created by alexey on 4/30/18.
 */

public class PutBoxCommand extends RobotCommand {

    public PutBoxCommand(Robot robot) {
        super(robot, null);
    }

    @Override
    public void execute() {
        mRobot.putBox();
    }
}

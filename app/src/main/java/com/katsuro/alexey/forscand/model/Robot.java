package com.katsuro.alexey.forscand.model;

import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Handler;
import android.util.Log;

import com.katsuro.alexey.forscand.CommandHandler;
import com.katsuro.alexey.forscand.commands.Command;

import java.util.List;

/**
 * Created by alexey on 4/24/18.
 */

public class Robot extends Box{

    private static final String TAG = Robot.class.getSimpleName();
    private final Map mMap;
    private Box mTakenBox;
    private CommandHandler mCommandHandler;


    public Robot(Gate gate,Map  map) {
        super(gate.getPosition(), map.getDpScale(), map.getDpScale());
        mMap=map;
        Handler handler = new Handler();
        mCommandHandler = new CommandHandler(handler);
        mCommandHandler.start();
        mCommandHandler.getLooper();
        Log.d(TAG, "Background thread started");
    }

    @Override
    protected void init() {
        super.init();
        getPaint().setColor(Color.GREEN);
        setMargin(3);
    }

    public void move(PointF dest){
        mPosition = dest;
        if(mTakenBox!=null){
            mTakenBox.setPosition(mPosition);
        }
    }

    public void takeBox(Box box){
        if(box.getPosition().equals(getPosition())) {
            mTakenBox = box;
            mTakenBox.setPosition(mPosition);
        }
    }

    public void putBox(){
        if(mTakenBox!=null) {
            mTakenBox.setPosition(mPosition);
            mTakenBox = null;
        }
    }
    public void quit() {
        mCommandHandler.quit();
        Log.i(TAG, "Background thread destroyed");
    }

    public void claerQueue(){
        mCommandHandler.clearQueue();
    }

    public void addCommand(Command command){
        mCommandHandler.queueCommand(command);
    }

    public void addCommands(List<Command> commands){
        for(Command command : commands){
            addCommand(command);
        }
    }

}

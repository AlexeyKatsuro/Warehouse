package com.katsuro.alexey.forscand;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import com.katsuro.alexey.forscand.commands.Command;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by alexey on 4/29/18.
 */

public class CommandHandler extends HandlerThread {
    private static final int MESSAGE_EXECUTE = 0;

    private static final String TAG = CommandHandler.class.getSimpleName();
    private Handler mRequestHandler;
    private Handler mResponseHandler;

    public CommandHandler(Handler handler) {
        super(TAG);
        mResponseHandler = handler;
    }

    @SuppressLint("HandlerLeak")
    @Override
    protected void onLooperPrepared() {
        mRequestHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what== MESSAGE_EXECUTE){

                    final Command command = (Command) msg.obj;
                    command.execute();
                }
            }
        };
    }

    public void queueCommand(Command command) {
        Log.d(TAG, "Got a Command: ");
        if(command==null){
        } else {
            mRequestHandler.obtainMessage(MESSAGE_EXECUTE,command).sendToTarget();
        }
    }

    public void clearQueue() {
        mRequestHandler.removeMessages(MESSAGE_EXECUTE);
    }
}

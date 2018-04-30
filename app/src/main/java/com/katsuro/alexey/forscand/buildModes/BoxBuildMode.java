package com.katsuro.alexey.forscand.buildModes;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.katsuro.alexey.forscand.R;
import com.katsuro.alexey.forscand.StorehouseView;
import com.katsuro.alexey.forscand.model.Box;
import com.katsuro.alexey.forscand.model.Map;

import java.util.List;

/**
 * Created by alexey on 4/25/18.
 */

public class BoxBuildMode extends BuildMode {

    private static final String TAG = BoxBuildMode.class.getSimpleName();
    private Box mCurrentBox;

    public BoxBuildMode(Context context, Map map) {
        super(context, map);
        mTitle = mContext.getString(R.string.box_mode);
    }


    @Override
    public View.OnTouchListener OnTouchListener() {
        return new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(TAG, "OnTouch");
                StorehouseView view = (StorehouseView) v;
                Log.d(TAG, String.format("x: %f, y: %f", event.getX(), event.getY()));
                float pxScale= mMap.getPxScale();
                float dpScale= mMap.getDpScale();
                float x = roundToScale(event.getX(), pxScale, false)+pxScale/2;
                float y = roundToScale(event.getY(), pxScale, false)+pxScale/2;
                x = convertPixelsToDp(x);
                y = convertPixelsToDp(y);

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.d(TAG, "ACTION_DOWN");
                        mCurrentBox = new Box();
                        mCurrentBox.setWidth(dpScale);
                        mCurrentBox.setHeight(dpScale);
                        mCurrentBox.setPosition(x, y);
                        mMap.getBoxList().add(mCurrentBox);
                        view.invalidate();
                        return true;

                    case MotionEvent.ACTION_MOVE:
                        Log.d(TAG, "ACTION_MOVE");
                        mCurrentBox.setPosition(x, y);
                        view.invalidate();
                        return true;

                    case MotionEvent.ACTION_UP:
                        Log.d(TAG, "ACTION_UP");
                        mCurrentBox.setPosition(x, y);
                        view.invalidate();
                        ;
                        mCurrentBox = null;
                        return true;

                    default:
                        return false;
                }
            }
        };
    }



    @Override
    public List<?> getObjectList() {
        return mMap.getBoxList();
    }
}

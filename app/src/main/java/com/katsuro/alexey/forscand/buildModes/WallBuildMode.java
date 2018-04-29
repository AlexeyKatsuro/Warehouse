package com.katsuro.alexey.forscand.buildModes;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.katsuro.alexey.forscand.R;
import com.katsuro.alexey.forscand.StorehouseView;
import com.katsuro.alexey.forscand.model.Map;
import com.katsuro.alexey.forscand.model.Wall;

import java.util.List;

/**
 * Created by alexey on 4/25/18.
 */

public class WallBuildMode extends BuildMode {

    private static final String TAG = WallBuildMode.class.getSimpleName();
    private Wall mCurrentWall;

    public WallBuildMode(Context context, Map map) {
        super(context, map);
        mTitle = mContext.getString(R.string.wall_mode);
    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    @Override
    public View.OnTouchListener OnTouchListener() {
        return new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i(TAG, "OnTouch");
                StorehouseView view = (StorehouseView) v;
                Log.i(TAG, String.format("x: %f, y: %f", event.getX(), event.getY()));
                float x = roundToScale(event.getX(), mMap.getScale(), true);
                float y = roundToScale(event.getY(), mMap.getScale(), true);

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.i(TAG, "ACTION_DOWN");
                        mCurrentWall = new Wall();
                        mMap.getWallList().add(mCurrentWall);
                        mCurrentWall.setStart(x, y);
                        mCurrentWall.setStop(x, y);
                        return true;

                    case MotionEvent.ACTION_MOVE:
                        Log.i(TAG, "ACTION_MOVE");
                        mCurrentWall.setStop(x, y);
                        view.invalidate();
                        return true;

                    case MotionEvent.ACTION_UP:
                        Log.i(TAG, "ACTION_UP");
                        mCurrentWall.setStop(x, y);
                        view.invalidate();
                        mCurrentWall = null;
                        return true;

                    default:
                        return false;
                }
            }
        };
    }


    @Override
    public List<?> getObjectList() {
        return mMap.getWallList();
    }
}

package com.katsuro.alexey.forscand.buildModes;

import android.content.Context;
import android.graphics.PointF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.katsuro.alexey.forscand.R;
import com.katsuro.alexey.forscand.StorehouseView;
import com.katsuro.alexey.forscand.model.Gate;
import com.katsuro.alexey.forscand.model.Map;

import java.util.List;

/**
 * Created by alexey on 4/25/18.
 */

public class GateBuildMode extends BuildMode {

    private static final String TAG = GateBuildMode.class.getSimpleName();
    private Gate mCurrentGate;

    public GateBuildMode(Context context, Map map) {
        super(context, map);
        mTitle = mContext.getString(R.string.gate_input);
    }

    @Override
    public View.OnTouchListener OnTouchListener() {
        return new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Log.i(TAG, "OnTouch");
                StorehouseView view = (StorehouseView) v;
                Log.i(TAG, String.format("x: %f, y: %f", event.getX(), event.getY()));
                float pxScale= mMap.getPxScale();
                float dpScale= mMap.getDpScale();
                PointF point = new PointF();
                point.x = roundToScale(event.getX(), pxScale, false)+pxScale/2;
                point.y = roundToScale(event.getY(), pxScale, false)+pxScale/2;

                point.x = convertPixelsToDp(point.x);
                point.y = convertPixelsToDp(point.y);

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.i(TAG, "ACTION_DOWN");
                        mCurrentGate = new Gate();
                        mMap.getGateList().add(mCurrentGate);
                        mCurrentGate.setPosition(point);
                        mCurrentGate.setWidth(dpScale);
                        return true;

                    case MotionEvent.ACTION_MOVE:
                        Log.i(TAG, "ACTION_MOVE");
                        mCurrentGate.setPosition(point);
                        view.invalidate();
                        return true;

                    case MotionEvent.ACTION_UP:
                        Log.i(TAG, "ACTION_UP");
                        mCurrentGate.setPosition(point);
                        view.invalidate();
                        mCurrentGate = null;
                        return true;

                    default:
                        return false;
                }
            }
        };
    }


    @Override
    public List<?> getObjectList() {
        return mMap.getGateList();
    }
}

package com.katsuro.alexey.forscand.buildModes;

import android.content.Context;
import android.view.View;

import com.katsuro.alexey.forscand.model.Map;

import java.util.List;

/**
 * Created by alexey on 4/25/18.
 */

public abstract class BuildMode {

    protected Map mMap;
    protected Context mContext;
    protected String mTitle;

    public BuildMode(Context context, Map map) {
        mMap = map;
        mContext = context;
    }

    public Map getMap() {
        return mMap;
    }

    public void setMap(Map map) {
        mMap = map;
    }

    public String getTitle(){
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public abstract View.OnTouchListener OnTouchListener();

    public abstract List<?> getObjectList();

    protected int roundToScale(float value, int scale, boolean sumRemainder) {
        value = Math.round(value);
        int r = (int) value % scale;
        int result = (int) (value - r);
        if (sumRemainder) {
            result += r >= scale / 2 ? scale : 0;
        }
        return result;

    }
}


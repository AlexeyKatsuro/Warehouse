package com.katsuro.alexey.forscand;

import android.graphics.Path;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.katsuro.alexey.forscand.model.Wall;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexey on 4/19/18.
 */

public class BuilderFragment extends Fragment {
    public static final String TAG = BuilderFragment.class.getSimpleName();
    private boolean isWallMode;

    WarehouseView mWarehouseView;
    private Wall mCurrent;
    private int scale =100;
    private TouchListener mOnTouchListener;

    // private List<Path> mWallList = new ArrayList<>();
    public static BuilderFragment newInstance() {


        BuilderFragment fragment = new BuilderFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_warehouse,container,false);
        mWarehouseView = view.findViewById(R.id.warehouse_view);
        mWarehouseView.setScale(scale);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.builder_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.wall:
                int drawable;
                isWallMode = !isWallMode;
                if(isWallMode){
                    drawable = R.drawable.ic_wall_light;
                    mOnTouchListener = new TouchListener();
                    mWarehouseView.setOnTouchListener(mOnTouchListener);
                } else {
                    drawable = R.drawable.ic_action_wall;
                    mWarehouseView.setOnTouchListener(null);
                }

                item.setIcon(drawable);


                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
    private int roundToScale(float value, int scale){
        value = Math.round(value);
        int r = (int) value%scale;
        int result = (int) (value - r);
        result += r>=scale/2 ? scale : 0 ;
        return result;

    }

    public class TouchListener implements View.OnTouchListener{
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            WarehouseView view = (WarehouseView) v;
            Log.i(TAG,String.format("x: %f, y: %f",event.getX(),event.getY()));
            float x = roundToScale(event.getX(),scale);
            float y = roundToScale(event.getY(),scale);

            Log.i(TAG,"OnTouch");
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    Log.i(TAG, "ACTION_DOWN");
                    mCurrent = new Wall();
                    view.getWallList().add(mCurrent);
                    mCurrent.setStart(x,y);

                    return true;

                case MotionEvent.ACTION_MOVE:
                    Log.i(TAG,"ACTION_MOVE");
                    mCurrent.setStop(x,y);
                    view.invalidate();
                    return true;

                case MotionEvent.ACTION_UP:
                    Log.i(TAG, "ACTION_UP");
                    mCurrent.setStop(x,y);
                    view.invalidate();
                    mCurrent = null;
                    return true;

                default:
                    return false;
            }
        }
    }
}

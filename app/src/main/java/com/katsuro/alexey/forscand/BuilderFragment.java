package com.katsuro.alexey.forscand;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.katsuro.alexey.forscand.model.Box;
import com.katsuro.alexey.forscand.model.Gate;
import com.katsuro.alexey.forscand.model.Map;
import com.katsuro.alexey.forscand.model.Wall;

import java.util.List;

/**
 * Created by alexey on 4/19/18.
 */

public class BuilderFragment extends Fragment {
    public static final String TAG = BuilderFragment.class.getSimpleName();
    public static final String EXTRA_MAP = "extra_map";
    private GestureDetectorCompat mDetectorCompat;

    enum Mode{wall, box,gateInput, gateOutPut};

    private Mode mCurrentMode;


    Gson mGson = new Gson();
    private StorehouseView mStorehouseView;
    private Map mMap = new Map();
    private Wall mCurrentWall;
    private Box mCurrentBox;
    private Gate mCurrentGate;
    private int scale =50;
    private View.OnTouchListener mTouchListener;
    private FileWriterReader mFileWriterReader;
    private String mFileName = "MapJSON.txt";

    public static BuilderFragment newInstance() {

        BuilderFragment fragment = new BuilderFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG,"onCreate");
        setHasOptionsMenu(true);
        mFileWriterReader = new FileWriterReader(getActivity());

//        String stringJSON = mFileWriterReader.readFromFile(mFileName);
//        if(stringJSON!=null) {
//            mMap = mGson.fromJson(stringJSON, Map.class);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG,"onCreateView");
        View view = inflater.inflate(R.layout.fragment_warehouse,container,false);
        mStorehouseView = view.findViewById(R.id.warehouse_view);
        mStorehouseView.setScale(scale);
        mStorehouseView.setMap(mMap);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        Log.i(TAG,"onCreateOptionsMenu");
        inflater.inflate(R.menu.builder_menu,menu);

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(TAG, "onOptionsItemSelected");
        boolean isChecked;
        switch (item.getItemId()) {
            case R.id.wall_mode:
                Log.i(TAG, getString(R.string.wall_mode));
                changeMode(Mode.wall);
                item.setChecked(true);
                setSubtitle(getString(R.string.wall_mode));
                return true;
            case R.id.box_mode:
                Log.i(TAG, getString(R.string.box_mode));
                changeMode(Mode.box);
                item.setChecked(true);
                setSubtitle(getString(R.string.box_mode));
                return true;
            case R.id.gate_input:
                Log.i(TAG, getString(R.string.gate_input));
                item.setChecked(true);
                changeMode(Mode.box);
                changeMode(Mode.gateInput);
                setSubtitle(getString(R.string.gate_input));
                return true;
            case R.id.gate_output:
                Log.i(TAG, getString(R.string.gate_output));
                item.setChecked(true);
                changeMode(Mode.gateOutPut);
                setSubtitle(getString(R.string.gate_output));
                return true;

            case R.id.undo :
                Log.i(TAG, getString(R.string.undo));
                onUndoClick(mCurrentMode);
                return true;

            case R.id.save:
                Log.i(TAG, getString(R.string.save));
                String mapGsonString = mGson.toJson(mMap);
                Log.i(TAG, String.format("JSON: %s", mapGsonString));

                sendResultGson(mapGsonString);
                mFileWriterReader.writeToFile(mapGsonString,mFileName);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void onUndoClick(Mode currentMode) {
        
        if(mCurrentMode==Mode.wall){
           List<Wall> list = mMap.getWallList();
           int size = list.size();
           if(size>0) {
               list.remove(size-1);
           }
        }
        if (mCurrentMode==Mode.box){
            List<Box> list = mMap.getBoxList();
            int size = list.size();
            if(size>0) {
                list.remove(size-1);
            }
        }

        if (mCurrentMode==Mode.gateInput || mCurrentMode==Mode.gateOutPut){
            List<Gate> list = mMap.getGateList();
            int size = list.size();
            if(size>0) {
                list.remove(size-1);
            }
        }
        mStorehouseView.invalidate();
    }

    private void setSubtitle(String subtitle) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle(subtitle);
    }

    private void changeMode(Mode next){

        mCurrentMode = next;

        mTouchListener = null;
        if(mCurrentMode==Mode.wall){
            mTouchListener = new WallTouchListener();
        }
        if (mCurrentMode==Mode.box){
            mTouchListener = new BoxTouchListener();
        }

        if(mCurrentMode==Mode.gateInput){
            mTouchListener = new GateTouchListener(Gate.Type.input);
        }

        if(mCurrentMode==Mode.gateOutPut){
            mTouchListener = new GateTouchListener(Gate.Type.output);
        }

        mStorehouseView.setDetector(mDetectorCompat);
        mStorehouseView.setOnTouchListener(mTouchListener);
    }
    private int roundToScale(float value, int scale, boolean sumRemainder){
        value = Math.round(value);
        int r = (int) value%scale;
        int result = (int) (value - r);
        if(sumRemainder) {
            result += r >= scale / 2 ? scale : 0;
        }
        return result;

    }

    private void sendResultGson(String gsonString){
        Intent resultIntent = new Intent();
        resultIntent.putExtra(EXTRA_MAP, gsonString);
        getActivity().setResult(Activity.RESULT_OK, resultIntent);
        getActivity().finish();
    }


    public class WallTouchListener implements View.OnTouchListener{
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            Log.i(TAG,"OnTouch");
            StorehouseView view = (StorehouseView) v;
            Log.i(TAG,String.format("x: %f, y: %f",event.getX(),event.getY()));
            float x = roundToScale(event.getX(),scale,true);
            float y = roundToScale(event.getY(),scale,true);

            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    Log.i(TAG, "ACTION_DOWN");
                    mCurrentWall = new Wall();
                    mMap.getWallList().add(mCurrentWall);
                    mCurrentWall.setStart(x,y);
                    mCurrentWall.setStop(x,y);
                    return true;

                case MotionEvent.ACTION_MOVE:
                    Log.i(TAG,"ACTION_MOVE");
                    mCurrentWall.setStop(x,y);
                    view.invalidate();
                    return true;

                case MotionEvent.ACTION_UP:
                    Log.i(TAG, "ACTION_UP");
                    mCurrentWall.setStop(x,y);
                    view.invalidate();
                    mCurrentWall = null;
                    return true;

                default:
                    return false;
            }
        }
    }

    public class BoxTouchListener implements View.OnTouchListener{

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            Log.i(TAG,"OnTouch");
            StorehouseView view = (StorehouseView) v;
            Log.i(TAG,String.format("x: %f, y: %f",event.getX(),event.getY()));
            float x = roundToScale(event.getX(),scale,false);
            float y = roundToScale(event.getY(),scale,false);


            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    Log.i(TAG, "ACTION_DOWN");
                    mCurrentBox = new Box();
                    mCurrentBox.setWidth(scale);
                    mCurrentBox.setHeight(scale);
                    mCurrentBox.setPosition(x,y);
                    mMap.getBoxList().add(mCurrentBox);
                    view.invalidate();
                    return true;

                case MotionEvent.ACTION_MOVE:
                    Log.i(TAG,"ACTION_MOVE");
                    mCurrentBox.setPosition(x,y);
                    view.invalidate();
                    return true;

                case MotionEvent.ACTION_UP:
                    Log.i(TAG, "ACTION_UP");
                    mCurrentBox.setPosition(x,y);
                    view.invalidate();;
                    mCurrentBox = null;
                    return true;

                default:
                    return false;
            }
        }
    }

    public class GateTouchListener implements View.OnTouchListener{
        private Gate.Type mType;

        public GateTouchListener(Gate.Type type) {
            mType = type;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            Log.i(TAG,"OnTouch");
            StorehouseView view = (StorehouseView) v;
            Log.i(TAG,String.format("x: %f, y: %f",event.getX(),event.getY()));
            float x = roundToScale(event.getX(),scale,true);
            float y = roundToScale(event.getY(),scale,true);

            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    Log.i(TAG, "ACTION_DOWN");
                    mCurrentGate = new Gate(mType);
                    mMap.getGateList().add(mCurrentGate);
                    mCurrentGate.setStart(x,y);
                    mCurrentGate.setStop(x,y);
                    return true;

                case MotionEvent.ACTION_MOVE:
                    Log.i(TAG,"ACTION_MOVE");
                    mCurrentGate.setStop(x,y);
                    view.invalidate();
                    return true;

                case MotionEvent.ACTION_UP:
                    Log.i(TAG, "ACTION_UP");
                    mCurrentGate.setStop(x,y);
                    view.invalidate();
                    mCurrentGate = null;
                    return true;

                default:
                    return false;
            }
        }
    }

    private class WallModeGestureDetector extends GestureDetector.SimpleOnGestureListener
        implements GestureDetector.OnDoubleTapListener
    {

        @Override
        public void onLongPress(MotionEvent event) {
            Log.d(TAG, "onLongPress: " + event.toString());
        }

        @Override
        public void onShowPress(MotionEvent event) {
            Log.d(TAG, "onShowPress: " + event.toString());
        }

        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            Log.d(TAG, "onSingleTapUp: " + event.toString());
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent event) {
            Log.d(TAG, "onDoubleTap: " + event.toString());
            return true;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent event) {
            Log.d(TAG, "onDoubleTapEvent: " + event.toString());
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent event) {
            Log.d(TAG, "onSingleTapConfirmed: " + event.toString());
            return true;
        }
    }
}

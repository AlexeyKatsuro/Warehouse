package com.katsuro.alexey.forscand.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.katsuro.alexey.forscand.buildModes.BoxBuildMode;
import com.katsuro.alexey.forscand.buildModes.BuildMode;
import com.katsuro.alexey.forscand.FileWriterReader;
import com.katsuro.alexey.forscand.R;
import com.katsuro.alexey.forscand.StorehouseView;
import com.katsuro.alexey.forscand.buildModes.GateBuildMode;
import com.katsuro.alexey.forscand.buildModes.WallBuildMode;
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

    enum Mode {wall, box, gateInput, gateOutPut}

    ;



    Gson mGson = new Gson();
    private StorehouseView mStorehouseView;
    private Map mMap = new Map();
    private Wall mCurrentWall;
    private Box mCurrentBox;
    private Gate mCurrentGate;

    private BuildMode mBuildMode;
    private FileWriterReader mFileWriterReader;
    private String mFileDefaultMapName = "MapDefault.txt";

    public static BuilderFragment newInstance() {

        BuilderFragment fragment = new BuilderFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        setHasOptionsMenu(true);
        mFileWriterReader = new FileWriterReader(getActivity());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_warehouse, container, false);
        mStorehouseView = view.findViewById(R.id.warehouse_view);
        mStorehouseView.setMap(mMap);
        changeMode(new WallBuildMode(getActivity(),mMap));
        updateUI();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        Log.i(TAG, "onCreateOptionsMenu");
        inflater.inflate(R.menu.builder_menu, menu);
    }

    protected void updateUI(){
        setSubtitle(mBuildMode.getTitle());
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected");
        boolean isChecked;
        switch (item.getItemId()) {
            case R.id.wall_mode:
                Log.d(TAG, getString(R.string.wall_mode));
                changeMode(new WallBuildMode(getActivity(), mMap));
                item.setChecked(true);
                updateUI();
                return true;
            case R.id.box_mode:
                Log.d(TAG, getString(R.string.box_mode));
                changeMode(new BoxBuildMode(getActivity(), mMap));
                item.setChecked(true);
                updateUI();
                return true;
            case R.id.gate_input:
                Log.d(TAG, getString(R.string.gate_mode));
                item.setChecked(true);
                changeMode(new GateBuildMode(getActivity(), mMap));
                updateUI();
                return true;


            case R.id.undo:
                Log.i(TAG, getString(R.string.undo));
                onUndoClick(mBuildMode);
                return true;

            case R.id.save:
                Log.i(TAG, getString(R.string.save));
                String mapGsonString = mGson.toJson(mMap);
                Log.i(TAG, String.format("JSON: %s", mapGsonString));
                sendResultGson(mapGsonString);
                mFileWriterReader.writeToFile(mapGsonString, mFileDefaultMapName);
                return true;
            case R.id.load:
                Log.i(TAG, getString(R.string.load));

                mMap = loadDefaultMap();
                mStorehouseView.setMap(mMap);
                mBuildMode.setMap(mMap);
                mStorehouseView.invalidate();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    protected Map loadDefaultMap() {
        Log.i(TAG,"loadDefaultMap");
        String stringJSON = mFileWriterReader.readFromFile(mFileDefaultMapName);
        Log.i(TAG,"loaded: " + stringJSON);
        Map map =null;
        if (stringJSON != null) {
            map = new Gson().fromJson(stringJSON, Map.class);
        }

        if(map==null){
            map= new Map();
        }
        return map;
    }

    private void onUndoClick(BuildMode buildMode) {


        List<?> list = buildMode.getObjectList();
        int size = list.size();
        if (size > 0) {
            list.remove(size - 1);
        }

        mStorehouseView.invalidate();
    }

    private void setSubtitle(String subtitle) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle(subtitle);
    }

    private void changeMode(BuildMode buildMode) {

        mBuildMode = buildMode;
        mStorehouseView.setOnTouchListener(mBuildMode.OnTouchListener());
    }



    private void sendResultGson(String gsonString) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(EXTRA_MAP, gsonString);
        getActivity().setResult(Activity.RESULT_OK, resultIntent);
        getActivity().finish();
    }

}

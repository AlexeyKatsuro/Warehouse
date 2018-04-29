package com.katsuro.alexey.forscand.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.katsuro.alexey.forscand.activities.BuilderActivity;
import com.katsuro.alexey.forscand.FileWriterReader;
import com.katsuro.alexey.forscand.R;
import com.katsuro.alexey.forscand.StorehouseView;
import com.katsuro.alexey.forscand.commands.Command;
import com.katsuro.alexey.forscand.commands.MoveAlongTrail;
import com.katsuro.alexey.forscand.commands.MoveCommand;
import com.katsuro.alexey.forscand.model.Gate;
import com.katsuro.alexey.forscand.model.Map;
import com.katsuro.alexey.forscand.model.Robot;
import com.katsuro.alexey.forscand.model.Trail;
import com.katsuro.alexey.forscand.model.Wall;

/**
 * Created by alexey on 4/19/18.
 */

public class MainFragment extends Fragment  {

    public static final String TAG = MainFragment.class.getSimpleName();
    private static final int REQUEST_MAP = 0;

    private StorehouseView mStorehouseView;

    private Map mMap;
    private Gson mGson = new Gson();
    private FileWriterReader mFileWriterReader;
    private String mDefaultMapFileName = "MapDefault.txt";

    public interface callBacks{

    }


    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mFileWriterReader = new FileWriterReader(getActivity());

        String gsonString = mFileWriterReader.readFromFile(mDefaultMapFileName);
        if(gsonString!=null) {
            mMap = mGson.fromJson(gsonString, Map.class);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_warehouse,container,false);
        mStorehouseView = view.findViewById(R.id.warehouse_view);
        mStorehouseView.setMap(mMap);

        if(mMap.getGateList().size()>=2) {
            Gate gateStart = mMap.getGateList().get(0);
            Gate gateEnd = mMap.getGateList().get(1);

            Robot robot= new Robot(gateStart, mMap);
            mStorehouseView.getRobotList().clear();
            mStorehouseView.getRobotList().add(robot);
        }

        return view;
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(TAG,"onOptionsItemSelected");
        switch (item.getItemId()){

            case R.id.start:
                Log.i(TAG,getString(R.string.start));
                start();
                return true;
            case R.id.builder:
                Log.i(TAG,getString(R.string.builder));
                Intent intent = new Intent(getActivity(),BuilderActivity.class);
                startActivityForResult(intent,REQUEST_MAP);
                return true;
            case R.id.settings:
                Log.i(TAG,getString(R.string.settings));

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private synchronized void updateUI(){
        mStorehouseView.invalidate();
    }

    private void start() {
        Gate gateStart = mMap.getGateList().get(0);
        Gate gateEnd = mMap.getGateList().get(1);
        Robot robot = mStorehouseView.getRobotList().get(0);
        Trail trail = getTrail(gateStart.getPosition(), gateEnd.getPosition());
        Command command = new MoveAlongTrail(robot, trail, mStorehouseView, new Handler());
        robot.addCommand(command);


    }

    private Trail getTrail(PointF start, PointF end) {
        Trail trail = new Trail(start);
        trail.lineTo(mMap.getBoxList().get(0).getPosition());
        trail.lineTo(end);
        return trail;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG,"onActivityResult");

        if(resultCode != Activity.RESULT_OK){
            Log.i(TAG,"RESULT_NOT_OK");
            return;
        }

        if(requestCode == REQUEST_MAP){
            Log.i(TAG,"REQUEST_MAP");
            Bundle bundle = data.getExtras();
            String gsonString = bundle.getString(BuilderFragment.EXTRA_MAP,null);
            mMap = mGson.fromJson(gsonString,Map.class);
            mStorehouseView.setMap(mMap);
            mStorehouseView.invalidate();
        }
    }

    public boolean isTrailPartsIntersected(Trail trail, Wall wall){
        for (int i=0;i<trail.length()-1;i++){
            if(isLinePartsIntersected(trail.get(i),trail.get(i+1),wall.getStart(),wall.getStop())){
                return true;
            }
        }
        return false;

    }

    public boolean isLinePartsIntersected(PointF a, PointF b, PointF c, PointF d)
    {
        double common = (b.x - a.x)*(d.y - c.y) - (b.y- a.y)*(d.x - c.x);
        if (common == 0) return false;
        double rH = (a.y - c.y)*(d.x - c.x) - (a.x - c.x)*(d.y - c.y);
        double sH = (a.y - c.y)*(b.x - a.x) - (a.x - c.x)*(b.y - a.y);

        double r = rH / common;
        double s = sH / common;

        if (r >= 0 && r <= 1 && s >= 0 && s <= 1)
            return true;
        else
            return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        for (Robot robot :mStorehouseView.getRobotList() ){
            robot.quit();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        for (Robot robot :mStorehouseView.getRobotList() ){
            robot.claerQueue();
        }
    }
}

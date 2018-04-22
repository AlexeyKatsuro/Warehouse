package com.katsuro.alexey.forscand;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import com.katsuro.alexey.forscand.model.Map;

/**
 * Created by alexey on 4/19/18.
 */

public class MainFragment extends Fragment  {

    public static final String TAG = MainFragment.class.getSimpleName();
    private static final int REQUEST_MAP = 0;

    private StorehouseView mStorehouseView;

    private Map mMap = new Map();
    private Gson mGson = new Gson();
    private FileWriterReader mFileWriterReader;
    private String mFileName = "MapJSON.txt";

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
//        String stringJSON = mFileWriterReader.readFromFile(mFileName);
//        if(stringJSON!=null) {
//            mMap = mGson.fromJson(stringJSON, Map.class);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_warehouse,container,false);
        mStorehouseView = view.findViewById(R.id.warehouse_view);
        mStorehouseView.setMap(mMap);
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
}

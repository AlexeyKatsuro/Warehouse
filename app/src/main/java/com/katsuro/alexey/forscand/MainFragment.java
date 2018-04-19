package com.katsuro.alexey.forscand;

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

/**
 * Created by alexey on 4/19/18.
 */

public class MainFragment extends Fragment  {

    public static final String TAG = MainFragment.class.getSimpleName();

    private WarehouseView mWarehouseView;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_warehouse,container,false);
        mWarehouseView = view.findViewById(R.id.warehouse_view);

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
                return true;
            case R.id.settings:
                Log.i(TAG,getString(R.string.settings));

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}

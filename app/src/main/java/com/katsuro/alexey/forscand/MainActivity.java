package com.katsuro.alexey.forscand;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends SingleFragmentActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    public Fragment createFragment() {
        return MainFragment.newInstance();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(TAG,"onOptionsItemSelected");
        switch (item.getItemId()){
            case R.id.builder:
                Log.i(TAG,getString(R.string.builder));
                replaceFragment(BuilderFragment.newInstance());
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

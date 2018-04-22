package com.katsuro.alexey.forscand.activities;

import android.support.v4.app.Fragment;

import com.katsuro.alexey.forscand.fragments.MainFragment;

public class MainActivity extends SingleFragmentActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    public Fragment createFragment() {
        return MainFragment.newInstance();
    }

}

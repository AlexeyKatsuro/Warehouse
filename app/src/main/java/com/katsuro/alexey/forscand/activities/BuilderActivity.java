package com.katsuro.alexey.forscand.activities;

import android.support.v4.app.Fragment;

import com.katsuro.alexey.forscand.fragments.BuilderFragment;

public class BuilderActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        return BuilderFragment.newInstance();
    }
}

package com.katsuro.alexey.forscand;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class BuilderActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        return BuilderFragment.newInstance();
    }
}

package com.takumalee.ormcomparison.activity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.takumalee.ormcomparison.config.ATLConfig;
import com.takumalee.ormcomparison.fragment.base.ActivityBaseFragment;


/**
 * Created by TakumaLee on 15/3/16.
 */
public class BaseOtherActivity extends AdsActivity {

    protected Tracker tracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GoogleAnalytics.getInstance(this).setLocalDispatchPeriod(1);
        tracker = GoogleAnalytics.getInstance(this).newTracker(ATLConfig.GA_PROPERTY_ID);
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleAnalytics.getInstance(this).reportActivityStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        GoogleAnalytics.getInstance(this).reportActivityStop(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void setupViewComponent(int resId, ActivityBaseFragment fragment) {

    }
}

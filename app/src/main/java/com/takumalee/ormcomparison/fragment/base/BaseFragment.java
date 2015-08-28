package com.takumalee.ormcomparison.fragment.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.takumalee.ormcomparison.config.ATLConfig;

/**
 * Created by TakumaLee on 15/3/27.
 */
public class BaseFragment extends Fragment {

    protected Activity activity;
    protected Tracker tracker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GoogleAnalytics.getInstance(getActivity()).setLocalDispatchPeriod(1);
        tracker = GoogleAnalytics.getInstance(getActivity()).newTracker(ATLConfig.GA_PROPERTY_ID);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    protected <T extends View> T findViewTById(View parents,int id){
        T view = (T) parents.findViewById(id);
        return view;
    }

}

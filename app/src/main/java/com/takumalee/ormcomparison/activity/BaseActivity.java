package com.takumalee.ormcomparison.activity;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.takumalee.ormcomparison.fragment.base.ActivityBaseFragment;


/**
 * Created by USER on 2015/1/25.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        findView();
        setupViewComponent();
    }

    protected abstract void findView();

    protected abstract void setupViewComponent();

    protected abstract void setupViewComponent(int resId, ActivityBaseFragment fragment);

    protected <T extends View> T findViewTById(int id){
        T view = (T) super.findViewById(id);
        return view;
    }

    protected <T extends View> T findViewTById(View parents,int id){
        T view = (T) parents.findViewById(id);
        return view;
    }
}

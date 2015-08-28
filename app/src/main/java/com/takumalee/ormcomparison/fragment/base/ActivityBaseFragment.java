package com.takumalee.ormcomparison.fragment.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.takumalee.ormcomparison.fragment.listeners.OnTransitionAnimationListener;


/**
 * Created by USER on 2015/3/5.
 */
public abstract class ActivityBaseFragment extends BaseFragment implements OnTransitionAnimationListener {
    protected ActionBarFunctionListener actionBarFunctionListener;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        setHasOptionsMenu(true);
        initActionBar();
        setCurrentFragment(this);
    }
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    protected void setActionBarTitle(String title) {
        if (null != actionBarFunctionListener) {
            actionBarFunctionListener.setActionBarTitle(title);
        }
    }

    protected void setActionBarMenuState(MaterialMenuDrawable.IconState iconState) {
        if (null != actionBarFunctionListener) {
            actionBarFunctionListener.setActionBarMenuState(iconState);
        }
    }

    protected void setCurrentFragment(ActivityBaseFragment currentFragment) {
        if (null != actionBarFunctionListener) {
            actionBarFunctionListener.setCurrentFragment(currentFragment);
        }
    }

    public void setActionBarFunctionListener(ActionBarFunctionListener actionBarFunctionListener) {
        this.actionBarFunctionListener = actionBarFunctionListener;
    }

    public void startNewFragment(ActivityBaseFragment fragment) {
        if (null != actionBarFunctionListener) {
            actionBarFunctionListener.startNewFragment(fragment);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }

    public boolean onBackPressed() {
        View thisView = getView();
        return true;
    }

    @Override
    public void onDestroy() {
        actionBarFunctionListener = null;
        super.onDestroy();
    }

    protected abstract void initActionBar();

    public interface ActionBarFunctionListener {
        public void startNewFragment(ActivityBaseFragment fragment);

        public void setActionBarTitle(String title);

        public void setActionBarTitleVisibility(int visibility);

        public void setActionBarMenuState(MaterialMenuDrawable.IconState actionBarMenuState);

        public void setActionBarAlpha(float alpha);

        public void setActionBarTitleAlpha(float alpha);

        public void setCurrentFragment(ActivityBaseFragment currentFragment);

    }

}

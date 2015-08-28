package com.takumalee.ormcomparison.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.takumalee.ormcomparison.fragment.base.ActivityBaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TakumaLee on 15/6/26.
 */
public class FragmentControlActivity extends BaseActivity implements ActivityBaseFragment.ActionBarFunctionListener {

    public interface OnImplementListener {
        void startNewFragmentListener();
        void endFragment();
    }

    private int resId;
    protected ActivityBaseFragment currentFragment;
    protected ActivityBaseFragment parentFragment;
    protected OnImplementListener onImplementListener;

    private List<Fragment> fragmentBackStack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentBackStack = new ArrayList<>();
    }

    @Override
    public void onBackPressed() {
        if (fragmentBackStack.size() > 0) { //getSupportFragmentManager().getBackStackEntryCount()
            Fragment popFragment = fragmentBackStack.get(fragmentBackStack.size() - 1);
            fragmentBackStack.remove(popFragment);
            ((ActivityBaseFragment) popFragment).setActionBarFunctionListener(this);
            getSupportFragmentManager().beginTransaction().replace(resId, popFragment, popFragment.getClass().getName()).commit();
            if (null != onImplementListener) {
                onImplementListener.endFragment();
            }
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void findView() {

    }

    @Override
    protected void setupViewComponent() {

    }

    @Override
    protected void setupViewComponent(int resId, ActivityBaseFragment fragment) {
        this.resId = resId;
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (null == fragments || fragments.size() == 0) {
            fragment.setActionBarFunctionListener(this);
            getSupportFragmentManager().beginTransaction().add(resId, fragment, fragment.getClass().getName()).commit();
            parentFragment = fragment;
            currentFragment = fragment;
        }
    }

    public void setOnImplementListener(OnImplementListener onImplementListener) {
        this.onImplementListener = onImplementListener;
    }

    @Override
    public void startNewFragment(ActivityBaseFragment fragment) {
        addFragmentBackStack();
        currentFragment = fragment;
        fragment.setActionBarFunctionListener(this);
        try {
            getSupportFragmentManager().beginTransaction().replace(resId, fragment, fragment.getClass().getName()).commit(); //addToBackStack(fragment.getClass().getName())
            if (null != onImplementListener) {
                onImplementListener.startNewFragmentListener();
            }
        } catch (Exception e) {
        }
    }

    private void addFragmentBackStack() {
        fragmentBackStack.add(parentFragment);
    }

    @Override
    public void setActionBarTitle(String title) {

    }

    @Override
    public void setActionBarTitleVisibility(int visibility) {

    }

    @Override
    public void setActionBarMenuState(MaterialMenuDrawable.IconState actionBarMenuState) {

    }

    @Override
    public void setActionBarAlpha(float alpha) {

    }

    @Override
    public void setActionBarTitleAlpha(float alpha) {

    }

    @Override
    public void setCurrentFragment(ActivityBaseFragment currentFragment) {
        this.currentFragment = currentFragment;
    }
}

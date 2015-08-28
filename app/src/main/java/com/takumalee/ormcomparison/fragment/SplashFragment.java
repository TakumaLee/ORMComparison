package com.takumalee.ormcomparison.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.takumalee.ormcomparison.fragment.base.FullScreenDialogFragment;
import com.takumalee.ormcomparison.utils.WindowSizeUtils;


/**
 * Created by TakumaLee on 15/2/22.
 */
public class SplashFragment extends FullScreenDialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ImageView imageView = new ImageView(getActivity());
        imageView.setFitsSystemWindows(true);
//        imageView.setBackgroundResource(R.drawable.mmm_background);
        imageView.setMinimumHeight(WindowSizeUtils.getWindowHeightSize(getActivity()));
        imageView.setMinimumWidth(WindowSizeUtils.getWindowWidthSize(getActivity()));
        return imageView;
    }
}

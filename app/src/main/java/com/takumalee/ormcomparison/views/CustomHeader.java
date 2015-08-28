package com.takumalee.ormcomparison.views;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.pkmmte.view.CircularImageView;
import com.takumalee.ormcomparison.R;
import com.takumalee.ormcomparison.config.BaseConfig;

/**
 * Created by TakumaLee on 15/6/29.
 */
public class CustomHeader extends LinearLayout {

    private CircularImageView cpi;

    public CustomHeader(Context context) {
        this(context, null);
    }

    public CustomHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        setBackgroundResource(R.mipmap.wallpaper);
        float density = getContext().getResources().getDisplayMetrics().density;
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (56 * density));
        params.gravity = Gravity.CENTER | Gravity.LEFT;
        int statusBarHeight = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) ? BaseConfig.dpToPx(32) : 0;
        params.setMargins(16, 16 + statusBarHeight, 16, 16);
        cpi = new CircularImageView(getContext());
        cpi.setImageResource(R.mipmap.anonymous);
        addView(cpi, params);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getResources().getDisplayMetrics().heightPixels / 3));
    }

    public CircularImageView getCpImageView() {
        return cpi;
    }
}

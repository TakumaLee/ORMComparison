package com.takumalee.ormcomparison.views.animations;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;

/**
 * Created by TakumaLee on 15/6/6.
 */
public class MaterialAnimation {
    public static void ScaleViewSizeAnimator(final View view) {
        ScaleAnimation animator = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animator.setDuration(500);
        animator.setInterpolator(new BounceInterpolator());
        view.setAnimation(animator);
        animator.start();
    }
}

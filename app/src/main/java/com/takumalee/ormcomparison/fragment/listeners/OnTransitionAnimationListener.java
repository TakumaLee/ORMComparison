package com.takumalee.ormcomparison.fragment.listeners;

import android.animation.Animator;

/**
 * Created by TakumaLee on 15/6/26.
 */
public interface OnTransitionAnimationListener {
    public void onAnimationStart();
    public void onAnimationEnd(Animator animation);
//    public void onViewAnimationStart(CanvasView from,View to,Animator animation);
//    public void onViewAnimationEnd(CanvasView from,View to,Animator animation);
//    public void onViewAnimationCancel(CanvasView view,Animator animation);
//    public void onViewAnimationRepeat(CanvasView view,Animator animation);
}


package com.github.mikephil.charting.animation;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;

public class ChartAnimator {
    private AnimatorUpdateListener mListener;

    public ChartAnimator() {

    }

    public ChartAnimator(AnimatorUpdateListener listener) {
        mListener = listener;
    }
    protected float mPhaseY = 1f;
    protected float mPhaseX = 1f;

    public void animateXY(int durationMillisX, int durationMillisY, EasingFunction easingX,
            EasingFunction easingY) {
        if (android.os.Build.VERSION.SDK_INT < 11)
            return;

        ObjectAnimator animatorY = ObjectAnimator.ofFloat(this, "phaseY", 0f, 1f);
        animatorY.setInterpolator(easingY);
        animatorY.setDuration(
                durationMillisY);
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(this, "phaseX", 0f, 1f);
        animatorX.setInterpolator(easingX);
        animatorX.setDuration(
                durationMillisX);
        if (durationMillisX > durationMillisY) {
            animatorX.addUpdateListener(mListener);
        } else {
            animatorY.addUpdateListener(mListener);
        }
        animatorX.start();
        animatorY.start();
    }

    public void animateX(int durationMillis, EasingFunction easing) {

        if (android.os.Build.VERSION.SDK_INT < 11)
            return;

        ObjectAnimator animatorX = ObjectAnimator.ofFloat(this, "phaseX", 0f, 1f);
        animatorX.setInterpolator(easing);
        animatorX.setDuration(durationMillis);
        animatorX.addUpdateListener(mListener);
        animatorX.start();
    }

    public void animateY(int durationMillis, EasingFunction easing) {

        if (android.os.Build.VERSION.SDK_INT < 11)
            return;

        ObjectAnimator animatorY = ObjectAnimator.ofFloat(this, "phaseY", 0f, 1f);
        animatorY.setInterpolator(easing);
        animatorY.setDuration(durationMillis);
        animatorY.addUpdateListener(mListener);
        animatorY.start();
    }

    public void animateXY(int durationMillisX, int durationMillisY, Easing.EasingOption easingX,
            Easing.EasingOption easingY) {

        if (android.os.Build.VERSION.SDK_INT < 11)
            return;

        ObjectAnimator animatorY = ObjectAnimator.ofFloat(this, "phaseY", 0f, 1f);
        animatorY.setInterpolator(Easing.getEasingFunctionFromOption(easingY));
        animatorY.setDuration(
                durationMillisY);
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(this, "phaseX", 0f, 1f);
        animatorX.setInterpolator(Easing.getEasingFunctionFromOption(easingX));
        animatorX.setDuration(
                durationMillisX);
        if (durationMillisX > durationMillisY) {
            animatorX.addUpdateListener(mListener);
        } else {
            animatorY.addUpdateListener(mListener);
        }

        animatorX.start();
        animatorY.start();
    }

    public void animateX(int durationMillis, Easing.EasingOption easing) {

        if (android.os.Build.VERSION.SDK_INT < 11)
            return;

        ObjectAnimator animatorX = ObjectAnimator.ofFloat(this, "phaseX", 0f, 1f);
        animatorX.setInterpolator(Easing.getEasingFunctionFromOption(easing));
        animatorX.setDuration(durationMillis);
        animatorX.addUpdateListener(mListener);
        animatorX.start();
    }

    public void animateY(int durationMillis, Easing.EasingOption easing) {

        if (android.os.Build.VERSION.SDK_INT < 11)
            return;

        ObjectAnimator animatorY = ObjectAnimator.ofFloat(this, "phaseY", 0f, 1f);
        animatorY.setInterpolator(Easing.getEasingFunctionFromOption(easing));
        animatorY.setDuration(durationMillis);
        animatorY.addUpdateListener(mListener);
        animatorY.start();
    }

    public void animateXY(int durationMillisX, int durationMillisY) {

        if (android.os.Build.VERSION.SDK_INT < 11)
            return;

        ObjectAnimator animatorY = ObjectAnimator.ofFloat(this, "phaseY", 0f, 1f);
        animatorY.setDuration(
                durationMillisY);
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(this, "phaseX", 0f, 1f);
        animatorX.setDuration(
                durationMillisX);
        if (durationMillisX > durationMillisY) {
            animatorX.addUpdateListener(mListener);
        } else {
            animatorY.addUpdateListener(mListener);
        }

        animatorX.start();
        animatorY.start();
    }
    public void animateX(int durationMillis) {

        if (android.os.Build.VERSION.SDK_INT < 11)
            return;

        ObjectAnimator animatorX = ObjectAnimator.ofFloat(this, "phaseX", 0f, 1f);
        animatorX.setDuration(durationMillis);
        animatorX.addUpdateListener(mListener);
        animatorX.start();
    }
    public void animateY(int durationMillis) {

        if (android.os.Build.VERSION.SDK_INT < 11)
            return;

        ObjectAnimator animatorY = ObjectAnimator.ofFloat(this, "phaseY", 0f, 1f);
        animatorY.setDuration(durationMillis);
        animatorY.addUpdateListener(mListener);
        animatorY.start();
    }
    public float getPhaseY() {
        return mPhaseY;
    }

    public void setPhaseY(float phase) {
        mPhaseY = phase;
    }

    public float getPhaseX() {
        return mPhaseX;
    }

    public void setPhaseX(float phase) {
        mPhaseX = phase;
    }
}

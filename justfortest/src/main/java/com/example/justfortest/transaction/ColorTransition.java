package com.example.justfortest.transaction;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.os.Build;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class ColorTransition extends Transition {

    private static final String COLOR_BACKGROUND = "ColorTransition:change_color:background";
    private int mStartColor;
    private int mEndColor;

    public ColorTransition(int mStartColor, int mEndColor) {
        this.mStartColor = mStartColor;
        this.mEndColor = mEndColor;
    }

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        transitionValues.values.put(COLOR_BACKGROUND, mStartColor);
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        transitionValues.values.put(COLOR_BACKGROUND, mEndColor);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        if (null == startValues || null == endValues) {
            return null;
        }

        final View view = endValues.view;

        int startColor = (int) startValues.values.get(COLOR_BACKGROUND);
        int endColor = (int) endValues.values.get(COLOR_BACKGROUND);

        if (startColor != endColor) {
            ValueAnimator animator = ValueAnimator.ofArgb(startColor, endColor);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    Object value = animation.getAnimatedValue();
                    if (null != value) {
                        view.setBackgroundColor((Integer) value);
                    }
                }
            });
            return animator;
        }
        return null;
    }
}
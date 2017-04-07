package com.gennext.newproject.util;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.transition.ChangeBounds;
import android.support.v4.app.Fragment;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.Visibility;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;

import com.gennext.newproject.R;

import java.util.ArrayList;

/**
 * Created by Abhijit on 22-Dec-16.
 */

public class AppAnimation {

    public static final int ZOOM_IN = 1, ZOOM_IN_BOUNCE = 2, FLIP_HORIZONTAL = 3, FLIP_VERTICAL = 4, HIDE_FLIP_HORIZONTAL = 5, EXPEND = 6, COLLAPSE = 7;

    public static void setDrawerAnimation(Context context, Fragment fragment) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide slideTransition = new Slide(Gravity.RIGHT);
            slideTransition.setDuration(context.getResources().getInteger(R.integer.anim_duration_medium));
            ChangeBounds changeBoundsTransition = new ChangeBounds();
            changeBoundsTransition.setDuration(context.getResources().getInteger(R.integer.anim_duration_medium));
            fragment.setEnterTransition(slideTransition);
            fragment.setAllowEnterTransitionOverlap(true);
            fragment.setAllowReturnTransitionOverlap(true);
            fragment.setSharedElementEnterTransition(changeBoundsTransition);
        }
    }

    public static void setDialogAnimation(Context context, Fragment fragment) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Fade fade = new Fade();
            fade.setStartDelay(50);
//            ChangeBounds changeBoundsTransition = new ChangeBounds();
//            changeBoundsTransition.setDuration(context.getResources().getInteger(R.integer.anim_duration_medium));
            fragment.setEnterTransition(fade);
            fragment.setAllowEnterTransitionOverlap(true);
            fragment.setAllowReturnTransitionOverlap(true);
//            fragment.setSharedElementEnterTransition(changeBoundsTransition);
        }
    }

    public static void setExplodeAnimation(Context context, Fragment fragment) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Explode enterTransition = new Explode();
            enterTransition.setDuration(context.getResources().getInteger(R.integer.anim_duration_long));
            fragment.setEnterTransition(enterTransition);
            fragment.setAllowEnterTransitionOverlap(true);
            fragment.setAllowReturnTransitionOverlap(true);
        }
    }

    public static ObjectAnimator setViewAnimation(final View view, int type) {

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(400);
        animatorSet.setInterpolator(new LinearInterpolator());
        ArrayList<Animator> animatorList = new ArrayList<>();
        ObjectAnimator scaleXAnimator, scaleYAnimator = null;
        switch (type) {
            case ZOOM_IN:
                scaleXAnimator = ObjectAnimator.ofFloat(view, "ScaleX", 0f, 1f, 1f);
                animatorList.add(scaleXAnimator);
                scaleYAnimator = ObjectAnimator.ofFloat(view, "ScaleY", 0f, 1f, 1f);
                animatorList.add(scaleYAnimator);
                animatorSet.playTogether(animatorList);
                break;
            case ZOOM_IN_BOUNCE:
                scaleXAnimator = ObjectAnimator.ofFloat(view, "ScaleX", 0f, 1.2f, 1f);
                animatorList.add(scaleXAnimator);
                scaleYAnimator = ObjectAnimator.ofFloat(view, "ScaleY", 0f, 1.2f, 1f);
                animatorList.add(scaleYAnimator);
                animatorSet.playTogether(animatorList);
                break;
            case FLIP_VERTICAL:
                scaleXAnimator = ObjectAnimator.ofFloat(view, "ScaleX", 0f, 1.2f, 1f);
                animatorSet.playTogether(scaleXAnimator);
                break;
            case FLIP_HORIZONTAL:
                scaleYAnimator = ObjectAnimator.ofFloat(view, "ScaleY", 0f, 1f, 1f);
                animatorSet.playTogether(scaleYAnimator);
//                scaleYAnimator = ObjectAnimator.ofFloat(view, "ScaleY", 0f, 1f, 1f);
//                animatorSet.playTogether(scaleYAnimator);
                break;
            case HIDE_FLIP_HORIZONTAL:
                scaleYAnimator = ObjectAnimator.ofFloat(view, "ScaleY", 1f, 0f, 0f);
                scaleYAnimator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                    }
                });
                animatorSet.playTogether(scaleYAnimator);
                break;
        }
        view.setVisibility(View.VISIBLE);

        animatorSet.start();
        return scaleYAnimator;
    }

    public static void setSlideAnimation(Context context, Fragment fragment, int gravity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide slideTransition = new Slide(gravity);
            slideTransition.setDuration(context.getResources().getInteger(R.integer.anim_duration_medium));
            ChangeBounds changeBoundsTransition = new ChangeBounds();
            changeBoundsTransition.setDuration(context.getResources().getInteger(R.integer.anim_duration_medium));
            fragment.setEnterTransition(slideTransition);
            fragment.setAllowEnterTransitionOverlap(true);
            fragment.setAllowReturnTransitionOverlap(true);
            fragment.setSharedElementEnterTransition(changeBoundsTransition);
        }
    }

    public static void setupWindowAnimations(Activity activity) {
        Visibility enterTransition = buildEnterTransition(activity);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && enterTransition != null) {
            activity.getWindow().setEnterTransition(enterTransition);
        }
    }

    public static void setupWindowExplodeAnimations(Activity activity) {
        Transition transition = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            transition = buildEnterExplodeTransition(activity);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && transition != null) {
            activity.getWindow().setEnterTransition(transition);
        }
    }

    private static Transition buildEnterExplodeTransition(Activity activity) {
        Explode enterTransition = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            enterTransition = new Explode();
            enterTransition.setDuration(activity.getResources().getInteger(R.integer.anim_duration_long));
        }
        return enterTransition;
    }

    private static Visibility buildEnterTransition(Context context) {
        Fade enterTransition = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            enterTransition = new Fade();
            enterTransition.setDuration(context.getResources().getInteger(R.integer.anim_duration_long));
            // This view will not be affected by enter transition animation
//            enterTransition.excludeTarget(R.id.square_red, true);
//            enterTransition.excludeTarget(nextScreenViewId, true);
        }
        return enterTransition;
    }

    private static Visibility buildReturnTransition(Context context) {
        Visibility enterTransition = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            enterTransition = new Slide();
            enterTransition.setDuration(context.getResources().getInteger(R.integer.anim_duration_long));
        }
        return enterTransition;
    }

    public void expandOrCollapse(final View v, int exp_or_colpse) {
        TranslateAnimation anim = null;
        if (exp_or_colpse == EXPEND) {
            anim = new TranslateAnimation(0.0f, 0.0f, -v.getHeight(), 0.0f);
            v.setVisibility(View.VISIBLE);
        } else if (exp_or_colpse == COLLAPSE) {
            anim = new TranslateAnimation(0.0f, 0.0f, 0.0f, -v.getHeight());
            Animation.AnimationListener collapselistener = new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    v.setVisibility(View.GONE);
                }
            };

            anim.setAnimationListener(collapselistener);
        }

        // To Collapse
        //

        anim.setDuration(300);
        anim.setInterpolator(new AccelerateInterpolator(0.5f));
        v.startAnimation(anim);
    }
}

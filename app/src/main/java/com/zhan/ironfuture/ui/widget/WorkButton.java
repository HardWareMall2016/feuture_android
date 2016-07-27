package com.zhan.ironfuture.ui.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Property;
import android.view.MotionEvent;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;

/**
 * Created by WuYue on 2016/5/31.
 */
public class WorkButton extends Button {

    private static final int FADE_EXTRA_DELAY = 50;

    private static final int DEFAULT_DURATION = 1000*3;

    private Point currentCoords = new Point();
    private final Rect bounds = new Rect();

    private AnimatorSet rippleAnimator;

    private float radius;
    private int rippleAlpha;
    private int rippleColor;
    private int rippleDuration;
    private int rippleFadeDuration;
    private Drawable rippleBackground;

    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private Path mPath=new Path();

    private Callback mCallback;

    public interface Callback{
        void onWorkFinished();
    }

    public WorkButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        rippleColor= Color.BLACK;
        rippleAlpha= (int) (255 *0.2f);
        rippleDuration=DEFAULT_DURATION;
        rippleFadeDuration=75;

        rippleBackground = new ColorDrawable(Color.TRANSPARENT);

        paint.setColor(rippleColor);
        paint.setAlpha(rippleAlpha);
    }

    public void setCallback(Callback callback){
        mCallback=callback;
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bounds.set(0, 0, w, h);
        rippleBackground.setBounds(bounds);
    }

    @Override
    public void draw(Canvas canvas) {

        super.draw(canvas);

        rippleBackground.draw(canvas);

        mPath.reset();

        canvas.save();

        mPath.addRoundRect(new RectF(0, 0, getWidth(), getHeight()), 90, 90, Path.Direction.CW);
        canvas.clipPath(mPath, Region.Op.INTERSECT);
        canvas.drawCircle(currentCoords.x, currentCoords.y, radius, paint);

        canvas.restore();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                currentCoords.set((int) event.getX(), (int) event.getY());
                startRipple();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                cancelAnimations();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
        }
        return true;
    }

    private void startRipple() {

        float endRadius = getEndRadius();

        cancelAnimations();

        rippleAnimator = new AnimatorSet();
        rippleAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                setRadius(0);
                setRippleAlpha(rippleAlpha);
                if(mCallback!=null&&animation.isRunning()){
                    mCallback.onWorkFinished();
                }
            }
        });

        ObjectAnimator ripple = ObjectAnimator.ofFloat(this, radiusProperty, radius, endRadius);
        ripple.setDuration(rippleDuration);
        ripple.setInterpolator(new DecelerateInterpolator());
        ObjectAnimator fade = ObjectAnimator.ofInt(this, circleAlphaProperty, rippleAlpha, 0);
        fade.setDuration(rippleFadeDuration);
        fade.setInterpolator(new AccelerateInterpolator());
        fade.setStartDelay(rippleDuration - rippleFadeDuration - FADE_EXTRA_DELAY);

        /*if (ripplePersistent) {
            rippleAnimator.play(ripple);
        } else*/
        if (getRadius() > endRadius) {
            fade.setStartDelay(0);
            rippleAnimator.play(fade);
        } else {
            rippleAnimator.playTogether(ripple, fade);
        }
        rippleAnimator.start();
    }

    private float getEndRadius() {
        final int width = getWidth();
        final int height = getHeight();

        final int halfWidth = width / 2;
        final int halfHeight = height / 2;

        final float radiusX = halfWidth > currentCoords.x ? width - currentCoords.x : currentCoords.x;
        final float radiusY = halfHeight > currentCoords.y ? height - currentCoords.y : currentCoords.y;

        return (float) Math.sqrt(Math.pow(radiusX, 2) + Math.pow(radiusY, 2)) * 1.2f;
    }

    private void cancelAnimations() {
        if (rippleAnimator != null) {
            rippleAnimator.cancel();
            rippleAnimator.removeAllListeners();
        }
        setRadius(0);
        setRippleAlpha(rippleAlpha);
    }

    /*
     * Animations
     */
    private Property<WorkButton, Float> radiusProperty
            = new Property<WorkButton, Float>(Float.class, "radius") {
        @Override
        public Float get(WorkButton object) {
            return object.getRadius();
        }

        @Override
        public void set(WorkButton object, Float value) {
            object.setRadius(value);
        }
    };

    private float getRadius() {
        return radius;
    }


    public void setRadius(float radius) {
        this.radius = radius;
        invalidate();
    }

    private Property<WorkButton, Integer> circleAlphaProperty
            = new Property<WorkButton, Integer>(Integer.class, "rippleAlpha") {
        @Override
        public Integer get(WorkButton object) {
            return object.getRippleAlpha();
        }

        @Override
        public void set(WorkButton object, Integer value) {
            object.setRippleAlpha(value);
        }
    };

    public int getRippleAlpha() {
        return paint.getAlpha();
    }

    public void setRippleAlpha(Integer rippleAlpha) {
        paint.setAlpha(rippleAlpha);
        invalidate();
    }
}

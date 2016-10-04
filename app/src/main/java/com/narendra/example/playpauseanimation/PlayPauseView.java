package com.narendra.example.playpauseanimation;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

public class PlayPauseView extends FrameLayout {

    private static final Property<PlayPauseView, Integer> COLOR =
            new Property<PlayPauseView, Integer>(Integer.class, "color") {
                @Override
                public Integer get(PlayPauseView v) {
                    return v.getColor();
                }

                @Override
                public void set(PlayPauseView v, Integer value) {
                    v.setColor(value);
                }
            };

    private static final long PLAY_PAUSE_ANIMATION_DURATION = 200;

    private final PlayPauseDrawable mDrawable;
    private final Paint mPaint = new Paint();
    private final int mPauseBackgroundColor;
    private final int mPlayBackgroundColor;
    private final Paint mPaintbroder = new Paint();
    private AnimatorSet mAnimatorSet;
    private int mBackgroundColor;
    private int mWidth;
    private int mHeight;

    public PlayPauseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        mBackgroundColor = getResources().getColor(R.color.white);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mDrawable = new PlayPauseDrawable(context);
        mDrawable.setCallback(this);

        mPauseBackgroundColor = getResources().getColor(R.color.white);
        mPlayBackgroundColor = getResources().getColor(R.color.blue);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // final int size = Math.min(getMeasuredWidth(), getMeasuredHeight());
        int requiredWidth = 0;
        int requiredHeight = 0;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int childCount = this.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View v = this.getChildAt(i);
            v.measure(widthMeasureSpec, heightMeasureSpec);
        }

        switch (widthMode) {
            case MeasureSpec.AT_MOST:
                requiredWidth = widthSize;
                break;
            case MeasureSpec.EXACTLY:
                requiredWidth = widthSize;
                break;
        }

        switch (heightMode) {
            case MeasureSpec.AT_MOST:
                int totalChildsWidth = 0;
                int line = 1;
                for (int i = 0; i < childCount; i++) {
                    View v = this.getChildAt(i);
                    int childWidth = this.getMeasuredWidth();
                    int childHeight = this.getMeasuredHeight();
                    totalChildsWidth += childWidth;
                    if (totalChildsWidth > requiredWidth) {
                        line += 1;
                        totalChildsWidth = 0;
                        totalChildsWidth += childWidth;
                    }
                }
                break;
            case MeasureSpec.EXACTLY:
                requiredHeight = heightSize;
                break;
        }

        setMeasuredDimension(requiredWidth, requiredHeight);
    }

    @Override
    protected void onSizeChanged(final int w, final int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mDrawable.setBounds(0, 0, w, h);
        mWidth = w;
        mHeight = h;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setOutlineProvider(new ViewOutlineProvider() {
                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void getOutline(View view, Outline outline) {
                    outline.setOval(0, 0, view.getWidth(), view.getHeight());
                }
            });
            setClipToOutline(true);
        }
    }

    private int getColor() {
        return mBackgroundColor;
    }

    private void setColor(int color) {
        mBackgroundColor = color;
        invalidate();
    }

    @Override
    protected boolean verifyDrawable(Drawable who) {
        return who == mDrawable || super.verifyDrawable(who);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(mBackgroundColor);
        mPaintbroder.setStyle(Paint.Style.STROKE);
        mPaintbroder.setColor(Color.parseColor("#19AFCB"));
        mPaintbroder.setStrokeWidth(20);
        final float radius = Math.min(mWidth, mHeight) / 2f;
        canvas.drawCircle(mWidth / 2f, mHeight / 2f, radius, mPaint);
        canvas.drawCircle(mWidth / 2f, mHeight / 2f, radius, mPaintbroder);

        mDrawable.draw(canvas);

    }

    public void toggle() {
        if (mAnimatorSet != null) {
            mAnimatorSet.cancel();
        }

        mAnimatorSet = new AnimatorSet();
        final boolean isPlay = mDrawable.isPlay();
        final ObjectAnimator colorAnim = ObjectAnimator.ofInt(this, COLOR, isPlay ? mPauseBackgroundColor : mPlayBackgroundColor);
        colorAnim.setEvaluator(new ArgbEvaluator());
        final Animator pausePlayAnim = mDrawable.getPausePlayAnimator();
        mAnimatorSet.setInterpolator(new DecelerateInterpolator());
        mAnimatorSet.setDuration(PLAY_PAUSE_ANIMATION_DURATION);
        mAnimatorSet.playTogether(colorAnim, pausePlayAnim);
        mAnimatorSet.start();
    }
}

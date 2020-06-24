package com.ikeeko.searchproject.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;

import com.ikeeko.searchproject.R;
import com.ikeeko.searchproject.until.DensityUtil;

import androidx.appcompat.widget.AppCompatImageView;

/**
 * Created by ZSC on 2020-06-24.
 */
public class ScrollParallaxView extends AppCompatImageView implements ViewTreeObserver.OnScrollChangedListener {
    private int mOutRoundColor;
    private boolean mIsCircle;
    private boolean mIsParallax;
    private RectF mRect;
    private Path mPath;
    private Paint mPaint;
    private Matrix mMatrix;
    private int[] mScreenLocation = new int[2];
    private int mRountWidth;
    private float mParallaxRate;
    private static final float DEFAULT_PARALLAX_RATE = 0.3F;
    private int mScreenHeight = 0;


    public ScrollParallaxView(Context context) {
        this(context, null);
    }

    public ScrollParallaxView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollParallaxView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ScrollParallaxView);
        mParallaxRate = ta.getFloat(R.styleable.ScrollParallaxView_parallaxRate, DEFAULT_PARALLAX_RATE);
        mIsParallax = ta.getBoolean(R.styleable.ScrollParallaxView_enableParallax, true);
        mIsCircle = ta.getBoolean(R.styleable.ScrollParallaxView_enableCircle, false);
        mRountWidth = ta.getDimensionPixelSize(R.styleable.ScrollParallaxView_roundWidth, 0);
        mOutRoundColor = ta.getColor(R.styleable.ScrollParallaxView_outRoundColor, Color.WHITE);
        ta.recycle();

        init();
    }

    private void init() {
        mMatrix = new Matrix();

        mPath = new Path();
        mPath.setFillType(Path.FillType.INVERSE_EVEN_ODD);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(mOutRoundColor);

        mScreenHeight = DensityUtil.getScreenSize(getContext()).y;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mIsCircle) {
            int width = getMeasuredWidth();
            int height = getMeasuredHeight();
            setMeasuredDimension(Math.min(width, height), Math.min(width, height));
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPath.reset();
        if (mRountWidth != 0) {
            mRountWidth = mIsCircle ? Math.min(w / 2, h / 2) : mRountWidth;
            mPath.addRoundRect(new RectF(0, 0, w, h), mRountWidth, mRountWidth, Path.Direction.CCW);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mIsParallax) {
            int saveCount = canvas.save();
            getLocationInWindow(mScreenLocation);
            if (mRect == null) {
                mRect = new RectF(getDrawable().getBounds());
            }
            float parallaxScale = 0;
            float vw = getWidth();
            float vh = getHeight();
            float bw = mRect.width();
            float bh = mRect.height();
            float vratio = vw / vh;
            float bratio = bw / bh;
            float ph = (1 + mParallaxRate) * vh;
            if (bratio > vratio) {
                parallaxScale = ph / (vh / bratio);
            } else {
                float _scale = vw / (vh * bratio);
                float _ph = vw / bratio;
                if (_ph < ph) {
                    _scale = ph / vh;
                }
                parallaxScale = _scale;
            }
            mMatrix.reset();
            mMatrix.mapRect(mRect);
            mMatrix.postScale(parallaxScale, parallaxScale, vw / 2, vh / 2);
            float translationY = mParallaxRate / 2f * vh;
            mMatrix.postTranslate(0, translationY);
            mMatrix.postTranslate(0, -(mParallaxRate * vh) * ((float) mScreenLocation[1] / mScreenHeight));
            canvas.concat(mMatrix);
            super.onDraw(canvas);
            canvas.restoreToCount(saveCount);
        } else {
            super.onDraw(canvas);
        }
        canvas.drawPath(mPath, mPaint);
    }

    public ScrollParallaxView setParallaxRate(int rate) {
        if (rate < 0) {
            rate = 0;
        }
        this.mParallaxRate = rate;
        return this;
    }

    public ScrollParallaxView setParallax(boolean parallax) {
        mIsParallax = parallax;
        return this;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnScrollChangedListener(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        getViewTreeObserver().removeOnScrollChangedListener(this);
        super.onDetachedFromWindow();
    }

    @Override
    public void onScrollChanged() {
        postInvalidate();
    }
}

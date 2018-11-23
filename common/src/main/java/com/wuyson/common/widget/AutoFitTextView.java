package com.wuyson.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.support.v7.widget.AppCompatTextView;
import android.util.TypedValue;

import com.wuyson.common.R;
import com.wuyson.common.util.DisplayHelper;

/**
 * 在宽度固定的情况下，文字多到一行放不下时能缩小文字大小来自适应
 * <p>
 * 需要添加：
 * android:maxLines="1"
 *
 * @author : Wuyson
 * @date : 2018/11/23-15:43
 */
public class AutoFitTextView extends AppCompatTextView {
    private static final String TAG = "AutoFitTextView";
    private Paint mPaint;
    private float mMinSize;
    private float mMaxSize;

    public AutoFitTextView(Context context) {
        this(context, null);
    }

    public AutoFitTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint();
        mPaint.set(this.getPaint());

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.AutoFitTextView);
        mMinSize = array.getDimensionPixelSize(
                R.styleable.AutoFitTextView_wys_minTextSize, Math.round(14 * DisplayHelper.DENSITY));
        mMaxSize = array.getDimensionPixelSize(
                R.styleable.AutoFitTextView_wys_maxTextSize, Math.round(18 * DisplayHelper.DENSITY));
        array.recycle();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        int height = getMeasuredHeight();
        refitText(this.getText().toString(), parentWidth);
        this.setMeasuredDimension(parentWidth, height);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        refitText(text.toString(), this.getWidth());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (w != oldw) {
            refitText(this.getText().toString(), w);
        }
    }

    /**
     * 重新计算
     * (要先设置文字大小，在获取文字长度和控件比大小)
     *
     * @param text      文字
     * @param textWidth textView的宽度
     */
    private void refitText(String text, int textWidth) {
        if (textWidth <= 0) {
            return;
        }
        int targetWidth = textWidth - this.getPaddingLeft() - this.getPaddingRight();
        float hi = mMaxSize;
        float lo = mMinSize;
        final float deviation = 0.5f;
        float size;

        mPaint.set(this.getPaint());//
        mPaint.setTextSize(mMaxSize);//

        if (mPaint.measureText(text) <= targetWidth) {
            lo = mMaxSize;
        } else {
            mPaint.setTextSize(mMinSize);//
            if (mPaint.measureText(text) < targetWidth) {//
                while (hi - lo > deviation) {
                    size = (hi + lo) / 2;
                    mPaint.setTextSize(size);
                    if (mPaint.measureText(text) >= targetWidth) {
                        //too big
                        hi = size;
                    } else {
                        //too small
                        lo = size;
                    }
                }
            }
        }
        this.setTextSize(TypedValue.COMPLEX_UNIT_PX, lo);
    }
}


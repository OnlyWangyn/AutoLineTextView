package com.example.wangyn.autolinetextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by wangyn on 16/4/21.
 */
public class HotelDrawLinePriceTextView extends View {
    private String mTitle;
    private String mPrice;
    private Context mContext;
    private TextPaint mTextPaint;
    public HotelDrawLinePriceTextView(Context context) {
        this(context, null);
    }

    public HotelDrawLinePriceTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HotelDrawLinePriceTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta = context.obtainStyledAttributes(attrs,
                new int[]{android.R.attr.textSize,android.R.attr.textColor});
        int textSize = ta.getDimensionPixelSize(0, 42);
        int textColor = ta.getColor(1,getResources().getColor(R.color.black));
        mTextPaint = new TextPaint();
        mTextPaint.setTextSize(textSize);
        mTextPaint.setColor(textColor);
        mTextPaint.setAntiAlias(true);
        ta.recycle();
    }
    public void setTitle(String title){
        this.mTitle = title;
    }
    public void setPrice(String price){
        this.mPrice = price;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        int drawWidth = 0;
        if(!TextUtils.isEmpty(mTitle)){
            canvas.drawText(mTitle,drawWidth,mTextPaint.getTextSize(),mTextPaint);
            float titleWidth = mTextPaint.measureText(mTitle);
            drawWidth += titleWidth;
        }
        if(!TextUtils.isEmpty(mPrice)){
            canvas.drawText(mPrice, drawWidth, mTextPaint.getTextSize(), mTextPaint);
            float priceWidth = mTextPaint.measureText(mPrice);
            Paint.FontMetrics fm = mTextPaint.getFontMetrics();
            float lineY = (fm.descent - fm.top)/2;
            canvas.drawLine(drawWidth,lineY,drawWidth + priceWidth,lineY,mTextPaint);
        }
        if(drawWidth>0){
            setVisibility(View.VISIBLE);
        }
        canvas.restore();
    }
}

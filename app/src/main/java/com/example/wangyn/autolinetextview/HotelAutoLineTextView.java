package com.example.wangyn.autolinetextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.LruCache;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangyn on 16/4/17.
 */
public class HotelAutoLineTextView extends View {

    private ArrayList<TextEntity> mTextEntities = new ArrayList<TextEntity>();
    private LruCache<String, TextPaint> mTextPaintCache = new LruCache<String, TextPaint>(16);
    private TextPaint mDefaultTextPaint;
    private int mTextPadding;
    private int mDefaultTextSize;
    private int mDefaultTextColor;

    private Context mContext;
    public HotelAutoLineTextView(Context context) {
        this(context, null);
    }

    public HotelAutoLineTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HotelAutoLineTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        TypedArray typeArray = context.obtainStyledAttributes(attrs, R.styleable.HotelAutoLineTextView);
        mDefaultTextSize = typeArray.getDimensionPixelSize(R.styleable.HotelAutoLineTextView_defaultTextSize,
                getResources().getDimensionPixelOffset(R.dimen.default_text_padding));
        mDefaultTextColor = typeArray.getColor(R.styleable.HotelAutoLineTextView_defaultTextColor,
                getResources().getColor(R.color.black));
        mTextPadding = typeArray.getDimensionPixelSize(R.styleable.HotelAutoLineTextView_textPadding, 0);
        mDefaultTextPaint = new TextPaint();
        mDefaultTextPaint.setColor(mDefaultTextColor);
        mDefaultTextPaint.setTextSize(mDefaultTextSize);
        mDefaultTextPaint.setAntiAlias(true);
        typeArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(mTextEntities.size()>0){
            canvas.save();
            int maxWidth = getWidth();
            float drawWidth = 0;
            int line=0;
            for(int i = 0;i < mTextEntities.size();i++){
                String text = mTextEntities.get(i).text;
                int appearance = mTextEntities.get(i).appearance;
                TextPaint textPaint = getTextPaint(text, appearance);
                if(textPaint == null){
                    textPaint = mDefaultTextPaint;
                }
                float textWidth = textPaint.measureText(text);
                if(maxWidth - drawWidth < textWidth){
                    drawWidth = 0;
                    line++;
                }
                canvas.drawText(text,drawWidth,(line+1)*mDefaultTextPaint.getTextSize(),textPaint);
                drawWidth += textWidth + mTextPadding;
            }
            canvas.restore();
        }
    }

    private void buildTextView(List<TextEntity> textEntities) {
        mTextEntities.clear();
        mTextEntities.addAll(textEntities);
        invalidate();
    }
    private TextPaint getTextPaint(String text,int appearance){
        if(appearance ==0){
            return null;
        }
        String key = createTextPaintKey(text,appearance);
        TextPaint textPaint = mTextPaintCache.get(key);
        if(textPaint == null){
            textPaint = new TextPaint();
            TypedArray ta = mContext.obtainStyledAttributes(appearance,
                    new int[]{android.R.attr.textSize,android.R.attr.textColor});
            int textSize = ta.getDimensionPixelSize(0,0);
            int textColor = ta.getColor(1,0);
            if(textSize==0){
              textSize = mDefaultTextSize;
            }
            if(textColor ==0){
                textColor = mDefaultTextColor;
            }
            textPaint.setTextSize(textSize);
            textPaint.setColor(textColor);
            mTextPaintCache.put(key, textPaint);
            ta.recycle();
        }
        return textPaint;
    }
    private String createTextPaintKey(String text,int appearance){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(text).append("|").append(appearance);
        return stringBuffer.toString();
    }
    public static class Builder{

        private static List<TextEntity> mTextEntities = new ArrayList<TextEntity>();
        private static Builder mBuilder = new Builder();

        public static Builder getInstance(){
            mTextEntities.clear();
            return mBuilder;
        }
        public Builder append(String text,int appearance){
            TextEntity textEntity = new TextEntity();
            textEntity.text = text;
            textEntity.appearance = appearance;
            mTextEntities.add(textEntity);
            return this;
        }
        public Builder append(String text,int appearance,Drawable drawable){
            TextEntity textEntity = new TextEntity();
            textEntity.text = text;
            textEntity.appearance = appearance;
            textEntity.drawable = drawable;
            mTextEntities.add(textEntity);
            return this;
        }
        public void into(HotelAutoLineTextView autoLineTextView){
            if(autoLineTextView != null) {
                autoLineTextView.buildTextView(mTextEntities);
            }
        }

    }

    private static class TextEntity{
        String text;
        int appearance;
        Drawable drawable;
    }
}

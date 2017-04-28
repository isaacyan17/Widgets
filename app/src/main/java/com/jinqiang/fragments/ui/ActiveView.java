package com.jinqiang.fragments.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by jingqiang on 2017/4/28.
 */

public class ActiveView extends View{
    Paint mPaint;
    Context mContext;
    int BackgroundColor;
    int TransparentColor;
    int mWith;
    int mHeight;
    int[] circleCenter;

    public ActiveView(Context context) {
        this(context,null);
    }

    public ActiveView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ActiveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        mContext = context;
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        BackgroundColor = Color.parseColor("#cc222222");
        TransparentColor = Color.parseColor("#00000000");
        mPaint.setXfermode(null);
        WindowManager manager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        mHeight = manager.getDefaultDisplay().getHeight();
        mWith = manager.getDefaultDisplay().getWidth();
    }
    public void getCircleCenter(int[] centers){
        this.circleCenter = centers;
        this.invalidate();//触发重绘
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        setMeasuredDimension(mWith,mHeight);

    }

    private Bitmap drawReactBm(){
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(BackgroundColor);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        Bitmap bm = Bitmap.createBitmap(mWith,mHeight, Bitmap.Config.ARGB_8888);
        Canvas cavas = new Canvas(bm);
        cavas.drawRect(new RectF(0,0,mWith,mHeight),paint);
//        cavas.drawColor(GrayColor);
        return bm;
    }

    private Bitmap drawCircleBm(){
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(TransparentColor);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        Bitmap bm = Bitmap.createBitmap(220,220, Bitmap.Config.ARGB_8888);
        Canvas cavas = new Canvas(bm);
        cavas.drawCircle(150,150,120,paint);
        return bm;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int sc = canvas.saveLayer(0, 0,mWith,mHeight, null, Canvas.MATRIX_SAVE_FLAG |
                Canvas.CLIP_SAVE_FLAG |
                Canvas.HAS_ALPHA_LAYER_SAVE_FLAG |
                Canvas.FULL_COLOR_LAYER_SAVE_FLAG |
                Canvas.CLIP_TO_LAYER_SAVE_FLAG);
        canvas.drawBitmap(drawReactBm(),0,0,mPaint);
//        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
//        canvas.drawBitmap(drawCircleBm(),mWith - 290,230,mPaint);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(TransparentColor);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
//        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
//        mPaint.setColor(TransparentColor);
//        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        if(circleCenter == null) {
            canvas.drawCircle(mWith - 140, 390, 140, paint);
        }
        else {
            Log.v("yjq","location:"+circleCenter[0]+"   "+circleCenter[1]);
            canvas.drawCircle(circleCenter[0], circleCenter[1], 120, paint);
        }

//        mPaint.setXfermode(null);
        // 还原画布
//        canvas.restoreToCount(sc);
    }
}

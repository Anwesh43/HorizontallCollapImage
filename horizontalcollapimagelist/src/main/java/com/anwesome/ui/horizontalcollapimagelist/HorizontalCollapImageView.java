package com.anwesome.ui.horizontalcollapimagelist;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by anweshmishra on 05/06/17.
 */

public class HorizontalCollapImageView extends View{
    private Bitmap bitmap;
    private int time = 0,w,h,color = Color.parseColor("#3949AB");
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    public HorizontalCollapImageView(Context context, Bitmap bitmap) {
        super(context);
        this.bitmap = bitmap;
    }
    public void onDraw(Canvas canvas) {
        if(time == 0) {
            w = canvas.getWidth();
            h = canvas.getHeight();
            bitmap = Bitmap.createScaledBitmap(bitmap,4*w/5,4*h/5,true);
        }
        paint.setColor(color);
        time++;
    }
    public void update(float factor) {
        postInvalidate();
    }
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {

        }
        return true;
    }
    private class CollapImage {
        private float x,y,scale = 0;
        public CollapImage(float x,float y) {
            this.x = x;
            this.y = y;
        }
        public void draw(Canvas canvas) {
            int bw = bitmap.getWidth()/2,bh = bitmap.getHeight()/2;
            paint.setStrokeWidth(bw/20);
            canvas.save();
            canvas.translate(x,y);
            canvas.scale(scale,1);
            canvas.drawBitmap(bitmap,-bw,-bh,paint);
            canvas.drawLine(-bw*scale,-bh,-bw*scale,bh,paint);
            canvas.drawLine(bw*scale,-bh,bw*scale,bh,paint);
            canvas.drawLine(-bw*scale,-bh,bw*scale,-bh,paint);
            canvas.drawLine(-bw*scale,bh,bw*scale,bh,paint);
            canvas.restore();
        }
        public void update(float factor) {
            scale = factor;
        }
    }
    private class Indicator {
        private float x,y,r,a = 0;
        public Indicator() {
            x = w/2;
            y = 5*h/6+h/12;
            r = h/15;
        }
        public void draw(Canvas canvas) {
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.argb(150,0,0,0));
            canvas.drawRect(new RectF(w/10,5*h/6,9*h/10,h),paint);
            paint.setColor(color);
            canvas.save();
            for(int i=0;i<2;i++) {
                canvas.drawArc(new RectF(-r, -r, r, r), i*180, a,false, paint);
            }
            canvas.restore();
        }
        public void update(float factor) {
            a = 180*factor;
        }
    }
    private class AnimationHandler extends AnimatorListenerAdapter implements ValueAnimator.AnimatorUpdateListener{
        private boolean isAnimated = false;
        private int dir = 0;
        private ValueAnimator startAnim = ValueAnimator.ofFloat(0,1),endAnim = ValueAnimator.ofFloat(1,0);
        public void start() {
            if(!isAnimated) {
                if(dir == 0) {
                    startAnim.start();
                }
                else {
                    endAnim.start();
                }
                isAnimated = true;
            }
        }
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            if(isAnimated) {
                update((float)valueAnimator.getAnimatedValue());
            }
        }
        public void onAnimationEnd(Animator animator) {
            if(isAnimated) {
                dir = dir == 0?1:0;
                isAnimated = false;
            }
        }
        public void AnimationHandler() {
            startAnim.setDuration(500);
            endAnim.setDuration(500);
            startAnim.addUpdateListener(this);
            endAnim.addUpdateListener(this);
            startAnim.addListener(this);
            endAnim.addListener(this);
        }
    }
}

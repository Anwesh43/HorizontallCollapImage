package com.anwesome.ui.horizontalcollapimagelist;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by anweshmishra on 05/06/17.
 */

public class HorizontalCollapImageView extends View{
    private Bitmap bitmap;
    private int time = 0,w,h,color = Color.BLUE;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private AnimationHandler animationHandler;
    private CollapImage collapImage;
    private Indicator indicator;
    public HorizontalCollapImageView(Context context, Bitmap bitmap) {
        super(context);
        this.bitmap = bitmap;
    }
    public void onDraw(Canvas canvas) {
        if(time == 0) {
            w = canvas.getWidth();
            h = canvas.getHeight();
            bitmap = Bitmap.createScaledBitmap(bitmap,4*w/5,4*h/5,true);
            animationHandler = new AnimationHandler();
            collapImage = new CollapImage();
            indicator = new Indicator();
        }
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setColor(color);
        collapImage.draw(canvas);
        indicator.draw(canvas);
        time++;
    }
    public void update(float factor) {
        collapImage.update(factor);
        indicator.update(factor);
        postInvalidate();
    }
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN && indicator!=null && indicator.handleTap(event.getX(),event.getY())) {
            animationHandler.start();
        }
        return true;
    }
    private class CollapImage {
        private float x,y,scale = 0;
        public CollapImage() {
            this.x = w/2;
            this.y = h/2;
        }
        public void draw(Canvas canvas) {
            int bw = bitmap.getWidth()/2,bh = bitmap.getHeight()/2;
            paint.setStrokeWidth(bw/20);
            canvas.save();
            canvas.translate(x,y);
            canvas.save();
            Path path = new Path();
            path.addRect(new RectF(-bw*scale,-bh,bw*scale,bh), Path.Direction.CCW);
            canvas.clipPath(path);
            canvas.drawBitmap(bitmap,-bw,-bh,paint);
            canvas.restore();
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
            y = 9*h/10-h/6;
            r = h/15;
        }
        public boolean handleTap(float x,float y) {
            return x>=this.x -r && x<=this.x+r && y>=this.y+h/12-r && y<=this.y+h/12+r;
        }
        public void draw(Canvas canvas) {
            canvas.save();
            canvas.translate(x,y);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.argb(150,0,0,0));
            canvas.drawRect(new RectF(-2*w/5,0,2*w/5,h/6),paint);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.GRAY);
            canvas.drawCircle(0,h/12,r,paint);
            paint.setColor(color);
            for(int i=0;i<2;i++) {
                canvas.drawArc(new RectF(-r, h/12-r, r, h/12+r), i*180, a,false, paint);
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
                isAnimated = true;
                if(dir == 0) {
                    startAnim.start();
                }
                else {
                    endAnim.start();
                }

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
        public AnimationHandler() {
            startAnim.setDuration(500);
            endAnim.setDuration(500);
            startAnim.addUpdateListener(this);
            endAnim.addUpdateListener(this);
            startAnim.addListener(this);
            endAnim.addListener(this);
        }
    }
}

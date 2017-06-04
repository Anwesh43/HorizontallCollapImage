package com.anwesome.ui.horizontalcollapimagelist;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
}

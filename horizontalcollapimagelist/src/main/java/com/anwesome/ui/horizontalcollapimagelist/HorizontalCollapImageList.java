package com.anwesome.ui.horizontalcollapimagelist;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.hardware.display.DisplayManager;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

/**
 * Created by anweshmishra on 05/06/17.
 */

public class HorizontalCollapImageList {
    private ScrollView scrollView;
    private Activity activity;
    private boolean isShown = false;
    private ListLayout listLayout;
    public HorizontalCollapImageList(Activity activity) {
        this.activity = activity;
        this.scrollView = new ScrollView(activity);
        listLayout = new ListLayout(activity);
        scrollView.addView(listLayout,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }
    public void addImage(Bitmap bitmap) {
        if(!isShown) {
            listLayout.addImage(bitmap);
        }
    }
    public void show() {
        if(!isShown) {
            activity.setContentView(scrollView);
            isShown = true;
        }
    }
    private class ListLayout extends ViewGroup {
        private int w,h;
        public ListLayout(Context context) {
            super(context);
            initDimension(context);
        }
        public void initDimension(Context context) {
            DisplayManager displayManager = (DisplayManager)context.getSystemService(Context.DISPLAY_SERVICE);
            Display display = displayManager.getDisplay(0);
            if(display != null) {
                Point size = new Point();
                display.getRealSize(size);
                w = size.x;
                h = size.y;
            }
        }
        public void addImage(Bitmap bitmap) {
            HorizontalCollapImageView horizontalCollapImageView = new HorizontalCollapImageView(getContext(),bitmap);
            addView(horizontalCollapImageView,new LayoutParams(9*w/10,9*w/10));
            requestLayout();
        }
        public void onMeasure(int wspec,int hspec) {
            int hMax = h/20;
            for(int i=0;i<getChildCount();i++) {
                View child = getChildAt(i);
                measureChild(child,wspec,hspec);
                hMax += (h+h/20);
            }
            setMeasuredDimension(w,hMax);
        }
        public void onLayout(boolean reloaded,int a,int b,int wa,int ha) {
            int x = w/20,y = h/20;
            for(int i=0;i<getChildCount();i++) {
                View child = getChildAt(i);
                child.layout(x,y,x+child.getMeasuredWidth(),y+child.getMeasuredHeight());
                y += (child.getMeasuredHeight()+h/20);
            }
        }
    }
}

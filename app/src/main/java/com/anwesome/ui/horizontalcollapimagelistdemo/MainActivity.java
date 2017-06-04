package com.anwesome.ui.horizontalcollapimagelistdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.anwesome.ui.horizontalcollapimagelist.HorizontalCollapImageList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.stp);
        HorizontalCollapImageList horizontalCollapImageList = new HorizontalCollapImageList(this);
        for(int i=0;i<6;i++) {
            horizontalCollapImageList.addImage(bitmap);
        }
        horizontalCollapImageList.show();
    }
}

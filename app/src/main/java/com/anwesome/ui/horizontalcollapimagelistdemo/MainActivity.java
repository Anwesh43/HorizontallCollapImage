package com.anwesome.ui.horizontalcollapimagelistdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.anwesome.ui.horizontalcollapimagelist.HorizontalCollapImageList;
import com.anwesome.ui.horizontalcollapimagelist.OnImageCollapListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.stp);
        HorizontalCollapImageList horizontalCollapImageList = new HorizontalCollapImageList(this);
        for(int i=0;i<6;i++) {
            final int index = i+1;
            horizontalCollapImageList.addImage(bitmap, new OnImageCollapListener() {
                @Override
                public void onOpen() {
                    Toast.makeText(MainActivity.this, String.format("%d opened",index), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onClose() {
                    Toast.makeText(MainActivity.this, String.format("%d closed",index), Toast.LENGTH_SHORT).show();
                }
            });
        }
        horizontalCollapImageList.show();
    }
}

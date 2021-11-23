package com.example.digicala.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.digicala.R;

public class SplashView extends View {


    public float width, height;
    public int left, right, top, bottom;

    public SplashView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init();
    }


    private void init() {


        this.post(new Runnable() {
            @Override
            public void run() {
                width = getMeasuredWidth();
                height = getMeasuredHeight();

                left = (int) (width / 2) - 200;
                right = (int) (width / 2) + 200;
                top = 0;
                bottom = 400;
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        Paint paint = new Paint();
        //paint.setColor(Color.WHITE);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        int image_width = bitmap.getWidth();
        int image_height = bitmap.getHeight();

        Rect logo_rect = new Rect(left, top, right, bottom);
        Rect crop = new Rect(0, 0, (int) image_width, (int) image_height);


        canvas.drawBitmap(bitmap, crop, logo_rect, paint);
    }


}

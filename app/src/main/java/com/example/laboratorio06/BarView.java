package com.example.laboratorio06;


import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;


public class BarView extends View {
    float posx = 0, posy=0;
    Paint paint , bar, paintTextY, paintTextX;
    ArrayList<Float> trazo= new ArrayList<Float>();
    String Ejex [] = {"Argentina", "Bolivia", "Brazil", "Canada", "Chile",
            "Columbia", "Ecuador", "Guyana", "Mexico", "Paraguay", "Peru", "U.S.A.", "Uruguay", "Venezuela","Gotica","Metropolis"};
    Double[] Ejey = {20.7, 46.6, 28.6, 14.5, 23.4, 27.4, 32.9, 28.3, 29.0, 34.8, 32.9, 16.7, 18.0, 27.5,30.5,80.6};
    int i = 0;
    int width ;
    int height ;
    Point size = new Point();
    public BarView(Context context, @Nullable AttributeSet attrs) {

        super(context, attrs);
        paint = new Paint();
        bar = new Paint();
        paintTextY = new Paint();
        paintTextX = new Paint();
        paint.setStrokeWidth(1);
        bar.setStrokeWidth(25);
        paintTextY.setTextSize(30);
        paintTextX.setTextSize(30);
        paint.setARGB(255, 101, 97, 97);
        paintTextY.setARGB(255, 101, 97, 97);
        paintTextX.setARGB(255, 101, 97, 97);
        bar.setARGB(255, 255, 188, 0);
        Display display = ((Activity)context).getWindowManager().getDefaultDisplay();

        display.getSize(size);
         width = size.x;
         height = size.y;
    }

    private Double getMax(Double arr[]){
        Double max = arr[0];
        for(int i =0;i<arr.length;i++){
            if(max<arr[i]){
                max=arr[i];
            }
        }
        return max;
    }
    public static Point getLocationOnScreen(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return new Point(location[0], location[1]);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Double maxNum = getMax(Ejey);

        int maxY = (int) ((maxNum+10)-(maxNum%10));

        Log.d("Xvalor", String.valueOf(size.y));


        double stopY = 0.0;
        for(int i = 0 ; i <=maxY;i+=5 ) {
            canvas.drawLine(100, 100+i*10, size.x, 100+i*10, paint);
            canvas.drawText(toString(maxY-i),5,100+i*10,paintTextY);
            stopY=100+i*10;
        }

        int rango = (size.x)/(Ejex.length+2);
        for(int i = 0 ; i <Ejex.length;i++ ) {
            canvas.drawLine(100+i*rango, (float) (stopY-Ejey[i].doubleValue()*10), 100+i*rango, (float) stopY, bar);

            canvas.rotate(-90,100+i*rango,(float) (stopY+150));
            canvas.drawText(Ejex[i],100+i*rango,(float) (stopY+150),paintTextX);
            canvas.rotate(90,100+i*rango,(float) (stopY+150));
        }

        int i = 0;
        float nx=0;
        for(float ny : trazo) {

            if(i%2!=0) {
                canvas.drawCircle(nx,ny, 50, paint);
            }else{
                nx=ny;
            }
            i++;
        }
    }

    private String toString(int i) {
        return ""+i;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        posx = event.getX();
        posy = event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                trazo.add(posx);
                trazo.add(posy);
                break;
            case MotionEvent.ACTION_MOVE:

            case MotionEvent.ACTION_UP:

        }
        invalidate();
        return super.onTouchEvent(event);
    }
}

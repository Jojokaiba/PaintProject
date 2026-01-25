package com.jcorp.pingmong.data;

import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class Figure {

    protected float startX;
    protected float startY;

    protected float endX;
    protected float endY;

    protected Paint paint;



    public Figure(int figureType, float startX, float startY , float endX, float endY,  Paint paint ) {

        this.paint = paint;
        this.endY = endY;
        this.endX = endX;
        this.startY = startY;
        this.startX = startX;
    }

    /* public void display(Canvas canvas){
        switch (figureType){
            case 1:                 // Line
                canvas.drawLine(startX, startY, endX, endY, paint);
                break;

            case 2:                    // Rect
                canvas.drawRect(startX, startY, endX, endY, paint);
                break;

            case 3:
                canvas.drawOval(startX, startY, endX, endY, paint);
                break;
        }
    } */

    public abstract void displayCanvas(Canvas canvas);
}

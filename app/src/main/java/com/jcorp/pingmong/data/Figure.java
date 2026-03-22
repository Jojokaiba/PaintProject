package com.jcorp.pingmong.data;

import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class Figure {

    protected float startX;
    protected float startY;

    protected float endX;
    protected float endY;

    protected Paint paint;

    protected int strokeColor;
    protected int fillColor;
    protected float strokeWidth;


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

    public void setStyle(int strokeColor, int fillColor, float strokeWidth){
        this.strokeColor = strokeColor;
        this.fillColor = fillColor;
        this.strokeWidth = strokeWidth;
    }

    protected Paint getStrokePaint(){
        Paint p = new Paint();
        p.setStyle(Paint.Style.STROKE);
        p.setColor(strokeColor);
        p.setStrokeWidth(strokeWidth);
        p.setAntiAlias(true);
        return p;
    }


    protected Paint getFillPaint(){
        Paint p = new Paint();
        p.setStyle(Paint.Style.FILL);
        p.setColor(fillColor);
        p.setAntiAlias(true);
        return p;
    }


    public boolean contains(float x, float y){
        return x >= Math.min(startX, endX) &&
               x <= Math.max(startX, endX) &&
               y >= Math.min(startY, endY) &&
               y <= Math.max(startY, endY);
    }

    public void move(float dx, float dy){
        startX = startX + dx;
        endX = endX + dx;
        startY = startY + dy;
        endY = endY + dy;
    }


    public float getStartX() {     //On crée des getters pour les réutiliser dans DrawingView au niveau de onDraw
        return startX;
    }

    public float getStartY() {
        return startY;
    }

    public float getEndX() {
        return endX;
    }

    public float getEndY() {
        return endY;
    }
    public abstract void displayCanvas(Canvas canvas);
}

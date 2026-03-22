package com.jcorp.pingmong.data;

import android.graphics.Canvas;
import android.graphics.Paint;

public class FigureRect extends Figure{
    public FigureRect(int figureType, float startX, float startY, float endX, float endY, Paint paint) {
        super(figureType, startX, startY, endX, endY, paint);
    }

    @Override
    public void displayCanvas(Canvas canvas) {
        canvas.drawRect(startX, startY, endX, endY, getFillPaint());
        canvas.drawRect(startX, startY, endX, endY, getStrokePaint());
    }
}

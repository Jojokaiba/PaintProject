package com.jcorp.pingmong.data;

import android.graphics.Canvas;
import android.graphics.Paint;

public class FigureLine extends Figure {
    public FigureLine(int figureType, float startX, float startY, float endX, float endY, Paint paint) {
        super(figureType, startX, startY, endX, endY, paint);
    }

    @Override
    public void displayCanvas(Canvas canvas) {
        canvas.drawLine(startX, startY, endX, endY, getStrokePaint());
    }

    @Override
    public boolean contains(float x, float y){
        float tolerance = 20;

        float dx = endX - startX;
        float dy = endY - startY;
        float length = (float) Math.sqrt(dx*dx + dy*dy);

        float distance = Math.abs(dy*x - dx*y + endX*startY - endY*startX) / length;

        return distance < tolerance;
    }
}


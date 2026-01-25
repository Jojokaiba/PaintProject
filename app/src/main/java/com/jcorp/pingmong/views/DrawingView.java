package com.jcorp.pingmong.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.jcorp.pingmong.data.Figure;
import com.jcorp.pingmong.data.FigureLine;
import com.jcorp.pingmong.data.FigureOval;
import com.jcorp.pingmong.data.FigureRect;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

public class DrawingView extends View {

    private List<Figure> figures = new ArrayList<>();
    float posStartX;
    float posStartY;

    float posCurrentX;

    float posCurrentY;
    private Paint drawingPaint;
    private Paint textPaint;

    private int currentFigureType = 1;

    private Figure currentFigure = null;

    public DrawingView(Context context) {
        super(context);
        initComponents();
    }

    public DrawingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initComponents();
    }

    public DrawingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initComponents();
    }

    private void initComponents() {
        drawingPaint = new Paint();
        drawingPaint.setColor(Color.RED);
        drawingPaint.setStyle(Paint.Style.FILL);
        drawingPaint.setStrokeWidth(10);   // Epaisseur de la ligne
        drawingPaint.setAntiAlias(true);


        textPaint = new Paint();                // paramétrage du texte
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(60);
        textPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas drawingCanvas) {
        super.onDraw(drawingCanvas);
        for (Figure figure:figures){
            figure.displayCanvas(drawingCanvas);
        }
        // drawingCanvas.drawLine(posStartX, posStartY, posCurrentX, posCurrentY, drawingPaint);
        if (currentFigure != null)
        currentFigure.displayCanvas(drawingCanvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float posX = event.getX(); // position X dans la View
        float posY = event.getY(); //position Y dans la View

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                posStartX = posX;
                posStartY = posY;
                break;

            case  MotionEvent.ACTION_MOVE:
                posCurrentX = posX;
                posCurrentY = posY;
                // currentFigure = new Figure(currentFigureType,posStartX, posStartY, posCurrentX, posCurrentY, drawingPaint);
                switch (currentFigureType){
                    case 1:
                        currentFigure = new FigureLine(currentFigureType,posStartX, posStartY, posCurrentX, posCurrentY, drawingPaint);
                        break;

                    case 2:
                        currentFigure = new FigureRect(currentFigureType,posStartX, posStartY, posCurrentX, posCurrentY, drawingPaint);
                        break;

                    case 3:
                        currentFigure = new FigureOval(currentFigureType,posStartX, posStartY, posCurrentX, posCurrentY, drawingPaint);
                        break;
                }
                break;

            case MotionEvent.ACTION_UP:
                figures.add(currentFigure);
                break;
        }
        invalidate();
        return true;
    }
}



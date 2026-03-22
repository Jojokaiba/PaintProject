package com.jcorp.pingmong.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.jcorp.pingmong.data.Figure;
import com.jcorp.pingmong.data.FigureLine;
import com.jcorp.pingmong.data.FigureOval;
import com.jcorp.pingmong.data.FigureRect;

import java.util.ArrayList;
import java.util.List;

public class DrawingView extends View {

    private final List<Figure> figures = new ArrayList<>();
    float posStartX;
    float posStartY;

    float posCurrentX;

    float posCurrentY;
    private Paint drawingPaint;
    private Paint textPaint;

    private int currentFigureType = 1;

    private Figure currentFigure = null;

    private Figure selectedFigure = null;

    private float lastX;

    private float lastY;

    private int strokeColor = Color.BLACK;
    private int fillColor = Color.TRANSPARENT;
    private float strokeWidth = 10;

    public static final int LINE = 1;
    public static final int RECT = 2;
    public static final int OVAL = 3;


    public DrawingView(Context context) {
        super(context);
//        initComponents();
    }

    public DrawingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
//        initComponents();
    }

    public DrawingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        initComponents();
    }

//    private void initComponents() {
//       drawingPaint = new Paint();
//        drawingPaint.setColor(Color.RED);
//        drawingPaint.setStyle(Paint.Style.FILL);
//        drawingPaint.setStrokeWidth(10);   // Epaisseur de la ligne
//        drawingPaint.setAntiAlias(true);


//        textPaint = new Paint();                // paramétrage du texte
//        textPaint.setColor(Color.BLACK);
//        textPaint.setTextSize(60);
//        textPaint.setAntiAlias(true);
//    }

    @Override
    protected void onDraw(Canvas drawingCanvas) {
        super.onDraw(drawingCanvas);
        for (Figure figure:figures){
            figure.displayCanvas(drawingCanvas);
        }
        // drawingCanvas.drawLine(posStartX, posStartY, posCurrentX, posCurrentY, drawingPaint);
        if (currentFigure != null){
            currentFigure.displayCanvas(drawingCanvas);
        }

        //ajout de la sélection
        if (selectedFigure != null) {
            Paint p = new Paint();
            p.setColor(Color.argb(80, 0, 0, 255));
            p.setStrokeWidth(3);
            drawingCanvas.drawRect(selectedFigure.getStartX(), selectedFigure.getStartY(), selectedFigure.getEndX(), selectedFigure.getEndY(), p); //le but étant de dessiner une zone de sélection
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float posX = event.getX(); // position X dans la View
        float posY = event.getY(); //position Y dans la View

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:

                selectedFigure = findFigure(posX, posY);  //On veut savoir d'abord si une figure est sélectionné

                if (selectedFigure!=null){
                    lastX = posX;
                    lastY = posY;
                    return true;
                }


                posStartX = posX;
                posStartY = posY;
                break;

            case  MotionEvent.ACTION_MOVE:

                if (selectedFigure != null) {   //Toujours dans le cas où une figure est sélectionnée on veut modifier la position de la figure
                    float dx = posX - lastX;
                    float dy = posY - lastY;

                    selectedFigure.move(dx, dy);

                    lastX = posX;
                    lastY = posY;
                }else{                          //Dans le cas contraire on va créer une nouvelle figure

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
                    currentFigure.setStyle(strokeColor, fillColor, strokeWidth);
                }
                break;

            case MotionEvent.ACTION_UP:

                if (selectedFigure == null && currentFigure != null){ // Si aucune figure n'est sélectionné et qu'une figure est dessiné
                    figures.add(currentFigure);
                }
                currentFigure = null;                   //On neutralise la forme de la figure
                break;
        }
        invalidate();
        return true;
    }


    private Figure findFigure(float x, float y) {        // L'idée est de récupérer la figure la plus récente crée pour la sélection
        for (int i = figures.size() - 1; i >= 0; i--) {
            if (figures.get(i).contains(x, y)) {
                return figures.get(i);
            }
        }
        return null;
    }

// NOUVELLES FONCTIONNALITES


    public void setCurrentFigureType(int figureType) {
        this.currentFigureType = figureType;
    }

    public void undo() {
        if (!figures.isEmpty()) {
            figures.remove(figures.size() - 1);
            invalidate();
        }
    }

    public void deleteSelected() {
        if (selectedFigure != null) {
            figures.remove(selectedFigure);
            selectedFigure = null;
            invalidate();
        }
    }


    public void clearCanvas() {
        figures.clear();
        invalidate();
    }


    public void setStrokeColor(int color) {
        strokeColor = color;
        if (selectedFigure != null) {
            selectedFigure.setStyle(color, fillColor, strokeWidth);
            invalidate();
        }
    }

    public void setFillColor(int color) {
        fillColor = color;
        if (selectedFigure != null) {
            selectedFigure.setStyle(strokeColor, color, strokeWidth);
            invalidate();
        }
    }

    public void setStrokeWidth(float width) {
        strokeWidth = width;
        if (selectedFigure != null) {
            selectedFigure.setStyle(strokeColor, fillColor, width);
            invalidate();
        }
    }

    public Bitmap getBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        draw(canvas);
        return bitmap;
    }

}



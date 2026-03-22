package com.jcorp.pingmong;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.jcorp.pingmong.views.DrawingView;

public class MainActivity extends AppCompatActivity {


    private DrawingView drawingView; // ← IMPORTANT

    private boolean redActive = false;
    private boolean blueActive = false;
    private boolean fillActive = false;
    private boolean thickActive =false;

    private int strokeColor = Color.BLACK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addDrawingView();
        setupButtons();
        setupExtraButtons();
    }

    private void addDrawingView(){
        drawingView = new DrawingView(this);
        drawingView.setId(View.generateViewId());

        //Create ConstraintLayout params
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_CONSTRAINT,
                ConstraintLayout.LayoutParams.MATCH_CONSTRAINT
        );

        // Constrain to all four edges of parent
        params.topToBottom = R.id.tools;
        params.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;

        // Get parent layout and DrawingView
        ConstraintLayout mainLayout = findViewById(R.id.main);
        mainLayout.addView(drawingView, params);


    }

    private void setupButtons(){
        findViewById(R.id.btnLine).setOnClickListener(v ->
                drawingView.setCurrentFigureType(DrawingView.LINE));

        findViewById(R.id.btnRect).setOnClickListener(v ->
                drawingView.setCurrentFigureType(DrawingView.RECT));

        findViewById(R.id.btnOval).setOnClickListener(v ->
                drawingView.setCurrentFigureType(DrawingView.OVAL));
    }

    private void setupExtraButtons(){

        findViewById(R.id.btnUndo).setOnClickListener(v -> drawingView.undo());

        findViewById(R.id.btnDelete).setOnClickListener(v -> drawingView.deleteSelected());

        findViewById(R.id.btnClear).setOnClickListener(v -> drawingView.clearCanvas());

        findViewById(R.id.btnRed).setOnClickListener(v -> {
            if (!redActive){
                drawingView.setStrokeColor(Color.RED);
                redActive = true;
                strokeColor = Color.RED;
            }else{
                drawingView.setStrokeColor(Color.BLACK);            // couleur par défaut
                redActive = false;
                strokeColor = Color.BLACK;
            }

        });

        findViewById(R.id.btnBlue).setOnClickListener(v -> {
            if (!blueActive){
                drawingView.setStrokeColor(Color.BLUE);
                blueActive = true;
                strokeColor = Color.BLUE;
            }else{
                drawingView.setStrokeColor(Color.BLACK);  // couleur par défaut
                blueActive =false;
                strokeColor = Color.BLACK;
            }

        });

        findViewById(R.id.btnFill).setOnClickListener(v -> {
            if (!fillActive){
                if (strokeColor != Color.BLACK) {                   // Si le conteur est coloré alors le remplissage prend la même couleur
                    drawingView.setFillColor(strokeColor);
                }else{                                              // Sinon le remplissage est gris
                    drawingView.setFillColor(Color.GRAY);
                }
                fillActive = true;

            }else{
                drawingView.setFillColor(Color.TRANSPARENT);
                fillActive = false;
            }
        });

        findViewById(R.id.btnThick).setOnClickListener(v ->{
            if(!thickActive){
               drawingView.setStrokeWidth(20);
               thickActive = true;
            }else{
                drawingView.setStrokeWidth(10);
                thickActive = false;
            }

        });

        findViewById(R.id.btnShare).setOnClickListener(v -> shareDrawing());
    }

    private void shareDrawing(){
        Bitmap bitmap = drawingView.getBitmap();

        String path = MediaStore.Images.Media.insertImage(
                getContentResolver(), bitmap, "Drawing", null);

        Uri uri = Uri.parse(path);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_STREAM, uri);

        startActivity(Intent.createChooser(intent, "Partager"));
    }

}
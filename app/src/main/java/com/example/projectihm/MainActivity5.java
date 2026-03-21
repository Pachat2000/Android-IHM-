package com.example.projectihm;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity5 extends AppCompatActivity {

    private boolean selectedSmile = false;
    private boolean selectedSad = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main5);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button result = findViewById(R.id.resultBttn);
        result.setOnClickListener(view -> {
            startActivity(new Intent(this, MainResult.class));
        });

        ImageView smile = findViewById(R.id.HappyImg);
        smile.setOnClickListener(view -> {
            if(!selectedSmile){
                GradientDrawable border = new GradientDrawable();
                border.setColor(Color.TRANSPARENT);
                border.setStroke(10, Color.WHITE);
                selectedSmile = true;
                view.setForeground(border);
            }else{
                selectedSmile = false;
                view.setForeground(null);
            }
        });


        ImageView sad = findViewById(R.id.SadLbl);
        sad.setOnClickListener(view -> {
            if(!selectedSad){
                GradientDrawable border = new GradientDrawable();
                border.setColor(Color.TRANSPARENT);
                border.setStroke(10, Color.WHITE);
                selectedSad = true;
                view.setForeground(border);
            }else{
                selectedSad = false;
                view.setForeground(null);
            }
        });
    }

}
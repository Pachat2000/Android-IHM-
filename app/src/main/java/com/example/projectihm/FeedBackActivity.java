package com.example.projectihm;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FeedBackActivity extends AppCompatActivity {

    private boolean selectedSmile = false;
    private boolean selectedSad = false;

    Intent nextActiviyIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.feedback_activity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button result = findViewById(R.id.resultBttn);

        nextActiviyIntent = new Intent(this, ResultActivity.class);

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

    public void sendInfo(View view){
        // last activity information recuperation
        int answerId = getIntent().getIntExtra("ANSWER_ID", -1);
        int userId = getIntent().getIntExtra("USER_ID",-1);

        nextActiviyIntent.putExtra("USER_ID", userId);
        nextActiviyIntent.putExtra("ANSWER_ID",answerId);

        // if smile selected -> true, else -> false
        nextActiviyIntent.putExtra("USER_OPINION", selectedSmile);

        startActivity(nextActiviyIntent);
    }

}
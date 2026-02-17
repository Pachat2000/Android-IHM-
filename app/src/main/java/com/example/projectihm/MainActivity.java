package com.example.projectihm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

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

        LinearLayout header1 = findViewById(R.id.header_section1);
        LinearLayout body1 = findViewById(R.id.body_section1);
        ImageView arrow1 = findViewById(R.id.ImgArrowSectionUp);

        setupAction(header1, body1, arrow1);
    }

    public void setupAction(LinearLayout header, LinearLayout body, ImageView arrow){
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(body.getVisibility() == View.VISIBLE){
                    body.setVisibility(View.GONE);
                    arrow.animate().rotation(0).setDuration(200).start();
                }else{
                    body.setVisibility(View.VISIBLE);
                    arrow.animate().rotation(180).setDuration(200).start();
                }
            }
        });
    }

}
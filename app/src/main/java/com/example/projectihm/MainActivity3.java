package com.example.projectihm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.List;

public class MainActivity3 extends AppCompatActivity {

    private Chip cJoy;
    private Chip cConfusion;
    private Chip cSerenity;
    private Chip cFear;
    private Chip cStress;
    private Chip cSadness;
    private Chip cAnger;
    private Spinner sOpinion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main3);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        LinearLayout header4 = findViewById(R.id.header_section4);
        LinearLayout body4 = findViewById(R.id.body_section4);
        ImageView arrow4 = findViewById(R.id.ImgArrowSectionUp3);

        setupAction(header4, body4, arrow4);

        sOpinion = findViewById(R.id.spinnerOpinion);

        setupSpinner(sOpinion);

        cJoy = findViewById(R.id.chipJoy);
        cConfusion = findViewById(R.id.chipConfusion);
        cSerenity = findViewById(R.id.chipSerenity);
        cFear = findViewById(R.id.chipFear);
        cStress = findViewById(R.id.chipStress);
        cSadness = findViewById(R.id.chipSadness);
        cAnger = findViewById(R.id.chipAnger);

        Button next = findViewById(R.id.nextBttn4);
        next.setOnClickListener(view -> {
            startActivity(new Intent(this, MainActivity4.class));
        });
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

    public void setupSpinner(Spinner spinner){
        List<String> list = new ArrayList<>();
        list.add(getString(R.string.listitem1));
        list.add(getString(R.string.listitem2));
        list.add(getString(R.string.listitem3));
        list.add(getString(R.string.listitem4));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}
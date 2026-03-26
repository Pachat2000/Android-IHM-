package com.example.projectihm;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MenuActivity extends AppCompatActivity {

    private static final String TAG = "MenuActivity";
    private Button startActivityBtt;
    private Button historicBtt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.menu_activity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        startActivityBtt = findViewById(R.id.startActivityBtt);
        historicBtt = findViewById(R.id.HistoricBtt);


        startActivityBtt.setOnClickListener(view -> {
            startActivity(new Intent(this, UserInfoActivity.class));
        });

        historicBtt.setOnClickListener(view -> {
            startActivity(new Intent(this, HistoricActivity.class));
        });



    }
}
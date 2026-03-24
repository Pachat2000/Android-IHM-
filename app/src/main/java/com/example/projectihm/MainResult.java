package com.example.projectihm;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainResult extends AppCompatActivity {

    TextView userName;
    TextView userSatisfaction;
    TextView userDreaming;
    TextView userEmotion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_result);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    public void setResult(){
        int answerId = getIntent().getIntExtra("ANSWER_ID", -1);
        int userId = getIntent().getIntExtra("USER_ID",-1);
        boolean userSatisfaction = getIntent().getBooleanExtra("USER_OPINION", false);



    }
}
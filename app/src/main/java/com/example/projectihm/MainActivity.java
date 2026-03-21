package com.example.projectihm;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    private EditText userFirstName;
    private EditText userLastName;
    private EditText userEmailAdress;

    //@SuppressLint("MissingInflatedId")
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

        Button next = findViewById(R.id.nextBttn);
        next.setOnClickListener(view -> {
            startActivity(new Intent(this, MainActivity2.class));
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

//    public void addUser(View view){
//        AppDatabase db = AppDatabase.getDatabase(this);
//        Users user = new Users(
//                userFirstName.getText().toString(),
//                userLastName.getText().toString(),
//                userEmailAdress.getText().toString()
//        );
//
//        Single<Long> c = db.usersDAO().insertUserAsync(user);
//
//        Disposable disposable = c.subscribeOn(Schedulers.io())
//                .subscribe((userId) -> {
//                    // succes de l'insertion
//                    MainActivity.this.runOnUiThread(() -> {
//                        Toast.makeText(this, "Information registered", Toast.LENGTH_SHORT).show();
//                        Log.d(TAG,"Ajouté : "+user);
//                    });
//                    // user association to the answer given, only if user created
//                    registerAnswers(view, userId.intValue());
//                }, throwable -> {
//                    // en cas d'erreur
//                    runOnUiThread(() -> {
//                        Toast.makeText(this, "Error while registering your information", Toast.LENGTH_SHORT).show();
//                        Log.e(TAG, "Error on insert in users", throwable);
//                    });
//                });
//    }
//
//    public void registerAnswers(View view, int userId){
//        AppDatabase db = AppDatabase.getDatabase(this);
//
//        // collect answers
//        float dFrequency = dreamFrequency.getProgress();
//        float dDetails = dreamDetails.getRating();
//        float dLucid = 0.f;
//
//        if(sNever.isChecked()) dLucid += 1.f;
//        else if(sRarely.isChecked())dLucid += 2.f;
//        else if(sSometime.isChecked()) dLucid += 3.f;
//        else if(sOften.isChecked()) dLucid += 4.f;
//
//        float tFamilly = 0.f;
//        float tLove = 0.f;
//        float tWork = 0.f;
//        float tFall = 0.f;
//
//        if(rbFamillyN.isChecked()) tFamilly = 1.0f;
//        else if(rbFamillyR.isChecked()) tFamilly = 2.0f;
//        else if(rbFamillyO.isChecked()) tFamilly = 3.0f;
//        else if(rbFamillyS.isChecked()) tFamilly = 4.0f;
//
//        if(rbLoveN.isChecked()) tLove = 1.0f;
//        else if(rbLoveR.isChecked()) tLove = 2.0f;
//        else if(rbLoveO.isChecked()) tLove = 3.0f;
//        else if(rbLoveS.isChecked()) tLove = 4.0f;
//
//        if(rbWorkN.isChecked()) tWork = 1.0f;
//        else if(rbWorkR.isChecked()) tWork = 2.0f;
//        else if(rbWorkO.isChecked()) tWork = 3.0f;
//        else if(rbWorkS.isChecked()) tWork = 4.0f;
//
//        if(rbFallN.isChecked()) tFall = 1.0f;
//        else if(rbFallR.isChecked()) tFall = 2.0f;
//        else if(rbFallO.isChecked()) tFall = 3.0f;
//        else if(rbFallS.isChecked()) tFall = 4.0f;
//
//
//        Answers answer = new Answers(userId,dFrequency,dDetails,dLucid,tFamilly,tLove,tWork,tFall,
//                cJoy.isChecked() ? 2.f : 1.f,
//                cConfusion.isChecked() ? 2.f : 1.f,
//                cSerenity.isChecked() ? 2.f : 1.f,
//                cFear.isChecked() ? 2.f : 1.f,
//                cStress.isChecked() ? 2.f : 1.f,
//                cStress.isChecked() ? 2.f : 1.f,
//                cAnger.isChecked() ? 2.f : 1.f,
//                sOpinion.getSelectedItem().toString());
//
//        Completable c = db.answersDAO().insertAllAsync(answer);
//
//        Disposable disposable = c.subscribeOn(Schedulers.io())
//                .subscribe(() -> {
//                    // succes de l'insertion
//                    MainActivity.this.runOnUiThread(() -> {
//                        Toast.makeText(this, "Answer registered", Toast.LENGTH_SHORT).show();
//                        Log.d(TAG,"Added : "+answer);
//                    });
//                }, throwable -> {
//                    // en cas d'erreur
//                    runOnUiThread(() -> {
//                        Toast.makeText(this, "Error while registering your answers", Toast.LENGTH_SHORT).show();
//                        Log.e(TAG, "Error on insert in answers", throwable);
//                    });
//                });
//
//    }
//
//    public void evaluateAnswers(View view){
//        addUser(view);
//    }
//
//    public void testing(View view){
//        Log.d(TAG,"Etoiles :"+dreamDetails.getNumStars());
//    }
}
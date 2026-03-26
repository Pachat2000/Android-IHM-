package com.example.projectihm;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projectihm.dbmanager.AppDatabase;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LucidDreamActivity extends AppCompatActivity {

    public static String TAG = "LucidDreamActivity";
    public Intent nextActiviyIntent;

    private CheckBox candyCB;
    private CheckBox teddyCB;
    private CheckBox freddyCB;
    private CheckBox moneyCB;
    private CheckBox exesCB;
    private CheckBox clownCB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.lucid_dream_activity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        LinearLayout header5 = findViewById(R.id.header_section5);
        LinearLayout body5 = findViewById(R.id.body_section5);
        ImageView arrow5 = findViewById(R.id.ImgArrowSectionUp4);

        setupAction(header5 ,body5, arrow5);

        candyCB = findViewById(R.id.candyCB);
        teddyCB = findViewById(R.id.teddyCB);
        freddyCB = findViewById(R.id.freddyCB);
        moneyCB = findViewById(R.id.moneyCB);
        exesCB = findViewById(R.id.exeCB);
        clownCB = findViewById(R.id.clownCB);

        Button next = findViewById(R.id.nextBttn4);
        nextActiviyIntent = new Intent(this, FeedBackActivity.class);
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

    /**
     * Update answers score base on the user answer in this activity
     * @param view
     */
    public void updateAnswer(View view){
        AppDatabase db = AppDatabase.getDatabase(this);

        int answerId = getIntent().getIntExtra("ANSWER_ID", -1);
        int userId = getIntent().getIntExtra("USER_ID",-1);

        float dDegree = 0.f;
        float joyDegree = 0.f;
        float angerDegree = 0.f;
        float stressDegree = 0.f;
        float sadDegree = 0.f;

        if(candyCB.isChecked()) joyDegree+= 4.f;
        if(teddyCB.isChecked()) sadDegree += 5.f;
        if(freddyCB.isChecked()) dDegree += 4.f;
        if(moneyCB.isChecked()) joyDegree += 2.f;
        if(exesCB.isChecked())angerDegree += 8.f;
        if(clownCB.isChecked()) stressDegree += 10.f;

        float resJoyDegree = joyDegree;
        float resAngerDegree = angerDegree;
        float resStressDegree = stressDegree;
        float resSadDegree = sadDegree;
        float resDDegree = dDegree;
        Completable c = Completable.fromAction(() -> {
            db.answersDAO().addDreamingDegree(answerId, userId, resDDegree);
            db.answersDAO().addJoyDegree(answerId, userId, resJoyDegree);
            db.answersDAO().addAngerDegree(answerId, userId, resAngerDegree);
            db.answersDAO().addStressDegree(answerId, userId, resStressDegree);
            db.answersDAO().addSadnessDegree(answerId, userId, resSadDegree);
        });

        Disposable disposable = c.subscribeOn(Schedulers.io())
                .subscribe(() -> {
                    // exécution sur le thread background
                    LucidDreamActivity.this.runOnUiThread(() -> {
                        Toast.makeText(this, "Answer udpated", Toast.LENGTH_SHORT).show();
                        Log.d(TAG,"Answer updated "+answerId+" for user "+userId);
                    });
                    // information passing to next activity
                    nextActiviyIntent.putExtra("USER_ID", userId);
                    nextActiviyIntent.putExtra("ANSWER_ID",answerId);
                    // next activity starting
                    startActivity(nextActiviyIntent);
                }, throwable -> {
                    LucidDreamActivity.this.runOnUiThread(() -> {
                        Toast.makeText(this, "Error while registering your answers", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Error on update answer", throwable);
                    });
                });
    }
}
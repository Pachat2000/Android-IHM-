package com.example.projectihm;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projectihm.dbmanager.AppDatabase;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DreamEmotionActivity extends AppCompatActivity {

    public static final String TAG = "DreamEmotionActivity";
    public Intent nextActiviyIntent;

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
        setContentView(R.layout.dream_emotions_activity);
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

        nextActiviyIntent = new Intent(this, LucidDreamActivity.class);
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

        if(cJoy.isChecked()) joyDegree+= 4.f;
        if(cConfusion.isChecked()) stressDegree += 2.f;
        if(cSerenity.isChecked()) joyDegree += 2.f;
        if(cFear.isChecked()) stressDegree += 2.f;
        if(cStress.isChecked())stressDegree += 4.f;
        if(cSadness.isChecked()) sadDegree += 4.f;
        if(cAnger.isChecked()) angerDegree += 4.f;

        if(sOpinion.getSelectedItemPosition() == 0) stressDegree += 5.f;
        else if(sOpinion.getSelectedItemPosition() == 1) joyDegree += 2.f;
        else if(sOpinion.getSelectedItemPosition() == 2) dDegree += 2.f;
        else if(sOpinion.getSelectedItemPosition() == 3) stressDegree += 2.f;

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
                    DreamEmotionActivity.this.runOnUiThread(() -> {
                        Toast.makeText(this, "Answer udpated", Toast.LENGTH_SHORT).show();
                        Log.d(TAG,"Answer updated "+answerId+" for user "+userId);
                    });
                    // information passing to next activity
                    nextActiviyIntent.putExtra("USER_ID", userId);
                    nextActiviyIntent.putExtra("ANSWER_ID",answerId);
                    // next activity starting
                    startActivity(nextActiviyIntent);
                }, throwable -> {
                    DreamEmotionActivity.this.runOnUiThread(() -> {
                        Toast.makeText(this, "Error while registering your answers", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Error on update answer", throwable);
                    });
                });
    }
}
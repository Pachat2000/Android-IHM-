package com.example.projectihm;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projectihm.answers.Answers;
import com.example.projectihm.dbmanager.AppDatabase;
import com.example.projectihm.users.Users;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    private EditText userFirstName;
    private EditText userLastName;
    private EditText userEmailAdress;

    private SeekBar dreamFrequency;
    private RatingBar dreamDetails;

    private Switch sOften;
    private Switch sRarely;
    private Switch sSometime;
    private Switch sNever;

    private RadioButton rbFamillyN;
    private RadioButton rbFamillyO;
    private RadioButton rbFamillyR;
    private RadioButton rbFamillyS;

    private RadioButton rbLoveN;
    private RadioButton rbLoveO;
    private RadioButton rbLoveR;
    private RadioButton rbLoveS;

    private RadioButton rbWorkN;
    private RadioButton rbWorkO;
    private RadioButton rbWorkR;
    private RadioButton rbWorkS;

    private RadioButton rbFallN;
    private RadioButton rbFallO;
    private RadioButton rbFallR;
    private RadioButton rbFallS;

    private Chip cJoy;
    private Chip cConfusion;
    private Chip cSerenity;
    private Chip cFear;
    private Chip cStress;
    private Chip cSadness;
    private Chip cAnger;
    private Spinner sOpinion;


    @SuppressLint("MissingInflatedId")
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

        LinearLayout header2 = findViewById(R.id.header_section2);
        LinearLayout body2 = findViewById(R.id.body_section2);
        ImageView arrow2 = findViewById(R.id.ImgArrowSectionUp2);

        LinearLayout header3 = findViewById(R.id.header_section3);
        LinearLayout body3 = findViewById(R.id.body_section3);
        ImageView arrow3 = findViewById(R.id.imgArrowSection3);

        LinearLayout header4 = findViewById(R.id.header_section4);
        LinearLayout body4 = findViewById(R.id.body_section4);
        ImageView arrow4 = findViewById(R.id.ImgArrowSectionUp3);

        sOpinion = findViewById(R.id.spinnerOpinion);



        TextView seekBarText = findViewById(R.id.lblSeekbarTracker);
        seekBarText.setText("0");
        dreamFrequency = findViewById(R.id.lblSeekBar);

        setupAction(header1, body1, arrow1);

        setupAction(header2, body2, arrow2);

        setupAction(dreamFrequency, seekBarText);

        setupAction(header3, body3, arrow3);
        setupAction(header4, body4, arrow4);
        setupSpinner(sOpinion);

        userFirstName = findViewById(R.id.editName);
        userLastName = findViewById(R.id.editLastName);
        userEmailAdress = findViewById(R.id.editEmail);

        dreamDetails = findViewById(R.id.ratingBar3);

        sOften = findViewById(R.id.swtAnswerOften);
        sOften.setOnCheckedChangeListener(swtGroupListener);
        sRarely = findViewById(R.id.swtAnswerRarely);
        sRarely.setOnCheckedChangeListener(swtGroupListener);
        sSometime = findViewById(R.id.swtAnswerSometime);
        sSometime.setOnCheckedChangeListener(swtGroupListener);
        sNever = findViewById(R.id.swtAnswerNever);
        sNever.setOnCheckedChangeListener(swtGroupListener);

        rbFamillyN = findViewById(R.id.rbFamilyNever);
        rbFamillyO = findViewById(R.id.rbFamilyOften);
        rbFamillyR = findViewById(R.id.rbFamilyRarely);
        rbFamillyS = findViewById(R.id.rbFamilySometime);

        rbLoveN = findViewById(R.id.rbLoveNever);
        rbLoveO = findViewById(R.id.rbLoveOften);
        rbLoveR = findViewById(R.id.rbLoveRarely);
        rbLoveS = findViewById(R.id.rbLoveSometime);

        rbWorkN = findViewById(R.id.rbWorkNever);
        rbWorkO = findViewById(R.id.rbWorkOften);
        rbWorkR = findViewById(R.id.rbWorkRarely);
        rbWorkS = findViewById(R.id.rbWorkSometime);

        rbFallN = findViewById(R.id.rbFallNever);
        rbFallO = findViewById(R.id.rbFallOften);
        rbFallR = findViewById(R.id.rbFallRarely);
        rbFallS = findViewById(R.id.rbFallSometime);

        cJoy = findViewById(R.id.chipJoy);
        cConfusion = findViewById(R.id.chipConfusion);
        cSerenity = findViewById(R.id.chipSerenity);
        cFear = findViewById(R.id.chipFear);
        cStress = findViewById(R.id.chipStress);
        cSadness = findViewById(R.id.chipSadness);
        cAnger = findViewById(R.id.chipAnger);
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

    public void setupAction(SeekBar seekBar, TextView seekBarText){
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                seekBarText.setText(String.valueOf(i));
                int width = seekBar.getWidth() - seekBar.getPaddingLeft() - seekBar.getPaddingRight();

                float pos = (float) (i * width) / seekBar.getMax();
                seekBarText.setX(pos + seekBar.getX()+35);
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
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

    /***
     * Listener to set all switch to unchecked if one is checked
     */
    Switch.OnCheckedChangeListener swtGroupListener = new Switch.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                if (buttonView != sOften) sOften.setChecked(false);
                if (buttonView != sRarely) sRarely.setChecked(false);
                if (buttonView != sSometime) sSometime.setChecked(false);
                if (buttonView != sNever) sNever.setChecked(false);
            }
        }
    };

    public void addUser(View view){
        AppDatabase db = AppDatabase.getDatabase(this);
        Users user = new Users(
                userFirstName.getText().toString(),
                userLastName.getText().toString(),
                userEmailAdress.getText().toString()
        );

        Single<Long> c = db.usersDAO().insertUserAsync(user);

        Disposable disposable = c.subscribeOn(Schedulers.io())
                .subscribe((userId) -> {
                    // succes de l'insertion
                    MainActivity.this.runOnUiThread(() -> {
                        Toast.makeText(this, "Information registered", Toast.LENGTH_SHORT).show();
                        Log.d(TAG,"Ajouté : "+user);
                    });
                    // user association to the answer given, only if user created
                    registerAnswers(view, userId.intValue());
                }, throwable -> {
                    // en cas d'erreur
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Error while registering your information", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Error on insert in users", throwable);
                    });
                });
    }

    public void registerAnswers(View view, int userId){
        AppDatabase db = AppDatabase.getDatabase(this);

        // collect answers
        float dFrequency = dreamFrequency.getProgress();
        float dDetails = dreamDetails.getRating();
        float dLucid = 0.f;

        if(sNever.isChecked()) dLucid += 1.f;
        else if(sRarely.isChecked())dLucid += 2.f;
        else if(sSometime.isChecked()) dLucid += 3.f;
        else if(sOften.isChecked()) dLucid += 4.f;

        float tFamilly = 0.f;
        float tLove = 0.f;
        float tWork = 0.f;
        float tFall = 0.f;

        if(rbFamillyN.isChecked()) tFamilly = 1.0f;
        else if(rbFamillyR.isChecked()) tFamilly = 2.0f;
        else if(rbFamillyO.isChecked()) tFamilly = 3.0f;
        else if(rbFamillyS.isChecked()) tFamilly = 4.0f;

        if(rbLoveN.isChecked()) tLove = 1.0f;
        else if(rbLoveR.isChecked()) tLove = 2.0f;
        else if(rbLoveO.isChecked()) tLove = 3.0f;
        else if(rbLoveS.isChecked()) tLove = 4.0f;

        if(rbWorkN.isChecked()) tWork = 1.0f;
        else if(rbWorkR.isChecked()) tWork = 2.0f;
        else if(rbWorkO.isChecked()) tWork = 3.0f;
        else if(rbWorkS.isChecked()) tWork = 4.0f;

        if(rbFallN.isChecked()) tFall = 1.0f;
        else if(rbFallR.isChecked()) tFall = 2.0f;
        else if(rbFallO.isChecked()) tFall = 3.0f;
        else if(rbFallS.isChecked()) tFall = 4.0f;


        Answers answer = new Answers(userId,dFrequency,dDetails,dLucid,tFamilly,tLove,tWork,tFall,
                cJoy.isChecked() ? 2.f : 1.f,
                cConfusion.isChecked() ? 2.f : 1.f,
                cSerenity.isChecked() ? 2.f : 1.f,
                cFear.isChecked() ? 2.f : 1.f,
                cStress.isChecked() ? 2.f : 1.f,
                cStress.isChecked() ? 2.f : 1.f,
                cAnger.isChecked() ? 2.f : 1.f,
                sOpinion.getSelectedItem().toString());

        Completable c = db.answersDAO().insertAllAsync(answer);

        Disposable disposable = c.subscribeOn(Schedulers.io())
                .subscribe(() -> {
                    // succes de l'insertion
                    MainActivity.this.runOnUiThread(() -> {
                        Toast.makeText(this, "Answer registered", Toast.LENGTH_SHORT).show();
                        Log.d(TAG,"Added : "+answer);
                    });
                }, throwable -> {
                    // en cas d'erreur
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Error while registering your answers", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Error on insert in answers", throwable);
                    });
                });

    }

    public void evaluateAnswers(View view){
        addUser(view);
    }

    public void testing(View view){
        Log.d(TAG,"Etoiles :"+dreamDetails.getNumStars());
    }
}
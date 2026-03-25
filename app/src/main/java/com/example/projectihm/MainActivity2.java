package com.example.projectihm;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.SeekBar;
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

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity2 extends AppCompatActivity {

    public static final String TAG = "MainActivity2";

    public Intent nextActiviyIntent;
    private SeekBar dreamFrequency;
    private RatingBar dreamDetails;

    private Switch sJoy;
    private Switch sConfusion;
    private Switch sFrustration;
    private Switch sFear;

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

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        LinearLayout header2 = findViewById(R.id.header_section2);
        LinearLayout body2 = findViewById(R.id.body_section2);
        ImageView arrow2 = findViewById(R.id.ImgArrowSectionUp2);

        LinearLayout header3 = findViewById(R.id.header_section3);
        LinearLayout body3 = findViewById(R.id.body_section3);
        ImageView arrow3 = findViewById(R.id.imgArrowSection3);

        setupAction(header2, body2, arrow2);

        setupAction(header3, body3, arrow3);

        TextView seekBarText = findViewById(R.id.lblSeekbarTracker);
        seekBarText.setText("0");
        dreamFrequency = findViewById(R.id.lblSeekBar);

        setupAction(dreamFrequency, seekBarText);

        dreamDetails = findViewById(R.id.ratingBar3);

        sJoy = findViewById(R.id.swtAnswerJoy);
        sJoy.setOnCheckedChangeListener(swtGroupListener);
        sConfusion = findViewById(R.id.swtAnswerConfusion);
        sConfusion.setOnCheckedChangeListener(swtGroupListener);
        sFrustration= findViewById(R.id.swtAnswerFrustration);
        sFrustration.setOnCheckedChangeListener(swtGroupListener);
        sFear = findViewById(R.id.swtAnswerFear);
        sFear.setOnCheckedChangeListener(swtGroupListener);

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

        Button next = findViewById(R.id.nextBttn2);
//        next.setOnClickListener(view -> {
//            startActivity(new Intent(this, MainActivity3.class));
//        });

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

    Switch.OnCheckedChangeListener swtGroupListener = new Switch.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                if (buttonView != sJoy) sJoy.setChecked(false);
                if (buttonView != sConfusion) sConfusion.setChecked(false);
                if (buttonView != sFrustration) sFrustration.setChecked(false);
                if (buttonView != sFear) sFear.setChecked(false);
            }
        }
    };

    /**
     * Create a new section in the answers table to register user's answers
     * @param view
     */
    public void registerAnswers(View view){
        AppDatabase db = AppDatabase.getDatabase(this);

        int userId = getIntent().getIntExtra("USER_ID",-1);

        // collect answers
        float dDegree = dreamFrequency.getProgress() + dreamDetails.getRating();
        float joyDegree = 0.f;
        float angerDegree = 0.f;
        float stressDegree = 0.f;
        float sadDegree = 0.f;

        if(rbFamillyN.isChecked()) joyDegree = 1.0f;
        else if(rbFamillyR.isChecked()) joyDegree = 2.0f;
        else if(rbFamillyS.isChecked()) joyDegree = 3.0f;
        else if(rbFamillyO.isChecked()) joyDegree = 4.0f;

        if(rbLoveN.isChecked()) joyDegree = 1.0f;
        else if(rbLoveR.isChecked()) joyDegree = 2.0f;
        else if(rbLoveS.isChecked()) joyDegree = 3.0f;
        else if(rbLoveO.isChecked()) joyDegree = 4.0f;

        if(rbWorkN.isChecked()) stressDegree += 1.0f;
        else if(rbWorkR.isChecked()) stressDegree += 2.0f;
        else if(rbWorkS.isChecked()) stressDegree += 3.0f;
        else if(rbWorkO.isChecked()) stressDegree += 4.0f;

        if(rbFallN.isChecked()) stressDegree = 1.0f;
        else if(rbFallR.isChecked()) stressDegree = 2.0f;
        else if(rbFallS.isChecked()) stressDegree = 3.0f;
        else if(rbFallO.isChecked()) stressDegree = 4.0f;

        if(sJoy.isChecked()) joyDegree += 2.f;
        else if(sConfusion.isChecked()) stressDegree += 2.f;
        else if(sFrustration.isChecked()) angerDegree += 2.f;
        else if(sFear.isChecked()) stressDegree += 2.f;

        Answers answer = new Answers(userId,dDegree,joyDegree,angerDegree,stressDegree,sadDegree);
        Single<Long> c = db.answersDAO().insertAnswerAsync(answer);

        Disposable disposable = c.subscribeOn(Schedulers.io())
                .subscribe((answerId) -> {
                    // success
                    MainActivity2.this.runOnUiThread(() -> {
                        Toast.makeText(this, "Answer registered", Toast.LENGTH_SHORT).show();
                        Log.d(TAG,"New answer "+answerId+" for user "+userId);
                    });

                    nextActiviyIntent = new Intent(this, MainActivity3.class);
                    nextActiviyIntent.putExtra("USER_ID", userId);
                    nextActiviyIntent.putExtra("ANSWER_ID",answerId.intValue());
                    startActivity(nextActiviyIntent);
                }, throwable -> {
                    //
                    MainActivity2.this.runOnUiThread(() -> {
                        Toast.makeText(this, "Error while registering your answers", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Error on update answer", throwable);
                    });
                });
    }
}
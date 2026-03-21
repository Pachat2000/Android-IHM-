package com.example.projectihm;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {

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
        next.setOnClickListener(view -> {
            startActivity(new Intent(this, MainActivity3.class));
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
}
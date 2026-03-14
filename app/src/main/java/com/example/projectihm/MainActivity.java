package com.example.projectihm;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projectihm.dbmanager.AppDatabase;
import com.example.projectihm.users.Users;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    private EditText userFirstName;
    private EditText userLastName;
    private EditText userEmailAdress;

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

        Spinner spinner = findViewById(R.id.spinner);



        TextView seekBarText = findViewById(R.id.lblSeekbarTracker);
        seekBarText.setText("0");
        SeekBar seekBar = findViewById(R.id.lblSeekBar);

        setupAction(header1, body1, arrow1);

        setupAction(header2, body2, arrow2);

        setupAction(seekBar, seekBarText);

        setupAction(header3, body3, arrow3);
        setupAction(header4, body4, arrow4);
        setupSpinner(spinner);

        userFirstName = findViewById(R.id.editName);
        userLastName = findViewById(R.id.editLastName);
        userEmailAdress = findViewById(R.id.editEmail);
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

    public void addUser(View view){
        AppDatabase db = AppDatabase.getDatabase(this);
        Users user = new Users(
                userFirstName.getText().toString(),
                userLastName.getText().toString(),
                userEmailAdress.getText().toString()
        );

        Completable c = db.usersDAO().insertAllAsync(user);

        Disposable disposable = c.subscribeOn(Schedulers.io())
                .subscribe(() -> {
                    // succes de l'insertion
                    MainActivity.this.runOnUiThread(() -> {
                        Toast.makeText(this, "Ajout avec succes", Toast.LENGTH_SHORT).show();
                        Log.d(TAG,"Ajouté : "+user);
                    });
                }, throwable -> {
                    // en cas d'erreur
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Erreur lors de l'ajout", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Erreur lors de l'insertion", throwable);
                    });
                });
    }

    public void registerAnswers(View view){

    }

    public void evaluateAnswers(View view){
        addUser(view);
    }
}
package com.example.projectihm;

import static java.lang.Math.max;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import com.example.projectihm.users.UsersDAO;
import android.widget.EditText;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.InputType;

import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainResult extends AppCompatActivity {
    public static String TAG = "MainResult";
    TextView userName;
    TextView userSatisfaction;
    TextView userDreaming;
    TextView userEmotion;

    int answerId;
    int userId;
    boolean userSat;

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
        // Ryan ce bout de code te permet de recuperer id du user depuis l'historique
        //int id = getIntent().getIntExtra("USER_ID_CLIQUE", -1);

        answerId = getIntent().getIntExtra("ANSWER_ID", -1);
        userId = getIntent().getIntExtra("USER_ID",-1);
        userSat = getIntent().getBooleanExtra("USER_OPINION", false);

        userName = findViewById(R.id.userNameLabel);
        userSatisfaction = findViewById(R.id.userSatisfaction);
        userEmotion = findViewById(R.id.dreamerTypeUser);
        userDreaming = findViewById(R.id.degreeDreamerLabel);

        setResult();
    }


    public void setResult() {

        if (userSat) {
            userSatisfaction.setText(getString(R.string.satisfiedUser));
        } else {
            userSatisfaction.setText(getString(R.string.unsatisfiedUser));
        }

        // emotion score recuperation in database
        AppDatabase db = AppDatabase.getDatabase(this);

        Single<Answers> answerSingle = Single.fromCallable(() ->
                db.answersDAO().getAnswerById(answerId)
        );

        Disposable dAnswer = answerSingle.subscribeOn(Schedulers.io())
                .subscribe(answer -> {
                    MainResult.this.runOnUiThread(() -> {
                        float joy = answer.getJoyDegree();
                        float stress = answer.getStressDegree();
                        float anger = answer.getAngerDegree();
                        float sad = answer.getSadnessDegree();

                        float dreaming = answer.getDreamingDegree();

                        float maxEmotion = max(joy, max(anger, max(stress, sad)));

                        if (maxEmotion == joy) {
                            userEmotion.setText(getString(R.string.joyfullDreamer));
                        } else if (maxEmotion == anger) {
                            userEmotion.setText(getString(R.string.angryfullDreamer));
                        } else if (maxEmotion == sad) {
                            userEmotion.setText(getString(R.string.sadDreamer));
                        } else {
                            userEmotion.setText(getString(R.string.stressfullDreamer));
                        }

                        if (dreaming >= 10) {
                            userDreaming.setText(R.string.BigDreamer);
                        } else {
                            userDreaming.setText(R.string.smallDreamer);
                        }

                        Toast.makeText(this, "Calculating your result", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Calculate result successful");
                    });


                }, throwable -> {
                    MainResult.this.runOnUiThread(() -> {
                        Toast.makeText(this, "Error while calculating your result", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Error calculating result for answer : " + answerId, throwable);
                    });
                });

        // user info recuperation
        Single<Users> userSingle = Single.fromCallable(() ->
                db.usersDAO().findById(userId)
        );

        Disposable dUser = userSingle.subscribeOn(Schedulers.io())
                .subscribe(user -> {
                    MainResult.this.runOnUiThread(() -> {
                        String name = user.getFirstName() + " " + user.getLastName();
                        userName.setText(name);

                        Toast.makeText(this, "Getting your info", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Get user info successful");
                    });
                }, throwable -> {
                    MainResult.this.runOnUiThread(() -> {
                        Toast.makeText(this, "Error while getting your info", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Error on update answer", throwable);
                    });
                });
    }
    // TODO : tester envoie email
    // Ryan : regarde le AlertDialog pour faire facilement l'envoie de l'email
    // On pourra facilement recuperer le mail que l'utilisateur voudras rentrer
    @SuppressLint("IntentReset")
    protected void sendResult() {
        // user email recuperation
        AppDatabase db = AppDatabase.getDatabase(this);

        Single<Users> userSingle = Single.fromCallable(() ->
                db.usersDAO().findById(userId)
        );

        Disposable dUser = userSingle.subscribeOn(Schedulers.io())
                .subscribe(user ->{
                    MainResult.this.runOnUiThread(() -> {
                        String userEmail = user.getEmailAdress();

                        String[] TO = {userEmail};
                        Intent emailIntent = new Intent(Intent.ACTION_SEND);
                        emailIntent.setData(Uri.parse("mailto:"));
                        emailIntent.setType("text/plain");

                        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
                        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

                        try {
                            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                            finish();
                            Log.i("Finished sending email...", "");
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(MainResult.this,
                                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
                        }


                        Toast.makeText(this, "Email sent", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Email send successfully");
                    });
                }, throwable -> {
                    MainResult.this.runOnUiThread(() -> {
                        Toast.makeText(this, "Error while sending email", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Error sending email", throwable);
                    });
                });

        //Log.i(TAG, "Email send to user");
    }

    // TODO : fonction pour put les phrase dans la bd

    // TODO : fonction pour ecrire dans un fichier les resultats
}
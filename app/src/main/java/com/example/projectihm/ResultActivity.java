package com.example.projectihm;

import static java.lang.Math.max;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projectihm.answers.Answers;
import com.example.projectihm.dbmanager.AppDatabase;
import com.example.projectihm.results.Results;
import com.example.projectihm.users.Users;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ResultActivity extends AppCompatActivity {
    public static String TAG = "ResultActivity";
    TextView userName;
    TextView userSatisfaction;
    TextView userDreaming;
    TextView userEmotion;

    int answerId;
    int userId;
    boolean userSat;

    Button sendResultBtn;
    Button getResultBtn;
    Button backBtn;


    String textResult;
    String resultFileName;


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

        sendResultBtn = findViewById(R.id.sendResultBtn);
        sendResultBtn.setOnClickListener(this::sendResult);

        getResultBtn = findViewById(R.id.registerResultBtn);
        getResultBtn.setOnClickListener(this::registerResult);

        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
        });


        // to know if we are getting on result page from historic activity or after answers activities
        if(answerId != -1)setResultFromAnswers();
        else setResultFromDB();


    }

    /**
     * Set the result page based on user answers
     * Considering we get on this activity through answering first
     */
    public void setResultFromAnswers() {

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
                    ResultActivity.this.runOnUiThread(() -> {
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

                        // registering result setting in database
                        registerResultDB();

                        Toast.makeText(this, "Calculating your result", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Calculate result from answers successful");
                    });


                }, throwable -> {
                    ResultActivity.this.runOnUiThread(() -> {
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
                    ResultActivity.this.runOnUiThread(() -> {
                        String name = user.getFirstName() + " " + user.getLastName();
                        userName.setText(name);

                        // file name for the user if they want to register their result
                        resultFileName = "Dream_analyse_"+user.getFirstName()+"_"+user.getLastName()+".txt";

                        Toast.makeText(this, "Getting your info", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Get user info successful");
                    });
                }, throwable -> {
                    ResultActivity.this.runOnUiThread(() -> {
                        Toast.makeText(this, "Error while getting your info", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Error on update answer", throwable);
                    });
                });
    }

    /**
     * Set the result page base on the information stored in the results table
     * Considering we are getting on this activity throw historic
     */
    public void setResultFromDB() {

        // result recuperation in database
        AppDatabase db = AppDatabase.getDatabase(this);

        Single<Results> resultSingle = Single.fromCallable(() ->
                db.resultsDAO().getResultByUserId(userId)
        );

        Disposable dResult = resultSingle.subscribeOn(Schedulers.io())
                .subscribe(result -> {
                    ResultActivity.this.runOnUiThread(() -> {
                        String opinion = result.getOpinion();
                        String emotionAnalyse = result.getEmotionAnalyse();
                        String dreamerType = result.getDreamerType();

                        userEmotion.setText(emotionAnalyse);
                        userDreaming.setText(dreamerType);
                        userSatisfaction.setText(opinion);

                        Toast.makeText(this, "Calculating your result", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Calculate result from DB successful");
                    });

                }, throwable -> {
                    ResultActivity.this.runOnUiThread(() -> {
                        Toast.makeText(this, "Error while calculating your result", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Error getting result from DB : " + answerId, throwable);
                    });
                });

        // user info recuperation
        Single<Users> userSingle = Single.fromCallable(() ->
                db.usersDAO().findById(userId)
        );

        Disposable dUser = userSingle.subscribeOn(Schedulers.io())
                .subscribe(user -> {
                    ResultActivity.this.runOnUiThread(() -> {
                        String name = user.getFirstName() + " " + user.getLastName();
                        userName.setText(name);

                        resultFileName = "Dream_analyse_"+user.getFirstName()+"_"+user.getLastName()+".txt";

                        Toast.makeText(this, "Getting your info", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Get user info successful");
                    });
                }, throwable -> {
                    ResultActivity.this.runOnUiThread(() -> {
                        Toast.makeText(this, "Error while getting your info", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Error on update getting user info", throwable);
                    });
                });
    }

    /**
     * Set a text with the analyse made to the user, and allow them to send this text trough any other application
     * @param view view
     */
    protected void sendResult(View view) {
        Intent sendIntent = new Intent();
        textResult = userSatisfaction.getText().toString() + "\n" + userDreaming.getText().toString() + "\n" + userEmotion.getText().toString();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, textResult);
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }

    /**
     * Register all user result in an external file
     * @param view view
     */
    public void registerResult(View view){

        textResult = userSatisfaction.getText().toString() + "\n" + userDreaming.getText().toString() + "\n" + userEmotion.getText().toString();

        File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        File fileOut = new File(folder,resultFileName);

        Log.d(TAG, "Data saved in " + fileOut.getAbsolutePath());
        Log.d(TAG, "Data saved in " + fileOut.getName());

        try (FileOutputStream fos = new FileOutputStream(fileOut)) {
            PrintStream ps = new PrintStream(fos);
            ps.println(textResult);
            ps.close();

            Toast.makeText(this, "File download", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "File download successful : " + textResult);
        } catch (FileNotFoundException e) {
            Toast.makeText(this, "Error while downloading file", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "File not found", e);
        } catch (IOException e) {
            Toast.makeText(this, "Error while downloading file", Toast.LENGTH_SHORT).show();
            Log.e(TAG,"Error I/O",e);
        }
    }


    /**
     * Register all text set up in the result page, to use it later with the historic
     */
    public void registerResultDB() {
        AppDatabase db = AppDatabase.getDatabase(this);

        Results result = new Results(userId, userSatisfaction.getText().toString(),userDreaming.getText().toString(), userEmotion.getText().toString());

        Single<Long> c = db.resultsDAO().insertResultAsync(result);

        Disposable disposable = c.subscribeOn(Schedulers.io())
                .subscribe((resultId) -> {
                    // successful insert
                    ResultActivity.this.runOnUiThread(() -> {
                        Toast.makeText(this, "Result registered", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Added : " + result);
                    });
                }, throwable -> {
                    // failed insert
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Error while registering your result", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Error on insert in results", throwable);
                    });
                });
    }
}
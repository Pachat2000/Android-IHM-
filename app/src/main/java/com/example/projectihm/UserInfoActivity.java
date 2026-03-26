package com.example.projectihm;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projectihm.dbmanager.AppDatabase;
import com.example.projectihm.users.Users;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UserInfoActivity extends AppCompatActivity {
    public static final String TAG = "UserInfoActivity";

    private EditText userFirstName;
    private EditText userLastName;
    private EditText userEmailAdress;

    public Intent nextActiviyIntent;

    //@SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.user_info_activity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        nextActiviyIntent = new Intent(this, DreamDetailsActivity.class);

        LinearLayout header1 = findViewById(R.id.header_section1);
        LinearLayout body1 = findViewById(R.id.body_section1);
        ImageView arrow1 = findViewById(R.id.ImgArrowSectionUp);

        userFirstName = findViewById(R.id.editName);
        userLastName = findViewById(R.id.editLastName);
        userEmailAdress = findViewById(R.id.editEmail);

        setupAction(header1, body1, arrow1);

        Button next = findViewById(R.id.nextBttn);

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
     * Add all information about the user in the database
     * And go to the next activity only if the user enter correct value
     * @param view
     */
    public void addUser(View view){
        AppDatabase db = AppDatabase.getDatabase(this);
        if(userFirstName.getText().toString().isEmpty() || userLastName.getText().toString().isEmpty() || userEmailAdress.getText().toString().isEmpty()){
            Toast.makeText(this, getString(R.string.enter_value), Toast.LENGTH_SHORT).show();
        }
        else {
            Users user = new Users(
                    userFirstName.getText().toString(),
                    userLastName.getText().toString(),
                    userEmailAdress.getText().toString()
            );

            Single<Long> c = db.usersDAO().insertUserAsync(user);

            Disposable disposable = c.subscribeOn(Schedulers.io())
                    .subscribe((userId) -> {
                        // successful insert
                        UserInfoActivity.this.runOnUiThread(() -> {
                            Toast.makeText(this, "Information registered", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "Added : " + user);
                        });
                        // user id recuperation for next activity
                        nextActiviyIntent.putExtra("USER_ID", userId.intValue());
                        // new activity starting
                        startActivity(nextActiviyIntent);
                    }, throwable -> {
                        // failed insert
                        runOnUiThread(() -> {
                            Toast.makeText(this, "Error while registering your information", Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "Error on insert in users", throwable);
                        });
                    });
        }
    }

    // TODO : ajouter un bouton historique pour accedet à un page d'historique
    //  voir comment récuperer les infos quand un utilisateur clique sur un des boutons de l'historique pour afficher
    //  le resultat dans la page qui faut (voir pour réutiliser la page résultat existante ??)
}
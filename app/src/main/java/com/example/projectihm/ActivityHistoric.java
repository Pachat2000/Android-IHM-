package com.example.projectihm;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectihm.answers.Answers;
import com.example.projectihm.answers.AnswersDAO;
import com.example.projectihm.dbmanager.AppDatabase;
import com.example.projectihm.users.Users;
import com.example.projectihm.users.UsersDAO;

import java.util.List;
import java.util.Locale;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ActivityHistoric extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_historic);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView lblSize = findViewById(R.id.editSize);
        lblSize.setText(R.string.loading);
        RecyclerView recyclerView = findViewById(R.id.listPerson);


        UsersDAO dao = AppDatabase.getDatabase(this).usersDAO();
        Single<List<Users>> single =  dao.getUsersAsync();

        Disposable disposable = single.subscribeOn(Schedulers.io())
                .subscribe(
                        (historic) -> runOnUiThread(() -> {

                            lblSize.setText(String.format(Locale.getDefault(),"There are %d persons",historic.size()));
                            UsersRecyclerViewAdapter adapter = new UsersRecyclerViewAdapter(historic);
                            recyclerView.setAdapter(adapter);

                            // ATTENTION: il faut obligatoirement un layout!!!
                            //recyclerView.setLayoutManager(new LinearLayoutManager(this));
                            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                        }),
                        throwable -> {
                            // en cas d'erreur
                            runOnUiThread(() -> {
                                lblSize.setText(R.string.error_loading);
                                Toast.makeText(this, "Erreur lors de l'ajout", Toast.LENGTH_SHORT).show();
                                Log.e("PersonneActivity", "Erreur lors de l'insertion", throwable);
                            });
                        }
                );

    }

    public void closeActivity(View v) {
        finish();
    }
}
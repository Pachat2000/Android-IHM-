package com.example.projectihm.dbmanager;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.projectihm.answers.Answers;
import com.example.projectihm.answers.AnswersDAO;
import com.example.projectihm.users.Users;
import com.example.projectihm.users.UsersDAO;

@Database(entities = {Users.class, Answers.class}, version = 4, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UsersDAO usersDAO();

    public abstract AnswersDAO answersDAO();

    private static AppDatabase INSTANCE = null;

    public static synchronized AppDatabase getDatabase(Context context) {
        Context appcontext = context.getApplicationContext();
        if(INSTANCE == null || !INSTANCE.isOpen())
            INSTANCE = Room.databaseBuilder(appcontext, AppDatabase.class , "dreamform.db").fallbackToDestructiveMigration().build();
        return INSTANCE;
    }

    public static void closeDatabase() {
        if(INSTANCE != null)
            INSTANCE.close();
    }
}

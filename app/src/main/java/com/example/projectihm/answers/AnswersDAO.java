package com.example.projectihm.answers;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.projectihm.users.Users;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface AnswersDAO {
    /**
     * This method get all answer to the form that are registered in the database
     *
     * @return users in the database
     */
    @Query("SELECT * FROM answers")
    List<Answers> getAnswers();

    /**
     * ASYNCHRONE
     * Get all users in the database
     *
     * @return list of all users in the database
     */
    @Query("SELECT * FROM answers")
    Single<List<Answers>> getUsersAsync();

    @Query("SELECT * FROM answers WHERE uid IN (:uids)")
    List<Answers> loadAllByIds(int[] uids);

    @Query("SELECT * FROM answers WHERE user_id LIKE :uid LIMIT 1")
    Answers findByUid(int uid);

    /**
     * Update an answer in the database and ignoring conflicts
     *
     * @param answer answer to update
     * @return number of lines udpated in the database
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    int update(Answers answer);

    /**
     * Insert data into the database
     * @param answers answers needed to be add to the database
     * @return lines id
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(Answers... answers);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAllAsync(Answers... answers);


    /**
     * Delete a user from the database
     * @param answer user to delete
     * @return Deleted lines
     */
    @Delete
    int delete(Answers answer);
}

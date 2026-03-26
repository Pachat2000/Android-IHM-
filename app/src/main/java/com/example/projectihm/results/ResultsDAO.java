package com.example.projectihm.results;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.projectihm.results.Results;
import com.example.projectihm.users.Users;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface ResultsDAO {
    /**
     * This method get all result to the form that are registered in the database
     *
     * @return users in the database
     */
    @Query("SELECT * FROM results")
    List<Results> getResults();

    /**
     * ASYNCHRONE
     * Get all users in the database
     *
     * @return list of all results in the database
     */
    @Query("SELECT * FROM results")
    Single<List<Results>> getResultsAsync();

    @Query("SELECT * FROM results WHERE result_id IN (:uids)")
    List<Results> loadAllByIds(int[] uids);

    /**
     * get result using user id to find it
     * @param uid user id
     * @return associated result
     */
    @Query("SELECT * FROM results WHERE user_id LIKE :uid LIMIT 1")
    Results getResultByUserId(int uid);

    /**
     * Get text related to user opinion
     * @param uid result result_id
     * @return text
     */
    @Query("SELECT opinion FROM results WHERE result_id LIKE :uid LIMIT 1")
    String getOpinion(int uid);

    /**
     * Get text related to user emotion analyse
     * @param uid result result_id
     * @return text
     */
    @Query("SELECT emotion_analyse FROM results WHERE result_id LIKE :uid LIMIT 1")
    String getEmotionAnalyse(int uid);

    /**
     * Get text related to user dreamer type
     * @param uid result result_id
     * @return text
     */
    @Query("SELECT dreamer_type FROM results WHERE result_id LIKE :uid LIMIT 1")
    String getDreamerType(int uid);

    /**
     * Update an result in the database and ignoring conflicts
     *
     * @param result result to update
     * @return number of lines udpated in the database
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    int update(Results result);

    /**
     * Insert data into the database
     * @param results results needed to be add to the database
     * @return lines id
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(Results... results);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAllAsync(Results... results);

    // getting new result id to use later
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Single<Long> insertResultAsync(Results result);


    /**
     * Delete a user from the database
     * @param result user to delete
     * @return Deleted lines
     */
    @Delete
    int delete(Results result);
}

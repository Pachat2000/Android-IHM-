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
     * @return list of all answers in the database
     */
    @Query("SELECT * FROM answers")
    Single<List<Answers>> getUsersAsync();

    @Query("SELECT * FROM answers WHERE uid IN (:uids)")
    List<Answers> loadAllByIds(int[] uids);

    @Query("SELECT * FROM answers WHERE user_id LIKE :uid LIMIT 1")
    Answers getAnswerById(int uid);

    /**
     * Get joy score related to the answer which uid is set in parameter
     * @param uid answer uid
     * @return joy score
     */
    @Query("SELECT joy_degree FROM answers WHERE uid LIKE :uid LIMIT 1")
    float getJoyScore(int uid);

    /**
     * Get anger score related to the answer which uid is set in parameter
     * @param uid answer uid
     * @return anger score
     */
    @Query("SELECT anger_degree FROM answers WHERE uid LIKE :uid LIMIT 1")
    float getAngerScore(int uid);

    /**
     * Get stress score related to the answer which uid is set in parameter
     * @param uid answer uid
     * @return stress score
     */
    @Query("SELECT stress_degree FROM answers WHERE uid LIKE :uid LIMIT 1")
    float getStressScore(int uid);

    /**
     * Get sad score related to the answer which uid is set in parameter
     * @param uid answer uid
     * @return sad score
     */
    @Query("SELECT sadness_degree FROM answers WHERE uid LIKE :uid LIMIT 1")
    float getSadScore(int uid);

    /**
     * Get dream score related to the answer which uid is set in parameter
     * @param uid answer uid
     * @return dream score
     */
    @Query("SELECT dreaming_degree FROM answers WHERE uid LIKE :uid LIMIT 1")
    float getDreamScore(int uid);

    /**
     * Update the value of dreaming_degree column
     */
    @Query("UPDATE answers SET dreaming_degree = dreaming_degree + :newDreamingDegree WHERE user_id = :userId AND uid = :answerId")
    void addDreamingDegree(int answerId, int userId, float newDreamingDegree);

    /**
     * Update the value of joy_degree column
     */
    @Query("UPDATE answers SET joy_degree = joy_degree + :newJoyDegree WHERE user_id = :userId AND uid = :answerId")
    void addJoyDegree(int answerId, int userId, float newJoyDegree);

    /**
     * Update the value of anger_degree column
     */
    @Query("UPDATE answers SET anger_degree = anger_degree + :newAngerDegree WHERE user_id = :userId AND uid = :answerId")
    void addAngerDegree(int answerId, int userId, float newAngerDegree);

    /**
     * Update the value of stress_degree column
     */
    @Query("UPDATE answers SET stress_degree = stress_degree + :newStressDegree WHERE user_id = :userId AND uid = :answerId")
    void addStressDegree(int answerId, int userId, float newStressDegree);

    /**
     * Update the value of sadness_degree column
     */
    @Query("UPDATE answers SET sadness_degree = sadness_degree + :newSadnessDegree WHERE user_id = :userId AND uid = :answerId")
    void addSadnessDegree(int answerId, int userId, float newSadnessDegree);

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

    // getting new answer id to use later
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Single<Long> insertAnswerAsync(Answers answer);


    /**
     * Delete a user from the database
     * @param answer user to delete
     * @return Deleted lines
     */
    @Delete
    int delete(Answers answer);
}

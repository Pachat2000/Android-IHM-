package com.example.projectihm.users;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface UsersDAO {
    /**
     * This method get all users who answered the form and are register in the database
     *
     * @return users in the database
     */
    @Query("SELECT * FROM users")
    List<Users> getUsers();

    /**
     * ASYNCHRONE
     * Get all users in the database
     *
     * @return list of all users in the database
     */
    @Query("SELECT * FROM users")
    Single<List<Users>> getUsersAsync();

    @Query("SELECT * FROM users WHERE uid IN (:uids)")
    List<Users> loadAllByIds(int[] uids);

    @Query("SELECT * FROM users WHERE uid LIKE :uid LIMIT 1")
    Users findById(int uid);

    /**
     * Get a user last name using their uid
     * @param uid user uid
     * @return user last name
     */
    @Query("SELECT last_name FROM users WHERE uid LIKE :uid")
    String getLastName(int uid);

    /**
     * Get a user first name using their uid
     * @param uid user uid
     * @return user first name
     */
    @Query("SELECT first_name FROM users WHERE uid LIKE :uid")
    String getFirstName(int uid);

    /**
     * Update a user in the database and ignoring conflicts
     *
     * @param user user to update
     * @return number of lines udpated in the database
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    int update(Users user);

    /**
     * Insert data into the database
     * @param users user needed to be add to the database
     * @return lines id
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(Users... users);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAllAsync(Users... users);

    // getting new user id to give it to the answers table
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Single<Long> insertUserAsync(Users user);


    /**
     * Delete a user from the database
     * @param user user to delete
     * @return Deleted lines
     */
    @Delete
    int delete(Users user);

}

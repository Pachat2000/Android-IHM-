package com.example.projectihm.users;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class Users {
    @PrimaryKey(autoGenerate = true)
    int uid;

    @ColumnInfo(name = "first_name")
    private String firstName;

    @ColumnInfo(name = "last_name")
    private String lastName;

    @ColumnInfo(name = "email_adress")
    private String emailAdress;

    public Users(@NonNull String firstName,@NonNull String lastName, @NonNull String emailAdress) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAdress = emailAdress;
    }

    public int getUid() {
        return uid;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmailAdress(){return emailAdress;}

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmailAdress(String emailAdress){this.emailAdress = emailAdress;}

    @NonNull
    @Override
    public String toString() {
        return "Users{" +
                "uid=" + uid +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailAdress ='" + emailAdress +'\'' +
                '}';
    }
}

package com.example.projectihm.answers;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.example.projectihm.users.Users;

/***
 * This table is used to store all answer a user will give while
 * filling the form
 */
@Entity(tableName = "answers",
        foreignKeys = @ForeignKey(entity = Users.class, parentColumns = "uid", childColumns = "user_id", onDelete = ForeignKey.CASCADE),
        indices = @Index(value = "user_id"))
public class Answers {
    @PrimaryKey(autoGenerate = true)
    int answer_id;
    @ColumnInfo(name = "user_id")
    int userId;

    @ColumnInfo(name = "dreaming_degree")
    float dreamingDegree;

    @ColumnInfo(name = "joy_degree")
    float joyDegree;

    @ColumnInfo(name = "anger_degree")
    float angerDegree;

    @ColumnInfo(name = "stress_degree")
    float stressDegree;

    @ColumnInfo(name = "sadness_degree")
    float sadnessDegree;

    public Answers(int userId, float dreamingDegree, float joyDegree, float angerDegree, float stressDegree, float sadnessDegree)
    {
        this.userId = userId;
        this.dreamingDegree = dreamingDegree;
        this.joyDegree = joyDegree;
        this.angerDegree = angerDegree;
        this.stressDegree = stressDegree;
        this.sadnessDegree = sadnessDegree;
    }

    public int getAnswer_id(){return answer_id;}
    public int getUserId(){return userId;}
    public float getDreamingDegree(){return dreamingDegree;}
    public float getJoyDegree(){return joyDegree;}
    public float getAngerDegree(){return angerDegree;}
    public float getStressDegree() {return stressDegree;}
    public float getSadnessDegree() {return sadnessDegree;}
    public void setUserId(int userId) {this.userId = userId;}
    public void setDreamingDegree(float dreamingDegree) {this.dreamingDegree = dreamingDegree;}
    public void setJoyDegree(float joyDegree) {this.joyDegree = joyDegree;}
    public void setAngerDegree(float angerDegree) {this.angerDegree = angerDegree;}
    public void setStressDegree(float stressDegree) {this.stressDegree = stressDegree;}
    public void setSadnessDegree(float sadnessDegree) {this.sadnessDegree = sadnessDegree;}

    @NonNull
    @Override
    public String toString() {
        return "Answer{" +
                "answer_id=" + answer_id +
                ", user_id='" + userId + '\'' +
                ", dreamingDegree='" + dreamingDegree + '\'' +
                ", joyDegree='" + joyDegree + '\'' +
                ", angerDegree ='" + angerDegree +'\'' +
                ", stressDegree ='" + stressDegree +'\'' +
                ", sadnessDegree ='" + sadnessDegree +'\'' +
                '}';
    }
}


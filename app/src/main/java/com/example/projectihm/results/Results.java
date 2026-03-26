package com.example.projectihm.results;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.example.projectihm.users.Users;

@Entity(tableName = "results",
        foreignKeys = @ForeignKey(entity = Users.class, parentColumns = "uid", childColumns = "user_id", onDelete = ForeignKey.CASCADE),
        indices = @Index(value = "user_id"))

public class Results {
    @PrimaryKey(autoGenerate = true)
    int result_id;
    @ColumnInfo(name = "user_id")
    int userId;

    @ColumnInfo(name = "opinion")
    String opinion;

    @ColumnInfo(name = "dreamer_type")
    String dreamerType;

    @ColumnInfo(name = "emotion_analyse")
    String emotionAnalyse;


    public Results(int userId, String opinion, String dreamerType, String emotionAnalyse){
        this.userId = userId;
        this.opinion = opinion;
        this.dreamerType = dreamerType;
        this.emotionAnalyse = emotionAnalyse;
    }

    public int getResult_id(){return result_id;}

    public int getUserId(){return userId;}
    public String getOpinion(){return opinion;}
    public String getDreamerType(){return dreamerType;}
    public String getEmotionAnalyse(){return emotionAnalyse;}

    public void setUserId(int userId){this.userId = userId;}
    public void setOpinion(String opinion){this.opinion = opinion;}
    public void setDreamerType(String dreamerType){this.dreamerType = dreamerType;}
    public void setEmotionAnalyse(String emotionAnalyse){this.emotionAnalyse = emotionAnalyse;}

    @NonNull
    @Override
    public String toString() {
        return "Results{" +
                "result_id=" + result_id +
                ", user_id='" + userId + '\'' +
                ", opinion='" + opinion + '\'' +
                ", dreamer_type ='" + dreamerType + '\'' +
                ", emotion_analyse ='" + emotionAnalyse +'\'' +
                '}';
    }
}

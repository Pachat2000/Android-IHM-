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
    int uid;
    @ColumnInfo(name = "user_id")
    int userId;

    @ColumnInfo(name = "dream_frequency")
    float dreamFrequency;

    @ColumnInfo(name = "dream_details")
    float dreamDetails;

    @ColumnInfo(name = "lucid_dream")
    float lucidDream;

    @ColumnInfo(name = "topic_family")
    float topicFamily;

    @ColumnInfo(name = "topic_love")
    float topicLove;

    @ColumnInfo(name = "topic_work")
    float topicWork;

    @ColumnInfo(name = "topic_fall")
    float topicFall;

    @ColumnInfo(name = "emotion_joy")
    float emotionJoy;

    @ColumnInfo(name = "emotion_confusion")
    float emotionConfusion;

    @ColumnInfo(name = "emotion_serenity")
    float emotionSerenity;

    @ColumnInfo(name = "emotion_fear")
    float emotionFear;

    @ColumnInfo(name = "emotion_stress")
    float emotionStress;

    @ColumnInfo(name = "emotion_sadness")
    float emotionSadness;

    @ColumnInfo(name = "emotion_anger")
    float emotionAnger;

    @ColumnInfo(name = "emotion_opinion")
    String emotionOpinion;

    public Answers(int userId, float dreamFrequency, float dreamDetails, float lucidDream,
                   float topicFamily, float topicLove, float topicWork, float topicFall,
                   float emotionJoy, float emotionConfusion, float emotionSerenity, float emotionFear,
                   float emotionStress, float emotionSadness, float emotionAnger, @NonNull String emotionOpinion)
    {
        this.userId = userId;
        this.dreamFrequency = dreamFrequency;
        this.dreamDetails = dreamDetails;
        this.lucidDream = lucidDream;
        this.topicFamily = topicFamily;
        this.topicLove = topicLove;
        this.topicWork = topicWork;
        this.topicFall = topicFall;
        this.emotionJoy = emotionJoy;
        this.emotionConfusion = emotionConfusion;
        this.emotionSerenity = emotionSerenity;
        this.emotionFear = emotionFear;
        this.emotionStress = emotionStress;
        this.emotionSadness = emotionSadness;
        this.emotionAnger = emotionAnger;
        this.emotionOpinion = emotionOpinion;
    }

    public int getUid(){return uid;}
    public int getUserId(){return userId;}
    public float getDreamFrequency(){return dreamFrequency;}
    public float getDreamDetails(){return dreamDetails;}
    public float getLucidDream(){return lucidDream;}
    public float getTopicFamily() {return topicFamily;}
    public float getTopicLove() {return topicLove;}
    public float getTopicWork() {return topicWork;}
    public float getTopicFall() {return topicFall;}
    public float getEmotionJoy() {return emotionJoy;}
    public float getEmotionConfusion() {return emotionConfusion;}
    public float getEmotionSerenity() {return emotionSerenity;}
    public float getEmotionFear() {return emotionFear;}
    public float getEmotionStress() {return emotionStress;}
    public float getEmotionSadness() {return emotionSadness;}
    public float getEmotionAnger() {return emotionAnger;}
    public String getEmotionOpinion() {return emotionOpinion;}
    public void setUserId(int userId) {this.userId = userId;}
    public void setDreamFrequency(float dreamFrequency) {this.dreamFrequency = dreamFrequency;}
    public void setDreamDetails(float dreamDetails) {this.dreamDetails = dreamDetails;}
    public void setLucidDream(float lucidDream) {this.lucidDream = lucidDream;}
    public void setTopicFamily(float topicFamily) {this.topicFamily = topicFamily;}
    public void setTopicLove(float topicLove) {this.topicLove = topicLove;}
    public void setTopicWork(float topicWork) {this.topicWork = topicWork;}
    public void setTopicFall(float topicFall) {this.topicFall = topicFall;}
    public void setEmotionJoy(float emotionJoy) {this.emotionJoy = emotionJoy;}
    public void setEmotionConfusion(float emotionConfusion) {this.emotionConfusion = emotionConfusion;}
    public void setEmotionSerenity(float emotionSerenity) {this.emotionSerenity = emotionSerenity;}
    public void setEmotionFear(float emotionFear) {this.emotionFear = emotionFear;}
    public void setEmotionStress(float emotionStress) {this.emotionStress = emotionStress;}
    public void setEmotionSadness(float emotionSadness) {this.emotionSadness = emotionSadness;}
    public void setEmotionAnger(float emotionAnger) {this.emotionAnger = emotionAnger;}
    public void setEmotionOpinion(String emotionOpinion) {this.emotionOpinion = emotionOpinion;}

    @NonNull
    @Override
    public String toString() {
        return "Answer{" +
                "uid=" + uid +
                ", dreamFrequency='" + dreamFrequency + '\'' +
                ", dreamDetails='" + dreamDetails + '\'' +
                ", lucidDream ='" + lucidDream +'\'' +
                ", topicFamily ='" + topicFamily +'\'' +
                ", topicLove ='" + topicLove +'\'' +
                ", topicWork ='" + topicWork +'\'' +
                ", topicFall ='" + topicFall +'\'' +
                ", emotionJoy ='" + emotionJoy +'\'' +
                ", emotionConfusion ='" + emotionConfusion +'\'' +
                ", emotionSerenity ='" + emotionSerenity +'\'' +
                ", emotionFear ='" + emotionFear +'\'' +
                ", emotionStress ='" + emotionStress +'\'' +
                ", emotionSadness ='" + emotionSadness +'\'' +
                ", emotionAnger ='" + emotionAnger +'\'' +
                ", emotionOpinion ='" + emotionOpinion +'\'' +
                '}';
    }
}


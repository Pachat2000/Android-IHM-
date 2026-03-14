package com.example.projectihm.answers;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/***
 * This table is used to store all answer a user will give while
 * filling the form
 */
@Entity(tableName = "answers")
public class Answers {
    @PrimaryKey(autoGenerate = true)
    int uid;

    @ColumnInfo(name = "dream_frequency")
    int dreamFrequency;

    @ColumnInfo(name = "dream_details")
    int dreamDetails;

    @ColumnInfo(name = "lucid_dream")
    int lucidDream;

    @ColumnInfo(name = "topic_family")
    int topicFamily;

    @ColumnInfo(name = "topic_love")
    int topicLove;

    @ColumnInfo(name = "topic_work")
    int topicWork;

    @ColumnInfo(name = "topic_fall")
    int topicFall;

    @ColumnInfo(name = "emotion_joy")
    int emotionJoy;

    @ColumnInfo(name = "emotion_confusion")
    int emotionConfusion;

    @ColumnInfo(name = "emotion_serenity")
    int emotionSerenity;

    @ColumnInfo(name = "emotion_fear")
    int emotionFear;

    @ColumnInfo(name = "emotion_stress")
    int emotionStress;

    @ColumnInfo(name = "emotion_sadness")
    int emotionSadness;

    @ColumnInfo(name = "emotion_anger")
    int emotionAnger;

    @ColumnInfo(name = "emotion_opinion")
    String emotionOpinion;

    public Answers(int dreamFrequency, int dreamDetails, int lucidDream,
                   int topicFamily, int topicLove, int topicWork, int topicFall,
                   int emotionJoy, int emotionConfusion, int emotionSerenity, int emotionFear,
                   int emotionStress, int emotionSadness, int emotionAnger, @NonNull String emotionOpinion)
    {
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
    public int getDreamFrequency(){return dreamFrequency;}
    public int getDreamDetails(){return dreamDetails;}
    public int getLucidDream(){return lucidDream;}
    public int getTopicFamily() {return topicFamily;}
    public int getTopicLove() {return topicLove;}
    public int getTopicWork() {return topicWork;}
    public int getTopicFall() {return topicFall;}
    public int getEmotionJoy() {return emotionJoy;}
    public int getEmotionConfusion() {return emotionConfusion;}
    public int getEmotionSerenity() {return emotionSerenity;}
    public int getEmotionFear() {return emotionFear;}
    public int getEmotionStress() {return emotionStress;}
    public int getEmotionSadness() {return emotionSadness;}
    public int getEmotionAnger() {return emotionAnger;}
    public String getEmotionOpinion() {return emotionOpinion;}
    public void setDreamFrequency(int dreamFrequency) {this.dreamFrequency = dreamFrequency;}
    public void setDreamDetails(int dreamDetails) {this.dreamDetails = dreamDetails;}
    public void setLucidDream(int lucidDream) {this.lucidDream = lucidDream;}
    public void setTopicFamily(int topicFamily) {this.topicFamily = topicFamily;}
    public void setTopicLove(int topicLove) {this.topicLove = topicLove;}
    public void setTopicWork(int topicWork) {this.topicWork = topicWork;}
    public void setTopicFall(int topicFall) {this.topicFall = topicFall;}
    public void setEmotionJoy(int emotionJoy) {this.emotionJoy = emotionJoy;}
    public void setEmotionConfusion(int emotionConfusion) {this.emotionConfusion = emotionConfusion;}
    public void setEmotionSerenity(int emotionSerenity) {this.emotionSerenity = emotionSerenity;}
    public void setEmotionFear(int emotionFear) {this.emotionFear = emotionFear;}
    public void setEmotionStress(int emotionStress) {this.emotionStress = emotionStress;}
    public void setEmotionSadness(int emotionSadness) {this.emotionSadness = emotionSadness;}
    public void setEmotionAnger(int emotionAnger) {this.emotionAnger = emotionAnger;}
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


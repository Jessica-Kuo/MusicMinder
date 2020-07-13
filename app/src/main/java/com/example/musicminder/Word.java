package com.example.musicminder;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

//since words are being stored, this is a class to create and store words
//need a primary key and a string

//creating annotations to tell Room the relation of Word class to the database
@Entity(tableName = "word_table")
public class Word {
    //specify the column name
    //set the primary key to be the name of the word
    //make sure the primary key can never be null
    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    @ColumnInfo(name="word")
    //creates a constructor to take a non null string as a word and assign it to mWord
    //getWord is a getter method to return the input and instantiate it in other things
    private String mWord;
    public Word(@NonNull String word) {this.mWord = word;}

    @Ignore
    public Word(int id, @NonNull String word){
        this.id = id;
        this.mWord = word;
    }

    public String getWord(){
        return this.mWord;
    }

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }
}

package com.example.musicminder;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

//creates the DAO, which interacts with SQL and the word entity
//generates queries from annotations and has things like @Insert
@Dao
public interface WordDao {

    //declare a method to insert a word and annotate with @Insert
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Word word);

    //declare a method ot clear the table, theres no convenient annotation for deleting multiple
    //entries, so use a generic query annotation
    @Query("DELETE FROM word_table")
    void deleteAll();

    @Delete
    void deleteWord(Word word);

    @Query("SELECT * from word_table LIMIT 1")
    Word[] getAnyWord();

    //grabs all words and sorts them alphabetically
    //LiveData uses Room to let app respond to data changes
    @Query("SELECT * from word_table ORDER BY word ASC")
    LiveData<List<Word>> getAllWords();

    @Update
    void update(Word... word);
}
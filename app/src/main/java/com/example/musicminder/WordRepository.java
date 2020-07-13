package com.example.musicminder;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

//creates a repo to abstract access to many data sources
//handles data operations and provides a clean API for the rest of the app
public class WordRepository {

    //adding member variable for the DAO and list of words
    private WordDao mWordDao;
    private LiveData<List<Word>> mAllWords;

    //add a constructor to get a handle to database and initialize member variables
    WordRepository(Application application){
        WordDatabase db = WordDatabase.getDatabase(application);
        mWordDao = db.wordDao();
        mAllWords = mWordDao.getAllWords();
    }

    //add a wrapper method to return all words
    LiveData<List<Word>> getAllWords(){return mAllWords;}

    //add a wrapper for the insert method
    public void insert(Word word){
        new insertAsyncTask(mWordDao).execute(word);
    }
    public void update(Word word){ new updateWordAsyncTask(mWordDao).execute(word); }
    public void deleteAll(){
        new deleteAllWordsAsyncTask(mWordDao).execute();
    }
    public void deleteWord(Word word){
        new deleteWordAsyncTask(mWordDao).execute(word);
    }

    //create insertAsyncTask as an inner class
    private static class insertAsyncTask extends AsyncTask<Word, Void, Void> {
        private WordDao mAsyncTaskDao;
        insertAsyncTask(WordDao dao){
            mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(final Word... params){
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAllWordsAsyncTask extends AsyncTask<Void, Void, Void> {
        private WordDao mAsyncTaskDao;

        deleteAllWordsAsyncTask(WordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    private static class deleteWordAsyncTask extends AsyncTask<Word, Void, Void>{
        private WordDao mAsyncTaskDao;

        deleteWordAsyncTask(WordDao dao){
            mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(final Word... params){
            mAsyncTaskDao.deleteWord(params[0]);
            return null;
        }
    }

    private static class updateWordAsyncTask extends  AsyncTask<Word, Void, Void>{
        private WordDao mAsyncTaskDao;
        updateWordAsyncTask(WordDao dao){ mAsyncTaskDao = dao; }
        @Override
        protected Void doInBackground(final Word... params){
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }
}

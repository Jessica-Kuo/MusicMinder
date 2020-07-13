package com.example.musicminder;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

//annotate the class to be a Room database
//the version has to be updated each time theres a change
//exportSchema is kept false to not keep a history of schema versions, only needed when migrating databases
@Database(entities = {Word.class}, version = 1, exportSchema = false)

//creating a room database that is an abstraction of the SQLite database
//Room eliminates the need for a database helper
public abstract class WordDatabase extends RoomDatabase {

    //abstract getter method for each @Dao
    public abstract WordDao wordDao();

    //create the database as a singleton to prevent multiple instances being opened at the same time
    //rn versions are destroyed and not migrated, hence fallbacktoDesctructiveMigrations()
    private static WordDatabase INSTANCE;
    public static WordDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (WordDatabase.class){
                if(INSTANCE == null){
                    //insert database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            WordDatabase.class, "word_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }

    //populate the database
    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final WordDao mDao;
        String[] words = {"Press '+' to add words", "Swipe me to delete", "Click me to edit"};

        PopulateDbAsync(WordDatabase db) { mDao = db.wordDao(); }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate the database
            // when it is first created
            //mDao.deleteAll();

            if(mDao.getAnyWord().length<1){
                for (int i = 0; i <= words.length - 1; i++) {
                    Word word = new Word(words[i]);
                    mDao.insert(word);
                }
            }
            return null;
        }
    }
}

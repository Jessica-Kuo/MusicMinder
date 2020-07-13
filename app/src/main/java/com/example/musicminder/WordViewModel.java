package com.example.musicminder;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

//dont store activity, fragment, or view or their contexts in the view model
/* this could create memory leaks since activites are created and destroyed during the
 * view model lifecycle (ie rotation) because you might get references that point to the
 * destroyed activity */

//create the view model to hold the apps UI data so it survives configuration changes
//separates duties of activities and fragments from the view (single responsibility principle)
//activities and fragments draw data to screen, and view model holds and processes data for UI
public class WordViewModel extends AndroidViewModel {
    //add private member variable to hold a reference to repo
    private WordRepository mRepository;

    //add private livedata member variable to cache the list of words
    private LiveData<List<Word>> mAllWords;

    //add constructor that gets a reference to word repo and gets the words from it
    public WordViewModel(Application application){
        super(application);
        mRepository = new WordRepository(application);
        mAllWords = mRepository.getAllWords();
    }

    //add a getter method to hide implementation from the UI
    LiveData<List<Word>> getAllWords() {return mAllWords;}

    //create a wrapper to call the repos insert() method, hiding the implementation from UI
    public void insert(Word word){mRepository.insert(word);}

    //add deleteAll() to the wordviewmodel class
    public void deleteAll(){mRepository.deleteAll();}

    public void deleteWord(Word word){
        mRepository.deleteWord(word);
    }

    public void update(Word word){
        mRepository.update(word);
    }
}


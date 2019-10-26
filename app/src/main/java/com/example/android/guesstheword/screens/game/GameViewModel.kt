package com.example.android.guesstheword.screens.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel(){
    //init MutableLiveData for internal use and LiveData as a setter and external use
    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>get() = _score //restricts setter access to this class only by exposing as LiveData

    private val _word = MutableLiveData<String>()
    val word:LiveData<String>get() = _word

    //game finished event liveData
    private val _isFinished = MutableLiveData<Boolean>()
    val isFinished:LiveData<Boolean>get() = _isFinished

    private var wordList: MutableList<String>

    //init ViewModel
    init {
        Log.i("Game ViewModel class", "ViewModel crated!")
        _score.value = 0
        _word.value = ""
        wordList = mutableListOf(
                "queen",
                "hospital",
                "basketball",
                "cat",
                "change",
                "snail",
                "soup",
                "calendar",
                "sad",
                "desk",
                "guitar",
                "home",
                "railway",
                "zebra",
                "jelly",
                "car",
                "crow",
                "trade",
                "bag",
                "roll",
                "bubble"
        )
        resetList()
        nextWord()
    }

    fun onCorrect(){
        _score.value = (score.value)?.plus(1) //subtracting 1 with null safety
        nextWord()
    }

    fun onSkip(){
        _score.value =(score.value)?.minus(1) //adding 1 with null safety
        nextWord()
    }

    private fun resetList() {
        wordList.shuffle()
    }

    private fun nextWord(){
        //Select and remove a word from the list
        if (wordList.isEmpty()) {
            _isFinished.value = true
        } else {
            _word.value = wordList.removeAt(0)
        }
    }

    //reset the state so code doesn't get triggered multiple times
    fun resetGameFinishedState(){
        _isFinished.value = false
    }

    //destroy ViewModel
    override fun onCleared() {
        super.onCleared()
        Log.i("Game ViewModel class", "GameViewModel destroyed")
    }
}
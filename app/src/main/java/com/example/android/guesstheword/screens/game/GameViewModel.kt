package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel(){

    //a singleton instance of some static inner class vars
    companion object{
        //game over
        private const val DONE = 0L

        //one second
        private const val ONE_SECOND = 1000L

        //total duration of the game
        private const val DURATION = 10000L
    }

    //init MutableLiveData for internal use and LiveData as a setter and external use
    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>get() = _score //restricts setter access to this class only by exposing as LiveData

    private val _word = MutableLiveData<String>()
    val word:LiveData<String>get() = _word

    //game finished event liveData
    private val _isFinished = MutableLiveData<Boolean>()
    val isFinished:LiveData<Boolean>get() = _isFinished

    //timer
    private val _timer:CountDownTimer
    private val _timeElapsed = MutableLiveData<Int>()
    val timeElapsed:LiveData<Int>get() = _timeElapsed


    private var wordList: MutableList<String>

    //init ViewModel
    init {
        Log.i("Game ViewModel class", "ViewModel crated!")
        _score.value = 0
        _timeElapsed.value = 0
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

        //init timer
        _timer = object : CountDownTimer(DURATION, ONE_SECOND) {
            override fun onFinish() {
                Log.i("GameViewModel", "Timer had finished")
                _isFinished.value = true
            }

            override fun onTick(millisUntilFinished: Long) {
                Log.i("GameViewModel","Timer is ticking")
                _timeElapsed.value  = (_timeElapsed.value)?.plus(1)
            }

        }
        _timer.start()
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
            resetList()
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
        _timer.cancel()
    }
}
package com.example.android.guesstheword.screens.score

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

//ViewModel for Score fragment
class ScoreViewModel(finalScore: Int): ViewModel(){

    //setter and getters
    private val _score = MutableLiveData<Int>()
    val score:LiveData<Int>get() = _score

    private val _willPlayAgain = MutableLiveData<Boolean>()
    val willPlayAgain:LiveData<Boolean>get() = _willPlayAgain

    init {
        Log.i("ScoreViewModel", "Final score is $finalScore")
        _score.value = finalScore
    }

    fun reMatch(){
        _willPlayAgain.value = true
        //resetting it
        _willPlayAgain.value = false
    }


}
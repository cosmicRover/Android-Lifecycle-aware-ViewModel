package com.example.android.guesstheword.screens.game

import android.util.Log
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel(){
    //init ViewModel
    init {
        Log.i("Game ViewModel class", "ViewModel crated!")
    }

    //destroy ViewModel
    override fun onCleared() {
        super.onCleared()
        Log.i("Game ViewModel class", "GameViewModel destroyed")
    }
}
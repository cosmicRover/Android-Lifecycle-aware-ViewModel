/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.guesstheword.screens.game

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.android.guesstheword.R
import com.example.android.guesstheword.databinding.GameFragmentBinding

/**
 * Fragment where the game is played
 */
class GameFragment : Fragment() {

    lateinit var viewModel: GameViewModel
    // The list of words - the front of the list is the next word to guess


    private lateinit var binding: GameFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.game_fragment,
                container,
                false
        )

        //get a reference to the ViewModel from ViewModel provider
        //viewModel contains all the data logic for this fragment
        Log.i("GameFragment--->>>", "Called ViewModel provider")
        viewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)

        binding.correctButton.setOnClickListener {
            viewModel.onCorrect()
//            updateScoreText()
//            updateWordText()
        }
        binding.skipButton.setOnClickListener {
            viewModel.onSkip()
//            updateScoreText()
//            updateWordText()
        }

        //the three observers for score, word and isFinished
        viewModel.score.observe(this, Observer {newScore ->
            updateScoreText(newScore)
        })

        viewModel.word.observe(this, Observer { newWord ->
            updateWordText(newWord)
        })

        viewModel.isFinished.observe(this, Observer {isFinished ->
            if (isFinished){
                gameFinished()
                viewModel.resetGameFinishedState()
            }
        })

        viewModel.timeElapsed.observe(this, Observer { newTime ->
            Log.i("NEWTIME", "${newTime}")
            updateTimerText(newTime.toString())
        })

        return binding.root
    }

    /**
     * Called when the game is finished
     */
    private fun gameFinished() {
        val action = GameFragmentDirections.actionGameToScore(viewModel.score.value ?: 0)
        findNavController(this).navigate(action)
    }

    /** Methods for updating the UI **/

    private fun updateWordText(word: String) {
        binding.wordText.text = word
    }

    private fun updateTimerText(newTime:String){
        binding.timerText.text = newTime
    }

    private fun updateScoreText(score: Int) {
        binding.scoreText.text = score.toString()
    }
}

package com.example.myapplication.features.game

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.myapplication.R
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

sealed class Figure(val name : Int){
    data object Rock : Figure(R.drawable.rock)
    data object Paper : Figure(R.drawable.paper)
    data object Scissors : Figure(R.drawable.scissors)
}

sealed class ButtonSize(val size : Int){
    data object Win : ButtonSize(300)
    data object Large : ButtonSize(240)
    data object Small : ButtonSize(180)
}

@HiltViewModel
class GameScreenViewModel @Inject constructor() : ViewModel(){
    val computerWins = mutableIntStateOf(0)
    val playerWins = mutableIntStateOf(0)

    var playerChoice = mutableIntStateOf(Figure.Rock.name)
    var computerChoice = mutableIntStateOf(Figure.Rock.name)

    val rockSize = mutableIntStateOf(ButtonSize.Small.size)
    val paperSize = mutableIntStateOf(ButtonSize.Small.size)
    val scissorsSize = mutableIntStateOf(ButtonSize.Small.size)

    var play = mutableStateOf(true)
    var waiting = mutableStateOf(true)

    var computerFigureSize = mutableIntStateOf(ButtonSize.Small.size)
    var playerFigureSize = mutableIntStateOf(ButtonSize.Small.size)

    var computerDisplayFigure = mutableIntStateOf(R.drawable.questiion)
    var playerDisplayFigure = mutableIntStateOf(R.drawable.questiion)

    fun clear(){
        playerChoice.intValue = (Figure.Rock.name)
        computerChoice.intValue = (Figure.Rock.name)

        rockSize.intValue = (ButtonSize.Small.size)
        paperSize.intValue = (ButtonSize.Small.size)
        scissorsSize.intValue = (ButtonSize.Small.size)

        play = mutableStateOf(true)
        waiting = mutableStateOf(true)

        computerFigureSize = mutableIntStateOf(ButtonSize.Small.size)
        playerFigureSize = mutableIntStateOf(ButtonSize.Small.size)

        computerDisplayFigure = mutableIntStateOf(R.drawable.questiion)
        playerDisplayFigure = mutableIntStateOf(R.drawable.questiion)
    }

    fun clearCount(){
        computerWins.intValue = 0
        playerWins.intValue = 0
    }
}
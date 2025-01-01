package com.rehmankhan.tictactoegame.logic

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf

class BackEnd() {

    val playerScore = mutableIntStateOf(0)
    val drawScore = mutableIntStateOf(0)
    val aiScore = mutableIntStateOf(0)

    val currentTurn = mutableStateOf("player")

    val moves = mutableListOf<Boolean?>(null, null, null, null, null, null, null, null, null)


}
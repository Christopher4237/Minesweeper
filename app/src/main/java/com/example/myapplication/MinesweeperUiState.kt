package com.example.myapplication

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

enum class gameModes {
    Easy,
    Medium,
    Hard
}

data class MinesweeperUiState(
    val boardSize: Int = 0,
    val columns: Int = 0,
    val rows: Int = 0,
    val numberOfMinesTotal: Int = 0,
    //numberOfMinesNear should be null if that area is a mine itself
    var numberOfMinesNear: SnapshotStateList<Int?> = mutableStateListOf(),
    val isMine: SnapshotStateList<Boolean> = mutableStateListOf(),
    val isRevealed: SnapshotStateList<Boolean> = mutableStateListOf(),
    val isGameOver: Boolean = false,
    val listOfValidMineSites: SnapshotStateList<Int> = mutableStateListOf(),
    val suspectedMines: SnapshotStateList<Int> = mutableStateListOf(),
    val minesLeft: Int = 99,
    val won: Boolean? = null,
    val currentGameTimeInMillis: Double = 0.00,
    var gameMode: gameModes? = gameModes.Easy,
    var bestTimeEasy: Double? = null,
    var newBestTimeEasy: SnapshotStateList<Double?> = mutableStateListOf()
)
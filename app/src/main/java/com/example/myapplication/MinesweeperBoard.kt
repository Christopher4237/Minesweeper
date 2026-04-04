package com.example.myapplication


import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@SuppressLint("DefaultLocale")
@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MinesweeperBoard(
    navigateHome: () -> Unit,
    data: List<Int>,
    minesLeft: Int,
    columns: Int,
    won: Boolean?,
    isGameOver: Boolean,
    isRevealed: List<Boolean>,
    firstClick: (Int) -> Unit,
    checkForNonMine: (Int) -> Unit,
    suspectedMines: List<Int>,
    addSuspectedMine: (Int) -> Unit,
    removeSuspectedMine: (Int) -> Unit,
    numberOfMinesNear: List<Int?>,
    isMine: List<Boolean>,
    postGameAnalysisMode: () -> Unit,
    resetGame: () -> Unit,
    time: Double?,
    updateHistoricalTimesEasy: (Double?) -> Unit,
    updateHistoricalTimesMedium: (Double?) -> Unit,
    updateHistoricalTimesHard: (Double?) -> Unit,
    onEasyMode: Boolean,
    onMediumMode: Boolean,
    onHardMode: Boolean,
    gameStarted: Boolean,
    modifier: Modifier = Modifier
) {

    //val minesweeperUiState by minesweeperViewModel.uiState.collectAsState()

    val data = data

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Spacer(
                modifier = Modifier
                    .weight(1f)
            )
            if(gameStarted) {
                Text(
                    text = "Time: " + String.format("%.1f", time).toDouble(),
                    fontSize = 12.sp
                )
            }
            Spacer(
                modifier = Modifier
                    .weight(1f)
            )
            Text(
                text = "Mines left: $minesLeft",
                fontSize = 12.sp
            )
            Spacer(
                modifier = Modifier
                    .weight(1f)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(columns),
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .border(1.dp, Color.Black)
            ) {
                items(data) { item ->
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .border(0.5.dp, Color.Black)
                            .fillMaxSize()
                            .combinedClickable(
                                onClick = if (!(won == null && isGameOver == true)) {
                                    if (isRevealed.all { it == false }) {
                                        {
                                            firstClick(item)
                                        }
                                    } else {
                                        if (isRevealed[item] == false && item !in suspectedMines) {
                                            {
                                                checkForNonMine(item)
                                            }
                                        } else {
                                            {
                                                {}
                                            }
                                        }
                                    }
                                } else {
                                    {}
                                },
                                onLongClick = if (!(won == null && isGameOver == true)) {
                                    if (!isRevealed.all { it == false }) {
                                        if (item !in suspectedMines) {
                                            {
                                                addSuspectedMine(item)
                                            }
                                        } else if (item in suspectedMines) {
                                            {
                                                removeSuspectedMine(item)
                                            }
                                        } else {
                                            {}
                                        }
                                    } else {
                                        {
                                            firstClick(item)
                                        }
                                    }
                                } else {
                                    {}
                                }
                            )
                        /*
                    .pointerInput(Unit) {
                        if(!isRevealed.all { it == true }) {
                            detectTapGestures(
                                onPress = {
                                    status = "Pressed"
                                    // Wait to see if the press is released or cancelled
                                    val pressSucceeded = tryAwaitRelease()
                                    status = if (pressSucceeded) {
                                        "Released"
                                    } else {
                                        "Cancelled"
                                    }
                                },
                                onLongPress = {
                                    status = "Long Press Detected"
                                }
                            )
                        }
                    }
                     */
                    ) {
                        if (isMine.toList().isEmpty()) {
                            Text(
                                text = "",
                                fontSize = 6.sp,
                                modifier = Modifier
                                    .background(Color.LightGray)
                                    .fillMaxSize()
                            )
                        } else if (isRevealed.toList()[item] == true) {
                            Text(
                                text = if (numberOfMinesNear[item] != 0) {
                                    numberOfMinesNear[item].toString()
                                } else {
                                    ""
                                },
                                fontSize = 6.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .background(Color.LightGray)
                                    .fillMaxSize()
                                    .align(Alignment.CenterHorizontally)
                            )
                        } else if (isMine.toList()[item] == true && item !in suspectedMines) {
                            Text(
                                text = "",
                                fontSize = 6.sp,
                                modifier = Modifier
                                    .background(Color.Red)
                                    .fillMaxSize()
                            )
                        } else if (item in suspectedMines) {
                            Text(
                                text = "",
                                fontSize = 6.sp,
                                modifier = Modifier
                                    .background(Color.Yellow)
                                    .fillMaxSize()
                            )
                        } else {
                            Text(
                                text = "",
                                fontSize = 6.sp,
                                modifier = Modifier
                                    .background(Color.Green)
                                    .fillMaxSize()
                            )
                        }
                    }
                }
            }
        }
        if (isGameOver == true) {
            if (won == false) {
                MinesweeperAlertDialog(
                    onDismissRequest = {
                        postGameAnalysisMode()
                    },
                    onConfirmation = navigateHome,
                    playAgain = {
                        resetGame()
                    },
                    text = { Text("That was a mine! Game over. New game?") }
                )
            }
            if (won == true) {
                if(onEasyMode) {
                    updateHistoricalTimesEasy(time)
                }
                if(onMediumMode) {
                    updateHistoricalTimesMedium(time)
                }
                if(onHardMode) {
                    updateHistoricalTimesHard(time)
                }
                MinesweeperAlertDialog(
                    onDismissRequest = {
                        postGameAnalysisMode()
                    },
                    onConfirmation = navigateHome,
                    playAgain = {
                        resetGame()
                    },
                    text = { Text("Congrats! All mines have been defused." +
                            "Your time was " + String.format("%.1f", time).toDouble()) }
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            if (won == null && isGameOver == true) {
                Spacer(
                    modifier = Modifier
                        .weight(1f)
                )
                Button(
                    onClick = {
                        resetGame()
                    }
                ) {
                    Text(
                        text = "Play Again",
                        fontSize = 12.sp
                    )
                }
                Spacer(
                    modifier = Modifier
                        .weight(1f)
                )
                Button(
                    onClick = navigateHome
                ) {
                    Text(
                        text = "Return Home",
                        fontSize = 12.sp
                    )
                }
                Spacer(
                    modifier = Modifier
                        .weight(1f)
                )
            } else {
                Spacer(
                    modifier = Modifier
                        .weight(1f)
                )
                Button(
                    onClick = {
                        resetGame()
                    }
                ) {
                    Text(
                        text = "Reset",
                        fontSize = 12.sp
                    )
                }
                Spacer(
                    modifier = Modifier
                        .weight(1f)
                )
                Button(
                    onClick = navigateHome
                ) {
                    Text(
                        text = "Return Home",
                        fontSize = 12.sp
                    )
                }
                Spacer(
                    modifier = Modifier
                        .weight(1f)
                )
            }
        }
        Text(
            text = String.format("%.1f", time).toDouble().toString()
        )
    }
}


@Preview(showBackground = true)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MinesweeperBoard2(
    modifier: Modifier = Modifier
) {
    MinesweeperBoard(
        navigateHome = {},
        data = listOf(),
        minesLeft = 0,
        columns = 0,
        won = null,
        isGameOver = false,
        isRevealed = listOf(),
        firstClick = {},
        checkForNonMine = {},
        suspectedMines = listOf(),
        addSuspectedMine = {},
        removeSuspectedMine = {},
        numberOfMinesNear = listOf(),
        isMine = listOf(),
        postGameAnalysisMode = {},
        resetGame = {},
        time = 0.00,
        updateHistoricalTimesEasy = {},
        updateHistoricalTimesMedium = {},
        updateHistoricalTimesHard = {},
        onEasyMode = false,
        onMediumMode = false,
        onHardMode = false,
        gameStarted = false
    )
}
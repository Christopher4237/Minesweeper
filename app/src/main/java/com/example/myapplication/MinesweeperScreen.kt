package com.example.myapplication

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay

enum class Screens {
    Home,
    EasyGame,
    MediumGame,
    HardGame
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MinesweeperScreen(
    navController: NavHostController = rememberNavController(),
    minesweeperViewModel: MinesweeperViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = Screens.valueOf(
        backStackEntry?.destination?.route ?: Screens.Home.name
    )

    val minesweeperUiState by minesweeperViewModel.uiState.collectAsState()


    var time by remember { mutableStateOf(minesweeperUiState.currentGameTimeInMillis) }

    //var gameMode by remember { mutableStateOf(minesweeperUiState.gameMode) }

    LaunchedEffect(key1 = time) {
        while(minesweeperUiState.isGameOver != true) {
            delay(1000L)
            time++
        }

    }

    LaunchedEffect(
        key1 = minesweeperUiState.gameMode,
        key2 = currentScreen,
        key3 = minesweeperUiState.isRevealed.all { it == false }
    ) {
        time = 0.00
    }

    NavHost(
        navController = navController,
        startDestination = Screens.Home.name
    ) {
        composable(
            route = Screens.Home.name
        ) {
            MinesweeperHome(
                navigateToEasyGame = {
                    minesweeperUiState.gameMode = gameModes.Easy
                    minesweeperViewModel.resetGame()
                    navController.navigate(Screens.EasyGame.name)
                },
                navigateToMediumGame = {
                    minesweeperUiState.gameMode = gameModes.Medium
                    minesweeperViewModel.resetGame()
                    navController.navigate(Screens.MediumGame.name)
                },
                navigateToHardGame = {
                    minesweeperUiState.gameMode = gameModes.Hard
                    minesweeperViewModel.resetGame()
                    navController.navigate(Screens.HardGame.name)
                },
                historicalTimesEasy = minesweeperUiState.historicalTimesEasy,
                mostRecentTimeEasy = minesweeperUiState.mostRecentTimeEasy,
                historicalTimesMedium = minesweeperUiState.historicalTimesMedium,
                mostRecentTimeMedium = minesweeperUiState.mostRecentTimeMedium,
                mostRecentTimeHard = minesweeperUiState.mostRecentTimeHard,
                historicalTimesHard = minesweeperUiState.historicalTimesHard
            )
        }
        composable(
            route = Screens.EasyGame.name
        ) {
            MinesweeperBoard(
                navigateHome = {
                    navController.navigate(Screens.Home.name)
                    minesweeperViewModel.resetGame()
                },
                data = (0 until minesweeperUiState.columns * minesweeperUiState.rows).toList(),
                minesLeft = minesweeperUiState.minesLeft,
                columns = minesweeperUiState.columns,
                won = minesweeperUiState.won,
                isGameOver = minesweeperUiState.isGameOver,
                isRevealed = minesweeperUiState.isRevealed,
                firstClick = {
                    minesweeperViewModel.firstClick(it)
                },
                checkForNonMine = {
                    minesweeperViewModel.checkForNonMine(it)
                },
                suspectedMines = minesweeperUiState.suspectedMines,
                addSuspectedMine = {
                    minesweeperViewModel.addSuspectedMine(it)
                },
                removeSuspectedMine = {
                    minesweeperViewModel.removeSuspectedMine(it)
                },
                numberOfMinesNear = minesweeperUiState.numberOfMinesNear,
                isMine = minesweeperUiState.isMine,
                postGameAnalysisMode = {
                    minesweeperViewModel.postGameAnalysisMode()
                },
                resetGame = {
                    minesweeperViewModel.resetGame()
                },
                time = time,
                updateHistoricalTimesEasy = {
                    minesweeperViewModel.updateHistoricalTimesEasy(time)
                },
                updateHistoricalTimesMedium = {
                    minesweeperViewModel.updateHistoricalTimesMedium(time)
                },
                updateHistoricalTimesHard = {
                    minesweeperViewModel.updateHistoricalTimesHard(time)
                },
                onEasyMode = minesweeperUiState.gameMode == gameModes.Easy,
                onMediumMode = minesweeperUiState.gameMode == gameModes.Medium,
                onHardMode = minesweeperUiState.gameMode == gameModes.Hard
            )
        }
        composable(
            route = Screens.MediumGame.name
        ) {
            MinesweeperBoard(
                navigateHome = {
                    navController.navigate(Screens.Home.name)
                    minesweeperViewModel.resetGame()
                },
                data = (0 until minesweeperUiState.columns * minesweeperUiState.rows).toList(),
                minesLeft = minesweeperUiState.minesLeft,
                columns = minesweeperUiState.columns,
                won = minesweeperUiState.won,
                isGameOver = minesweeperUiState.isGameOver,
                isRevealed = minesweeperUiState.isRevealed,
                firstClick = {
                    minesweeperViewModel.firstClick(it)
                },
                checkForNonMine = {
                    minesweeperViewModel.checkForNonMine(it)
                },
                suspectedMines = minesweeperUiState.suspectedMines,
                addSuspectedMine = {
                    minesweeperViewModel.addSuspectedMine(it)
                },
                removeSuspectedMine = {
                    minesweeperViewModel.removeSuspectedMine(it)
                },
                numberOfMinesNear = minesweeperUiState.numberOfMinesNear,
                isMine = minesweeperUiState.isMine,
                postGameAnalysisMode = {
                    minesweeperViewModel.postGameAnalysisMode()
                },
                resetGame = {
                    minesweeperViewModel.resetGame()
                },
                time = time,
                updateHistoricalTimesEasy = {
                    minesweeperViewModel.updateHistoricalTimesEasy(time)
                },
                updateHistoricalTimesMedium = {
                    minesweeperViewModel.updateHistoricalTimesMedium(time)
                },
                updateHistoricalTimesHard = {
                    minesweeperViewModel.updateHistoricalTimesHard(time)
                },
                onEasyMode = minesweeperUiState.gameMode == gameModes.Easy,
                onMediumMode = minesweeperUiState.gameMode == gameModes.Medium,
                onHardMode = minesweeperUiState.gameMode == gameModes.Hard
            )
        }
        composable(
            route = Screens.HardGame.name
        ) {
            MinesweeperBoard(
                navigateHome = {
                    navController.navigate(Screens.Home.name)
                    minesweeperViewModel.resetGame()
                },
                data = (0 until minesweeperUiState.columns * minesweeperUiState.rows).toList(),
                minesLeft = minesweeperUiState.minesLeft,
                columns = minesweeperUiState.columns,
                won = minesweeperUiState.won,
                isGameOver = minesweeperUiState.isGameOver,
                isRevealed = minesweeperUiState.isRevealed,
                firstClick = {
                    minesweeperViewModel.firstClick(it)
                },
                checkForNonMine = {
                    minesweeperViewModel.checkForNonMine(it)
                },
                suspectedMines = minesweeperUiState.suspectedMines,
                addSuspectedMine = {
                    minesweeperViewModel.addSuspectedMine(it)
                },
                removeSuspectedMine = {
                    minesweeperViewModel.removeSuspectedMine(it)
                },
                numberOfMinesNear = minesweeperUiState.numberOfMinesNear,
                isMine = minesweeperUiState.isMine,
                postGameAnalysisMode = {
                    minesweeperViewModel.postGameAnalysisMode()
                },
                resetGame = {
                    minesweeperViewModel.resetGame()
                },
                time = time,
                updateHistoricalTimesEasy = {
                    minesweeperViewModel.updateHistoricalTimesEasy(time)
                },
                updateHistoricalTimesMedium = {
                    minesweeperViewModel.updateHistoricalTimesMedium(time)
                },
                updateHistoricalTimesHard = {
                    minesweeperViewModel.updateHistoricalTimesHard(time)
                },
                onEasyMode = minesweeperUiState.gameMode == gameModes.Easy,
                onMediumMode = minesweeperUiState.gameMode == gameModes.Medium,
                onHardMode = minesweeperUiState.gameMode == gameModes.Hard
            )
        }
    }
}
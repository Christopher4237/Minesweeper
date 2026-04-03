package com.example.myapplication

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateSetOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MinesweeperViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(MinesweeperUiState())

    val uiState: StateFlow<MinesweeperUiState> = _uiState.asStateFlow()

    init {
        resetGame()
    }

    fun resetGame() {
        if(_uiState.value.gameMode == gameModes.Easy) {
            var allretainedTimesEasy: SnapshotStateList<Double?> = _uiState.value.mostRecentTimeEasy
            var retainedTimeEasy: Double? = null
            if(_uiState.value.historicalTimesEasy != null && _uiState.value.won == true) {
                retainedTimeEasy = _uiState.value.historicalTimesEasy
                allretainedTimesEasy.add(retainedTimeEasy)
            } else {
                retainedTimeEasy = null
            }
            _uiState.value = MinesweeperUiState(
                columns = 8,
                boardSize = 64,
                numberOfMinesTotal = 10,
                isGameOver = false,
                won = null,
                currentGameTimeInMillis = 0.00,
                gameMode = gameModes.Easy,
                minesLeft = 10,
                historicalTimesEasy = retainedTimeEasy,
                mostRecentTimeEasy = allretainedTimesEasy,
                historicalTimesMedium = _uiState.value.historicalTimesMedium,
                mostRecentTimeMedium = _uiState.value.mostRecentTimeMedium,
                historicalTimesHard = _uiState.value.historicalTimesHard,
                mostRecentTimeHard = _uiState.value.mostRecentTimeHard
            )
        } else if(_uiState.value.gameMode == gameModes.Medium) {
            var allretainedTimesMedium: SnapshotStateList<Double?> = _uiState.value.mostRecentTimeMedium
            var retainedTimeMedium: Double? = null
            if(_uiState.value.historicalTimesMedium != null && _uiState.value.won == true) {
                retainedTimeMedium = _uiState.value.historicalTimesMedium
                allretainedTimesMedium.add(retainedTimeMedium)
            } else {
                retainedTimeMedium = null
            }
            _uiState.value = MinesweeperUiState(
                columns = 16,
                boardSize = 256,
                numberOfMinesTotal = 40,
                isGameOver = false,
                won = null,
                currentGameTimeInMillis = 0.00,
                gameMode = gameModes.Medium,
                minesLeft = 40,
                historicalTimesEasy = _uiState.value.historicalTimesEasy,
                mostRecentTimeEasy  = _uiState.value.mostRecentTimeEasy,
                historicalTimesMedium = retainedTimeMedium,
                mostRecentTimeMedium = allretainedTimesMedium,
                historicalTimesHard = _uiState.value.historicalTimesHard,
                mostRecentTimeHard = _uiState.value.mostRecentTimeHard
            )
        } else if(_uiState.value.gameMode == gameModes.Hard) {
            var allretainedTimesHard: SnapshotStateList<Double?> = _uiState.value.mostRecentTimeHard
            var retainedTimeHard: Double? = null
            if(_uiState.value.historicalTimesHard != null && _uiState.value.won == true) {
                retainedTimeHard = _uiState.value.historicalTimesHard
                allretainedTimesHard.add(retainedTimeHard)
            } else {
                retainedTimeHard = null
            }
            _uiState.value = MinesweeperUiState(
                columns = 30,
                boardSize = 480,
                numberOfMinesTotal = 99,
                isGameOver = false,
                won = null,
                currentGameTimeInMillis = 0.00,
                gameMode = gameModes.Hard,
                minesLeft = 99,
                historicalTimesEasy = _uiState.value.historicalTimesEasy,
                mostRecentTimeEasy = _uiState.value.mostRecentTimeEasy,
                historicalTimesMedium = _uiState.value.historicalTimesMedium,
                mostRecentTimeMedium = _uiState.value.mostRecentTimeMedium,
                historicalTimesHard = retainedTimeHard,
                mostRecentTimeHard = allretainedTimesHard
            )
        }

        _uiState.update {
            it.copy(
                rows = _uiState.value.boardSize / _uiState.value.columns
            )
        }
    }


    fun iterateInDirections(
        item: Int
    ) {
        if(_uiState.value.isMine[item] == false && item !in _uiState.value.suspectedMines) {
            if (_uiState.value.numberOfMinesNear[item] == 0) {

                //all indexes at the very start revealed
                val originalItemsRevealed =
                    _uiState.value.isRevealed.mapIndexedNotNull { index, value ->
                        if (value == true) index else null
                    }.toMutableList()

                //reveal the first item
                _uiState.value.isRevealed[item] = true

                var thisIterationItemsRevealed: MutableSet<Int> = mutableStateSetOf()

                var thisIterationItemsRevealedSize: Int = thisIterationItemsRevealed.size

                //Get all indexes with no mines or not a mine
                val indexesWithNoMines =
                    _uiState.value.numberOfMinesNear.mapIndexedNotNull { index, value ->
                        if (value == 0) index else null
                    }

                //go until you have numbers in every non-diagonal direction
                while (true) {

                    //first iteration
                    if (thisIterationItemsRevealed.isEmpty()) {

                        //add down
                        if ((item + _uiState.value.columns < _uiState.value.boardSize) && (item + _uiState.value.columns > 0)
                            && (_uiState.value.isMine[item + _uiState.value.columns] == false)
                        ) {
                            _uiState.value.isRevealed[item + _uiState.value.columns] = true
                            thisIterationItemsRevealed.add(item + _uiState.value.columns)
                            //justThoseRevealedThisClick.add(item + _uiState.value.columns)
                        }
                        //add up
                        if ((item - _uiState.value.columns < _uiState.value.boardSize) && (item - _uiState.value.columns > 0)
                            && (_uiState.value.isMine[item - _uiState.value.columns] == false)
                        ) {
                            _uiState.value.isRevealed[item - _uiState.value.columns] = true
                            thisIterationItemsRevealed.add(item - _uiState.value.columns)
                            //justThoseRevealedThisClick.add(item - _uiState.value.columns)
                        }

                        //add right
                        if ((item + 1 < _uiState.value.boardSize) && (item + 1 > 0)
                            && ((item + 1) % _uiState.value.columns != 0)
                            && (_uiState.value.isMine[item + 1] == false)
                        ) {
                            _uiState.value.isRevealed[item + 1] = true
                            thisIterationItemsRevealed.add(item + 1)
                            //justThoseRevealedThisClick.add(item + 1)
                        }
                        //add left
                        if ((item - 1 < _uiState.value.boardSize) && (item - 1 > 0)
                            && (item % _uiState.value.columns != 0)
                            && (_uiState.value.isMine[item - 1] == false)
                        ) {
                            _uiState.value.isRevealed[item - 1] = true
                            thisIterationItemsRevealed.add(item - 1)
                            //justThoseRevealedThisClick.add(item - 1)
                        }
                    } else {
                        //iterating over all indexes with no mines
                        for(item in indexesWithNoMines) {
                            //append downwards
                            //if the item directly ABOVE is part of what's been revealed
                            if (item > 0 && item < _uiState.value.boardSize &&
                                thisIterationItemsRevealed.any { it == item - _uiState.value.columns } == true
                                && item !in originalItemsRevealed
                            ) {
                                _uiState.value.isRevealed[item] = true
                                thisIterationItemsRevealed.add(item)
                            }
                            //append upwards
                            //if the item directly BELOW is part of what's been revealed
                            if (item > 0 && item < _uiState.value.boardSize &&
                                thisIterationItemsRevealed.any { it == item + _uiState.value.columns } == true
                                && item !in originalItemsRevealed) {
                                _uiState.value.isRevealed[item] = true
                                thisIterationItemsRevealed.add(item)
                                //justThoseRevealedThisClick.add(item)
                            }
                            //append right
                            //if the item directly LEFT is part of what's been revealed
                            if (item > 0 && item < _uiState.value.boardSize && item % _uiState.value.columns != 0 &&
                                thisIterationItemsRevealed.any { it == item - 1 } == true
                                && item !in originalItemsRevealed) {
                                _uiState.value.isRevealed[item] = true
                                thisIterationItemsRevealed.add(item)
                                //justThoseRevealedThisClick.add(item)
                            }
                            //append left
                            //if the item directly RIGHT is part of what's been revealed
                            if (item > 0 && item < _uiState.value.boardSize && (item + 1) % _uiState.value.columns != 0 &&
                                thisIterationItemsRevealed.any { it == item + 1 } == true
                                && item !in originalItemsRevealed) {
                                _uiState.value.isRevealed[item] = true
                                thisIterationItemsRevealed.add(item)
                                //justThoseRevealedThisClick.add(item)
                            }
                        }
                    }
                    if(thisIterationItemsRevealed.size == thisIterationItemsRevealedSize) {
                        for(item in thisIterationItemsRevealed) {
                            _uiState.value.isRevealed[item] = true
                            thisIterationItemsRevealed.add(item)
                        }
                        break
                    }
                    thisIterationItemsRevealedSize = thisIterationItemsRevealed.size
                }


                for(item in thisIterationItemsRevealed) {
                    if((item - _uiState.value.columns) > 0 &&
                        _uiState.value.isMine[item - _uiState.value.columns] == false
                    //&& _uiState.value.numberOfMinesNear[item] == 0
                    ) {
                        _uiState.value.isRevealed[item - _uiState.value.columns] = true
                        //thisIterationItemsRevealed.add(item - _uiState.value.columns)
                    }
                    if((item + 1) % _uiState.value.columns != 0 &&
                        _uiState.value.isMine[item + 1] == false
                    //&& _uiState.value.numberOfMinesNear[item] == 0
                    ) {
                        _uiState.value.isRevealed[item + 1] = true
                        //thisIterationItemsRevealed.add(item + 1)
                    }
                    if((item + _uiState.value.columns) < _uiState.value.boardSize &&
                        _uiState.value.isMine[item + _uiState.value.columns] == false
                    //&& _uiState.value.numberOfMinesNear[item] == 0
                    ) {
                        _uiState.value.isRevealed[item + _uiState.value.columns] = true
                        //thisIterationItemsRevealed.add(item + _uiState.value.columns)
                    }
                    if(item % _uiState.value.columns != 0 &&
                        _uiState.value.isMine[item - 1] == false
                    //&& _uiState.value.numberOfMinesNear[item] == 0
                    ) {
                        _uiState.value.isRevealed[item - 1] = true
                        //thisIterationItemsRevealed.add(item - 1)
                    }
                }

                val indexesWithoutCornerRecords = _uiState.value.isRevealed.mapIndexedNotNull { index, value ->
                    if (value == true) index else null
                }.toMutableList()


                for(item in thisIterationItemsRevealed) {
                    //append top left
                    if((item - _uiState.value.columns - 1) > 0 && _uiState.value.isMine[item] == false &&
                        (item - 1) < _uiState.value.boardSize && _uiState.value.isMine[item - _uiState.value.columns] == false && ((item - _uiState.value.columns) in indexesWithoutCornerRecords) &&
                        _uiState.value.isMine[item - 1] == false && ((item - 1) in indexesWithoutCornerRecords) &&
                        _uiState.value.isMine[item - _uiState.value.columns - 1] == false && ((item - _uiState.value.columns - 1) !in indexesWithoutCornerRecords) &&
                        _uiState.value.numberOfMinesNear[item - _uiState.value.columns - 1] != 0
                    ) {
                        _uiState.value.isRevealed[item - _uiState.value.columns - 1] = true
                    }
                    //append top right
                    if((item - _uiState.value.columns + 1) > 0 && _uiState.value.isMine[item] == false && (item + 1) < _uiState.value.boardSize &&
                        _uiState.value.isMine[item - _uiState.value.columns] == false && ((item - _uiState.value.columns) in indexesWithoutCornerRecords) &&
                        _uiState.value.isMine[item + 1] == false && ((item + 1) in indexesWithoutCornerRecords) &&
                        _uiState.value.isMine[item - _uiState.value.columns + 1] == false && ((item - _uiState.value.columns + 1) !in indexesWithoutCornerRecords) &&
                        _uiState.value.numberOfMinesNear[item - _uiState.value.columns + 1] != 0) {
                        _uiState.value.isRevealed[item - _uiState.value.columns + 1] = true
                    }
                    //append bottom left
                    if((item + _uiState.value.columns) < _uiState.value.boardSize && _uiState.value.isMine[item] == false &&
                        _uiState.value.isMine[item + _uiState.value.columns] == false && ((item + _uiState.value.columns) in indexesWithoutCornerRecords) &&
                        _uiState.value.isMine[item - 1] == false && ((item - 1) in indexesWithoutCornerRecords) &&
                        _uiState.value.isMine[item + _uiState.value.columns - 1] == false && ((item + _uiState.value.columns - 1) !in indexesWithoutCornerRecords) &&
                        _uiState.value.numberOfMinesNear[item + _uiState.value.columns - 1] != 0) {
                        _uiState.value.isRevealed[item + _uiState.value.columns - 1] = true
                    }
                    //append bottom right
                    if((item + _uiState.value.columns + 1) < _uiState.value.boardSize && _uiState.value.isMine[item] == false &&
                        _uiState.value.isMine[item + _uiState.value.columns] == false && ((item + _uiState.value.columns) in indexesWithoutCornerRecords) &&
                        _uiState.value.isMine[item + 1] == false && ((item + 1) in indexesWithoutCornerRecords) &&
                        _uiState.value.isMine[item + _uiState.value.columns + 1] == false && ((item + _uiState.value.columns + 1) !in indexesWithoutCornerRecords) &&
                        _uiState.value.numberOfMinesNear[item + _uiState.value.columns + 1] != 0) {
                        _uiState.value.isRevealed[item + _uiState.value.columns + 1] = true
                    }
                }
            } else {
                _uiState.value.isRevealed[item] = true
            }
        }
    }

    fun firstClick(
        item: Int
    ) {
        //initialise with everything as a non-mine
        for (item in (0 until _uiState.value.boardSize)) {
            _uiState.value.isMine.add(false)
        }


        //generating where the mines can go
        var listOfValidMineSites: SnapshotStateList<Int> = mutableStateListOf()

        for (item in (0 until _uiState.value.boardSize)) {
            listOfValidMineSites.add(item)
        }

        //bottom left corner
        if (item % _uiState.value.columns == 0 && item == _uiState.value.boardSize - _uiState.value.columns) {
            listOfValidMineSites.remove(item)
            listOfValidMineSites.remove(item - _uiState.value.columns)
            listOfValidMineSites.remove(item - _uiState.value.columns + 1)
            listOfValidMineSites.remove(item + 1)
        } //top left corner
        else if (item == 0) {
            listOfValidMineSites.remove(item)
            listOfValidMineSites.remove(item + 1)
            listOfValidMineSites.remove(item + _uiState.value.columns)
            listOfValidMineSites.remove(item + _uiState.value.columns + 1)
        } //top right corner
        else if (item == _uiState.value.columns - 1) {
            listOfValidMineSites.remove(item)
            listOfValidMineSites.remove(item - 1)
            listOfValidMineSites.remove(item + _uiState.value.columns - 1)
            listOfValidMineSites.remove(item + _uiState.value.columns)
        } //bottom right corner
        else if (item == (_uiState.value.columns * _uiState.value.rows) - 1) {
            listOfValidMineSites.remove(item - _uiState.value.columns - 1)
            listOfValidMineSites.remove(item - _uiState.value.columns)
            listOfValidMineSites.remove(item - 1)
            listOfValidMineSites.remove(item)
        } //left edge
        else if (item % _uiState.value.columns == 0) {
            listOfValidMineSites.remove(item - _uiState.value.columns)
            listOfValidMineSites.remove(item - _uiState.value.columns + 1)
            listOfValidMineSites.remove(item)
            listOfValidMineSites.remove(item + 1)
            listOfValidMineSites.remove(item + _uiState.value.columns)
            listOfValidMineSites.remove(item + _uiState.value.columns + 1)
        } //top edge
        else if (item < _uiState.value.columns) {
            listOfValidMineSites.remove(item - 1)
            listOfValidMineSites.remove(item)
            listOfValidMineSites.remove(item + 1)
            listOfValidMineSites.remove(item + _uiState.value.columns - 1)
            listOfValidMineSites.remove(item + _uiState.value.columns)
            listOfValidMineSites.remove(item + _uiState.value.columns + 1)
        } //right edge
        else if (item % _uiState.value.columns == _uiState.value.columns - 1) {
            listOfValidMineSites.remove(item - _uiState.value.columns - 1)
            listOfValidMineSites.remove(item - _uiState.value.columns)
            listOfValidMineSites.remove(item - 1)
            listOfValidMineSites.remove(item)
            listOfValidMineSites.remove(item + _uiState.value.columns - 1)
            listOfValidMineSites.remove(item + _uiState.value.columns)
        } //bottom edge
        else if (item >= _uiState.value.boardSize -_uiState.value.columns) {
            listOfValidMineSites.remove(item - _uiState.value.columns - 1)
            listOfValidMineSites.remove(item - _uiState.value.columns)
            listOfValidMineSites.remove(item - _uiState.value.columns + 1)
            listOfValidMineSites.remove(item - 1)
            listOfValidMineSites.remove(item)
            listOfValidMineSites.remove(item + 1)
        } //anything else
        else {

            listOfValidMineSites.remove(item - _uiState.value.columns - 1)
            listOfValidMineSites.remove(item - _uiState.value.columns)
            listOfValidMineSites.remove(item - _uiState.value.columns + 1)

            listOfValidMineSites.remove(item - 1)
            listOfValidMineSites.remove(item)
            listOfValidMineSites.remove(item + 1)

            listOfValidMineSites.remove(item + _uiState.value.columns - 1)
            listOfValidMineSites.remove(item + _uiState.value.columns)
            listOfValidMineSites.remove(item + _uiState.value.columns + 1)
        }

        _uiState.update {
            it.copy(
                listOfValidMineSites = listOfValidMineSites
            )
        }


        //creating new variable to hold index values of mine sites
        var mineSites: SnapshotStateList<Int> = mutableStateListOf()
        var newListOfValidMineSites: List<Int> = mutableListOf()

        //randomising the list of valid mine sites
        newListOfValidMineSites = listOfValidMineSites.shuffled()

        //adding the first 'n' records in the shuffled list to be a mine
        for (item in (0 until _uiState.value.numberOfMinesTotal)) {
            mineSites.add(newListOfValidMineSites[item])
        }

        //making the new records mines by inserting the index
        for (item in mineSites) {
            _uiState.value.isMine[item] = true
        }

        //get numberOfMinesNear for each record
        for (box in (0 until _uiState.value.boardSize)) {
            //numberOfMinesNear should be null if that area is a mine itself
            var counter = 0
            if (_uiState.value.isMine[box] == true) {
                _uiState.value.numberOfMinesNear.add(null)
            } else {
                //bottom left corner
                if(box % _uiState.value.columns == 0 && box == _uiState.value.boardSize -_uiState.value.columns) {
                    if(_uiState.value.isMine[box - _uiState.value.columns]) {
                        counter += 1
                    }
                    if(_uiState.value.isMine[box - _uiState.value.columns + 1]) {
                        counter += 1
                    }
                    if(_uiState.value.isMine[box + 1]) {
                        counter += 1
                    }
                } //bottom right corner
                else if(box == (_uiState.value.columns * _uiState.value.rows) - 1) {
                    if(_uiState.value.isMine[box - _uiState.value.columns - 1]) {
                        counter += 1
                    }
                    if(_uiState.value.isMine[box - _uiState.value.columns]) {
                        counter += 1
                    }
                    if(_uiState.value.isMine[box - 1]) {
                        counter += 1
                    }
                } //top left corner
                else if(box == 0) {
                    if(_uiState.value.isMine[box + 1]) {
                        counter += 1
                    }
                    if(_uiState.value.isMine[box + _uiState.value.columns]) {
                        counter += 1
                    }
                    if(_uiState.value.isMine[box + _uiState.value.columns + 1]) {
                        counter += 1
                    }
                } //top right corner
                else if(box == _uiState.value.columns - 1) {
                    if(_uiState.value.isMine[box - 1]) {
                        counter += 1
                    }
                    if(_uiState.value.isMine[box + _uiState.value.columns - 1]) {
                        counter += 1
                    }
                    if(_uiState.value.isMine[box + _uiState.value.columns]) {
                        counter += 1
                    }
                } //left edge
                else if(box % _uiState.value.columns == 0) {
                    if(_uiState.value.isMine[box - _uiState.value.columns]) {
                        counter += 1
                    }
                    if(_uiState.value.isMine[box - _uiState.value.columns + 1]) {
                        counter += 1
                    }
                    if(_uiState.value.isMine[box + 1]) {
                        counter += 1
                    }
                    if(_uiState.value.isMine[box + _uiState.value.columns]) {
                        counter += 1
                    }
                    if(_uiState.value.isMine[box + _uiState.value.columns + 1]) {
                        counter += 1
                    }
                } //top edge
                else if(box < _uiState.value.columns) {
                    if(_uiState.value.isMine[box - 1]) {
                        counter += 1
                    }
                    if(_uiState.value.isMine[box + 1]) {
                        counter += 1
                    }
                    if(_uiState.value.isMine[box + _uiState.value.columns - 1]) {
                        counter += 1
                    }
                    if(_uiState.value.isMine[box + _uiState.value.columns]) {
                        counter += 1
                    }
                    if(_uiState.value.isMine[box + _uiState.value.columns + 1]) {
                        counter += 1
                    }
                } //right edge
                else if (box % _uiState.value.columns == _uiState.value.columns - 1) {
                    if(_uiState.value.isMine[box - _uiState.value.columns - 1]) {
                        counter += 1
                    }
                    if(_uiState.value.isMine[box - _uiState.value.columns]) {
                        counter += 1
                    }
                    if(_uiState.value.isMine[box - 1]) {
                        counter += 1
                    }
                    if(_uiState.value.isMine[box + _uiState.value.columns - 1]) {
                        counter += 1
                    }
                    if(_uiState.value.isMine[box + _uiState.value.columns]) {
                        counter += 1
                    }
                } //bottom edge
                else if(box >= _uiState.value.boardSize - _uiState.value.columns) {
                    if(_uiState.value.isMine[box - _uiState.value.columns - 1]) {
                        counter += 1
                    }
                    if(_uiState.value.isMine[box - _uiState.value.columns]) {
                        counter += 1
                    }
                    if(_uiState.value.isMine[box - _uiState.value.columns + 1]) {
                        counter += 1
                    }
                    if(_uiState.value.isMine[box - 1]) {
                        counter += 1
                    }
                    if(_uiState.value.isMine[box + 1]) {
                        counter += 1
                    }
                } //anything else
                else {
                    if(_uiState.value.isMine[box - _uiState.value.columns - 1]) {
                        counter += 1
                    }
                    if(_uiState.value.isMine[box - _uiState.value.columns]) {
                        counter += 1
                    }
                    if(_uiState.value.isMine[box - _uiState.value.columns + 1]) {
                        counter += 1
                    }
                    if(_uiState.value.isMine[box - 1]) {
                        counter += 1
                    }
                    if(_uiState.value.isMine[box + 1]) {
                        counter += 1
                    }
                    if(_uiState.value.isMine[box + _uiState.value.columns - 1]) {
                        counter += 1
                    }
                    if(_uiState.value.isMine[box + _uiState.value.columns]) {
                        counter += 1
                    }
                    if(_uiState.value.isMine[box + _uiState.value.columns + 1]) {
                        counter += 1
                    }
                }
                _uiState.value.numberOfMinesNear.add(counter)
            }
        }

        //initialise all items as non-revealed
        for(it in (0 until _uiState.value.boardSize)) {
            _uiState.value.isRevealed.add(false)
        }

        //item clicked should not have any nearby mines
        _uiState.value.isRevealed[item] = true

        //IDEA SHOULD BE TO THEN ITERATIVELY REVEAL ALL SQUARES UNTIL EVERY SQUARE
        //HAS A NUMBER SURROUNDED


        //go until you have numbers in every non-diagonal direction
        while (true) {

            //Get all indexes currently revealed
            val indexesRevealed = _uiState.value.isRevealed.mapIndexedNotNull { index, value ->
                if (value == true) index else null
            }.toMutableList()

            val originalIndexesRevealed = _uiState.value.isRevealed.mapIndexedNotNull { index, value ->
                if (value == true) index else null
            }.toMutableList()

            //Get all indexes with no mines or not a mine
            val indexesWithNoMines = _uiState.value.numberOfMinesNear.mapIndexedNotNull { index, value ->
                if (value == 0) index else null
            }

            //iterating over all indexes with no mines
            for(item in indexesWithNoMines) {

                //if the item is not part of what's been revealed
                if(indexesRevealed.any { it == item } != true) {
                    //append downwards
                    //if the item directly ABOVE is part of what's been revealed
                    if (indexesRevealed.any { it == item - _uiState.value.columns } == true &&
                        item < _uiState.value.boardSize) {
                        indexesRevealed.add(item)
                        _uiState.value.isRevealed[item] = true
                    }
                    //append upwards
                    //if the item directly BELOW is part of what's been revealed
                    if (indexesRevealed.any { it == item + _uiState.value.columns } == true &&
                        item > 0) {
                        indexesRevealed.add(item)
                        _uiState.value.isRevealed[item] = true
                    }
                    //append right
                    //if the item directly LEFT is part of what's been revealed
                    if (indexesRevealed.any { (it == item - 1) } == true && (item % _uiState.value.columns != 0)) {
                        indexesRevealed.add(item)
                        _uiState.value.isRevealed[item] = true
                    }
                    //append left
                    if (indexesRevealed.any { (it == item + 1) } == true && ((item + 1) % _uiState.value.columns != 0)) {
                        indexesRevealed.add(item)
                        _uiState.value.isRevealed[item] = true
                    }
                }
            }

            if(originalIndexesRevealed == indexesRevealed) {
                for(item in indexesRevealed) {
                    _uiState.value.isRevealed[item] = true
                }
                break
            }
        }



        val indexesWithNoMinesRevealed = _uiState.value.isRevealed.mapIndexedNotNull { index, value ->
            if (value == true) index else null
        }.toMutableList()






        for(item in indexesWithNoMinesRevealed) {
            if((item - _uiState.value.columns) > 0) {
                _uiState.value.isRevealed[item - _uiState.value.columns] = true
            }
            if((item + 1) % _uiState.value.columns != 0) {
                _uiState.value.isRevealed[item + 1] = true
            }
            if((item + _uiState.value.columns) < _uiState.value.boardSize) {
                _uiState.value.isRevealed[item + _uiState.value.columns] = true
            }
            if(item % _uiState.value.columns != 0) {
                _uiState.value.isRevealed[item - 1] = true
            }
        }


        val indexesWithoutCornerRecords = _uiState.value.isRevealed.mapIndexedNotNull { index, value ->
            if (value == true) index else null
        }.toMutableList()



        for(item in indexesWithoutCornerRecords) {
            //append top left
            if((item - _uiState.value.columns - 1) > 0 && _uiState.value.isMine[item] == false && (item - 1) < _uiState.value.boardSize &&
                _uiState.value.isMine[item - _uiState.value.columns] == false && ((item - _uiState.value.columns) in indexesWithoutCornerRecords) &&
                _uiState.value.isMine[item - 1] == false && ((item - 1) in indexesWithoutCornerRecords) &&
                _uiState.value.isMine[item - _uiState.value.columns - 1] == false && ((item - _uiState.value.columns - 1) !in indexesWithoutCornerRecords) &&
                _uiState.value.numberOfMinesNear[item - _uiState.value.columns - 1] != 0) {
                _uiState.value.isRevealed[item - _uiState.value.columns - 1] = true
            }
            //append top right
            if((item - _uiState.value.columns) > 0 && _uiState.value.isMine[item] == false && (item + 1) < _uiState.value.boardSize &&
                _uiState.value.isMine[item - _uiState.value.columns] == false && ((item - _uiState.value.columns) in indexesWithoutCornerRecords) &&
                _uiState.value.isMine[item + 1] == false && ((item + 1) in indexesWithoutCornerRecords) &&
                _uiState.value.isMine[item - _uiState.value.columns + 1] == false && ((item - _uiState.value.columns + 1) !in indexesWithoutCornerRecords) &&
                _uiState.value.numberOfMinesNear[item - _uiState.value.columns + 1] != 0) {
                _uiState.value.isRevealed[item - _uiState.value.columns + 1] = true
            }
            //append bottom left
            if((item - 1) > 0 && (item + _uiState.value.columns) < _uiState.value.boardSize && _uiState.value.isMine[item] == false &&
                _uiState.value.isMine[item + _uiState.value.columns] == false && ((item + _uiState.value.columns) in indexesWithoutCornerRecords) &&
                _uiState.value.isMine[item - 1] == false && ((item - 1) in indexesWithoutCornerRecords) &&
                _uiState.value.isMine[item + _uiState.value.columns - 1] == false && ((item + _uiState.value.columns - 1) !in indexesWithoutCornerRecords) &&
                _uiState.value.numberOfMinesNear[item + _uiState.value.columns - 1] != 0) {
                _uiState.value.isRevealed[item + _uiState.value.columns - 1] = true
            }
            //append bottom right
            if((item + 1) > 0 && (item + _uiState.value.columns + 1) < _uiState.value.boardSize && _uiState.value.isMine[item] == false &&
                _uiState.value.isMine[item + _uiState.value.columns] == false && ((item + _uiState.value.columns) in indexesWithoutCornerRecords) &&
                _uiState.value.isMine[item + 1] == false && ((item + 1) in indexesWithoutCornerRecords) &&
                _uiState.value.isMine[item + _uiState.value.columns + 1] == false && ((item + _uiState.value.columns + 1) !in indexesWithoutCornerRecords) &&
                _uiState.value.numberOfMinesNear[item + _uiState.value.columns + 1] != 0) {
                _uiState.value.isRevealed[item + _uiState.value.columns + 1] = true
            }
        }
    }

    fun checkGameLost(
        item: Int
    ) {
        if(_uiState.value.isMine[item] == true && item !in _uiState.value.suspectedMines) {
            _uiState.update {
                it.copy(
                    isGameOver = true,
                    won = false
                )
            }
        }
    }

    fun checkGameWon(
        item: Int
    ) {
        val allRecordsNotMines = _uiState.value.isMine.mapIndexedNotNull { index, value ->
            if (value == false) index else null
        }.toMutableList()

        val allMines = _uiState.value.isMine.mapIndexedNotNull { index, value ->
            if (value == true) index else null
        }.toMutableList()

        val suspectedMines = _uiState.value.suspectedMines.toMutableList()


        if(allRecordsNotMines.all { _uiState.value.isRevealed[it] == true } ||
            allMines == suspectedMines
        ) {
            _uiState.update {
                it.copy(
                    isGameOver = true,
                    won = true
                )
            }
        }
    }

    fun checkForNonMine(
        item: Int
    ) {
        checkGameLost(item)
        iterateInDirections(item)
        checkGameWon(item)
    }


    fun addSuspectedMine(
        item: Int
    ) {
        _uiState.value.suspectedMines.add(item)

        _uiState.update {
            it.copy(
                minesLeft = _uiState.value.minesLeft - 1
            )
        }
    }


    fun removeSuspectedMine(
        item: Int
    ) {
        _uiState.value.suspectedMines.remove(item)
    }

    fun postGameAnalysisMode() {
        _uiState.update {
            it.copy(
                won = null
            )
        }
    }

    fun updateHistoricalTimesEasy(
        timeToUpdate: Double?
    ) {
        _uiState.update {
            it.copy(
                historicalTimesEasy = timeToUpdate
            )
        }
    }

    fun updateHistoricalTimesMedium(
        timeToUpdate: Double?
    ) {
        _uiState.update {
            it.copy(
                historicalTimesMedium = timeToUpdate
            )
        }
    }

    fun updateHistoricalTimesHard(
        timeToUpdate: Double?
    ) {
        _uiState.update {
            it.copy(
                historicalTimesHard = timeToUpdate
            )
        }
    }
}
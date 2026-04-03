package com.example.myapplication


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MinesweeperHome(
    navigateToEasyGame: () -> Unit,
    navigateToMediumGame: () -> Unit,
    navigateToHardGame: () -> Unit,
    historicalTimesEasy: Double?,
    mostRecentTimeEasy: List<Double?>?,
    historicalTimesMedium: Double?,
    mostRecentTimeMedium: List<Double?>?,
    historicalTimesHard: Double?,
    mostRecentTimeHard: List<Double?>?,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier
                .weight(0.4f)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Spacer(
                modifier = Modifier
                    .weight(0.4f)
            )
            Button(
                onClick = navigateToEasyGame
            ) {
                Text(
                    text = "Easy"
                )
            }
            Spacer(
                modifier = Modifier
                    .weight(0.2f)
            )
            Text(
                text = "Recent time: $historicalTimesEasy",
                fontSize = 6.sp
            )
            Spacer(
                modifier = Modifier
                    .weight(0.2f)
            )
            Text(
                text = "Historical times: " + mostRecentTimeEasy?.toList().toString(),
                fontSize = 6.sp
            )
        }
        Spacer(
            modifier = Modifier
                .weight(0.1f)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Spacer(
                modifier = Modifier
                    .weight(0.4f)
            )
            Button(
                onClick = navigateToMediumGame
            ) {
                Text(
                    text = "Medium"
                )
            }
            Spacer(
                modifier = Modifier
                    .weight(0.2f)
            )
            Text(
                text = "Recent time: $historicalTimesMedium",
                fontSize = 6.sp
            )
            Spacer(
                modifier = Modifier
                    .weight(0.2f)
            )
            Text(
                text = "Historical times: " + mostRecentTimeMedium?.toList().toString(),
                fontSize = 6.sp
            )
        }
        Spacer(
            modifier = Modifier
                .weight(0.1f)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Spacer(
                modifier = Modifier
                    .weight(0.4f)
            )
            Button(
                onClick = navigateToHardGame
            ) {
                Text(
                    text = "Hard"
                )
            }
            Spacer(
                modifier = Modifier
                    .weight(0.2f)
            )
            Text(
                text = "Recent time: $historicalTimesHard",
                fontSize = 6.sp
            )
            Spacer(
                modifier = Modifier
                    .weight(0.2f)
            )
            Text(
                text = "Historical times: " + mostRecentTimeHard?.toList().toString(),
                fontSize = 6.sp
            )
        }
        Spacer(
            modifier = Modifier
                .weight(0.4f)
        )
    }
}
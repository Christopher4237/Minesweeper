package com.example.myapplication


import android.annotation.SuppressLint
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

@SuppressLint("DefaultLocale")
@Composable
fun MinesweeperHome(
    navigateToEasyGame: () -> Unit,
    navigateToMediumGame: () -> Unit,
    navigateToHardGame: () -> Unit,
    mostRecentTimeEasy: Double?,
    bestTimeEasy: Double?,
    mostRecentTimeMedium: Double?,
    bestTimeMedium: Double?,
    mostRecentTimeHard: Double?,
    bestTimeHard: Double?,
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
            verticalAlignment = Alignment.CenterVertically,
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
            if(mostRecentTimeEasy!= null) {
                Text(
                    text = "Recent time: " + String.format("%.1f", mostRecentTimeEasy).toDouble(),
                    fontSize = 12.sp
                )
            }
            Spacer(
                modifier = Modifier
                    .weight(0.2f)
            )
            if(bestTimeEasy!= null) {
                Text(
                    text = "Best time: " + String.format("%.1f", bestTimeEasy).toDouble(),
                    fontSize = 12.sp
                )
            }
        }
        Spacer(
            modifier = Modifier
                .weight(0.1f)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
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
            if(mostRecentTimeMedium!= null) {
                Text(
                    text = "Recent time: $mostRecentTimeMedium",
                    fontSize = 12.sp
                )
            }
            Spacer(
                modifier = Modifier
                    .weight(0.2f)
            )
            if(bestTimeMedium!= null) {
                Text(
                    text = "Best time: $bestTimeMedium",
                    fontSize = 12.sp
                )
            }
        }
        Spacer(
            modifier = Modifier
                .weight(0.1f)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
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
            if(mostRecentTimeHard!= null) {
                Text(
                    text = "Recent time: $mostRecentTimeHard",
                    fontSize = 12.sp
                )
            }
            Spacer(
                modifier = Modifier
                    .weight(0.2f)
            )
            if(bestTimeHard!= null) {
                Text(
                    text = "Best time: $bestTimeHard",
                    fontSize = 12.sp
                )
            }
        }
        Spacer(
            modifier = Modifier
                .weight(0.4f)
        )
    }
}
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
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MinesweeperHome(
    navigateToEasyGame: () -> Unit,
    navigateToMediumGame: () -> Unit,
    navigateToHardGame: () -> Unit,
    bestTimeEasy: Double?,
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
                text = "Best time: $bestTimeEasy"
            )
            Spacer(
                modifier = Modifier
                    .weight(0.2f)
            )
        }
        Spacer(
            modifier = Modifier
                .weight(0.1f)
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
                .weight(0.1f)
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
                .weight(0.4f)
        )
    }
}
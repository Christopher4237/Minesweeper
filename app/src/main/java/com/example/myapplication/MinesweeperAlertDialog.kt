package com.example.myapplication

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MinesweeperAlertDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    playAgain: () -> Unit,
    text:  @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        title = {
            Text(
                text = "Game Over"
            )
        },
        text = text,
        dismissButton = {
            TextButton(
                onClick = playAgain
            ) {
                Text(
                    text = "Play Again"
                )
            }
        },
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(
                onClick = onConfirmation
            ) {
                Text(
                    text = "Return Home"
                )
            }
        }
    )
}
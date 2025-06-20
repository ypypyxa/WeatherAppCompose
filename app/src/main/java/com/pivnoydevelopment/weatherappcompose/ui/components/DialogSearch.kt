package com.pivnoydevelopment.weatherappcompose.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun DialogSearch(
    dialogState: MutableState<Boolean> = mutableStateOf(false),
    onSubmit: (String) -> Unit
) {
    val dialogText = remember {
        mutableStateOf("")
    }

    AlertDialog(
        onDismissRequest = { dialogState.value = false },
        confirmButton = {
            TextButton(
                onClick = {
                    onSubmit(dialogText.value)
                    dialogState.value = false
                }
            ) {
                Text(text = "OK")
            }
        },
        dismissButton = {
            TextButton(
                onClick = { dialogState.value = false }
            ) {
                Text(text = "Cancel")
            }
        },
        title = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Введите название города:")
                TextField(
                    value = dialogText.value,
                    onValueChange = {
                        dialogText.value = it
                    }
                )
            }
        }
    )
}
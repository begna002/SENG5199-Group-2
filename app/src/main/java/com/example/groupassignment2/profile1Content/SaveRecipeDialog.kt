package com.example.groupassignment2.profile1Content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SaveRecipeDialog(startDialog: MutableState<Boolean>, text: String) {
    val closeDialog: () -> Unit = {
        startDialog.value = false
    }

    SaveRecipeDialog(startDialog.value, closeDialog, text)
}


@Composable
fun SaveRecipeDialog(dialog: Boolean, closeDialog: () -> Unit, text: String) {
    val animationClose = remember { mutableStateOf(false) }

    val animateTrigger = remember { mutableStateOf(false) }
    LaunchedEffect(key1 = Unit) {
        launch {
            animateTrigger.value = true
            delay(1000)
            animationClose.value = true
        }
    }

    if (animationClose.value) {
        LaunchedEffect(key1 = Unit) {
            launch {
                animateTrigger.value = false
                delay(380)
                closeDialog()
            }
        }
    }

    if (dialog) {
        Dialog(onDismissRequest = { closeDialog() }) {
            AnimatedScaleInFadeOutTransition(visible = animateTrigger.value) {

                Card(
                    modifier = Modifier
                        .size(300.dp, 75.dp),
                    shape = RoundedCornerShape(16.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        Text(
                            text = "$text",
                            Modifier.padding(start = 8.dp, end = 8.dp, top= 8.dp),
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp
                        )
                    }
                }
            }
        }
    }
}

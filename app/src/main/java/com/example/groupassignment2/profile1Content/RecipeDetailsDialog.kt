package com.example.groupassignment2.profile1Content

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.groupassignment2.data.FoodItem
import com.example.groupassignment2.data.RecipeIngredient
import com.example.groupassignment2.data.RecipeStep
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun RecipeDetailsDialog(startDialog: MutableState<Boolean>, item: FoodItem, isSaved: Boolean) {
    val closeDialog: () -> Unit = {
        startDialog.value = false
    }

    RecipeDialog(startDialog.value, closeDialog, item, isSaved)
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RecipeDialog(dialog: Boolean, closeDialog: () -> Unit, item: FoodItem, isSaved: Boolean) {
    val animationClose = remember { mutableStateOf(false) }

    val listState = rememberLazyListState()
    val closeAfterDelete: () -> Unit = {
        closeDialog()
    }

    val animateTrigger = remember { mutableStateOf(false) }
    LaunchedEffect(key1 = Unit) {
        launch {
            animateTrigger.value = true
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
        Dialog(
            onDismissRequest = { closeDialog() },
            properties = DialogProperties(usePlatformDefaultWidth = false),
        ) {
            AnimatedScaleInOutTransition(visible = animateTrigger.value) {
                Card(
                    modifier = Modifier.fillMaxSize(),
                    shape = RoundedCornerShape(16.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Box {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(
                                    painter = rememberAsyncImagePainter(
                                        model = ImageRequest.Builder(LocalContext.current)
                                            .data(item.imageUrl)
                                            .memoryCacheKey(item.imageUrl)
                                            .diskCacheKey(item.imageUrl)
                                            .build()
                                    ),
                                    contentDescription = item.imageUrl,
                                    modifier = Modifier
                                        .size(width = 350.dp, height = 250.dp)
                                        .padding(top = 12.dp, bottom = 12.dp)
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(end = 12.dp, top = 10.dp),
                                horizontalArrangement = Arrangement.End
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Close",
                                    modifier = Modifier
                                        .size(30.dp)
                                        .clickable {
                                            animationClose.value = true
                                        }
                                )
                            }
                        }
                        Text(
                            text = item.title,
                            modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        if (item.isVegetarian || item.isVegan) {
                            Text(
                                text = item.getDiet(),
                                modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                                textAlign = TextAlign.Center,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Text(
                            text = item.getReadyIn(),
                            modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp),
                            textAlign = TextAlign.Center,
                            fontSize = 12.sp,
                            fontStyle = FontStyle.Italic
                        )
                        Column(Modifier.fillMaxSize()) {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp)
                                    .weight(1f),
                                state = listState,
                            )
                            {
                                stickyHeader {
                                    RecipeHeader("Ingredients")
                                }
                                itemsIndexed(item.ingredients) { index, item ->
                                    IngredientLine(item)
                                }
                                stickyHeader {
                                    RecipeHeader("Instructions")
                                }
                                if (item.instructions.isNotEmpty()) {
                                    itemsIndexed(item.instructions[0].steps) { index, instruction ->
                                        InstructionLine(instruction)
                                        if (index != item.instructions[0].steps.size - 1)
                                            Divider(
                                                modifier = Modifier.padding(
                                                    start = 12.dp,
                                                    end = 12.dp,
                                                    top = 12.dp
                                                )
                                            )
                                    }
                                }
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                            ) {
                                if (isSaved) {
                                    RemoveRecipeButton(item, closeAfterDelete)
                                } else {
                                    SaveRecipeButton(item)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun IngredientLine(ingredient: RecipeIngredient) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "${ingredient.name}",
            modifier = Modifier.padding(top = 12.dp, start = 12.dp, end = 12.dp),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun InstructionLine(step: RecipeStep) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = "${step.number}",
            modifier = Modifier.padding(top = 12.dp, start = 12.dp, end = 12.dp),
        )
        Text(
            text = "${step.step.replace("  ", " ")}",
            modifier = Modifier.padding(top = 12.dp, start = 12.dp, end = 12.dp),
        )
    }
}

@Composable
fun RecipeHeader(text: String) {
    Row(
        modifier = Modifier
            .background(Color.LightGray)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text,
            fontStyle = FontStyle.Italic
        )
    }
}
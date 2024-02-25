package com.example.groupassignment2.service

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.groupassignment2.data.Event
import com.example.groupassignment2.ui.theme.GreenStart
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.TextField


@Composable
fun LogsSection(
    events: List<Event>,
    onDeleteClicked: (Event) -> Unit,
    onUpdateClicked: (Event) -> Unit
) {
    var isVisible by remember { mutableStateOf(false) }
    var iconState by remember { mutableStateOf(Icons.Rounded.KeyboardArrowUp) }
    var editEventDialogOpen by remember { mutableStateOf(false) }
    var editedEvent by remember { mutableStateOf(Event("", "", Icons.Default.Edit, Icons.Default.Delete)) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp),
        contentAlignment = Alignment.BottomCenter
    ) {

        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .background(MaterialTheme.colorScheme.inverseOnSurface)
                .animateContentSize()
        ) {

            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .animateContentSize()
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(modifier = Modifier
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.secondary)
                    .clickable {
                        isVisible = !isVisible
                        iconState = if (isVisible) {
                            Icons.Rounded.KeyboardArrowUp
                        } else {
                            Icons.Rounded.KeyboardArrowDown
                        }
                    }
                ) {
                    Icon(
                        modifier = Modifier.size(25.dp),
                        imageVector = iconState,
                        contentDescription = "Test",
                        tint = MaterialTheme.colorScheme.onSecondary
                    )
                }

                Spacer(modifier = Modifier.width(20.dp))

                Text(
                    text = "Event logs",
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    fontWeight = FontWeight.Bold
                )

            }

            Spacer(
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.secondaryContainer)
            )

            if (isVisible) {
                BoxWithConstraints(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
                        .background(MaterialTheme.colorScheme.background)
                ) {

                    val boxWithConstraintsScope = this
                    val width = boxWithConstraintsScope.maxWidth / 3

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {

                            Text(
                                modifier = Modifier.width(width),
                                text = "Event",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onBackground
                            )

                            Text(
                                modifier = Modifier.width(width),
                                text = "Date/Time",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onBackground,
                                textAlign = TextAlign.End
                            )

                            Text(
                                modifier = Modifier.width(width),
                                text = "Status",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onBackground,
                                textAlign = TextAlign.End
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        LazyColumn {
                            items(events.size) { index ->
                                LogsEventSectionItem(
                                    event = events[index],
                                    width = width,
                                    onDeleteClicked = { onDeleteClicked(events[index]) },
                                    onUpdateClicked = { onUpdateClicked(events[index]) },
                                    onEditClicked = { editedEvent = it; editEventDialogOpen = true }
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    EditEventDialog(
        event = editedEvent,
        isOpen = editEventDialogOpen,
        onClose = { editEventDialogOpen = false },
        onSave = { /* Implement saving edited event here */ }
    )
}

@Composable
fun LogsEventSectionItem(
    event: Event,
    width: Dp,
    onDeleteClicked: () -> Unit,
    onUpdateClicked: () -> Unit,
    onEditClicked: (Event) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row(
            modifier = Modifier.width(width),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(GreenStart)
                    .padding(4.dp)
            ) {
                // Display event name or icon here
            }

            Text(
                modifier = Modifier
                    .padding(start = 10.dp),
                text = event.event_name,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }

        Text(
            modifier = Modifier
                .width(width)
                .padding(start = 10.dp),
            text = "${event.date_time}",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.End
        )

        Row(
            modifier = Modifier
                .width(width)
                .padding(start = 45.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = event.status_update_icon,
                contentDescription = "Status Update Icon",
                tint = Color.Blue,
                modifier = Modifier
                    .size(18.dp)
                    .clickable { onUpdateClicked() }
            )
            Icon(
                imageVector = event.status_delete_icon,
                contentDescription = "Status Delete Icon",
                tint = Color.Red,
                modifier = Modifier
                    .size(18.dp)
                    .clickable { onDeleteClicked() }
            )
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit Icon",
                tint = Color.Gray,
                modifier = Modifier
                    .size(18.dp)
                    .clickable { onEditClicked(event) } // Invoke edit click with event parameter
            )
        }
    }
}
@Composable
fun EditEventDialog(
    event: Event,
    isOpen: Boolean,
    onClose: () -> Unit,
    onSave: (Event) -> Unit
) {
    var editedEventName by remember { mutableStateOf(event.event_name) }
    var editedDateTime by remember { mutableStateOf(event.date_time) }

    // Handle changes to edited event name and date/time
    val onEventNameChange: (String) -> Unit = { editedEventName = it }
    val onDateTimeChange: (String) -> Unit = { editedDateTime = it }

    if (isOpen) {
        AlertDialog(
            onDismissRequest = onClose,
            title = {
                Text(text = "Edit Event")
            },
            text = {
                Column {
                    TextField(
                        value = editedEventName,
                        onValueChange = onEventNameChange,
                        label = { Text("Event Name") }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = editedDateTime,
                        onValueChange = onDateTimeChange,
                        label = { Text("Date/Time") }
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    onSave(
                        event.copy(
                            event_name = editedEventName,
                            date_time = editedDateTime
                        )
                    )
                    onClose()
                }) {
                    Text(text = "Save")
                }
            },
            dismissButton = {
                Button(onClick = onClose) {
                    Text(text = "Close")
                }
            }
        )
    }
}
package com.example.groupassignment2.service
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.groupassignment2.data.Event
import com.example.groupassignment2.source.PlusButton
@Composable
fun EventsFormSection() {
    var eventName by remember { mutableStateOf("") }
    var dateTime by remember { mutableStateOf("") }
    var events by remember { mutableStateOf(emptyList<Event>()) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var editEventDialogOpen by remember { mutableStateOf(false) }
    var editedEvent by remember { mutableStateOf(Event("", "", Icons.Rounded.Edit, Icons.Rounded.Delete)) }

    val onUpdateClicked: (Event) -> Unit = { event ->
        editedEvent = event
        editEventDialogOpen = true
    }

    val onDeleteClicked: (Event) -> Unit = { event ->
        events = events - event
    }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Create Event",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        TextField(
            value = eventName,
            onValueChange = { eventName = it },
            label = { Text("Event Name") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { /* Handle next action */ })
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextField(
                value = dateTime,
                onValueChange = { dateTime = it },
                label = { Text("Event Date/Time") },
                modifier = Modifier.weight(0.8f),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    /* Handle done action */
                })
            )

            Spacer(modifier = Modifier.width(8.dp))

            PlusButton {
                if (eventName.isNotEmpty() && dateTime.isNotEmpty()) {
                    events = events + Event(
                        eventName,
                        dateTime,
                        status_update_icon = Icons.Rounded.Edit,
                        status_delete_icon = Icons.Rounded.Delete
                    )
                    // Clear fields after adding event
                    eventName = ""
                    dateTime = ""
                    errorMessage = null // Clear any previous error message
                } else {
                    errorMessage = "Event name and date/time are required"
                }
            }
        }

        errorMessage?.let { message ->
            Snackbar(
                modifier = Modifier.padding(vertical = 8.dp),
                action = {
                    TextButton(onClick = { errorMessage = null }) {
                        Text("Dismiss")
                    }
                }
            ) {
                Text(message)
            }
        }

        LogsSection(
            events = events,
            onDeleteClicked = onDeleteClicked,
            onUpdateClicked = onUpdateClicked
        )
    }

    EditEventDialog(
        event = editedEvent,
        isOpen = editEventDialogOpen,
        onClose = { editEventDialogOpen = false },
        onSave = { /* Implement saving edited event here */ }
    )
}
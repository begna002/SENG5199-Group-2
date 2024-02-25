package com.example.groupassignment2.data

import androidx.compose.ui.graphics.vector.ImageVector

data class Event (
    val event_name: String,
    val date_time: String,
    val status_delete_icon: ImageVector,
    val status_update_icon: ImageVector
)
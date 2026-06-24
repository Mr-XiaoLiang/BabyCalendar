package com.lollipop.babycalendar.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircleOutline
import androidx.compose.material.icons.rounded.Event
import androidx.compose.material.icons.rounded.EventBusy
import androidx.compose.material.icons.rounded.FastForward
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

class CalendarItem(
    val key: String,
    val labelId: Int,
    val summaryId: Int,
    val dateRange: String,
    val state: CalendarItemState,
    val countdown: Int
) {

    val flagIcon: ImageVector by lazy {
        when (state) {
            CalendarItemState.InProgress -> {
                Icons.Rounded.FastForward
            }

            CalendarItemState.NotStarted -> {
                Icons.Rounded.Event
            }

            CalendarItemState.Expired -> {
                Icons.Rounded.EventBusy
            }

            CalendarItemState.Completed -> {
                Icons.Rounded.CheckCircleOutline
            }
        }
    }

    val canSwipeStartToEnd: Boolean by lazy {
        state != CalendarItemState.Completed && state != CalendarItemState.NotStarted
    }

    val canSwipeEndToStart: Boolean by lazy {
        state == CalendarItemState.Completed
    }

    @Composable
    fun cardColor(): Color {
        return when (state) {
            CalendarItemState.InProgress -> {
                MaterialTheme.colorScheme.primaryContainer
            }

            CalendarItemState.NotStarted -> {
                MaterialTheme.colorScheme.surfaceContainer
            }

            CalendarItemState.Expired -> {
                MaterialTheme.colorScheme.errorContainer
            }

            CalendarItemState.Completed -> {
                MaterialTheme.colorScheme.surfaceContainer
            }
        }
    }


}
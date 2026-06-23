package com.lollipop.babycalendar.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircleOutline
import androidx.compose.material.icons.rounded.Event
import androidx.compose.material.icons.rounded.EventBusy
import androidx.compose.material.icons.rounded.FastForward
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lollipop.babycalendar.R
import com.lollipop.babycalendar.data.CalendarHelper
import com.lollipop.babycalendar.data.CalendarState

@Composable
fun ContentPage(insets: PaddingValues) {
    val layoutDirection = LocalLayoutDirection.current
    val itemList = remember { CalendarState.itemList }
    val pregnancyStart by remember { CalendarState.pregnancyStart }
    val pregnancyDay by remember { CalendarState.pregnancyDay }
    val pregnancyWeek by remember { CalendarState.pregnancyWeek }
    val pregnancyModDay by remember { CalendarState.pregnancyModDay }
    val prenatalCountdownDay by remember { CalendarState.prenatalCountdownDay }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = insets.calculateStartPadding(layoutDirection).coerceAtLeast(16.dp),
                end = insets.calculateEndPadding(layoutDirection).coerceAtLeast(16.dp)
            ),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        item {
            Spacer(modifier = Modifier.height(insets.calculateTopPadding().coerceAtLeast(16.dp)))
        }

        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(
                            R.string.label_header,
                            pregnancyWeek,
                            pregnancyModDay,
                            pregnancyDay
                        ),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    IconButton(
                        onClick = {
                            CalendarHelper.onStartTimeChanged(0L)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Refresh,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Text(
                    text = pregnancyStart,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }

        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.4F),
                contentAlignment = Alignment.Center
            ) {
                CircularWavyProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth(0.7F)
                        .aspectRatio(1F),
                    progress = {
                        pregnancyDay.toFloat() / 280F
                    },
                    // 关键属性：强制覆盖系统的默认动态计算，让波浪一直保持最大振幅
                    amplitude = { 1f },
                    wavelength = 30.dp
                )
                Text(
                    text = stringResource(R.string.label_days, prenatalCountdownDay),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }

        items(itemList, key = { it.key }) { item ->
            ItemCard(item)
        }

        item {
            Spacer(modifier = Modifier.height(insets.calculateBottomPadding().coerceAtLeast(16.dp)))
        }

    }
}

@Composable
private fun ItemCard(item: CalendarState.Item) {
    val flagIcon: ImageVector
    val cardColor: Color
    when (item.state) {
        CalendarState.ItemState.InProgress -> {
            flagIcon = Icons.Rounded.FastForward
            cardColor = MaterialTheme.colorScheme.primaryContainer
        }

        CalendarState.ItemState.NotStarted -> {
            flagIcon = Icons.Rounded.Event
            cardColor = MaterialTheme.colorScheme.surfaceContainer
        }

        CalendarState.ItemState.Expired -> {
            flagIcon = Icons.Rounded.EventBusy
            cardColor = MaterialTheme.colorScheme.errorContainer
        }

        CalendarState.ItemState.Completed -> {
            flagIcon = Icons.Rounded.CheckCircleOutline
            cardColor = MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.5F)
        }
    }
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(cardColor)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Icon(
                imageVector = flagIcon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(68.dp)
                    .alpha(0.16F)
                    .align(Alignment.BottomEnd)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    ),
            ) {
                val countDownText = if (item.countdown > 0) {
                    stringResource(R.string.label_countdown_days, item.countdown)
                } else if (item.countdown < 0) {
                    stringResource(R.string.label_expired_days, -item.countdown)
                } else {
                    ""
                }
                if (countDownText.isNotEmpty()) {
                    Text(
                        text = countDownText,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.align(Alignment.End)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }
                Text(
                    text = stringResource(item.labelId),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(item.summaryId),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Undo
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lollipop.babycalendar.R
import com.lollipop.babycalendar.data.CalendarHelper
import com.lollipop.babycalendar.data.CalendarItem
import com.lollipop.babycalendar.data.CalendarItemState
import com.lollipop.babycalendar.data.CalendarState
import kotlinx.coroutines.launch

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
private fun LazyItemScope.ItemCard(item: CalendarItem) {
    // 使用 SwipeToDismissBox 实现侧滑菜单
    val dismissState = rememberSwipeToDismissBoxState()

    // 2. 监听滑动到终点的事件触发
    // 当状态变为 Settled 以外的值（即滑到两端松手了），触发业务逻辑
    LaunchedEffect(dismissState.currentValue) {
        when (dismissState.currentValue) {
            SwipeToDismissBoxValue.StartToEnd -> {
                // 标记完成
                CalendarHelper.onItemStateChange(item.key, true)
            }

            SwipeToDismissBoxValue.EndToStart -> {
                // 撤回完成
                CalendarHelper.onItemStateChange(item.key, false)
            }

            SwipeToDismissBoxValue.Settled -> { /* 未滑动或回弹 */
            }
        }
    }

    val coroutineScope = rememberCoroutineScope()

    val swipeIcon = if (item.state == CalendarItemState.Completed) {
        Icons.AutoMirrored.Rounded.Undo
    } else {
        Icons.Rounded.Done
    }

    SwipeToDismissBox(
        modifier = Modifier
            .fillMaxWidth()
            .animateItem(),
        state = dismissState,
        enableDismissFromStartToEnd = item.canSwipeStartToEnd,
        enableDismissFromEndToStart = item.canSwipeEndToStart,
        backgroundContent = {
            val direction = dismissState.dismissDirection
            // 侧滑展示的按钮
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalArrangement = if (direction == SwipeToDismissBoxValue.StartToEnd) {
                    Arrangement.Start
                } else {
                    Arrangement.End
                },
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    coroutineScope.launch { dismissState.reset() }
                }) {
                    Icon(
                        modifier = Modifier.size(36.dp),
                        imageVector = swipeIcon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    ) {

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.large,
            colors = CardDefaults.cardColors(item.cardColor())
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Icon(
                    imageVector = item.flagIcon,
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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            text = stringResource(item.labelId),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.weight(1F)
                        )
                        if (countDownText.isNotEmpty()) {
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = countDownText,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                            )
                        }
                    }
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
}

package com.lollipop.babycalendar.data

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList

object CalendarState {

    /**
     * 所有事件列表
     */
    val itemList = SnapshotStateList<CalendarItem>()

    /**
     * 怀孕开始时间
     */
    val pregnancyStart = mutableStateOf("")

    /**
     * 怀孕天数
     */
    val pregnancyDay = mutableIntStateOf(0)

    /**
     * 怀孕周数
     */
    val pregnancyWeek = mutableIntStateOf(0)

    /**
     * 怀孕周数取余的天数
     */
    val pregnancyModDay = mutableIntStateOf(0)

    /**
     * 生产倒计时
     */
    val prenatalCountdownDay = mutableIntStateOf(0)

}
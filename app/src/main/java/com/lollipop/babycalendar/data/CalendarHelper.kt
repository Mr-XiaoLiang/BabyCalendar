package com.lollipop.babycalendar.data

import android.content.Context
import android.content.SharedPreferences
import android.icu.text.SimpleDateFormat
import androidx.core.content.edit
import java.util.Date
import java.util.Locale

object CalendarHelper {

    private var startTime = 0L

    private const val KEY_START_TIME = "start_time"

    private const val ONE_DAY = 1000L * 60 * 60 * 24

    private var prefInstance: SharedPreferences? = null

    private var dateFormat: SimpleDateFormat? = null

    fun init(context: Context) {
        val preferences = getPreferences(context)
        setStartTime(preferences.getLong(KEY_START_TIME, 0L))
    }

    fun onStartTimeChanged(time: Long) {
        setStartTime(time)
        prefInstance?.edit {
            putLong(KEY_START_TIME, time)
        }
    }

    fun onItemStateChange(key: String, isCompleted: Boolean) {
        prefInstance?.edit {
            putBoolean(key, isCompleted)
        }
        calculate()
    }

    private fun setStartTime(time: Long) {
        startTime = time
        calculate()
    }

    private fun calculate() {
        val time = startTime
        if (time == 0L) {
            CalendarState.itemList.clear()
            CalendarState.pregnancyDay.intValue = 0
            CalendarState.pregnancyWeek.intValue = 0
            CalendarState.pregnancyModDay.intValue = 0
            CalendarState.pregnancyStart.value = ""
            return
        }

        CalendarState.pregnancyStart.value = getDateFormat().format(Date(time))
        val nowDayNumber = System.currentTimeMillis() / ONE_DAY
        val startDayNumber = time / ONE_DAY
        val diffDays = (nowDayNumber - startDayNumber + 1).toInt()
        val weeks = diffDays / 7
        val modDays = diffDays % 7
        CalendarState.pregnancyDay.intValue = diffDays
        CalendarState.pregnancyWeek.intValue = weeks
        CalendarState.pregnancyModDay.intValue = modDays
        CalendarState.prenatalCountdownDay.intValue = (280 - diffDays)

        val itemList = mutableListOf<CalendarItem>()
        CalendarFlag.itemList.sortedBy { it.startDays }.forEach { flag ->
            val state = if (flag.startDays > diffDays) {
                // 如果开始时间比现在还大，那说明还没开始
                CalendarItemState.NotStarted
            } else {
                // 否则的情况下，
                if (isItemCompleted(flag)) {
                    // 我们看看是否完成了
                    CalendarItemState.Completed
                } else if (flag.endDays < diffDays) {
                    // 如果结束时间比现在小，那说明已经过期了
                    CalendarItemState.Expired
                } else {
                    // 否则，表示可以进行
                    CalendarItemState.InProgress
                }
            }
            val countdown = if (flag.startDays - diffDays > 0) {
                (flag.startDays - diffDays).toInt()
            } else if (flag.endDays - diffDays > 0) {
                0
            } else {
                (flag.endDays - diffDays).toInt()
            }
            itemList.add(
                CalendarItem(
                    key = flag.key,
                    labelId = flag.labelId,
                    summaryId = flag.summaryId,
                    timeBegin = flag.startDays * ONE_DAY,
                    timeEnd = flag.endDays * ONE_DAY,
                    state = state,
                    countdown = countdown
                )
            )
        }

        val sortedList = itemList.sortedBy { it.state.ordinal }
        CalendarState.itemList.clear()
        CalendarState.itemList.addAll(sortedList)
    }

    private fun isItemCompleted(flag: CalendarFlag.Item): Boolean {
        return prefInstance?.getBoolean(flag.key, false) ?: false
    }

    private fun getDateFormat(): SimpleDateFormat {
        return dateFormat ?: SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    }

    private fun getPreferences(context: Context): SharedPreferences {
        val sharedPreferences = context.getSharedPreferences("BABY_CALENDAR", Context.MODE_PRIVATE)
        prefInstance = sharedPreferences
        return sharedPreferences
    }


}
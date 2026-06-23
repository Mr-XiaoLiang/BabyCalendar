package com.lollipop.babycalendar.data

import com.lollipop.babycalendar.R

object CalendarFlag {

    /**
     *
     */
    val itemList = listOf(
        Item.week(
            key = "prenatal_checkup_times_1",
            labelId = R.string.title_prenatal_checkup_times_1,
            summaryId = R.string.summary_prenatal_checkup_times_1,
            startWeek = 6,
            endWeek = 13,
        ),
        Item.week(
            key = "prenatal_checkup_times_2",
            labelId = R.string.title_prenatal_checkup_times_2,
            summaryId = R.string.summary_prenatal_checkup_times_2,
            startWeek = 14,
            endWeek = 19,
        ),
        Item.week(
            key = "prenatal_checkup_times_3",
            labelId = R.string.title_prenatal_checkup_times_3,
            summaryId = R.string.summary_prenatal_checkup_times_3,
            startWeek = 20,
            endWeek = 24,
        ),
        Item.week(
            key = "prenatal_checkup_times_4",
            labelId = R.string.title_prenatal_checkup_times_4,
            summaryId = R.string.summary_prenatal_checkup_times_4,
            startWeek = 24,
            endWeek = 28,
        ),
        Item.week(
            key = "prenatal_checkup_times_5",
            labelId = R.string.title_prenatal_checkup_times_5,
            summaryId = R.string.summary_prenatal_checkup_times_5,
            startWeek = 30,
            endWeek = 32,
        ),
        Item.week(
            key = "prenatal_checkup_times_6",
            labelId = R.string.title_prenatal_checkup_times_6,
            summaryId = R.string.summary_prenatal_checkup_times_6,
            startWeek = 33,
            endWeek = 35,
        ),
        Item.week(
            key = "prenatal_checkup_times_7",
            labelId = R.string.title_prenatal_checkup_times_7,
            summaryId = R.string.summary_prenatal_checkup_times_7,
            startWeek = 36,
            endWeek = 36,
        ),
        Item.week(
            key = "prenatal_checkup_times_8",
            labelId = R.string.title_prenatal_checkup_times_8,
            summaryId = R.string.summary_prenatal_checkup_times_8,
            startWeek = 37,
            endWeek = 37,
        ),
        Item.week(
            key = "prenatal_checkup_times_9",
            labelId = R.string.title_prenatal_checkup_times_9,
            summaryId = R.string.summary_prenatal_checkup_times_9,
            startWeek = 38,
            endWeek = 38,
        ),
        Item.week(
            key = "prenatal_childbirth",
            labelId = R.string.title_prenatal_childbirth,
            summaryId = R.string.summary_prenatal_childbirth,
            startWeek = 39,
            endWeek = 39,
        ),
        Item.week(
            key = "first_heartbeat",
            labelId = R.string.title_first_heartbeat,
            summaryId = R.string.summary_first_heartbeat,
            startWeek = 7,
            endWeek = 7,
        ),
        Item(
            key = "first_trimester",
            labelId = R.string.title_first_trimester,
            summaryId = R.string.summary_first_trimester,
            startDays = 84,
            endDays = 84,
        ),
        Item.week(
            key = "first_heard",
            labelId = R.string.title_first_heard,
            summaryId = R.string.summary_first_heard,
            startWeek = 17,
            endWeek = 17,
        ),
        Item.week(
            key = "first_fetal_movement",
            labelId = R.string.title_first_fetal_movement,
            summaryId = R.string.summary_first_fetal_movement,
            startWeek = 18,
            endWeek = 22,
        ),
        Item(
            key = "awaiting_childbirth",
            labelId = R.string.title_awaiting_childbirth,
            summaryId = R.string.summary_awaiting_childbirth,
            startDays = 196,
            endDays = 196,
        ),
        Item(
            key = "full_term",
            labelId = R.string.title_full_term,
            summaryId = R.string.summary_full_term,
            startDays = 259,
            endDays = 259,
        )
    )

    class Item(
        val key: String,
        val labelId: Int,
        val summaryId: Int,
        val startDays: Long,
        val endDays: Long,
    ) {

        companion object {
            fun week(
                key: String,
                labelId: Int,
                summaryId: Int,
                startWeek: Long,
                endWeek: Long,
            ): Item {
                return Item(
                    key = key,
                    labelId = labelId,
                    summaryId = summaryId,
                    startDays = startWeek * 7,
                    endDays = (endWeek + 1) * 7 - 1,
                )
            }
        }

    }

}
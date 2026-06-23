package com.lollipop.babycalendar

import android.app.Application
import com.lollipop.babycalendar.data.CalendarHelper

class LApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        CalendarHelper.init(this)
    }

}
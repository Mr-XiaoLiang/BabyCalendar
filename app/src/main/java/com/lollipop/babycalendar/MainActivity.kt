package com.lollipop.babycalendar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.lollipop.babycalendar.data.CalendarState
import com.lollipop.babycalendar.page.ContentPage
import com.lollipop.babycalendar.page.StartDatePage
import com.lollipop.babycalendar.ui.theme.BabyCalendarTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val startDate by remember { CalendarState.pregnancyStart }
            BabyCalendarTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    if (startDate.isEmpty()) {
                        StartDatePage(innerPadding)
                    } else {
                        ContentPage(innerPadding)
                    }
                }
            }
        }
    }
}
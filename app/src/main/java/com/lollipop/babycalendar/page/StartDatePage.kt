package com.lollipop.babycalendar.page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lollipop.babycalendar.R
import com.lollipop.babycalendar.data.CalendarHelper

@Composable
fun StartDatePage(insets: PaddingValues) {
    val datePickerState = rememberDatePickerState()
    val selectedTime = datePickerState.selectedDateMillis
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(insets)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.large,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.End
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(R.string.title_select_prenatal_start),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.summary_select_prenatal_start),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextButton(
                    enabled = selectedTime != null,
                    onClick = {
                        datePickerState.selectedDateMillis?.let {
                            CalendarHelper.onStartTimeChanged(it)
                        }
                    }
                ) {
                    Text(text = stringResource(R.string.btn_select_prenatal_start))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.large,
        ) {
            DatePicker(
                state = datePickerState,
                showModeToggle = true
            )
        }
    }

}
package com.example.muzmatch.faketexts.extensions

import org.junit.Assert.*
import org.junit.Test
import java.util.*

class CalendarExtensionsTest {

    @Test
    fun toDateTimeString() {
        assertEquals(Calendar.getInstance().apply {timeInMillis = 1580515200750 }.toDayTimeString(), "Saturday, 00:00")
        assertEquals(Calendar.getInstance().apply {timeInMillis = 1580551200750 }.toDayTimeString(), "Saturday, 10:00")
        assertEquals(Calendar.getInstance().apply {timeInMillis = 1580579520750 }.toDayTimeString(), "Saturday, 17:52")
    }
}
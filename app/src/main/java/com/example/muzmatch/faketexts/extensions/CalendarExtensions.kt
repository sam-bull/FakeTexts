package com.example.muzmatch.faketexts.extensions

import java.util.*

fun Calendar.toDayTimeString(): String {
    val hour = this.get(Calendar.HOUR_OF_DAY)
    val minute = this.get(Calendar.MINUTE)
    return "${this.getDisplayName(
        Calendar.DAY_OF_WEEK,
        Calendar.LONG,
        Locale.UK
    )}, ${if (hour < 10) "0$hour" else "$hour"}:${if (minute < 10) "0$minute" else "$minute"}"
}
package com.kodeco.android.devscribe.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun formatTime(milliseconds: Long): String {
  val sdf = SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.ENGLISH)
  val calendar = Calendar.getInstance()
  calendar.timeInMillis = milliseconds
  return sdf.format(calendar.time)
}
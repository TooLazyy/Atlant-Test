package com.example.atlanttest.extensions

import java.text.SimpleDateFormat
import java.util.*

object DateTimeUtils {

    private val simpleDateFormatter = SimpleDateFormat("yyyy.MM.dd HH:mm:SS", Locale.getDefault())

    fun formatTransactionTimestamp(timestamp: Long): String {
        return simpleDateFormatter.format(Date(timestamp))
    }
}
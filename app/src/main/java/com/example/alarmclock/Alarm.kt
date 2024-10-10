package com.example.alarmclock

data class Alarm(
    val id: Int,
    val hour: Int,
    val minute: Int,
    var isActive: Boolean = true
)
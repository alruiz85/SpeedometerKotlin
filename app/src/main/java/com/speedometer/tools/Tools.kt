package com.speedometer.tools

private const val KILOMETER_PER_HOUR = 3.6
private const val MILES_PER_HOUR = 2.237

fun Float.toKmH() = (this * KILOMETER_PER_HOUR).toInt().toString()

fun Float.toMilesH() = (this * MILES_PER_HOUR).toInt().toString()

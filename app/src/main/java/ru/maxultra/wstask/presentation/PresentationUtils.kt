package ru.maxultra.wstask.presentation

import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.log10

fun formatDouble(double: Double): String {
    val abs = abs(double)
    if (abs < 1)
        return String.format("%.6f", double)
    var digits = 7 - ceil(log10(abs)).toInt()
    if (digits < 0) digits = 0
    return String.format("%.${digits}f", double)
}

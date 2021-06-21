package ru.maxultra.wstask

fun String.removeWhiteSpaces(): String = this.replace(Regex("\\s+"), "")



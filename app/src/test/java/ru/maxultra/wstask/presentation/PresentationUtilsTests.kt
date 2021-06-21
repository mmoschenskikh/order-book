package ru.maxultra.wstask.presentation

import org.junit.Assert.assertTrue
import org.junit.Test

class PresentationUtilsTests {

    @Test
    fun formatDoubleTest() {
        assertTrue(Regex("0[.,]000000").matches(formatDouble(0.0)))
        assertTrue(Regex("5[.,]010000").matches(formatDouble(5.01)))
        assertTrue(Regex("10[.,]01100").matches(formatDouble(10.011)))
        assertTrue(Regex("100[.,]0110").matches(formatDouble(100.01101)))
        assertTrue(Regex("245[.,]8990").matches(formatDouble(245.898989)))
        assertTrue(Regex("1234[.,]800").matches(formatDouble(1234.8)))
        assertTrue(Regex("32145[.,]54").matches(formatDouble(32145.53975573)))

        assertTrue(Regex("-5[.,]010000").matches(formatDouble(-5.01)))
        assertTrue(Regex("-10[.,]01100").matches(formatDouble(-10.011)))
        assertTrue(Regex("-100[.,]0110").matches(formatDouble(-100.01101)))
        assertTrue(Regex("-245[.,]8990").matches(formatDouble(-245.898989)))
        assertTrue(Regex("-1234[.,]800").matches(formatDouble(-1234.8)))
        assertTrue(Regex("-32145[.,]54").matches(formatDouble(-32145.53975573)))
    }
}

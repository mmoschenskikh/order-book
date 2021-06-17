package ru.maxultra.wstask.domain.entities.currency

/*
 I decided to extract Currency to a separate interface
 because some may want not to hardcode currencies, but
 to fetch them from the API.
 I've chosen interface instead of abstract class to easily
 integrate with enum class implementation.
 */
interface Currency {
    /**
     * Holds the currency name like "EUR", "BTC", "ETH", etc.
     */
    val name: String
}

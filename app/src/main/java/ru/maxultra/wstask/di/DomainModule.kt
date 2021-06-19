package ru.maxultra.wstask.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.maxultra.wstask.domain.entities.currency.Currency
import ru.maxultra.wstask.domain.entities.currency.EnumCurrency
import ru.maxultra.wstask.domain.entities.currencypair.CurrencyPair
import ru.maxultra.wstask.domain.entities.currencypair.SimpleCurrencyPair

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    fun provideCurrency(name: String): Currency {
        return EnumCurrency.valueOf(name.trim().uppercase())
    }

    @Provides
    fun provideCurrencyPair(baseCurrency: Currency, quoteCurrency: Currency): CurrencyPair {
        return SimpleCurrencyPair(baseCurrency, quoteCurrency)
    }
}

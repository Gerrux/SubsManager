package com.example.testsubsmanager.database.mappers

import com.example.testsubsmanager.database.dto.Currency
import com.example.testsubsmanager.database.entity.CurrencyDatabaseModel

object CurrencyMapper: Mapper<CurrencyDatabaseModel, Currency> {

    override fun toDTO(from: CurrencyDatabaseModel): Currency {
        return Currency(
            code = from.code,
            name = from.name,
            exchangeRate = from.exchangeRate,
            quoteDate = from.quoteDate
        )
    }

    override fun toEntity(from: Currency): CurrencyDatabaseModel {
        return CurrencyDatabaseModel(
            code = from.code,
            name = from.name,
            exchangeRate = from.exchangeRate,
            quoteDate = from.quoteDate
        )
    }

}
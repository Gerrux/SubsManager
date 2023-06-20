package com.example.testsubsmanager.database.mappers

import com.example.testsubsmanager.database.dto.Subscription
import com.example.testsubsmanager.database.dto.TypeDuration
import com.example.testsubsmanager.database.entity.SubscriptionDatabaseModel

object SubscriptionMapper : Mapper<SubscriptionDatabaseModel, Subscription> {

    override fun toDTO(from: SubscriptionDatabaseModel): Subscription {
        return Subscription(
            id = from.id,
            nameSub = from.nameSub,
            color = from.color,
            price = from.price,
            currency = CurrencyMapper.toDTO(from.currency),
            startDate = from.startDate,
            endDate = from.endDate,
            renewalDate = from.renewalDate,
            duration = from.duration,
            typeDuration = TypeDuration.valueOf(from.typeDuration)
        )
    }

    override fun toEntity(from: Subscription): SubscriptionDatabaseModel {
        return SubscriptionDatabaseModel(
            id = from.id,
            nameSub = from.nameSub,
            color = from.color,
            price = from.price,
            currency = CurrencyMapper.toEntity(from.currency),
            startDate = from.startDate,
            endDate = from.endDate,
            renewalDate = from.renewalDate,
            duration = from.duration,
            typeDuration = from.typeDuration.name
        )
    }
}


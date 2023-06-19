package com.example.testsubsmanager.database.mappers

interface Mapper<T, R> {

    fun toDTO(from: T):R

    fun toEntity(from: R): T

}
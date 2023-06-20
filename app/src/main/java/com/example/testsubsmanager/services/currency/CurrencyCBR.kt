package com.example.testsubsmanager.services.currency

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "Valute")
data class CurrencyCBR(
    @field:Attribute(name = "ID")
    val id: String,

    @field:Element(name = "NumCode")
    val numCode: String,

    @field:Element(name = "CharCode")
    val charCode: String,

    @field:Element(name = "Nominal")
    val nominal: Int,

    @field:Element(name = "Name")
    val name: String,

    @field:Element(name = "Value")
    val value: String
)

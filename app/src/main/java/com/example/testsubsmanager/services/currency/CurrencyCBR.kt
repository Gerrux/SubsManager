package com.example.testsubsmanager.services.currency

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "Valute", strict = false)
class CurrencyCBR (
    @param:Attribute(name = "ID", required = false)
    @get:Attribute(name = "ID", required = false)
    val id: String,

    @param:Element(name = "NumCode", required = false)
    @get:Element(name = "NumCode", required = false)
    val numCode: String,

    @param:Element(name = "CharCode", required = false)
    @get:Element(name = "CharCode", required = false)
    val charCode: String,

    @param:Element(name = "Nominal", required = false)
    @get:Element(name = "Nominal", required = false)
    val nominal: Int,

    @param:Element(name = "Name", required = false)
    @get:Element(name = "Name", required = false)
    val name: String,

    @param:Element(name = "Value", required = false)
    @get:Element(name = "Value", required = false)
    val value: String
)

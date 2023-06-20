package com.example.testsubsmanager.services.currency

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "ValCurs", strict = false)
class ValCurs(
    @param:Attribute(name = "Date", required = false)
    @get:Attribute(name = "Date", required = false)
    var date: String,

    @param:Attribute(name = "name", required = false)
    @get:Attribute(name = "name", required = false)
    var name: String,

    @param:ElementList(name = "Valute", inline = true, required = false)
    @get:ElementList(name = "Valute", inline = true, required = false)
    var valutes: List<CurrencyCBR>,
)
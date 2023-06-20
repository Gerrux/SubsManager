package com.example.testsubsmanager.services.currency

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "ValCurs")
data class ValCurs(
    @field:Attribute(name = "Date")
    var date: String,

    @field:Attribute(name = "name")
    var name: String,

    @field:ElementList(inline = true, required = false)
    var valutes: List<CurrencyCBR>
)
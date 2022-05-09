package com.hvdevs.mydailytaxes.constructor

import java.util.*

data class Taxes(
    var description: String = "",
    var initDate: String = "",
    var monthly: Boolean = false,
    var name: String = "",
    var price: Long = 0L,
    var quotes: Int = 0,
    var type: String = ""
)
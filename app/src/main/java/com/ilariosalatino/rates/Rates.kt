package com.ilariosalatino.rates

import java.util.*

//Model for parsing the JSON
class Rates {

    var rates:  LinkedHashMap<String, Double>

    init {
        this.rates = LinkedHashMap<String, Double>()
    }

    fun addEuro(){
        var temp = this.rates.toMutableMap()
        this.rates.clear()
        this.rates.put("EUR",1.0)
        this.rates.putAll(temp)
    }

}
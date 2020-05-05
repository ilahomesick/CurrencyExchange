package com.ilariosalatino.rates

import androidx.recyclerview.widget.DiffUtil

class RatesDiffCallback(oldRates: LinkedHashMap<String, Double>, newRates: LinkedHashMap<String, Double>): DiffUtil.Callback() {

    private val oldRates:LinkedHashMap<String, Double>
    private val newRates:LinkedHashMap<String, Double>
    private val oldKeys: Array<String>
    private val newKeys: Array<String>

    init {
        this.oldRates = oldRates
        this.newRates = newRates
        this.oldKeys = oldRates.keys.toTypedArray()
        this.newKeys = newRates.keys.toTypedArray()
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return true
    }

    override fun getOldListSize(): Int {
        return oldRates.size
    }

    override fun getNewListSize(): Int {
        return newRates.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        if(oldItemPosition==0){
            return true
        }
        return oldRates[oldKeys[oldItemPosition]] == newRates[newKeys[newItemPosition]]
    }
}
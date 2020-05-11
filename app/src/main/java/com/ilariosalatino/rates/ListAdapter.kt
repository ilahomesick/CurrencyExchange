package com.ilariosalatino.rates

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import java.io.IOException
import kotlin.math.round


class ListAdapter(context: Context) : RecyclerView.Adapter<CurrencyViewHolder>() {

    private val context: Context
    private var layoutInflater: LayoutInflater? = null
    private var conversionRate = 1.0
    var rates = LinkedHashMap<String, Double>()
    var descriptions = LinkedHashMap<String, String>()
    var first: String = ""

    init {
        this.context = context
        this.layoutInflater = this.context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?
        this.descriptions = parseDescriptionsJson()
    }

    fun parseDescriptionsJson(): LinkedHashMap<String, String> {

        val jsonFileString = getJsonDataFromAsset(this.context, "descriptions.json")

        val gson = Gson()
        return gson.fromJson(jsonFileString, LinkedHashMap<String, String>().javaClass)
    }

    fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }


    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        var keys = rates.keys.toTypedArray()
        var key = keys[position]
        var amount = holder.amount
        var number = this.convertCurrency(position, key)


        if (position != 0 || key != first) {
            holder.bind(key, number, getImageid(key), this.descriptions[key])
        }
        if (position == 0 && key != first) {
            this.first = key
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CurrencyViewHolder(
            inflater, parent,
            { v ->
                if (v!! != this.conversionRate) {   //callBack to handle data refresh when the amount is changed
                    this.conversionRate = v
                    notifyItemChanged(0)
                }
            }, { m, t ->
                this.conversionRate = t     //callBack to handle the click action
                this.rates = m
                notifyDataSetChanged()
            }, this.rates.toMutableMap() as LinkedHashMap<String, Double>
        )
    }

    override fun getItemCount(): Int {
        return rates.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    //function for retrieving the flag assets
    fun getImageid(key: String): Int {
        return context.resources
            .getIdentifier(key.toLowerCase(), "drawable", context.packageName)
    }

    //Called to populate the data retrieved from your server and refresh the view
    fun refreshList(rates: LinkedHashMap<String, Double>) {
        var diffCallback = RatesDiffCallback(this.rates, rates)
        var diffResult = DiffUtil.calculateDiff(diffCallback)

        if (this.rates.size == 0) {
            this.rates.putAll(rates)
        } else {
            updateRates(rates)
        }

        diffResult.dispatchUpdatesTo(this)
    }


    //Updates the rates received from the server without changing the order of the items in case the user has clicked on some rows
    fun updateRates(rates: LinkedHashMap<String, Double>) {
        var diffCallback = RatesDiffCallback(this.rates, rates)
        var diffResult = DiffUtil.calculateDiff(diffCallback)
        var temp = this.rates.toMutableMap()
        rates.forEach { k, v ->
            if (v != temp[k] && k != first) {
                temp[k] = v
            }
        }
        this.rates.clear()
        this.rates.putAll(temp)
        diffResult.dispatchUpdatesTo(this)
    }

    fun Double.round(decimals: Int): Double {
        var multiplier = 1.0
        repeat(decimals) { multiplier *= 10 }
        return round(this * multiplier) / multiplier
    }

    fun convertCurrency(position: Int, key: String): Double {
        return if (position == 0) this.conversionRate else ((rates[key]!! * this.conversionRate) / rates[first]!!).round(
            4
        )
    }
}
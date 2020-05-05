package com.ilariosalatino.rates

import android.content.Context
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.round
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.DiffUtil


class ListAdapter(context: Context): RecyclerView.Adapter<CurrencyViewHolder>() {

    private var rates:LinkedHashMap<String, Double> = LinkedHashMap<String, Double>()
    private val context: Context
    private var layoutInflater: LayoutInflater? = null


    init {

        this.context = context;
        this.layoutInflater = this.context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        var keys = rates.keys.toTypedArray()
        var key = keys[position]
        var amount = holder.amount
        holder.bind(key,rates[key])
        amount!!.setOnEditorActionListener { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_DONE){
                updateAmount(amount.text.toString().toDoubleOrNull(),rates[key])
                true
            } else {
                false
            }
        }
        holder.itemView.setOnClickListener { itemClicked(key) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):CurrencyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CurrencyViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int {
        return rates.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }



    fun updateAmount(newAmount: Double?, oldAmount: Double?){
        var newValues = this.rates.toMutableMap()
        this.rates.forEach { k,v ->
            var actual = v
            var newValue = (actual * newAmount!!)/ oldAmount!!
            newValues[k] = newValue.round(4)
        }
        this.rates = newValues as LinkedHashMap<String, Double>
        //Handler().postDelayed({ notifyDataSetChanged() }, 1000)
        notifyDataSetChanged()
    }

    fun refreshList(rates: LinkedHashMap<String, Double>) {
        var diffCallback = RatesDiffCallback(this.rates,rates)
        var diffResult = DiffUtil.calculateDiff(diffCallback)

        if(this.rates.size==0) {
            this.rates.putAll(rates)
        }
        else{
            updateRates(rates)
        }

        //this.rates =

        diffResult.dispatchUpdatesTo(this)
        //notifyDataSetChanged()
    }

    fun updateRates(rates: LinkedHashMap<String, Double>){
        var temp = this.rates.toMutableMap()
        rates.forEach{k,v ->
            if(v != temp[k]){
                temp[k] = v
            }
        }
        this.rates.clear()
        this.rates.putAll(temp)
    }

    fun itemClicked(key: String){
        var items = this.rates.toMutableMap()
        var first = this.rates [key]
        this.rates.remove(key)
        this.rates.clear()
        if (first != null) {
            this.rates.put(key,first)
        }
        this.rates.putAll(items)
        notifyDataSetChanged()
    }

    fun getItemKey(position: Int):String{
        var keys = rates.keys.toTypedArray()
        return keys[position]
    }

    fun Double.round(decimals: Int): Double {
        var multiplier = 1.0
        repeat(decimals) { multiplier *= 10 }
        return round(this * multiplier) / multiplier
    }
}
package com.ilariosalatino.rates

import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CurrencyViewHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item, parent, false)) {


        var title: TextView? = null
        var amount: EditText? = null


        init {
            title = itemView.findViewById(R.id.currencyTitle)
            amount = itemView.findViewById(R.id.amount)
        }

    fun bind(title: String, amount: Double?){
        this.title!!.text = title
        this.amount!!.setText(amount.toString())
    }
}
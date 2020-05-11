package com.ilariosalatino.rates

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class CurrencyViewHolder(
    inflater: LayoutInflater,
    parent: ViewGroup,
    refreshCallback: (Double?) -> Unit,
    clickCallback: (LinkedHashMap<String, Double>, Double) -> Unit,
    rates: LinkedHashMap<String, Double>
) : RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item, parent, false)) {

    var title: TextView? = null
    var amount: EditText? = null
    var image: ImageView? = null
    var description: TextView? = null
    var clickCallback: (LinkedHashMap<String, Double>, Double) -> Unit

    init {
        title = itemView.findViewById(R.id.currencyTitle)
        amount = itemView.findViewById(R.id.amount)
        image = itemView.findViewById(R.id.listImage)
        description = itemView.findViewById(R.id.currencyDescription)
        amount!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                var text = p0.toString().toDoubleOrNull()
                if (adapterPosition == 0 && text != null) {
                    refreshCallback(text)
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
        itemView.setOnClickListener {
            itemClicked(amount!!.text.toString().toDoubleOrNull(), title!!.text.toString(), rates)
        }
        this.clickCallback = clickCallback
    }

    fun bind(title: String, amount: Double?, image: Int, description: String?) {
        this.title!!.text = title
        this.amount!!.setText(amount.toString())
        this.image!!.setImageResource(image)
        this.description!!.text = description
    }

    fun itemClicked(text: Double?, key: String, rates: LinkedHashMap<String, Double>) {
        if (adapterPosition != 0) {
            if (text != null) {
                var items = rates.toMutableMap()
                var first = rates[key]
                rates.remove(key)
                rates.clear()
                if (first != null) {
                    rates.put(key, first)
                }
                rates.putAll(items)
                clickCallback(rates, text)
            }
        }


    }

}
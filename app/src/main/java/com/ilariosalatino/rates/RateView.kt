package com.ilariosalatino.rates

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.app.Activity
import android.content.Intent
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class RateView : AppCompatActivity() {


    private lateinit var model: RateViewModel
    private lateinit var listView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rate_view)
        this.listView = findViewById(R.id.cell_curren)
        val listAdapter = ListAdapter(this)
        listView.apply {
                layoutManager = LinearLayoutManager(this.context)
                adapter = listAdapter
        }
        listView.post {  }
        //this.listView.adapter = listAdapter
        /*listView.setOnItemClickListener { parent, view, position, id ->
            val key = listAdapter.getItemKey(position)
            listAdapter.itemClicked(key)
        }*/
        this.model = RateViewModel()
        this.model.retrieveRates(listAdapter)
    }
}

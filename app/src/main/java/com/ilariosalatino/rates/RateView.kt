package com.ilariosalatino.rates

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class RateView : AppCompatActivity() {


    private lateinit var model: RateViewModel
    private lateinit var listView: RecyclerView
    private lateinit var loader: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rate_view)
        this.listView = findViewById(R.id.cell_curren)

        val listAdapter = ListAdapter(this)
        listAdapter.setHasStableIds(true)
        listView.apply {
                layoutManager = LinearLayoutManager(this.context)
                adapter = listAdapter
        }
        listView.itemAnimator = null
        listView.setItemViewCacheSize(31)
        loader = findViewById(R.id.loader)

        this.model = RateViewModel()
        this.model.retrieveRates(listAdapter) {
            loader.visibility = View.GONE
        }
    }

}

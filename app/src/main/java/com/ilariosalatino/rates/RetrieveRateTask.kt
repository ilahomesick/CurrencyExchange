package com.ilariosalatino.rates

import android.os.AsyncTask
import androidx.annotation.MainThread
import com.beust.klaxon.Klaxon
import java.net.URL

class RetrieveRateTask(adapter: ListAdapter, callback: () -> Unit) : AsyncTask<Any, Any, Any>() {

    private var url = "https://hiring.revolut.codes/api/android/latest?base=EUR"
    private var adapter: ListAdapter
    private var hideLoadercallback: () -> Unit

    init {
        this.adapter = adapter
        this.hideLoadercallback = callback
    }

    override fun doInBackground(vararg params: Any): Any {

        var json = Klaxon().parse<Rates>(URL(url).readText())
        json!!.addEuro()
        return json.rates
    }

    @MainThread
    override fun onPostExecute(result: Any?) {
        adapter.refreshList(result as LinkedHashMap<String, Double>)
        hideLoadercallback()
    }


}


package com.ilariosalatino.rates

import android.os.AsyncTask
import androidx.annotation.MainThread
import com.beust.klaxon.Klaxon
import java.net.URL
import kotlin.collections.LinkedHashMap

class RetrieveRateTask(adapter: ListAdapter): AsyncTask<Any, Any, Any>() {

    private var url = "https://hiring.revolut.codes/api/android/latest?base=EUR"
    private var adapter: ListAdapter

    init {
        this.adapter = adapter
    }

    override fun doInBackground(vararg params: Any): Any {

        return URL(url).readText()
    }

    @MainThread
    override fun onPostExecute(result: Any?) {
        var json = Klaxon().parse<Rates>(result as String)
        json!!.addEuro()
        adapter.refreshList(json.rates)
    }


}


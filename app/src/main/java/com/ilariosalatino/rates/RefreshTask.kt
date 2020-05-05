package com.ilariosalatino.rates

import android.os.AsyncTask
import androidx.annotation.MainThread
import com.beust.klaxon.Klaxon

class RefreshTask(adapter: ListAdapter): AsyncTask<Any, Any, Any>() {

    private var adapter: ListAdapter

    init {
        this.adapter = adapter
    }

    override fun doInBackground(vararg params: Any?): Any {
        //To change body of created functions use File | Settings | File Templates.
    }

    @MainThread
    override fun onPostExecute(result: Any?) {
        var json = Klaxon().parse<Rates>(result as String)
        json!!.addEuro()
        adapter.refreshList(json.rates)
    }

}
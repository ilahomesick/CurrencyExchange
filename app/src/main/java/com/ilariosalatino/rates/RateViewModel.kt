package com.ilariosalatino.rates

import android.os.Handler


class RateViewModel {


    fun retrieveRates(listAdapter: ListAdapter, hideLoaderCallback: () -> Unit) {
        val handler = Handler()
        val retrieve = object : Runnable {
            override fun run() {

                RetrieveRateTask(listAdapter) {
                    hideLoaderCallback()
                }.execute()

                //Running the task for retrieving rates every second
                handler.postDelayed(this, 1000)
            }
        }
        handler.post(retrieve)

    }
}
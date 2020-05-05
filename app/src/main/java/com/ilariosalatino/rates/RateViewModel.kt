package com.ilariosalatino.rates

import android.os.Handler
import androidx.core.os.HandlerCompat.postDelayed



class RateViewModel() {


    fun retrieveRates(listAdapter: ListAdapter){
        val handler = Handler()
        val retrieve = object : Runnable {
            override fun run() {
                // Do something here on the main thread

                RetrieveRateTask(listAdapter).execute()
                // Repeat this the same runnable code block again another 2 seconds
                // 'this' is referencing the Runnable object
                handler.postDelayed(this, 1000)
            }
        }
        handler.post(retrieve);

    }
}
package com.balsdon.adapterexample.gps

import com.balsdon.adapterexample.adapters.GPSTracker
import com.balsdon.adapterexample.testdata.testPoints
import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicInteger


class StaticCoroutineGPSTracker(private val scope: CoroutineScope) : GPSTracker {
    var subscriber: ((x: Float, y: Float) -> Unit)? = null
    private var job: Job? = null
    private var index = AtomicInteger(0) // Because threads
    private val points = testPoints

    override fun subscribeToUpdates(onCoordinateReceived: (x: Float, y: Float) -> Unit) {
        job?.cancel()
        subscriber = onCoordinateReceived
        job = scope.launch {
            withContext(Dispatchers.IO) { // ensures running on separate thread
                publishNextCoordinate()
            }
        }
    }

    override fun unsubscribe() {
        job?.cancel()
        subscriber = null
    }

    private fun publishNextCoordinate() {
        subscriber?.apply {
            val position = index.get()
            scope.launch {
                withContext(Dispatchers.Main) {// Back on the main thread
                    this@apply(
                        points[position].x,
                        points[position].y
                    )
                }
            }
            Thread.sleep(points[position].sleepMillis)
            index.set((index.incrementAndGet()) % points.size) //ensure wrap
            publishNextCoordinate()
        }
    }
}
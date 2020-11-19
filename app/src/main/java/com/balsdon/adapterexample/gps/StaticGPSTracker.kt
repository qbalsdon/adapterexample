package com.balsdon.adapterexample.gps

import com.balsdon.adapterexample.adapters.GPSTracker


class StaticGPSTracker : GPSTracker {
    data class TestPoint(val x: Float, val y: Float, val sleepMillis: Long)

    var subscriber: ((x: Float, y: Float) -> Unit)? = null
    private var index: Int = 0
    private var points = listOf(
        TestPoint(1F, 1F, 300),
        TestPoint(2F, 2F, 250),
        TestPoint(3F, 3F, 600),
        TestPoint(4F, 4F, 100),
        TestPoint(5F, 5F, 100),
        TestPoint(6F, 6F, 250),
        TestPoint(7F, 7F, 300),
    )

    override fun subscribeToUpdates(onCoordinateReceived: (x: Float, y: Float) -> Unit) {
        subscriber = onCoordinateReceived
        publishNextCoordinate()
    }

    override fun unsubscribe() {
        subscriber = null
    }

    private fun publishNextCoordinate() {
        index %= points.size //ensure wrap

        subscriber?.apply {
            this(points[index].x, points[index].y)
            Thread.sleep(points[index++].sleepMillis)
            publishNextCoordinate()
        }
    }
}
package com.balsdon.adapterexample.adapters

interface GPSTracker {
    fun subscribeToUpdates(onCoordinateReceived: (x: Float, y: Float) -> Unit)
    fun unsubscribe()
}
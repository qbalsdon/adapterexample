package com.balsdon.adapterexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.balsdon.adapterexample.adapters.GPSTracker
import com.balsdon.adapterexample.adapters.MapMarkerAdapter
import com.balsdon.adapterexample.gps.StaticCoroutineGPSTracker
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private val gpsTracker: GPSTracker = StaticCoroutineGPSTracker(MainScope())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gpsTracker.subscribeToUpdates(::onCoordinateReceived)
    }

    private fun onCoordinateReceived(x: Float, y: Float) {
        (mapView as MapMarkerAdapter).placeMarker(x, y)
    }
}
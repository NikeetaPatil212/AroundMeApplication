package com.example.aroundmeapplication.activities

import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.aroundmeapplication.R
import com.example.aroundmeapplication.dataclass.MapData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request


class DistanceActivity:AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    lateinit var currentLocation: Location
    var fusedLocationProviderClient: FusedLocationProviderClient? = null
    val REQUEST_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_distance)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.distanceFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(mMap: GoogleMap) {

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE
            )
            return
        }

        val task = fusedLocationProviderClient!!.lastLocation
        task.addOnSuccessListener { location ->
            if (location != null) {
                currentLocation = location
                val supportMapFragment =
                    (supportFragmentManager.findFragmentById(R.id.distanceFragment) as SupportMapFragment?)
                supportMapFragment!!.getMapAsync {
                    this.mMap = it
                    val currentLocation =
                        LatLng(currentLocation!!.latitude, currentLocation!!.longitude)

                    val lat = intent.getDoubleExtra("lat", 0.00)
                    val long = intent.getDoubleExtra("long", 0.00)
                    mMap.addMarker(MarkerOptions().position(currentLocation))
                    val destinationLocation = LatLng(
                        lat,
                        long
                    )
                    mMap.addMarker(MarkerOptions().position(destinationLocation))
                    val urll = getDirectionsURL(currentLocation, destinationLocation)
                    GetDirection(urll).execute()
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 14F))
                }
            }
        }
    }





    fun getDirectionsURL(origin: LatLng, dest: LatLng) : String{

        return "https://maps.googleapis.com/maps/api/directions/json?origin=$origin&destination=$dest&key=AIzaSyCp3h3MhleoIOx57JOwaq4nqXfhZ2oJsRA"


    }

    fun decodePolyline(encode:String): List<LatLng>{
        var poly = ArrayList<LatLng>()
        var index = 0
        val len = encode.length
        var lat = 0
        var lng = 0

        while (index<len){
            var b:Int
            var shift= 0
            var result = 0

            do{
                b = encode[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)

                shift += 5

            }while (b >= 0x20)

            val dlat = if (result and 1 != 0) (result shr 1) else result shr 1
            lat += dlat

            shift = 0
            result = 0

            do {
                b = encode[index++].toInt() -63
                result = result or (b and 0x1f shl shift)
                shift += 5
            }while (b >= 0x20)

            val dlng = if (result and 1 != 0 ) (result shr 1).inv() else result shr 1
            lng += dlng

            val latlng = LatLng((lat.toDouble() / 1E5),(lng.toDouble() / 1E5))

            poly.add(latlng)

        }
        return  poly
    }

    private inner class GetDirection(val url:String) : AsyncTask<Void, Void, List<List<LatLng>>>(){
        override fun doInBackground(vararg params: Void?): List<List<LatLng>> {

            val client = OkHttpClient()
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            val data = response.body?.string()

            Log.d("GoogleMap","data : $data")

            val result = ArrayList<List<LatLng>>()
            try {
                val respObj = Gson().fromJson(data, MapData::class.java)
                val path = ArrayList<LatLng>()

                for (i in 0..(respObj.routes[0].legs[0].steps.size-1)){
                    path.addAll(decodePolyline(respObj.routes[0].legs[0].steps[i].polyline.points))
                }

                result.add(path)
            }catch (e:Exception){
                e.printStackTrace()
            }

            return result

        }


        override fun onPostExecute(result: List<List<LatLng>>) {
            val lineOption = PolylineOptions()
            for (i in result?.indices!!){
                lineOption.addAll(result[i])
                lineOption.width(10f)
                lineOption.color(Color.BLUE)
                lineOption.geodesic(true)


            }
            mMap.addPolyline(lineOption)
        }
    }
}
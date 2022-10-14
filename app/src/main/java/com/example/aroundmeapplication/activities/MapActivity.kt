package com.example.aroundmeapplication.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.aroundmeapplication.R
import com.example.aroundmeapplication.dataclass.AppConstant
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_map.*

class MapActivity : AppCompatActivity() {

    lateinit var mapFragment: SupportMapFragment
    lateinit var googleMap: GoogleMap

    var fusedLocationProviderClient : FusedLocationProviderClient? = null
    val REQUEST_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        fetchLocation()
        mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(OnMapReadyCallback{
            googleMap = it

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this, Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return@OnMapReadyCallback
            }
            googleMap.isMyLocationEnabled=true

            val latitude = intent.getDoubleExtra(AppConstant.LATITUDE,0.00)
            val longitude  = intent.getDoubleExtra(AppConstant.LONGITUDE,0.00)
            val name = intent.getStringExtra(AppConstant.NAME)
            val address = intent.getStringExtra(AppConstant.ADDRESS)
            val bundle = intent.extras
            if(bundle!=null){
                val imageview = bundle.getInt(AppConstant.IMAGE)
                ivImage.setImageResource(imageview)
            }

            tvItemName.setText(name)
            tvDescription.setText(address)

            val location1 = LatLng(latitude, longitude)
            googleMap.addMarker(MarkerOptions().position(location1).title(name))
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location1,70f))

            val ivDistanceLocation = findViewById<ImageView>(R.id.ivDistanceLocation)
            ivDistanceLocation.setOnClickListener {
                val intent = Intent(this, DistanceActivity::class.java)
                intent.putExtra("lat", latitude)
                intent.putExtra("long", longitude)
                startActivity(intent)
            }
        })
    }

    private fun fetchLocation() {
        //Helper for accessing features in Activity
        if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),REQUEST_CODE)
            return
        }

    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            REQUEST_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    fetchLocation()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
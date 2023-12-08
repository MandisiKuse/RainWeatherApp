package com.example.rainweatherapp.ui.mapActivity

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.rainweatherapp.R
import com.example.rainweatherapp.ui.weatherActivity.WeatherActivity
import com.example.rainweatherapp.viewmodels.MapActivityViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.MAP_TYPE_HYBRID
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    lateinit var  viewModel: MapActivityViewModel
    lateinit var location : String
    lateinit var googleMap: GoogleMap


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel= ViewModelProviders.of(this).get(MapActivityViewModel::class.java)
        initObservers()
        val mapFragment=supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)


        edt_location.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                location=p0.toString()
                var addresslist: List<Address>
                val  geocoder= Geocoder(this@MapActivity)

                if(!location.isNullOrEmpty()){

                    try {
                        addresslist=geocoder.getFromLocationName(location,1)
                        if (addresslist.isNotEmpty()){

                            val latLng= LatLng(addresslist[0].latitude,addresslist[0].longitude)
                            googleMap.addMarker(MarkerOptions().position(latLng).title(addresslist[0].getAddressLine(0)))
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10f))
                            btnGetWeather.isEnabled=true
                            fab_save_location.visibility=View.VISIBLE

                        }

                    }catch (e : IOException)
                    {
                        //Dialog nomatch
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                btnGetWeather.isEnabled=false
                fab_save_location.visibility=View.INVISIBLE
                return false
            }

        })

        btnGetWeather.setOnClickListener {
            viewModel.refresh(location)
        }
    }

    override fun onMapReady(map: GoogleMap?) {
        googleMap= map!!
        googleMap.mapType= MAP_TYPE_HYBRID
    }



    private fun initObservers()
    {
    viewModel.apiRootObject.observe(this,
    Observer { root->

        val intent = Intent(this@MapActivity, WeatherActivity::class.java)
        intent.putExtra("WeatherData",root)
        startActivity(intent)

    })

        viewModel.networkError.observe(this, Observer { errorloading->
            if (errorloading){
                btnGetWeather.isEnabled=false;
                fab_save_location.visibility=View.INVISIBLE
                val builder = AlertDialog.Builder(this)
                val alertDialog = builder.create()
                alertDialog.setMessage("Weather not available for this location")
                alertDialog.show()
            }
        })

    }

    }







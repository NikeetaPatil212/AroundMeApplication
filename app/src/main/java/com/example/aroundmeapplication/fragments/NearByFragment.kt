package com.example.aroundmeapplication.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aroundmeapplication.R
import com.example.aroundmeapplication.activities.MapActivity
import com.example.aroundmeapplication.adapter.RestaurantAdapter
import com.example.aroundmeapplication.database.RestaurantDatabase
import com.example.aroundmeapplication.dataclass.AppConstant
import com.example.aroundmeapplication.dataclass.RestaurantData
import com.example.aroundmeapplication.factory.RestaurantFactory
import com.example.aroundmeapplication.repository.RestaurantRepository
import com.example.aroundmeapplication.viewmodel.RestaurantViewModel
import kotlinx.android.synthetic.main.fragment_near_by.*

class NearByFragment : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_near_by, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list: ArrayList<RestaurantData> = ArrayList()
        add(list)

        var restaurantAdapter = RestaurantAdapter(list)
        nearByRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = restaurantAdapter

            val repository = RestaurantRepository(RestaurantDatabase(requireContext()))
            val viewModelProviderFactory = RestaurantFactory(repository)
            val viewModel: RestaurantViewModel =
                ViewModelProvider(requireActivity(), viewModelProviderFactory).get(
                    RestaurantViewModel::class.java
                )

            restaurantAdapter.setOnItemClickListener {
                viewModel.insertData(it)
            }


            restaurantAdapter.ItemClickListener(object : RestaurantAdapter.OnItemClickListener {
                override fun onItemclick(position: Int, restaurantData: RestaurantData) {
                    val intent = Intent(context, MapActivity::class.java)
                    intent.putExtra(AppConstant.LATITUDE, restaurantData.latitide)
                    intent.putExtra(AppConstant.LONGITUDE, restaurantData.longitude)
                    intent.putExtra(AppConstant.NAME, restaurantData.Name)
                    intent.putExtra(AppConstant.IMAGE, restaurantData.image)
                    intent.putExtra(AppConstant.ADDRESS, restaurantData.address)
                    startActivity(intent)

                }
            })
        }
    }
}
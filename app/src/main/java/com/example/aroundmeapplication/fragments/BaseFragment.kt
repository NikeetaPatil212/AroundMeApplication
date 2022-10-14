package com.example.aroundmeapplication.fragments

import androidx.fragment.app.Fragment
import com.example.aroundmeapplication.R
import com.example.aroundmeapplication.dataclass.RestaurantData

open class BaseFragment : Fragment() {
    fun add(list: ArrayList<RestaurantData>) {
        list.add(
            RestaurantData(
                1,
                getString(R.string.restaurant_one),
                getString(R.string.address_one),
                R.drawable.restaurant1,
                19.1138,
                72.8646
            )
        )
        list.add(
            RestaurantData(
                2,
                getString(R.string.restaurant_two),
                getString(R.string.address_two),
                R.drawable.restaurant2,
                18.9217,
                72.8332
            )
        )
        list.add(
            RestaurantData(
                3,
                getString(R.string.restaurant_three),
                getString(R.string.address_three),
                R.drawable.restaurant3,
                19.0777,
                72.8512
            )
        )
        list.add(
            RestaurantData(
                4,
                getString(R.string.restaurant_four),
                getString(R.string.address_four),
                R.drawable.restaurant4,
                19.0957,
                72.8538
            )
        )
        list.add(
            RestaurantData(
                5,
                getString(R.string.restaurant_five),
                getString(R.string.address_five),
                R.drawable.restaurant5,
                18.9242,
                72.8331
            )
        )
    }
}
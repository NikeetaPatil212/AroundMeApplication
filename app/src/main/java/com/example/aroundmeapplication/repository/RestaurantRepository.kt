package com.example.aroundmeapplication.repository

import com.example.aroundmeapplication.database.RestaurantDatabase
import com.example.aroundmeapplication.dataclass.RestaurantData

class RestaurantRepository(val restaurantDatabase: RestaurantDatabase) {

    fun getLocation() = restaurantDatabase.restaurantDao().getData()
    suspend fun dataInsert(data : RestaurantData)= restaurantDatabase.restaurantDao().addRestaurant(data)
    suspend fun deleteData(data: RestaurantData)= restaurantDatabase.restaurantDao().deleteRestaurant(data)
}
package com.example.aroundmeapplication.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.aroundmeapplication.dataclass.RestaurantData

@Dao
interface RestaurantDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)

    suspend fun addRestaurant(data: RestaurantData)

    @Query("select * From  restaurantData")
    fun getData(): LiveData<List<RestaurantData>>

    @Delete
    suspend fun deleteRestaurant(data:RestaurantData)
}
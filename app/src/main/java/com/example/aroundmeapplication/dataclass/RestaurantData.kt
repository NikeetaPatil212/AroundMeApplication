package com.example.aroundmeapplication.dataclass

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "restaurantData")
class RestaurantData(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    val Name: String,
    val address: String,
    val image: Int,
    val latitide : Double,
    val longitude :Double )

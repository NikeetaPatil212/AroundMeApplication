package com.example.aroundmeapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aroundmeapplication.dataclass.RestaurantData
import com.example.aroundmeapplication.repository.RestaurantRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RestaurantViewModel(val repository: RestaurantRepository): ViewModel() {

    fun getData() =
        repository.getLocation()

    fun insertData(data: RestaurantData) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.dataInsert(data)
        }
    fun deleteRestaurantData(data: RestaurantData) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteData(data)
        }
}
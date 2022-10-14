package com.example.aroundmeapplication.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.aroundmeapplication.repository.RestaurantRepository
import com.example.aroundmeapplication.viewmodel.RestaurantViewModel

class RestaurantFactory(val repository: RestaurantRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RestaurantViewModel(repository) as T
    }
}
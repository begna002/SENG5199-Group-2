package com.example.groupassignment2.profile4Content

import retrofit2.Call
import retrofit2.http.GET
interface RestaurantApiService {
    @GET("restaurants.json")
    fun getRestaurants(): Call<List<Restaurant>>
}
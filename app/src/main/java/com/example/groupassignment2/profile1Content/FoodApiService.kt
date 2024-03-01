package com.example.groupassignment2.profile1Content

import android.util.Log
import com.example.groupassignment2.data.FoodResponse
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

suspend fun getFoodData(apiKey: String, query: String): FoodResponse {
    try {
        val url = "https://api.spoonacular.com/"
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ApiService::class.java)

        return service.getFood(
            apiKey = apiKey,
            query = query,
            number = 20,
            fillIngredients = true,
            addRecipeInformation = true
        )
    } catch (ex: HttpException) {
        Log.e("Exception", "Error calling Spoonacular API: $ex")

        if (ex.code() == 402) {
            return FoodResponse(emptyList(), true, "Daily query limit of 150 reached")
        }

    }
    return FoodResponse()
}

interface ApiService {
    @GET("recipes/complexSearch/")
    suspend fun getFood(
        @Query("apiKey") apiKey: String,
        @Query("query") query: String,
        @Query("number") number: Number,
        @Query("fillIngredients") fillIngredients: Boolean,
        @Query("addRecipeInformation") addRecipeInformation: Boolean
    ): FoodResponse
}
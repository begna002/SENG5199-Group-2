package com.example.groupassignment2.profile4Content

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestaurantsViewModel(private val stateHandle: SavedStateHandle) :
    ViewModel() {
    private var restInterface: RestaurantApiService

    val state = mutableStateOf(emptyList<Restaurant>())

    private lateinit var restaurantsCall:
            Call<List<Restaurant>>

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create()
            )
            .baseUrl("https://twincitiesrestaurants-266ba-default-rtdb.firebaseio.com/"
            )
            .build()
        restInterface = retrofit.create(RestaurantApiService::class.java)
        getRestaurants()
    }
    // Making async call with the enqueue
    private fun getRestaurants() {
        restaurantsCall = restInterface.getRestaurants()
        restaurantsCall.enqueue(
            object : Callback<List<Restaurant>> {
                override fun onResponse(
                    call: Call<List<Restaurant>>,
                    response: Response<List<Restaurant>>
                ) {
                    response.body()?.let { restaurants ->
                        state.value =
                            restaurants.restoreSelections()
                    }
                }
                override fun onFailure(
                    call: Call<List<Restaurant>>, t: Throwable
                ) {
                    t.printStackTrace()
                }
            })
    }


    fun toggleFavorite(id: Int) {
        val restaurants = state.value.toMutableList()
        val itemIndex =
            restaurants.indexOfFirst { it.id == id }
        val item = restaurants[itemIndex]
        restaurants[itemIndex] =
            item.copy(isFavorite = !item.isFavorite)
        storeSelection(restaurants[itemIndex])
        state.value = restaurants
    }

    private fun storeSelection(item: Restaurant) {
        val savedToggled = stateHandle
            .get<List<Int>?>(FAVORITES)
            .orEmpty().toMutableList()
        if (item.isFavorite) savedToggled.add(item.id)
        else savedToggled.remove(item.id)
        stateHandle[FAVORITES] = savedToggled
    }
    companion object {
        const val FAVORITES = "favorites"
    }
    private fun List<Restaurant>.restoreSelections():
            List<Restaurant> {
        stateHandle.get<List<Int>?>(FAVORITES)?.let {
                selectedIds ->
            val restaurantsMap = this.associateBy { it.id }
            selectedIds.forEach { id ->
                restaurantsMap[id]?.isFavorite = true
            }
            return restaurantsMap.values.toList()
        }
        return this
    }


    //Clean up at the end of viewModel lifecycle
    override fun onCleared() {
        super.onCleared()
        restaurantsCall.cancel()
    }

}
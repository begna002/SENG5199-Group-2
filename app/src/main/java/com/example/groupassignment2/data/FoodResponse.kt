package com.example.groupassignment2.data

import com.google.gson.annotations.SerializedName

data class FoodResponse(
    @SerializedName("results")
    var results: List<FoodItem>,

    var error: Boolean,

    var message: String?

) {
    constructor() : this(
        emptyList(), false, ""
    )
}
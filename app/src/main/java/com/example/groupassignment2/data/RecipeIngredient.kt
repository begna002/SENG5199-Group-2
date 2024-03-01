package com.example.groupassignment2.data

import com.google.gson.annotations.SerializedName

data class RecipeIngredient(
    @SerializedName("id")
    val id: Long,

    @SerializedName("image")
    val imageUrl: String,

    @SerializedName("original")
    val name: String,
)

package com.example.groupassignment2.profile4Content

import com.google.gson.annotations.SerializedName

data class Restaurant(
    @SerializedName("r_id")
    val id: Int,
    @SerializedName("r_title")
    val title: String,
    @SerializedName("r_description")
    val description: String,
    @SerializedName("uri")
    val uri: String,
    var isFavorite: Boolean = false
)

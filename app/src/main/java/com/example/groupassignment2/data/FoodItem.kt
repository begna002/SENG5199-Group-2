package com.example.groupassignment2.data

import com.google.gson.annotations.SerializedName

data class FoodItem(
    @SerializedName("id")
    val id: Long,

    @SerializedName("title")
    val title: String,

    @SerializedName("image")
    val imageUrl: String,

    @SerializedName("missedIngredients")
    var ingredients: List<RecipeIngredient>,

    @SerializedName("analyzedInstructions")
    var instructions:  List<RecipeInstructions>,

    @SerializedName("vegetarian")
    var isVegetarian: Boolean,

    @SerializedName("vegan")
    var isVegan: Boolean,

    @SerializedName("readyInMinutes")
    var readyInMinutes: Int
) {
    constructor() : this(
        0, "", "", emptyList(), emptyList(), false, false, 0
    )

    fun getDiet() : String {
        var diet = "Diet: "

        if (this.isVegetarian) {
            diet += "Vegetarian"
        }

        if (this.isVegan) {
            diet += ", Vegan"
        }

        return diet
    }

    fun getReadyIn() : String {
        if (this.readyInMinutes < 60) {
            return "Ready in ${this.readyInMinutes} min"
        }
        val hours: Int = this.readyInMinutes / 60
        val minutes: Int = this.readyInMinutes % 60

        var readyIn = "Ready in $hours hr"

        if (minutes > 0) {
            readyIn += " $minutes min"
        }

        return readyIn
    }
}
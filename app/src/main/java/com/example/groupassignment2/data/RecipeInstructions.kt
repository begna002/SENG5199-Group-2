package com.example.groupassignment2.data

import com.google.gson.annotations.SerializedName

data class RecipeInstructions (
    @SerializedName("steps")
    val steps: List<RecipeStep>,

) {
    constructor() : this(
        emptyList()
    )
}

data class RecipeStep (
    @SerializedName("number")
    val number: Number,

    @SerializedName("step")
    val step: String

    ) {
    constructor() : this(
        0, ""
    )
}

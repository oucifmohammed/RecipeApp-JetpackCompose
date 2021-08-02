package com.example.recipeapp.network.responses

import com.example.recipeapp.network.model.RecipeDto
import com.google.gson.annotations.SerializedName

data class RecipeSearchResponse(

    @SerializedName("count")
    val count: Int,

    @SerializedName("results")
    val recipes: List<RecipeDto>
)
package com.example.recipeapp.Network.Recipe

import com.google.gson.annotations.SerializedName

data class RecipeRequest(
    @SerializedName("title")
    val name: String,
    @SerializedName("description")
    val description: String,
//    @SerializedName("url")
//    val url: String
)
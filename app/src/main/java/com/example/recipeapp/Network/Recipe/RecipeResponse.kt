package com.example.recipeapp.Network.Recipe

import com.google.gson.annotations.SerializedName

data class RecipeResponse (
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val name: String,
    @SerializedName("description")
    val description: String
)
package com.example.recipeapp.Network.Offer

import com.google.gson.annotations.SerializedName

data class OfferResponse (
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val name: String,
    @SerializedName("description")
    val description: String
)
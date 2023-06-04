package com.example.recipeapp.Network.MyResponse

import com.google.gson.annotations.SerializedName

open class MyResponse (
    @SerializedName("code")
    val code: Int = 0,
    @SerializedName("status")
    val status: String = "",
    @SerializedName("message")
    val message: String = ""
) {

}
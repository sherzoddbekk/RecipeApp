package com.example.recipeapp.Network.MyResponse

import com.google.gson.annotations.SerializedName

class MyListResponse<T> (@SerializedName("data")
                         val data: List<T>?) : MyResponse()
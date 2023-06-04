package com.example.recipeapp.Network.MyResponse

import com.google.gson.annotations.SerializedName

class MyItemResponse<T> (@SerializedName("data")
                         val data: T?) : MyResponse()
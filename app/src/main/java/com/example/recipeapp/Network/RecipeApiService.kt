package com.example.recipeapp.Network

import com.example.recipeapp.Network.MyResponse.MyItemResponse
import com.example.recipeapp.Network.MyResponse.MyListResponse
import com.example.recipeapp.Network.MyResponse.MyResponse
import com.example.recipeapp.Network.Recipe.RecipeRequest
import com.example.recipeapp.Network.Recipe.RecipeResponse
import retrofit2.http.*

interface RecipeApiService {
    @GET("records/all")
    suspend fun getAllOffers(
        @Query("student_id") student_id: String
    ): MyListResponse<RecipeResponse>

    @GET("records/{record_id}")
    suspend fun getOneRecipeById(
        @Path("record_id") record_id: String,
        @Query("student_id") student_id: String
    ): MyItemResponse<RecipeResponse>

    @POST("records")
    suspend fun insertNewRecipe(
        @Query("student_id") student_id: String,
        @Body offerRequest: RecipeRequest
    ): MyResponse

    @PUT("records/{record_id}")
    suspend fun updateOneRecipeById(
        @Path("record_id") record_id: String,
        @Query("student_id") student_id: String,
        @Body offerRequest: RecipeRequest
    ): MyResponse

    @DELETE("records/{record_id}")
    suspend fun deleteOneRecipeById(
        @Path("record_id") record_id: String,
        @Query("student_id") student_id: String
    ): MyResponse

    @DELETE("records/all")
    suspend fun deleteAllRecipe(
        @Query("student_id") student_id: String
    ): MyResponse
}
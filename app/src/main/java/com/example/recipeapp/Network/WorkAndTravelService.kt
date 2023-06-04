package com.example.recipeapp.Network

import com.example.recipeapp.Network.MyResponse.MyItemResponse
import com.example.recipeapp.Network.MyResponse.MyListResponse
import com.example.recipeapp.Network.MyResponse.MyResponse
import com.example.recipeapp.Network.Offer.OfferRequest
import com.example.recipeapp.Network.Offer.OfferResponse
import retrofit2.http.*

interface WorkAndTravelService {
    @GET("records/all")
    suspend fun getAllOffers(
        @Query("student_id") student_id: String
    ): MyListResponse<OfferResponse>

    @GET("records/{record_id}")
    suspend fun getOneOfferById(
        @Path("record_id") record_id: String,
        @Query("student_id") student_id: String
    ): MyItemResponse<OfferResponse>

    @POST("records")
    suspend fun insertNewOffer(
        @Query("student_id") student_id: String,
        @Body offerRequest: OfferRequest
    ): MyResponse

    @PUT("records/{record_id}")
    suspend fun updateOneOfferById(
        @Path("record_id") record_id: String,
        @Query("student_id") student_id: String,
        @Body offerRequest: OfferRequest
    ): MyResponse

    @DELETE("records/{record_id}")
    suspend fun deleteOneOfferById(
        @Path("record_id") record_id: String,
        @Query("student_id") student_id: String
    ): MyResponse

    @DELETE("records/all")
    suspend fun deleteAllOffers(
        @Query("student_id") student_id: String
    ): MyResponse
}
package com.example.recipeapp.DetailedView

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.Network.MyResponse.MyItemResponse
import com.example.recipeapp.Network.MyResponse.MyResponse
import com.example.recipeapp.Network.Offer.OfferRequest
import com.example.recipeapp.Network.Offer.OfferResponse
import com.example.recipeapp.Network.RetrofitInstance
import com.example.recipeapp.Offer.Offer
import com.example.workandtravelapp.Utils.Constants
import kotlinx.coroutines.launch
import java.lang.Exception

class DetailedViewModel(offerId: String) : ViewModel() {

    val offerLiveData: MutableLiveData<Offer> by lazy {
        MutableLiveData<Offer>()
    }

    init {
        getOfferByIdFromRemoteDb(offerId)
    }

    private fun getOfferByIdFromRemoteDb(offerId: String) {
        viewModelScope.launch {
            try {
                val response: MyItemResponse<OfferResponse> =
                    RetrofitInstance.offerService.getOneOfferById(offerId, Constants.STUDENT_ID)
                val offerFromResponse = response.data

                if (offerFromResponse != null) {
                    offerLiveData.value = Offer(
                        offerFromResponse.id,
                        offerFromResponse.name,
                        offerFromResponse.description
                    )
                }


            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun editOfferById(offerId: String, offerRequest: OfferRequest) {
        viewModelScope.launch {
            try {

                val response: MyResponse = RetrofitInstance.offerService.updateOneOfferById(
                    offerId,
                    Constants.STUDENT_ID,
                    offerRequest
                )

                Log.d("Update_response", response.toString())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun deleteOneOfferById(offerId: String) {
        viewModelScope.launch {
            try {

                val response: MyResponse = RetrofitInstance.offerService.deleteOneOfferById(
                    offerId,
                    Constants.STUDENT_ID
                )

                Log.d("Delete_response", response.toString())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
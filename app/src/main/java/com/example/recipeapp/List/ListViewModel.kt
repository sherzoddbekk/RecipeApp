package com.example.recipeapp.List

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.Network.MyResponse.MyListResponse
import com.example.recipeapp.Network.Offer.OfferResponse
import com.example.recipeapp.Network.RetrofitInstance
import com.example.recipeapp.Offer.Offer
import com.example.recipeapp.Utils.Constants
import kotlinx.coroutines.launch
import java.lang.Exception

class ListViewModel : ViewModel() {

    val offersLiveData: MutableLiveData<List<Offer>> by lazy {
        MutableLiveData<List<Offer>>()
    }

    init {
        getListOfOffersFromRemoteDb()
    }

    fun getListOfOffersFromRemoteDb() {
        viewModelScope.launch {
            try {
                val response: MyListResponse<OfferResponse> =
                    RetrofitInstance.offerService.getAllOffers(Constants.STUDENT_ID)
                val offersFromResponse = response.data

                if (offersFromResponse != null) {
                    val myOffers = mutableListOf<Offer>()

                    for (offerFromResponse in offersFromResponse) {
                        myOffers.add(
                            Offer(
                                offerFromResponse.id,
                                "Title: " + offerFromResponse.name,
                                "Description: " + offerFromResponse.description
                            )
                        )
                    }
                    offersLiveData.value = myOffers
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
package com.example.recipeapp.addNew

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.Network.MyResponse.MyResponse
import com.example.recipeapp.Network.Offer.OfferRequest
import com.example.recipeapp.Network.RetrofitInstance
import com.example.workandtravelapp.Utils.Constants
import kotlinx.coroutines.launch

class AddNewViewModel : ViewModel() {
    val offerInsertResponse: MutableLiveData<MyResponse> by lazy {
        MutableLiveData<MyResponse>()
    }

    fun saveNewOfferToRemoteDb(offer: OfferRequest) {

        viewModelScope.launch {
            try {

                val response: MyResponse = RetrofitInstance.offerService.insertNewOffer(
                    Constants.STUDENT_ID,
                    offer
                )

                offerInsertResponse.value = response

                Log.d("Update_response", response.toString())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }
}
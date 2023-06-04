package com.example.recipeapp.DetailedView

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.Network.MyResponse.MyItemResponse
import com.example.recipeapp.Network.MyResponse.MyResponse
import com.example.recipeapp.Network.Recipe.RecipeRequest
import com.example.recipeapp.Network.Recipe.RecipeResponse
import com.example.recipeapp.Network.RetrofitInstance
import com.example.recipeapp.Recipe.Recipe
import com.example.recipeapp.Utils.Constants
import kotlinx.coroutines.launch
import java.lang.Exception

class DetailedViewModel(offerId: String) : ViewModel() {

    val recipeLiveData: MutableLiveData<Recipe> by lazy {
        MutableLiveData<Recipe>()
    }

    val recipeData: MutableLiveData<MyResponse> by lazy {
        MutableLiveData<MyResponse>()
    }

    val editRecipeData: MutableLiveData<MyResponse> by lazy {
        MutableLiveData<MyResponse>()
    }




    init {
        getOfferByIdFromRemoteDb(offerId)
    }

    private fun getOfferByIdFromRemoteDb(offerId: String) {
        viewModelScope.launch {
            try {
                val response: MyItemResponse<RecipeResponse> =
                    RetrofitInstance.offerService.getOneRecipeById(offerId, Constants.STUDENT_ID)
                val offerFromResponse = response.data

                if (offerFromResponse != null) {
                    recipeLiveData.value = Recipe(
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

    fun editOfferById(offerId: String, offerRequest: RecipeRequest) {
        viewModelScope.launch {
            try {

                val response: MyResponse = RetrofitInstance.offerService.updateOneRecipeById(
                    offerId,
                    Constants.STUDENT_ID,
                    offerRequest
                )

                if (response != null){
                    editRecipeData.value = MyResponse(
                        code = response.code,
                        status = response.status,
                        message = response.message
                    )
                }

                Log.d("Update_response", response.toString())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun deleteOneRecipeById(offerId: String) {
        viewModelScope.launch {
            try {

                val response: MyResponse = RetrofitInstance.offerService.deleteOneRecipeById(
                    offerId,
                    Constants.STUDENT_ID
                )

                if (response != null) {
                    recipeData.value = MyResponse(
                        response.code,
                        response.status,
                        response.message
                    )
                }

                Log.d("Delete_response", response.toString())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
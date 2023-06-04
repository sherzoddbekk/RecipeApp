package com.example.recipeapp.List

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.Network.MyResponse.MyListResponse
import com.example.recipeapp.Network.Recipe.RecipeResponse
import com.example.recipeapp.Network.RetrofitInstance
import com.example.recipeapp.Recipe.Recipe
import com.example.recipeapp.Utils.Constants
import kotlinx.coroutines.launch
import java.lang.Exception

class ListViewModel : ViewModel() {

    val offersLiveData: MutableLiveData<List<Recipe>> by lazy {
        MutableLiveData<List<Recipe>>()
    }

    init {
        getListOfOffersFromRemoteDb()
    }

    fun getListOfOffersFromRemoteDb() {
        viewModelScope.launch {
            try {
                val response: MyListResponse<RecipeResponse> =
                    RetrofitInstance.offerService.getAllOffers(Constants.STUDENT_ID)
                val offersFromResponse = response.data

                if (offersFromResponse != null) {
                    val myOffers = mutableListOf<Recipe>()

                    for (offerFromResponse in offersFromResponse) {
                        myOffers.add(
                            Recipe(
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
package com.example.recipeapp.addNew

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.Network.MyResponse.MyResponse
import com.example.recipeapp.Network.Recipe.RecipeRequest
import com.example.recipeapp.Network.RetrofitInstance
import com.example.recipeapp.Utils.Constants
import kotlinx.coroutines.launch

class AddNewViewModel : ViewModel() {
    val recipeInsertResponse: MutableLiveData<MyResponse> by lazy {
        MutableLiveData<MyResponse>()
    }

    fun saveNewRecipeToRemoteDb(offer: RecipeRequest) {

        viewModelScope.launch {
            try {

                val response: MyResponse = RetrofitInstance.offerService.insertNewRecipe(
                    Constants.STUDENT_ID,
                    offer
                )

                recipeInsertResponse.value = response

                Log.d("Update_response", response.toString())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }
}
package com.develop.recipes.ui.main.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.develop.recipes.data.model.RecipeModel
import com.develop.recipes.data.repository.RecipeModelRepository

class RecipeViewModel(private val context: Context) : ViewModel() {


    var recipeLiveData: MutableLiveData<List<RecipeModel>>? = null


    fun getData() : MutableLiveData<List<RecipeModel>>? {
        recipeLiveData = RecipeModelRepository.getServicesApiCall()
        return recipeLiveData
    }

//    public fun getData() {
//        val call: Call<List<RecipeModel>> = ApiClient.getClient.getRecipes()
//        call.enqueue(object : Callback<List<RecipeModel>> {
//
//            override fun onResponse(
//                call: Call<List<RecipeModel>>?, response: Response<List<RecipeModel>>?
//            ) {
//                _recipeLiveData?.value = response?.body()
//            }
//
//            override fun onFailure(call: Call<List<RecipeModel>>?, t: Throwable?) {
//
//            }
//
//        })
//    }

}
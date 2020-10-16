package com.develop.recipes.data.repository

import androidx.lifecycle.MutableLiveData
import com.develop.recipes.data.api.ApiClient
import com.develop.recipes.data.model.RecipeModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object RecipeModelRepository {

    val mutableLiveData = MutableLiveData<List<RecipeModel>>()

    fun getServicesApiCall(): MutableLiveData<List<RecipeModel>> {

        val call: Call<List<RecipeModel>> = ApiClient.apiService.getRecipes()
        call.enqueue(object : Callback<List<RecipeModel>> {

            override fun onResponse(
                call: Call<List<RecipeModel>>?, response: Response<List<RecipeModel>>?
            ) {
                mutableLiveData?.value = response?.body()
            }

            override fun onFailure(call: Call<List<RecipeModel>>?, t: Throwable?) {

            }

        })

        return mutableLiveData
    }

}